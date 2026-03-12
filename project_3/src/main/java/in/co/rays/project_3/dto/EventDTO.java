package in.co.rays.project_3.dto;

import java.util.Date;

public class EventDTO  extends BaseDTO {
	
	private String participentName;
	private String eventName;
	private String email;
	private Date registrationDate;
	
	public String getParticipentName() {
		return participentName;
	}
	public void setParticipentName(String participentName) {
		this.participentName = participentName;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getRegistrationDate() {
		return registrationDate;
	}
	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}
	@Override
	public String getKey() {
		return id+"";
	}
	@Override
	public String getValue() {
		return participentName;
	}

}
