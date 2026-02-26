package in.co.rays.project_3.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.ExpenseDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class ExpenseModelHibImpl implements ExpenseModelInt {

    /**
     * Add Expense
     */
    @Override
    public long add(ExpenseDTO dto) throws ApplicationException, DuplicateRecordException {

        ExpenseDTO existDto = findByExpenseCode(dto.getExpenseCode());

        if (existDto != null) {
            throw new DuplicateRecordException("ExpenseCode already exists");
        }

        Session session = HibDataSource.getSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.save(dto);
            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw new ApplicationException("Exception in Expense Add " + e.getMessage());
        } finally {
            session.close();
        }

        return dto.getId();
    }

    /**
     * Delete Expense
     */
    @Override
    public void delete(ExpenseDTO dto) throws ApplicationException {

        Session session = null;
        Transaction tx = null;

        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            session.delete(dto);
            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw new ApplicationException("Exception in Expense Delete " + e.getMessage());
        } finally {
            session.close();
        }
    }

    /**
     * Update Expense
     */
    @Override
    public void update(ExpenseDTO dto) throws ApplicationException, DuplicateRecordException {

        Session session = null;
        Transaction tx = null;

        ExpenseDTO existDto = findByExpenseCode(dto.getExpenseCode());
        if (existDto != null && existDto.getId() != dto.getId()) {
            throw new DuplicateRecordException("ExpenseCode already exists");
        }

        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            session.saveOrUpdate(dto);
            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw new ApplicationException("Exception in Expense Update " + e.getMessage());
        } finally {
            session.close();
        }
    }

    /**
     * Find By PK
     */
    @Override
    public ExpenseDTO findByPk(long pk) throws ApplicationException {

        Session session = null;
        ExpenseDTO dto = null;

        try {
            session = HibDataSource.getSession();
            dto = (ExpenseDTO) session.get(ExpenseDTO.class, pk);

        } catch (HibernateException e) {
            throw new ApplicationException("Exception in getting Expense by PK");
        } finally {
            session.close();
        }

        return dto;
    }

    /**
     * Find By ExpenseCode (Internal Use)
     */
    public ExpenseDTO findByExpenseCode(String expenseCode) throws ApplicationException {

        Session session = null;
        ExpenseDTO dto = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(ExpenseDTO.class);
            criteria.add(Restrictions.eq("expenseCode", expenseCode));

            List list = criteria.list();

            if (list.size() == 1) {
                dto = (ExpenseDTO) list.get(0);
            }

        } catch (HibernateException e) {
            throw new ApplicationException("Exception in getting Expense by Code");
        } finally {
            session.close();
        }

        return dto;
    }

    /**
     * Find By Expense Status
     */
    @Override
    public ExpenseDTO findByexpenseStatus(String expenseStatus) throws ApplicationException {

        Session session = null;
        ExpenseDTO dto = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(ExpenseDTO.class);
            criteria.add(Restrictions.eq("expenseStatus", expenseStatus));

            List list = criteria.list();

            if (list.size() == 1) {
                dto = (ExpenseDTO) list.get(0);
            }

        } catch (HibernateException e) {
            throw new ApplicationException("Exception in getting Expense by Status");
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
            Criteria criteria = session.createCriteria(ExpenseDTO.class);

            if (pageSize > 0) {
                pageNo = (pageNo - 1) * pageSize;
                criteria.setFirstResult(pageNo);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (HibernateException e) {
            throw new ApplicationException("Exception in Expense list");
        } finally {
            session.close();
        }

        return list;
    }

    /**
     * Search
     */
    @Override
    public List search(ExpenseDTO dto) throws ApplicationException {
        return search(dto, 0, 0);
    }

    @Override
    public List search(ExpenseDTO dto, int pageNo, int pageSize) throws ApplicationException {

        Session session = null;
        ArrayList<ExpenseDTO> list = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(ExpenseDTO.class);

            if (dto != null) {

                if (dto.getId() != null && dto.getId() > 0) {
                    criteria.add(Restrictions.eq("id", dto.getId()));
                }

                if (dto.getExpenseCode() != null && dto.getExpenseCode().length() > 0) {
                    criteria.add(Restrictions.like("expenseCode", dto.getExpenseCode() + "%"));
                }

                if (dto.getExpenseType() != null && dto.getExpenseType().length() > 0) {
                    criteria.add(Restrictions.like("expenseType", dto.getExpenseType() + "%"));
                }

                if (dto.getExpenseStatus() != null && dto.getExpenseStatus().length() > 0) {
                    criteria.add(Restrictions.like("expenseStatus", dto.getExpenseStatus() + "%"));
                }
            }

            // Pagination
            if (pageSize > 0) {
                pageNo = (pageNo - 1) * pageSize;
                criteria.setFirstResult(pageNo);
                criteria.setMaxResults(pageSize);
            }

            list = (ArrayList<ExpenseDTO>) criteria.list();

        } catch (HibernateException e) {
            throw new ApplicationException("Exception in Expense search");
        } finally {
            session.close();
        }

        return list;
    }
}