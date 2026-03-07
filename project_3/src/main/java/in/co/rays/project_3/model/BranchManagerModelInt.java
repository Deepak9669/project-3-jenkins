package in.co.rays.project_3.model;

import java.util.List;
import in.co.rays.project_3.dto.BranchManagerDTO;

public interface BranchManagerModelInt {

    public long add(BranchManagerDTO dto) throws Exception;

    public void delete(BranchManagerDTO dto) throws Exception;

    public void update(BranchManagerDTO dto) throws Exception;

    public BranchManagerDTO findByPk(long pk) throws Exception;

    public BranchManagerDTO findByManagerName(String managerName) throws Exception;

    public List search(BranchManagerDTO dto) throws Exception;

    public List search(BranchManagerDTO dto, int pageNo, int pageSize) throws Exception;

    public List list() throws Exception;

    public List list(int pageNo, int pageSize) throws Exception;
}