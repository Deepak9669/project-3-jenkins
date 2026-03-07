package in.co.rays.project_3.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.BranchManagerDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

/**
 * Hibernate implementation of BranchManager Model
 */
public class BranchManagerModelHibImpl implements BranchManagerModelInt {

    /**
     * Add Branch Manager
     */
    @Override
    public long add(BranchManagerDTO dto) throws ApplicationException, DuplicateRecordException {

        BranchManagerDTO existDto = findByManagerName(dto.getManagerName());

        if (existDto != null) {
            throw new DuplicateRecordException("Manager Name already exists");
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
            throw new ApplicationException("Exception in Branch Manager Add " + e.getMessage());
        } finally {
            session.close();
        }

        return dto.getId();
    }

    /**
     * Delete
     */
    @Override
    public void delete(BranchManagerDTO dto) throws ApplicationException {

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
            throw new ApplicationException("Exception in Branch Manager Delete " + e.getMessage());
        } finally {
            session.close();
        }
    }

    /**
     * Update
     */
    @Override
    public void update(BranchManagerDTO dto) throws ApplicationException, DuplicateRecordException {

        Session session = null;
        Transaction tx = null;

        BranchManagerDTO existDto = findByManagerName(dto.getManagerName());
        if (existDto != null && existDto.getId() != dto.getId()) {
            throw new DuplicateRecordException("Manager Name already exists");
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
            throw new ApplicationException("Exception in Branch Manager Update " + e.getMessage());
        } finally {
            session.close();
        }
    }

    /**
     * Find By PK
     */
    @Override
    public BranchManagerDTO findByPk(long pk) throws ApplicationException {

        Session session = null;
        BranchManagerDTO dto = null;

        try {
            session = HibDataSource.getSession();
            dto = (BranchManagerDTO) session.get(BranchManagerDTO.class, pk);

        } catch (HibernateException e) {
            e.printStackTrace();
            throw new ApplicationException("Exception in getting Branch Manager by PK");
        } finally {
            session.close();
        }

        return dto;
    }

    /**
     * Find By Manager Name
     */
    @Override
    public BranchManagerDTO findByManagerName(String managerName) throws ApplicationException {

        Session session = null;
        BranchManagerDTO dto = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(BranchManagerDTO.class);
            criteria.add(Restrictions.eq("managerName", managerName));

            List list = criteria.list();

            if (list.size() == 1) {
                dto = (BranchManagerDTO) list.get(0);
            }

        } catch (HibernateException e) {
            e.printStackTrace();
            throw new ApplicationException("Exception in getting Branch Manager by Name " + e.getMessage());
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
            Criteria criteria = session.createCriteria(BranchManagerDTO.class);

            if (pageSize > 0) {
                pageNo = (pageNo - 1) * pageSize;
                criteria.setFirstResult(pageNo);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (HibernateException e) {
            e.printStackTrace();
            throw new ApplicationException("Exception in Branch Manager list");
        } finally {
            session.close();
        }

        return list;
    }

    /**
     * Search
     */
    @Override
    public List search(BranchManagerDTO dto) throws ApplicationException {
        return search(dto, 0, 0);
    }

    @Override
    public List search(BranchManagerDTO dto, int pageNo, int pageSize) throws ApplicationException {

        Session session = null;
        ArrayList<BranchManagerDTO> list = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(BranchManagerDTO.class);

            if (dto != null) {

                if (dto.getId() != null && dto.getId() > 0) {
                    criteria.add(Restrictions.eq("id", dto.getId()));
                }

                if (dto.getManagerName() != null && dto.getManagerName().length() > 0) {
                    criteria.add(Restrictions.like("managerName", dto.getManagerName() + "%"));
                }

                if (dto.getBranchName() != null && dto.getBranchName().length() > 0) {
                    criteria.add(Restrictions.like("branchName", dto.getBranchName() + "%"));
                }

                if (dto.getContactNumber() != null && dto.getContactNumber().length() > 0) {
                    criteria.add(Restrictions.like("contactNumber", dto.getContactNumber() + "%"));
                }
            }

            // Pagination
            if (pageSize > 0) {
                pageNo = (pageNo - 1) * pageSize;
                criteria.setFirstResult(pageNo);
                criteria.setMaxResults(pageSize);
            }

            list = (ArrayList<BranchManagerDTO>) criteria.list();

        } catch (HibernateException e) {
            e.printStackTrace();
            throw new ApplicationException("Exception in Branch Manager search");
        } finally {
            session.close();
        }

        return list;
    }
}