package in.co.rays.project_3.model;

import java.util.List;
import in.co.rays.project_3.dto.BusDTO;

public interface BusModelInt {

    public long add(BusDTO dto) throws Exception;

    public void delete(BusDTO dto) throws Exception;

    public void update(BusDTO dto) throws Exception;

    public BusDTO findByPk(long pk) throws Exception;

    public BusDTO findByBusNumber(String busNumber) throws Exception;

    public List search(BusDTO dto) throws Exception;

    public List search(BusDTO dto, int pageNo, int pageSize) throws Exception;

    // ✅ Added list methods
    public List list() throws Exception;

    public List list(int pageNo, int pageSize) throws Exception;
}
