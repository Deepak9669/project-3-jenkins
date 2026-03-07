package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.CertificateTemplateDTO;
import in.co.rays.project_3.model.CertificateTemplateModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(urlPatterns = { "/ctl/CertificateTemplateCtl" })
public class CertificateTemplateCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(CertificateTemplateCtl.class);

	// ----------------------------- PRELOAD -----------------------------
	@Override
	protected void preload(HttpServletRequest request) {

		HashMap<String, String> formatMap = new HashMap<>();

		formatMap.put("PDF", "PDF");
		formatMap.put("DOC", "DOC");
		formatMap.put("HTML", "HTML");
		formatMap.put("IMAGE", "IMAGE");

		request.setAttribute("formatMap", formatMap);
	}

	// ----------------------------- VALIDATE -----------------------------
	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("templateName"))) {
			request.setAttribute("templateName",
					PropertyReader.getValue("error.require", "Template Name"));
			pass = false;
		}else if (!DataValidator.isName(request.getParameter("templateName"))) {
			request.setAttribute("templateName",
					PropertyReader.getValue("error.require", "Invalid Template Name"));
			pass = false;
			
		}

		if (DataValidator.isNull(request.getParameter("format"))) {
			request.setAttribute("format",
					PropertyReader.getValue("error.require", "Format"));
			pass = false;
		}

		return pass;
	}

	// ----------------------------- POPULATE DTO -----------------------------
	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		CertificateTemplateDTO dto = new CertificateTemplateDTO();

		dto.setTemplateName(DataUtility.getString(request.getParameter("templateName")));
		dto.setFormat(DataUtility.getString(request.getParameter("format")));
		dto.setCreatedDate(DataUtility.getDate(request.getParameter("createdDate")));

		long id = DataUtility.getLong(request.getParameter("id"));
		if (id > 0) {
			dto.setId(id);
		}

		populateBean(dto, request);

		return dto;
	}

	// ----------------------------- DO GET -----------------------------
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.debug("CertificateTemplateCtl doGet Started");

		CertificateTemplateModelInt model = ModelFactory.getInstance().getCertificateTemplateModel();
		long id = DataUtility.getLong(request.getParameter("id"));

		if (id > 0) {
			try {
				CertificateTemplateDTO dto = model.findByPk(id);
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

	// ----------------------------- DO POST -----------------------------
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.debug("CertificateTemplateCtl doPost Started");

		String op = DataUtility.getString(request.getParameter("operation"));
		CertificateTemplateModelInt model = ModelFactory.getInstance().getCertificateTemplateModel();
		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			CertificateTemplateDTO dto = (CertificateTemplateDTO) populateDTO(request);

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

			CertificateTemplateDTO dto = (CertificateTemplateDTO) populateDTO(request);

			try {
				model.delete(dto);
			} catch (Exception e) {
				e.printStackTrace();
			}

			ServletUtility.redirect(ORSView.CERTIFICATE_TEMPLATE_LIST_CTL, request, response);
			return;

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.CERTIFICATE_TEMPLATE_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.CERTIFICATE_TEMPLATE_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.CERTIFICATE_TEMPLATE_VIEW;
	}
}
