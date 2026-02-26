package in.co.rays.project_3.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.JobDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

/**
 * Hibernate implementation of Job Model
 */
public class JobModelHibImpl implements JobModelInt {

	/**
	 * Add Job
	 */
	@Override
	public long add(JobDTO dto) throws ApplicationException, DuplicateRecordException {

		JobDTO existDto = findByTitle(dto.getTitle());

		if (existDto != null) {
			throw new DuplicateRecordException("Title already exists");
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
			throw new ApplicationException("Exception in Job Add " + e.getMessage());
		} finally {
			session.close();
		}

		return dto.getId();
	}

	/**
	 * Delete Job
	 */
	@Override
	public void delete(JobDTO dto) throws ApplicationException {

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
			throw new ApplicationException("Exception in Job Delete " + e.getMessage());
		} finally {
			session.close();
		}
	}

	/**
	 * Update Job
	 */
	@Override
	public void update(JobDTO dto) throws ApplicationException, DuplicateRecordException {

		Session session = null;
		Transaction tx = null;

		JobDTO existDto = findByTitle(dto.getTitle());
		if (existDto != null && existDto.getId() != dto.getId()) {
			throw new DuplicateRecordException("Title already exists");
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
			throw new ApplicationException("Exception in Job Update " + e.getMessage());
		} finally {
			session.close();
		}
	}

	/**
	 * Find By PK
	 */
	@Override
	public JobDTO findByPk(long pk) throws ApplicationException {

		Session session = null;
		JobDTO dto = null;

		try {
			session = HibDataSource.getSession();
			dto = (JobDTO) session.get(JobDTO.class, pk);

		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in getting Job by PK");
		} finally {
			session.close();
		}

		return dto;
	}

	/**
	 * Find By Title
	 */
	@Override
	public JobDTO findByTitle(String title) throws ApplicationException {

		Session session = null;
		JobDTO dto = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(JobDTO.class);
			criteria.add(Restrictions.eq("title", title));
			List list = criteria.list();

			if (list.size() == 1) {
				dto = (JobDTO) list.get(0);
			}

		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in getting Job by Title " + e.getMessage());
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
			Criteria criteria = session.createCriteria(JobDTO.class);

			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in Job list");
		} finally {
			session.close();
		}

		return list;
	}

	/**
	 * Search
	 */
	@Override
	public List search(JobDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	@Override
	public List search(JobDTO dto, int pageNo, int pageSize) throws ApplicationException {

		Session session = null;
		ArrayList<JobDTO> list = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(JobDTO.class);

			if (dto != null) {

				if (dto.getId() != null && dto.getId() > 0) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}

				if (dto.getTitle() != null && dto.getTitle().length() > 0) {
					criteria.add(Restrictions.like("title", dto.getTitle() + "%"));
				}

				if (dto.getExperience() != null && dto.getExperience().length() > 0) {
					criteria.add(Restrictions.like("experience", dto.getExperience() + "%"));
				}

				if (dto.getStatus() != null && dto.getStatus().length() > 0) {
					criteria.add(Restrictions.like("status", dto.getStatus() + "%"));
				}

				if (dto.getDob() != null) {
					criteria.add(Restrictions.eq("dob", dto.getDob()));
				}
			}

			// Pagination
			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = (ArrayList<JobDTO>) criteria.list();

		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in Job search");
		} finally {
			session.close();
		}

		return list;
	}
}