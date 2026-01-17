package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.BusDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.model.BusModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

/**
 * Bus List functionality controller to perform Search and List operation.
 * 
 * @author
 *
 */
@WebServlet(name = "BusListCtl", urlPatterns = { "/ctl/BusListCtl" })
public class BusListCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(BusListCtl.class);

	/**
	 * ✅ Preload (Same as BusCtl)
	 * BusType + City dropdown list load
	 */
	@Override
	protected void preload(HttpServletRequest request) {

		// ✅ Bus Type Dropdown Map
		HashMap<String, String> busTypeMap = new HashMap<>();
		busTypeMap.put("AC Sleeper", "AC Sleeper");
		busTypeMap.put("Non-AC Sleeper", "Non-AC Sleeper");
		busTypeMap.put("AC Seater", "AC Seater");
		busTypeMap.put("Non-AC Seater", "Non-AC Seater");

		// ✅ City Map (Source / Destination)
		HashMap<String, String> cityMap = new HashMap<>();
		cityMap.put("Indore", "Indore");
		cityMap.put("Bhopal", "Bhopal");
		cityMap.put("Ujjain", "Ujjain");
		cityMap.put("Dewas", "Dewas");
		cityMap.put("Delhi", "Delhi");

		// ✅ Set in request
		request.setAttribute("busTypeMap", busTypeMap);
		request.setAttribute("cityMap", cityMap);
	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		BusDTO dto = new BusDTO();

		dto.setBusNumber(DataUtility.getString(request.getParameter("busNumber")));
		dto.setBusType(DataUtility.getString(request.getParameter("busType")));
		dto.setTotalSeats(DataUtility.getString(request.getParameter("totalSeats")));
		dto.setSource(DataUtility.getString(request.getParameter("source")));
		dto.setDestination(DataUtility.getString(request.getParameter("destination")));

		populateBean(dto, request);

		return dto;
	}

	/**
	 * Contains Display logic
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("BusListCtl doGet Start");

		List list;
		List next;

		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

		BusDTO dto = (BusDTO) populateDTO(request);

		BusModelInt model = ModelFactory.getInstance().getBusModel();

		try {

			list = model.search(dto, pageNo, pageSize);
			next = model.search(dto, pageNo + 1, pageSize);

			ServletUtility.setList(list, request);

			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No record found ", request);
			}

			if (next == null || next.size() == 0) {
				request.setAttribute("nextListSize", 0);
			} else {
				request.setAttribute("nextListSize", next.size());
			}

			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);

		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}

		log.debug("BusListCtl doGet End");
	}

	/**
	 * Contains Submit logic
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("BusListCtl doPost Start");

		List list = null;
		List next = null;

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

		BusDTO dto = (BusDTO) populateDTO(request);

		String op = DataUtility.getString(request.getParameter("operation"));

		// get the selected checkbox ids array for delete list
		String[] ids = request.getParameterValues("ids");

		BusModelInt model = ModelFactory.getInstance().getBusModel();

		try {

			if (OP_SEARCH.equalsIgnoreCase(op) || OP_NEXT.equalsIgnoreCase(op) || OP_PREVIOUS.equalsIgnoreCase(op)) {

				if (OP_SEARCH.equalsIgnoreCase(op)) {
					pageNo = 1;
				} else if (OP_NEXT.equalsIgnoreCase(op)) {
					pageNo++;
				} else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
					pageNo--;
				}

			} else if (OP_NEW.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.BUS_CTL, request, response);
				return;

			} else if (OP_RESET.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.BUS_LIST_CTL, request, response);
				return;

			} else if (OP_DELETE.equalsIgnoreCase(op)) {

				pageNo = 1;

				if (ids != null && ids.length > 0) {

					BusDTO deleteDTO = new BusDTO();

					for (String id : ids) {
						deleteDTO.setId(DataUtility.getLong(id));
						model.delete(deleteDTO);
					}

					ServletUtility.setSuccessMessage("Data Successfully Deleted!", request);

				} else {
					ServletUtility.setErrorMessage("Select atleast one record", request);
				}
			}

			if (OP_BACK.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.BUS_LIST_CTL, request, response);
				return;
			}

			dto = (BusDTO) populateDTO(request);

			list = model.search(dto, pageNo, pageSize);
			next = model.search(dto, pageNo + 1, pageSize);

			ServletUtility.setDto(dto, request);
			ServletUtility.setList(list, request);

			if (list == null || list.size() == 0) {
				if (!OP_DELETE.equalsIgnoreCase(op)) {
					ServletUtility.setErrorMessage("No record found ", request);
				}
			}

			if (next == null || next.size() == 0) {
				request.setAttribute("nextListSize", 0);
			} else {
				request.setAttribute("nextListSize", next.size());
			}

			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);

			ServletUtility.forward(getView(), request, response);

		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}

		log.debug("BusListCtl doPost End");
	}

	@Override
	protected String getView() {
		return ORSView.BUS_LIST_VIEW;
	}
}
