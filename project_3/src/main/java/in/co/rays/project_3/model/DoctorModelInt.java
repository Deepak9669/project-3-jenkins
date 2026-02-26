package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.DoctorDTO;

public interface DoctorModelInt {

	public long add(DoctorDTO dto) throws Exception;

	public void delete(DoctorDTO dto) throws Exception;

	public void update(DoctorDTO dto) throws Exception;

	public DoctorDTO findByPk(long pk) throws Exception;

	public DoctorDTO findByDoctorName(String doctorName) throws Exception;

	public List search(DoctorDTO dto) throws Exception;

	public List search(DoctorDTO dto, int pageNo, int pageSize) throws Exception;

	public List list() throws Exception;

	public List list(int pageNo, int pageSize) throws Exception;

}
