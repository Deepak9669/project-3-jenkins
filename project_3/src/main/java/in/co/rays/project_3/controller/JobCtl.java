package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.JobDTO;
import in.co.rays.project_3.model.JobModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(urlPatterns = { "/ctl/JobCtl" })
public class JobCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(JobCtl.class);

	// ------------------------------- PRELOAD --------------------------------
	@Override
	protected void preload(HttpServletRequest request) {

		HashMap<String, String> statusMap = new HashMap<>();
		statusMap.put("Active", "Active");
		statusMap.put("Inactive", "Inactive");
		statusMap.put("Pending", "Pending");
		statusMap.put("Closed", "Closed");

		request.setAttribute("statusMap", statusMap);
	}

	// ------------------------------- VALIDATE --------------------------------
	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("title"))) {
			request.setAttribute("title",
					PropertyReader.getValue("error.require", "Title"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("experience"))) {
			request.setAttribute("experience",
					PropertyReader.getValue("error.require", "Experience"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("status"))) {
			request.setAttribute("status",
					PropertyReader.getValue("error.require", "Status"));
			pass = false;
		}

		return pass;
	}

	// ------------------------------- POPULATE DTO --------------------------------
	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		JobDTO dto = new JobDTO();

		dto.setTitle(DataUtility.getString(request.getParameter("title")));
		dto.setDob(DataUtility.getDate(request.getParameter("dob")));
		dto.setExperience(DataUtility.getString(request.getParameter("experience")));
		dto.setStatus(DataUtility.getString(request.getParameter("status")));

		long id = DataUtility.getLong(request.getParameter("id"));
		if (id > 0) {
			dto.setId(id);
		}

		return dto;
	}

	// ------------------------------- DO GET --------------------------------
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.debug("JobCtl doGet Started");

		JobModelInt model = ModelFactory.getInstance().getJobModel();
		long id = DataUtility.getLong(request.getParameter("id"));

		if (id > 0) {
			try {
				JobDTO dto = model.findByPk(id);
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

	// ------------------------------- DO POST --------------------------------
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.debug("JobCtl doPost Started");

		String op = DataUtility.getString(request.getParameter("operation"));
		JobModelInt model = ModelFactory.getInstance().getJobModel();
		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			JobDTO dto = (JobDTO) populateDTO(request);

			try {

				if (id > 0) {
					model.update(dto);
					ServletUtility.setSuccessMessage("Data Updated Successfully", request);
				} else {
					model.add(dto);
					ServletUtility.setSuccessMessage("Data Saved Successfully", request);
				}

				ServletUtility.setDto(dto, request);

			} catch (Exception e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			JobDTO dto = (JobDTO) populateDTO(request);

			try {
				model.delete(dto);
			} catch (Exception e) {
				e.printStackTrace();
			}

			ServletUtility.redirect(ORSView.JOB_LIST_CTL, request, response);
			return;

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.JOB_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.JOB_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.JOB_VIEW;
	}
}