package in.co.rays.project_3.model;

import java.util.List;
import in.co.rays.project_3.dto.JobDTO;

public interface JobModelInt {

	public long add(JobDTO dto) throws Exception;

	public void delete(JobDTO dto) throws Exception;

	public void update(JobDTO dto) throws Exception;

	public JobDTO findByPk(long pk) throws Exception;

	public JobDTO findByTitle(String title) throws Exception;

	public List search(JobDTO dto) throws Exception;

	public List search(JobDTO dto, int pageNo, int pageSize) throws Exception;

	public List list() throws Exception;

	public List list(int pageNo, int pageSize) throws Exception;
}