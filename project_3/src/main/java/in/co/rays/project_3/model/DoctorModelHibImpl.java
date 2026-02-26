package in.co.rays.project_3.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.DoctorDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class DoctorModelHibImpl implements DoctorModelInt {

	/**
	 * Add Doctor
	 */
	@Override
	public long add(DoctorDTO dto) throws ApplicationException, DuplicateRecordException {

		DoctorDTO existDto = findByDoctorName(dto.getDoctorName());

		if (existDto != null) {
			throw new DuplicateRecordException("Doctor Name already exists");
		}

		Session session = HibDataSource.getSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			session.save(dto);
			tx.commit();

		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in Doctor Add " + e.getMessage());
		} finally {
			session.close();
		}

		return dto.getId();
	}

	/**
	 * Delete Doctor
	 */
	@Override
	public void delete(DoctorDTO dto) throws ApplicationException {

		Session session = null;
		Transaction tx = null;

		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.delete(dto);
			tx.commit();

		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in Doctor Delete " + e.getMessage());
		} finally {
			session.close();
		}
	}

	/**
	 * Update Doctor
	 */
	@Override
	public void update(DoctorDTO dto) throws ApplicationException, DuplicateRecordException {

		Session session = null;
		Transaction tx = null;

		DoctorDTO existDto = findByDoctorName(dto.getDoctorName());

		if (existDto != null && existDto.getId() != dto.getId()) {
			throw new DuplicateRecordException("Doctor Name already exists");
		}

		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.saveOrUpdate(dto);
			tx.commit();

		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in Doctor Update " + e.getMessage());
		} finally {
			session.close();
		}
	}

	/**
	 * Find By PK
	 */
	@Override
	public DoctorDTO findByPk(long pk) throws ApplicationException {

		Session session = null;
		DoctorDTO dto = null;

		try {
			session = HibDataSource.getSession();
			dto = (DoctorDTO) session.get(DoctorDTO.class, pk);

		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in getting Doctor by PK");
		} finally {
			session.close();
		}

		return dto;
	}

	/**
	 * Find By Doctor Name
	 */
	public DoctorDTO findByDoctorName(String doctorName) throws ApplicationException {

		Session session = null;
		DoctorDTO dto = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(DoctorDTO.class);
			criteria.add(Restrictions.eq("doctorName", doctorName));

			List list = criteria.list();

			if (list.size() == 1) {
				dto = (DoctorDTO) list.get(0);
			}

		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in getting Doctor by Name");
		} finally {
			session.close();
		}

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

		Session session = null;
		List list = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(DoctorDTO.class);

			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in Doctor list");
		} finally {
			session.close();
		}

		return list;
	}

	/**
	 * Search
	 */
	@Override
	public List search(DoctorDTO dto, int pageNo, int pageSize) throws ApplicationException {

		Session session = null;
		List list = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(DoctorDTO.class);

			if (dto != null) {

				if (dto.getDoctorName() != null && dto.getDoctorName().length() > 0) {
					criteria.add(Restrictions.like("doctorName", dto.getDoctorName() + "%"));
				}

				if (dto.getHospitalName() != null && dto.getHospitalName().length() > 0) {
					criteria.add(Restrictions.like("hospitalName", dto.getHospitalName() + "%"));
				}
			}

			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();   // ✅ NO CAST

		} catch (Exception e) {
			throw new ApplicationException("Exception in Doctor search");
		} finally {
			session.close();
		}

		return list;
	}

	@Override
	public List search(DoctorDTO dto) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	
}
