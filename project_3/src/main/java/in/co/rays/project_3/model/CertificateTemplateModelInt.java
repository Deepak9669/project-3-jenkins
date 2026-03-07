package in.co.rays.project_3.model;

import java.util.List;
import in.co.rays.project_3.dto.CertificateTemplateDTO;

public interface CertificateTemplateModelInt {

	public long add(CertificateTemplateDTO dto) throws Exception;

	public void delete(CertificateTemplateDTO dto) throws Exception;

	public void update(CertificateTemplateDTO dto) throws Exception;

	public CertificateTemplateDTO findByPk(long pk) throws Exception;

	public CertificateTemplateDTO findByTemplateName(String templateName) throws Exception;

	public List search(CertificateTemplateDTO dto) throws Exception;

	public List search(CertificateTemplateDTO dto, int pageNo, int pageSize) throws Exception;

	public List list() throws Exception;

	public List list(int pageNo, int pageSize) throws Exception;

}