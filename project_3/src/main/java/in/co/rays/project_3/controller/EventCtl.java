package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.EventDTO;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.EventModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(urlPatterns = { "/ctl/EventCtl" })

public class EventCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(EventCtl.class);

	// ------------------------------- PRELOAD -------------------------------
	@Override
	protected void preload(HttpServletRequest request) {

		HashMap<String, String> eventMap = new HashMap<>();

		eventMap.put("Seminar", "Seminar");
		eventMap.put("Workshop", "Workshop");
		eventMap.put("Conference", "Conference");
		eventMap.put("Webinar", "Webinar");

		request.setAttribute("eventMap", eventMap);
	}

	// ------------------------------- VALIDATE -------------------------------
	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("participentName"))) {
			request.setAttribute("participentName",
					PropertyReader.getValue("error.require", "Participent Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("eventName"))) {
			request.setAttribute("eventName",
					PropertyReader.getValue("error.require", "Event Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("email"))) {
			request.setAttribute("email",
					PropertyReader.getValue("error.require", "Email"));
			pass = false;
		}

		return pass;
	}

	// ------------------------------- POPULATE DTO -------------------------------
	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		EventDTO dto = new EventDTO();

		dto.setParticipentName(DataUtility.getString(request.getParameter("participentName")));
		dto.setEventName(DataUtility.getString(request.getParameter("eventName")));
		dto.setEmail(DataUtility.getString(request.getParameter("email")));
		dto.setRegistrationDate(DataUtility.getDate(request.getParameter("registrationDate")));

		long id = DataUtility.getLong(request.getParameter("id"));
		if (id > 0) {
			dto.setId(id);
		}

		return dto;
	}

	// ------------------------------- DO GET -------------------------------
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.debug("EventCtl doGet Started");

		EventModelInt model = ModelFactory.getInstance().getEventModel();
		long id = DataUtility.getLong(request.getParameter("id"));

		if (id > 0) {
			try {
				EventDTO dto = model.findByPk(id);
				ServletUtility.setDto(dto, request);

			} catch (Exception e) {
				e.printStackTrace();
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}

		ServletUtility.forward(getView(), request, response);
	}

	// ------------------------------- DO POST -------------------------------
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.debug("EventCtl doPost Started");

		String op = DataUtility.getString(request.getParameter("operation"));
		EventModelInt model = ModelFactory.getInstance().getEventModel();
		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			EventDTO dto = (EventDTO) populateDTO(request);

			try {

				if (id > 0) {
					model.update(dto);
					ServletUtility.setSuccessMessage("Data Updated Successfully", request);

				} else {
					model.add(dto);
					ServletUtility.setSuccessMessage("Data Saved Successfully", request);
				}

				ServletUtility.setDto(dto, request);

			} catch (DuplicateRecordException e) {

				ServletUtility.setDto(dto, request);
				ServletUtility.setErrorMessage("Event Name already exists", request);

			} catch (Exception e) {

				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			EventDTO dto = (EventDTO) populateDTO(request);

			try {
				model.delete(dto);

			} catch (Exception e) {
				e.printStackTrace();
			}

			ServletUtility.redirect(ORSView.EVENT_LIST_CTL, request, response);
			return;

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.EVENT_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.EVENT_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.EVENT_VIEW;
	}
}