package in.co.rays.project_3.dto;

import java.util.Date;

public class DoctorDTO extends BaseDTO {

	private String doctorName;
	private String hospitalName;
	private String patientName;
	private String diseases;
	private Date appointmentDate;

	@Override
	public String getKey() {
		return id + "";
	}

	@Override
	public String getValue() {
		return doctorName;
	}

	// Getter Setter

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getDiseases() {
		return diseases;
	}

	public void setDiseases(String diseases) {
		this.diseases = diseases;
	}

	public Date getAppointmentDate() {
		return appointmentDate;
	}

	public void setAppointmentDate(Date appointmentDate) {
		this.appointmentDate = appointmentDate;
	}
}
