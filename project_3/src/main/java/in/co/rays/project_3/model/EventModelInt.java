package in.co.rays.project_3.model;

import java.util.List;
import in.co.rays.project_3.dto.EventDTO;

public interface EventModelInt {

	public long add(EventDTO dto) throws Exception;

	public void delete(EventDTO dto) throws Exception;

	public void update(EventDTO dto) throws Exception;

	public EventDTO findByPk(long pk) throws Exception;

	public EventDTO findByEventName(String eventName) throws Exception;

	public List search(EventDTO dto) throws Exception;

	public List search(EventDTO dto, int pageNo, int pageSize) throws Exception;

	public List list() throws Exception;

	public List list(int pageNo, int pageSize) throws Exception;

}