package in.co.rays.project_3.dto;
	
	public class BranchManagerDTO extends BaseDTO {

	    private Long managerId;
	    private String managerName;
	    private String branchName;
	    private String contactNumber;

	    public Long getManagerId() {
	        return managerId;
	    }

	    public void setManagerId(Long managerId) {
	        this.managerId = managerId;
	    }

	    public String getManagerName() {
	        return managerName;
	    }

	    public void setManagerName(String managerName) {
	        this.managerName = managerName;
	    }

	    public String getBranchName() {
	        return branchName;
	    }

	    public void setBranchName(String branchName) {
	        this.branchName = branchName;
	    }

	    public String getContactNumber() {
	        return contactNumber;
	    }

	    public void setContactNumber(String contactNumber) {
	        this.contactNumber = contactNumber;
	    }

		@Override
		public String getKey() {
			return id+"";
		}

		@Override
		public String getValue() {
			return managerName;
		}
	}


