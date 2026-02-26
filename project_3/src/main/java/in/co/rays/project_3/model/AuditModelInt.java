package in.co.rays.project_3.model;

import java.util.List;
import in.co.rays.project_3.dto.AuditDTO;

public interface AuditModelInt {

	public long add(AuditDTO dto) throws Exception;

	public void delete(AuditDTO dto) throws Exception;

	public void update(AuditDTO dto) throws Exception;

	public AuditDTO findByPk(long pk) throws Exception;

	public AuditDTO findByActionBy(String actionBy) throws Exception;

	public List search(AuditDTO dto) throws Exception;

	public List search(AuditDTO dto, int pageNo, int pageSize) throws Exception;

	public List list() throws Exception;

	public List list(int pageNo, int pageSize) throws Exception;
}
