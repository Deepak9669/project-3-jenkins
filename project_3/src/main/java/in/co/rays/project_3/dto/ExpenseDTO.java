package in.co.rays.project_3.dto;

import java.util.Date;

public class ExpenseDTO  extends BaseDTO{

	private Long expenseId;
	private String expenseCode; // UNIQUE
	private String expenseType;
	private String amount;
	private Date expenseDate;
	private String expenseStatus;

	public Long getExpenseId() {
		return expenseId;
	}

	public void setExpenseId(Long expenseId) {
		this.expenseId = expenseId;
	}

	public String getExpenseCode() {
		return expenseCode;
	}

	public void setExpenseCode(String expenseCode) {
		this.expenseCode = expenseCode;
	}

	public String getExpenseType() {
		return expenseType;
	}

	public void setExpenseType(String expenseType) {
		this.expenseType = expenseType;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public Date getExpenseDate() {
		return expenseDate;
	}

	public void setExpenseDate(Date expenseDate) {
		this.expenseDate = expenseDate;
	}

	public String getExpenseStatus() {
		return expenseStatus;
	}

	public void setExpenseStatus(String expenseStatus) {
		this.expenseStatus = expenseStatus;
	}

	@Override
	public String getKey() {
		return id+"";
	}

	@Override
	public String getValue() {
		return "expenseId";
	}
}
