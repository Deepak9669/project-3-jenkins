package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.DoctorDTO;
import in.co.rays.project_3.dto.ExpenseDTO;

public interface ExpenseModelInt {

	public long add(ExpenseDTO dto) throws Exception;

	public void delete(ExpenseDTO dto) throws Exception;

	public void update(ExpenseDTO dto) throws Exception;

	public ExpenseDTO findByPk(long pk) throws Exception;

	public ExpenseDTO findByexpenseStatus(String expenseStatus) throws Exception;

	public List search(ExpenseDTO dto) throws Exception;

	public List search(ExpenseDTO dto, int pageNo, int pageSize) throws Exception;

	public List list() throws Exception;

	public List list(int pageNo, int pageSize) throws Exception;

}
