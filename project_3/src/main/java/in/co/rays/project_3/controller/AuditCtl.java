package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.AuditDTO;
import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.model.AuditModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(urlPatterns = { "/ctl/AuditCtl" })
public class AuditCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(AuditCtl.class);

	// -------------------------------- PRELOAD --------------------------------
	@Override
	protected void preload(HttpServletRequest request) {

		HashMap<String, String> cityMap = new HashMap<>();
		cityMap.put("Indore", "Indore");
		cityMap.put("Bhopal", "Bhopal");
		cityMap.put("Ujjain", "Ujjain");
		cityMap.put("Dewas", "Dewas");
		cityMap.put("Delhi", "Delhi");

		request.setAttribute("cityMap", cityMap);
	}

	// -------------------------------- VALIDATE --------------------------------
	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("actionBy"))) {
			request.setAttribute("actionBy",
					PropertyReader.getValue("error.require", "Action By"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("actionType"))) {
			request.setAttribute("actionType",
					PropertyReader.getValue("error.require", "Action Type"));
			pass = false;
		}

		return pass;
	}

	// -------------------------------- POPULATE DTO --------------------------------
	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		AuditDTO dto = new AuditDTO();

		dto.setActionBy(DataUtility.getString(request.getParameter("actionBy")));
		dto.setActionType(DataUtility.getString(request.getParameter("actionType")));
		dto.setCreatedDate(DataUtility.getDate(request.getParameter("createdDate")));
		dto.setUpdatedDate(DataUtility.getDate(request.getParameter("updatedDate")));
		dto.setRemarks(DataUtility.getString(request.getParameter("remarks")));

		long id = DataUtility.getLong(request.getParameter("id"));
		if (id > 0) {
			dto.setId(id);
		}

		// ⭐ IMPORTANT: populateBean() intentionally removed
		return dto;
	}

	// -------------------------------- DO GET --------------------------------
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.debug("AuditCtl doGet Started");

		AuditModelInt model = ModelFactory.getInstance().getAuditModel();
		long id = DataUtility.getLong(request.getParameter("id"));

		if (id > 0) {
			try {
				AuditDTO dto = model.findByPk(id);
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

	// -------------------------------- DO POST --------------------------------
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.debug("AuditCtl doPost Started");

		String op = DataUtility.getString(request.getParameter("operation"));
		AuditModelInt model = ModelFactory.getInstance().getAuditModel();
		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			AuditDTO dto = (AuditDTO) populateDTO(request);

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

			AuditDTO dto = (AuditDTO) populateDTO(request);

			try {
				model.delete(dto);
			} catch (Exception e) {
				e.printStackTrace();
			}

			ServletUtility.redirect(ORSView.AUDIT_LIST_CTL, request, response);
			return;

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.AUDIT_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.AUDIT_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.AUDIT_VIEW;
	}
}
