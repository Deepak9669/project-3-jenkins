package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.DoctorDTO;
import in.co.rays.project_3.model.DoctorModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(urlPatterns = { "/ctl/DoctorCtl" })
public class DoctorCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(DoctorCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("doctorName"))) {
			request.setAttribute("doctorName", PropertyReader.getValue("error.require", "Doctor Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("hospitalName"))) {
			request.setAttribute("hospitalName", PropertyReader.getValue("error.require", "Hospital Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("patientName"))) {
			request.setAttribute("patientName", PropertyReader.getValue("error.require", "Patient Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("diseases"))) {
			request.setAttribute("diseases", PropertyReader.getValue("error.require", "Diseases"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("appointmentDate"))) {
			request.setAttribute("appointmentDate", PropertyReader.getValue("error.require", "Appointment Date"));
			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		DoctorDTO dto = new DoctorDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));

		dto.setDoctorName(DataUtility.getString(request.getParameter("doctorName")));
		dto.setHospitalName(DataUtility.getString(request.getParameter("hospitalName")));
		dto.setPatientName(DataUtility.getString(request.getParameter("patientName")));
		dto.setDiseases(DataUtility.getString(request.getParameter("diseases")));

		dto.setAppointmentDate(DataUtility.getDate(request.getParameter("appointmentDate")));

		populateBean(dto, request);

		log.debug("DoctorCtl populateDTO End");
		return dto;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.debug("DoctorCtl doGet Started");

		String op = DataUtility.getString(request.getParameter("operation"));
		DoctorModelInt model = ModelFactory.getInstance().getDoctorModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (id > 0 || op != null) {

			DoctorDTO dto = null;

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

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.debug("DoctorCtl doPost Started");

		String op = DataUtility.getString(request.getParameter("operation"));
		DoctorModelInt model = ModelFactory.getInstance().getDoctorModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			DoctorDTO dto = (DoctorDTO) populateDTO(request);

			if (id > 0) {
				try {
					model.update(dto);
					ServletUtility.setSuccessMessage("Data is successfully Updated", request);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				try {
					model.add(dto);
					ServletUtility.setSuccessMessage("Data is successfully saved", request);
				} catch (Exception e) {
					ServletUtility.handleException(e, request, response);
					e.printStackTrace();
				}
			}

			ServletUtility.setDto(dto, request);

		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			DoctorDTO dto = (DoctorDTO) populateDTO(request);

			try {
				model.delete(dto);
			} catch (Exception e) {
				e.printStackTrace();
			}

			ServletUtility.redirect(ORSView.DOCTOR_LIST_CTL, request, response);
			return;

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.DOCTOR_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.DOCTOR_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
		log.debug("DoctorCtl doPost End");
	}

	@Override
	protected String getView() {
		return ORSView.DOCTOR_VIEW;
	}
}
