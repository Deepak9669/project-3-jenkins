package in.co.rays.project_3.dto;

public class BusDTO extends BaseDTO {

	private String busNumber;
	private String busType; // AC / Non-AC
	private String totalSeats; // String as requested
	private String source;
	private String destination;

	// 🔹 BaseDTO abstract methods implementation
	@Override
	public String getKey() {
		return id + "";
	}

	@Override
	public String getValue() {
		return busNumber + " (" + source + " - " + destination + ")";
	}

	// Getters & Setters
	public String getBusNumber() {
		return busNumber;
	}

	public void setBusNumber(String busNumber) {
		this.busNumber = busNumber;
	}

	public String getBusType() {
		return busType;
	}

	public void setBusType(String busType) {
		this.busType = busType;
	}

	public String getTotalSeats() {
		return totalSeats;
	}

	public void setTotalSeats(String totalSeats) {
		this.totalSeats = totalSeats;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}
}
