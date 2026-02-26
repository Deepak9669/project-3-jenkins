package in.co.rays.project_3.dto;
import java.util.Date;

import java.util.Date;

public class AuditDTO extends BaseDTO {

    private Long id;
    private String actionBy;
    private String actionType;
    private Date createdDate;
    private Date updatedDate;
    private String remarks;


    public Long getAuditId() {
        return id;
    }

    public void setAuditId(Long auditId) {
        this.id = auditId;
    }


    public String getActionBy() {
        return actionBy;
    }

    public void setActionBy(String actionBy) {
        this.actionBy = actionBy;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String getKey() {
        return id + "";
    }

    @Override
    public String getValue() {
        return actionBy;
    }

}
