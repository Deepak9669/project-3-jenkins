package in.co.rays.project_3.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.AuditDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

/**
 * Hibernate implements of Audit model
 * 
 * @author Deepak Verma
 *
 */
public class AuditModelHibImpl implements AuditModelInt {

    /**
     * Add Audit
     */
    @Override
    public long add(AuditDTO dto) throws ApplicationException, DuplicateRecordException {

        AuditDTO existDto = findByActionBy(dto.getActionBy());

        if (existDto != null) {
            throw new DuplicateRecordException("ActionBy already exists");
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
            throw new ApplicationException("Exception in Audit Add " + e.getMessage());
        } finally {
            session.close();
        }

        return dto.getId();
    }

    /**
     * Delete Audit
     */
    @Override
    public void delete(AuditDTO dto) throws ApplicationException {

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
            throw new ApplicationException("Exception in Audit Delete " + e.getMessage());
        } finally {
            session.close();
        }
    }

    /**
     * Update Audit
     */
    @Override
    public void update(AuditDTO dto) throws ApplicationException, DuplicateRecordException {

        Session session = null;
        Transaction tx = null;

        AuditDTO existDto = findByActionBy(dto.getActionBy());
        if (existDto != null && existDto.getId() != dto.getId()) {
            throw new DuplicateRecordException("ActionBy already exists");
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
            throw new ApplicationException("Exception in Audit Update " + e.getMessage());
        } finally {
            session.close();
        }
    }

    /**
     * Find By PK
     */
    @Override
    public AuditDTO findByPk(long pk) throws ApplicationException {

        Session session = null;
        AuditDTO dto = null;

        try {
            session = HibDataSource.getSession();
            dto = (AuditDTO) session.get(AuditDTO.class, pk);

        } catch (HibernateException e) {
            e.printStackTrace();
            throw new ApplicationException("Exception : Exception in getting Audit by PK");
        } finally {
            session.close();
        }

        return dto;
    }

    /**
     * Find By ActionBy
     */
    @Override
    public AuditDTO findByActionBy(String actionBy) throws ApplicationException {

        Session session = null;
        AuditDTO dto = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(AuditDTO.class);
            criteria.add(Restrictions.eq("actionBy", actionBy));
            List list = criteria.list();

            if (list.size() == 1) {
                dto = (AuditDTO) list.get(0);
            }

        } catch (HibernateException e) {
            e.printStackTrace();
            throw new ApplicationException("Exception in getting Audit by ActionBy " + e.getMessage());
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
            Criteria criteria = session.createCriteria(AuditDTO.class);

            if (pageSize > 0) {
                pageNo = (pageNo - 1) * pageSize;
                criteria.setFirstResult(pageNo);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (HibernateException e) {
            e.printStackTrace();
            throw new ApplicationException("Exception : Exception in Audit list");
        } finally {
            session.close();
        }

        return list;
    }

    /**
     * Search
     */
    @Override
    public List search(AuditDTO dto) throws ApplicationException {
        return search(dto, 0, 0);
    }

    @Override
    public List search(AuditDTO dto, int pageNo, int pageSize) throws ApplicationException {

        Session session = null;
        ArrayList<AuditDTO> list = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(AuditDTO.class);

            if (dto != null) {

                if (dto.getId() != null && dto.getId() > 0) {
                    criteria.add(Restrictions.eq("id", dto.getId()));
                }

                if (dto.getActionBy() != null && dto.getActionBy().length() > 0) {
                    criteria.add(Restrictions.like("actionBy", dto.getActionBy() + "%"));
                }

                if (dto.getActionType() != null && dto.getActionType().length() > 0) {
                    criteria.add(Restrictions.like("actionType", dto.getActionType() + "%"));
                }

                if (dto.getRemarks() != null && dto.getRemarks().length() > 0) {
                    criteria.add(Restrictions.like("remarks", dto.getRemarks() + "%"));
                }
            }

            // Pagination
            if (pageSize > 0) {
                pageNo = (pageNo - 1) * pageSize;
                criteria.setFirstResult(pageNo);
                criteria.setMaxResults(pageSize);
            }

            list = (ArrayList<AuditDTO>) criteria.list();

        } catch (HibernateException e) {
            e.printStackTrace();
            throw new ApplicationException("Exception in Audit search");
        } finally {
            session.close();
        }

        return list;
    }
}
