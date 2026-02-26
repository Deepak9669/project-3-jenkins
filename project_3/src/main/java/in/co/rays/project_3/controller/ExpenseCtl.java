package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.ExpenseDTO;
import in.co.rays.project_3.model.ExpenseModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(urlPatterns = { "/ctl/ExpenseCtl" })
public class ExpenseCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(ExpenseCtl.class);

	// ------------------------------ PRELOAD ------------------------------
	@Override
	protected void preload(HttpServletRequest request) {

		HashMap<String, String> map = new HashMap<>();
		map.put("active", "active");
		map.put("inactive", "inactive");
		
		request.setAttribute("map", map);
	}

	// ------------------------------ VALIDATE ------------------------------
	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("expenseCode"))) {
			request.setAttribute("expenseCode",
					PropertyReader.getValue("error.require", "Expense Code"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("expenseType"))) {
			request.setAttribute("expenseType",
					PropertyReader.getValue("error.require", "Expense Type"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("amount"))) {
			request.setAttribute("amount",
					PropertyReader.getValue("error.require", "Amount"));
			pass = false;
		}

		return pass;
	}

	// ------------------------------ POPULATE DTO ------------------------------
	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		ExpenseDTO dto = new ExpenseDTO();

		dto.setExpenseCode(DataUtility.getString(request.getParameter("expenseCode")));
		dto.setExpenseType(DataUtility.getString(request.getParameter("expenseType")));
		dto.setAmount(DataUtility.getString(request.getParameter("amount")));
		dto.setExpenseDate(DataUtility.getDate(request.getParameter("expenseDate")));
		dto.setExpenseStatus(DataUtility.getString(request.getParameter("expenseStatus")));

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

		log.debug("ExpenseCtl doGet Started");

		ExpenseModelInt model = ModelFactory.getInstance().getExpenseModel();
		long id = DataUtility.getLong(request.getParameter("id"));

		if (id > 0) {
			try {
				ExpenseDTO dto = model.findByPk(id);
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

		log.debug("ExpenseCtl doPost Started");

		String op = DataUtility.getString(request.getParameter("operation"));
		ExpenseModelInt model = ModelFactory.getInstance().getExpenseModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			ExpenseDTO dto = (ExpenseDTO) populateDTO(request);

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

			ExpenseDTO dto = (ExpenseDTO) populateDTO(request);

			try {
				model.delete(dto);
			} catch (Exception e) {
				e.printStackTrace();
			}

			ServletUtility.redirect(ORSView.EXPENSE_LIST_CTL, request, response);
			return;

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.EXPENSE_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.EXPENSE_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.EXPENSE_VIEW;
	}
}