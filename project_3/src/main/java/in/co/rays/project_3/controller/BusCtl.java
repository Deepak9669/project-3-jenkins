package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.BusDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.BusModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

/**
 * Bus functionality controller to perform add, delete and update operation
 * 
 * @author Deepak Verma
 *
 */
@WebServlet(urlPatterns = { "/ctl/BusCtl" })
public class BusCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(BusCtl.class);

	@Override
	protected void preload(HttpServletRequest request) {

		// ✅ Bus Type Dropdown Map
		HashMap<String, String> busTypeMap = new HashMap<>();
		busTypeMap.put("AC Sleeper", "AC Sleeper");
		busTypeMap.put("Non-AC Sleeper", "Non-AC Sleeper");
		busTypeMap.put("AC Seater", "AC Seater");
		busTypeMap.put("Non-AC Seater", "Non-AC Seater");

		// ✅ City Dropdown Map (Source/Destination)
		HashMap<String, String> cityMap = new HashMap<>();
		cityMap.put("Indore", "Indore");
		cityMap.put("Bhopal", "Bhopal");
		cityMap.put("Ujjain", "Ujjain");
		cityMap.put("Dewas", "Dewas");
		cityMap.put("Delhi", "Delhi");

		// ✅ Request setAttribute
		request.setAttribute("busTypeMap", busTypeMap);
		request.setAttribute("cityMap", cityMap);
	}

	/**
	 * Validate request parameters
	 */
	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("busNumber"))) {
			request.setAttribute("busNumber", PropertyReader.getValue("error.require", "Bus Number"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("busType"))) {
			request.setAttribute("busType", PropertyReader.getValue("error.require", "Bus Type"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("totalSeats"))) {
			request.setAttribute("totalSeats", PropertyReader.getValue("error.require", "Total Seats"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("source"))) {
			request.setAttribute("source", PropertyReader.getValue("error.require", "Source"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("destination"))) {
			request.setAttribute("destination", PropertyReader.getValue("error.require", "Destination"));
			pass = false;
		}

		return pass;
	}

	/**
	 * Populate DTO
	 */
	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		BusDTO dto = new BusDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));

		dto.setBusNumber(DataUtility.getString(request.getParameter("busNumber")));
		dto.setBusType(DataUtility.getString(request.getParameter("busType")));
		dto.setTotalSeats(DataUtility.getString(request.getParameter("totalSeats")));
		dto.setSource(DataUtility.getString(request.getParameter("source")));
		dto.setDestination(DataUtility.getString(request.getParameter("destination")));

		populateBean(dto, request);

		log.debug("BusCtl populateDTO End");
		return dto;
	}

	/**
	 * Display Bus Data on view page
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.debug("BusCtl doGet Started");

		String op = DataUtility.getString(request.getParameter("operation"));
		BusModelInt model = ModelFactory.getInstance().getBusModel();
		long id = DataUtility.getLong(request.getParameter("id"));

		if (id > 0 || op != null) {
			BusDTO dto = null;
			try {
				dto = model.findByPk(id);
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

	/**
	 * Submit Bus Data
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.debug("BusCtl doPost Started");

		String op = DataUtility.getString(request.getParameter("operation"));
		BusModelInt model = ModelFactory.getInstance().getBusModel();
		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			BusDTO dto = (BusDTO) populateDTO(request);

			if (id > 0) {
				try {
					model.update(dto);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ServletUtility.setSuccessMessage("Data is successfully Updated", request);

			} else {
				try {
					model.add(dto);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ServletUtility.setSuccessMessage("Data is successfully saved", request);
			}

			ServletUtility.setDto(dto, request);

		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			BusDTO dto = (BusDTO) populateDTO(request);

			try {
				model.delete(dto);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ServletUtility.redirect(ORSView.BUS_LIST_CTL, request, response);
			return;

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.BUS_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.BUS_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
		log.debug("BusCtl doPost End");
	}

	@Override
	protected String getView() {
		return ORSView.BUS_VIEW;
	}
}
