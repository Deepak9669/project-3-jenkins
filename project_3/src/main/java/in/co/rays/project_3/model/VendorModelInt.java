package in.co.rays.project_3.model;

import java.util.List;
import in.co.rays.project_3.dto.VendorDTO;

public interface VendorModelInt {

	public long add(VendorDTO dto) throws Exception;

	public void delete(VendorDTO dto) throws Exception;

	public void update(VendorDTO dto) throws Exception;

	public VendorDTO findByPk(long pk) throws Exception;

	public VendorDTO findByVendorName(String vendorName) throws Exception;

	public List search(VendorDTO dto) throws Exception;

	public List search(VendorDTO dto, int pageNo, int pageSize) throws Exception;

	public List list() throws Exception;

	public List list(int pageNo, int pageSize) throws Exception;

}