package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.VendorDTO;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.VendorModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(urlPatterns = { "/ctl/VendorCtl" })
public class VendorCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(VendorCtl.class);

	// ------------------------------- PRELOAD -------------------------------
	@Override
	protected void preload(HttpServletRequest request) {

		HashMap<String, String> statusMap = new HashMap<>();

		statusMap.put("Active", "Active");
		statusMap.put("Inactive", "Inactive");
		statusMap.put("Pending", "Pending");

		request.setAttribute("statusMap", statusMap);
	}

	// ------------------------------- VALIDATE -------------------------------
	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("vendorName"))) {
			request.setAttribute("vendorName",
					PropertyReader.getValue("error.require", "Vendor Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("contactNumber"))) {
			request.setAttribute("contactNumber",
					PropertyReader.getValue("error.require", "Contact Number"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("vendorStatus"))) {
			request.setAttribute("vendorStatus",
					PropertyReader.getValue("error.require", "Vendor Status"));
			pass = false;
		}

		return pass;
	}

	// ------------------------------- POPULATE DTO -------------------------------
	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		VendorDTO dto = new VendorDTO();

		dto.setVendorName(DataUtility.getString(request.getParameter("vendorName")));
		dto.setContactNumber(DataUtility.getString(request.getParameter("contactNumber")));
		dto.setVendorStatus(DataUtility.getString(request.getParameter("vendorStatus")));

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

		log.debug("VendorCtl doGet Started");

		VendorModelInt model = ModelFactory.getInstance().getVendorModel();
		long id = DataUtility.getLong(request.getParameter("id"));

		if (id > 0) {
			try {
				VendorDTO dto = model.findByPk(id);
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

		log.debug("VendorCtl doPost Started");

		String op = DataUtility.getString(request.getParameter("operation"));
		VendorModelInt model = ModelFactory.getInstance().getVendorModel();
		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			VendorDTO dto = (VendorDTO) populateDTO(request);

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
			    ServletUtility.setErrorMessage("Vendor Name already exists", request);

			} catch (Exception e) {

			    e.printStackTrace();
			    ServletUtility.handleException(e, request, response);
			    return;
			}

		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			VendorDTO dto = (VendorDTO) populateDTO(request);

			try {
				model.delete(dto);
			} catch (Exception e) {
				e.printStackTrace();
			}

			ServletUtility.redirect(ORSView.VENDOR_LIST_CTL, request, response);
			return;

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.VENDOR_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.VENDOR_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.VENDOR_VIEW;
	}
}

