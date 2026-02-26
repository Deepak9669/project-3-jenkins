package in.co.rays.project_3.dto;

import java.util.Date;

public class JobDTO extends BaseDTO {

	private String title;
	private Date dob;
	private String experience;
	private String status;

	@Override
	public String getKey() {
		return id + "";
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String getValue() {
		return title;
	}

}
