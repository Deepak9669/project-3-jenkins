package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.BranchManagerDTO;
import in.co.rays.project_3.model.BranchManagerModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(urlPatterns = { "/ctl/BranchManagerCtl" })
public class BranchManagerCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(BranchManagerCtl.class);

	// ------------------------------ PRELOAD ------------------------------
	@Override
	protected void preload(HttpServletRequest request) {

		HashMap<String, String> branchMap = new HashMap<>();

		branchMap.put("Indore", "Indore");
		branchMap.put("Bhopal", "Bhopal");
		branchMap.put("Ujjain", "Ujjain");
		branchMap.put("Dewas", "Dewas");
		branchMap.put("Delhi", "Delhi");

		request.setAttribute("branchMap", branchMap);
	}

	@Override
	protected boolean validate(HttpServletRequest request) {

	    boolean pass = true;

	    String op = DataUtility.getString(request.getParameter("operation"));

	    if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

	        if (DataValidator.isNull(request.getParameter("managerName"))) {
	            request.setAttribute("managerName",
	                    PropertyReader.getValue("error.require", "Manager Name"));
	            pass = false;
	        } else if (!DataValidator.isName(request.getParameter("managerName"))) {
	        	 request.setAttribute("managerName",
		                    PropertyReader.getValue("error.require", "Invalid Manager Name"));
		            pass = false;
				
			}

	        if (DataValidator.isNull(request.getParameter("branchName"))) {
	            request.setAttribute("branchName",
	                    PropertyReader.getValue("error.require", "Branch Name"));
	            pass = false;
	        }

	        if (DataValidator.isNull(request.getParameter("contactNumber"))) {
	            request.setAttribute("contactNumber",
	                    PropertyReader.getValue("error.require", "Contact Number"));
	            pass = false;
	        }
	    }

	    return pass;
	}
	// ------------------------------ POPULATE DTO ------------------------------
	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		BranchManagerDTO dto = new BranchManagerDTO();

		dto.setManagerName(DataUtility.getString(request.getParameter("managerName")));
		dto.setBranchName(DataUtility.getString(request.getParameter("branchName")));
		dto.setContactNumber(DataUtility.getString(request.getParameter("contactNumber")));

		long id = DataUtility.getLong(request.getParameter("id"));
		if (id > 0) {
			dto.setId(id);
		}

		return dto;
	}

	// ------------------------------ DO GET ------------------------------
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.debug("BranchManagerCtl doGet Started");

		BranchManagerModelInt model = ModelFactory.getInstance().getBranchManagerModel();
		long id = DataUtility.getLong(request.getParameter("id"));

		if (id > 0) {
			try {
				BranchManagerDTO dto = model.findByPk(id);
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

	// ------------------------------ DO POST ------------------------------
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.debug("BranchManagerCtl doPost Started");

		String op = DataUtility.getString(request.getParameter("operation"));
		BranchManagerModelInt model = ModelFactory.getInstance().getBranchManagerModel();
		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			BranchManagerDTO dto = (BranchManagerDTO) populateDTO(request);

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

			BranchManagerDTO dto = (BranchManagerDTO) populateDTO(request);

			try {
				model.delete(dto);
			} catch (Exception e) {
				e.printStackTrace();
			}

			ServletUtility.redirect(ORSView.BRANCH_MANAGER_LIST_CTL, request, response);
			return;

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.BRANCH_MANAGER_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.BRANCH_MANAGER_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.BRANCH_MANAGER_VIEW;
	}
}