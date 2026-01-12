package in.co.rays.project_3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BusDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.JDBCDataSource;

public class BusModelJDBCImpl implements BusModelInt {

	private static Logger log = Logger.getLogger(BusModelJDBCImpl.class);

	/**
	 * Next Primary Key
	 */
	public long nextPK() throws DatabaseException {
		log.debug("BusModel nextPK started");
		Connection con = null;
		long pk = 0;

		try {
			con = JDBCDataSource.getConnection();
			PreparedStatement ps = con.prepareStatement("SELECT MAX(ID) FROM ST_BUS");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				pk = rs.getLong(1);
			}
			rs.close();
			ps.close();

		} catch (Exception e) {
			log.error(e);
			throw new DatabaseException("Database Exception : " + e.getMessage());
		} finally {
			JDBCDataSource.closeConnection(con);
		}

		log.debug("BusModel nextPK ended");
		return pk + 1;
	}

	/**
	 * Add Bus
	 */
	@Override
	public long add(BusDTO dto) throws ApplicationException, DuplicateRecordException {
		log.debug("BusModel add started");

		Connection con = null;
		long pk = 0;

		BusDTO existDto = findByBusNumber(dto.getBusNumber());
		if (existDto != null) {
			throw new DuplicateRecordException("Bus Number already exists");
		}

		try {
			con = JDBCDataSource.getConnection();
			con.setAutoCommit(false);

			pk = nextPK();

			PreparedStatement ps = con.prepareStatement("INSERT INTO ST_BUS VALUES(?,?,?,?,?,?,?,?,?,?)");

			ps.setLong(1, pk);
			ps.setString(2, dto.getBusNumber());
			ps.setString(3, dto.getBusType());
			ps.setString(4, dto.getTotalSeats());
			ps.setString(5, dto.getSource());
			ps.setString(6, dto.getDestination());
			ps.setString(7, dto.getCreatedBy());
			ps.setString(8, dto.getModifiedBy());
			ps.setTimestamp(9, dto.getCreatedDatetime());
			ps.setTimestamp(10, dto.getModifiedDatetime());

			ps.executeUpdate();
			con.commit();
			ps.close();

		} catch (Exception e) {
			log.error("Database Exception..", e);
			try {
				if (con != null)
					con.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add Bus");
		} finally {
			JDBCDataSource.closeConnection(con);
		}

		log.debug("BusModel add ended");
		return pk;
	}

	/**
	 * Delete Bus
	 */
	@Override
	public void delete(BusDTO dto) throws ApplicationException {
		log.debug("BusModel delete started");
		Connection con = null;

		try {
			con = JDBCDataSource.getConnection();
			con.setAutoCommit(false);

			PreparedStatement ps = con.prepareStatement("DELETE FROM ST_BUS WHERE ID=?");
			ps.setLong(1, dto.getId());
			ps.executeUpdate();

			con.commit();
			ps.close();

		} catch (Exception e) {
			log.error("Database Exception..", e);
			try {
				if (con != null)
					con.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in delete Bus");
		} finally {
			JDBCDataSource.closeConnection(con);
		}

		log.debug("BusModel delete ended");
	}

	/**
	 * Update Bus
	 */
	@Override
	public void update(BusDTO dto) throws ApplicationException, DuplicateRecordException {
		log.debug("BusModel update started");
		Connection con = null;

		BusDTO existDto = findByBusNumber(dto.getBusNumber());
		if (existDto != null && existDto.getId() != dto.getId()) {
			throw new DuplicateRecordException("Bus Number already exists");
		}

		try {
			con = JDBCDataSource.getConnection();
			con.setAutoCommit(false);

			PreparedStatement ps = con.prepareStatement(
					"UPDATE ST_BUS SET BUS_NUMBER=?, BUS_TYPE=?, TOTAL_SEATS=?, SOURCE=?, DESTINATION=?, "
							+ "CREATED_BY=?, MODIFIED_BY=?, CREATED_DATETIME=?, MODIFIED_DATETIME=? WHERE ID=?");

			ps.setString(1, dto.getBusNumber());
			ps.setString(2, dto.getBusType());
			ps.setString(3, dto.getTotalSeats());
			ps.setString(4, dto.getSource());
			ps.setString(5, dto.getDestination());
			ps.setString(6, dto.getCreatedBy());
			ps.setString(7, dto.getModifiedBy());
			ps.setTimestamp(8, dto.getCreatedDatetime());
			ps.setTimestamp(9, dto.getModifiedDatetime());
			ps.setLong(10, dto.getId());

			ps.executeUpdate();

			con.commit();
			ps.close();

		} catch (Exception e) {
			log.error("Database Exception..", e);
			try {
				if (con != null)
					con.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Update rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in update Bus");
		} finally {
			JDBCDataSource.closeConnection(con);
		}

		log.debug("BusModel update ended");
	}

	/**
	 * Find By PK
	 */
	@Override
	public BusDTO findByPk(long pk) throws ApplicationException {
		log.debug("BusModel findByPk started");

		Connection con = null;
		BusDTO dto = null;

		try {
			con = JDBCDataSource.getConnection();
			PreparedStatement ps = con.prepareStatement("SELECT * FROM ST_BUS WHERE ID=?");
			ps.setLong(1, pk);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				dto = new BusDTO();
				dto.setId(rs.getLong(1));
				dto.setBusNumber(rs.getString(2));
				dto.setBusType(rs.getString(3));
				dto.setTotalSeats(rs.getString(4));
				dto.setSource(rs.getString(5));
				dto.setDestination(rs.getString(6));
				dto.setCreatedBy(rs.getString(7));
				dto.setModifiedBy(rs.getString(8));
				dto.setCreatedDatetime(rs.getTimestamp(9));
				dto.setModifiedDatetime(rs.getTimestamp(10));
			}
			rs.close();
			ps.close();

		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in find Bus by PK");
		} finally {
			JDBCDataSource.closeConnection(con);
		}

		log.debug("BusModel findByPk ended");
		return dto;
	}

	/**
	 * Find By Bus Number
	 */
	@Override
	public BusDTO findByBusNumber(String busNumber) throws ApplicationException {
		log.debug("BusModel findByBusNumber started");

		Connection con = null;
		BusDTO dto = null;

		try {
			con = JDBCDataSource.getConnection();
			PreparedStatement ps = con.prepareStatement("SELECT * FROM ST_BUS WHERE BUS_NUMBER=?");
			ps.setString(1, busNumber);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				dto = new BusDTO();
				dto.setId(rs.getLong(1));
				dto.setBusNumber(rs.getString(2));
				dto.setBusType(rs.getString(3));
				dto.setTotalSeats(rs.getString(4));
				dto.setSource(rs.getString(5));
				dto.setDestination(rs.getString(6));
				dto.setCreatedBy(rs.getString(7));
				dto.setModifiedBy(rs.getString(8));
				dto.setCreatedDatetime(rs.getTimestamp(9));
				dto.setModifiedDatetime(rs.getTimestamp(10));
			}
			rs.close();
			ps.close();

		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in find Bus by Bus Number");
		} finally {
			JDBCDataSource.closeConnection(con);
		}

		log.debug("BusModel findByBusNumber ended");
		return dto;
	}

	/**
	 * List
	 */
	@Override
	public List list() throws ApplicationException {
		return list(0, 0);
	}

	@Override
	public List list(int pageNo, int pageSize) throws ApplicationException {
		log.debug("BusModel list started");

		Connection con = null;
		PreparedStatement ps = null;
		List list = new ArrayList();
		BusDTO dto = null;

		StringBuffer sql = new StringBuffer("SELECT * FROM ST_BUS WHERE 1=1");

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" LIMIT " + pageNo + "," + pageSize);
		}

		try {
			con = JDBCDataSource.getConnection();
			ps = con.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				dto = new BusDTO();
				dto.setId(rs.getLong(1));
				dto.setBusNumber(rs.getString(2));
				dto.setBusType(rs.getString(3));
				dto.setTotalSeats(rs.getString(4));
				dto.setSource(rs.getString(5));
				dto.setDestination(rs.getString(6));
				dto.setCreatedBy(rs.getString(7));
				dto.setModifiedBy(rs.getString(8));
				dto.setCreatedDatetime(rs.getTimestamp(9));
				dto.setModifiedDatetime(rs.getTimestamp(10));
				list.add(dto);
			}
			rs.close();
			ps.close();

		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in list Bus");
		} finally {
			JDBCDataSource.closeConnection(con);
		}

		log.debug("BusModel list ended");
		return list;
	}

	/**
	 * Search
	 */
	@Override
	public List search(BusDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	@Override
	public List search(BusDTO dto, int pageNo, int pageSize) throws ApplicationException {
		log.debug("BusModel search started");

		Connection con = null;
		PreparedStatement ps = null;
		List list = new ArrayList();

		StringBuffer sql = new StringBuffer("SELECT * FROM ST_BUS WHERE 1=1");

		if (dto != null) {
			if (dto.getId() > 0) {
				sql.append(" AND ID = " + dto.getId());
			}
			if (dto.getBusNumber() != null && dto.getBusNumber().length() > 0) {
				sql.append(" AND BUS_NUMBER like '" + dto.getBusNumber() + "%'");
			}
			if (dto.getBusType() != null && dto.getBusType().length() > 0) {
				sql.append(" AND BUS_TYPE like '" + dto.getBusType() + "%'");
			}
			if (dto.getSource() != null && dto.getSource().length() > 0) {
				sql.append(" AND SOURCE like '" + dto.getSource() + "%'");
			}
			if (dto.getDestination() != null && dto.getDestination().length() > 0) {
				sql.append(" AND DESTINATION like '" + dto.getDestination() + "%'");
			}
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" LIMIT " + pageNo + "," + pageSize);
		}

		try {
			con = JDBCDataSource.getConnection();
			ps = con.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				dto = new BusDTO();
				dto.setId(rs.getLong(1));
				dto.setBusNumber(rs.getString(2));
				dto.setBusType(rs.getString(3));
				dto.setTotalSeats(rs.getString(4));
				dto.setSource(rs.getString(5));
				dto.setDestination(rs.getString(6));
				dto.setCreatedBy(rs.getString(7));
				dto.setModifiedBy(rs.getString(8));
				dto.setCreatedDatetime(rs.getTimestamp(9));
				dto.setModifiedDatetime(rs.getTimestamp(10));
				list.add(dto);
			}

			rs.close();
			ps.close();

		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in search Bus");
		} finally {
			JDBCDataSource.closeConnection(con);
		}

		log.debug("BusModel search ended");
		return list;
	}
}
