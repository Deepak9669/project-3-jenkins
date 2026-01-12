package in.co.rays.project_3.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.BusDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

/**
 * Hibernate implements of Bus model
 * 
 * @author Deepak Verma
 *
 */
public class BusModelHibImpl implements BusModelInt {

    /**
     * Add Bus
     */
    @Override
    public long add(BusDTO dto) throws ApplicationException, DuplicateRecordException {

        BusDTO existDto = null;
        existDto = findByBusNumber(dto.getBusNumber());

        if (existDto != null) {
            throw new DuplicateRecordException("Bus Number already exists");
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
            throw new ApplicationException("Exception in Bus Add " + e.getMessage());
        } finally {
            session.close();
        }

        return dto.getId();
    }

    /**
     * Delete Bus
     */
    @Override
    public void delete(BusDTO dto) throws ApplicationException {

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
            throw new ApplicationException("Exception in Bus Delete " + e.getMessage());
        } finally {
            session.close();
        }
    }

    /**
     * Update Bus
     */
    @Override
    public void update(BusDTO dto) throws ApplicationException, DuplicateRecordException {

        Session session = null;
        Transaction tx = null;

        BusDTO existDto = findByBusNumber(dto.getBusNumber());
        if (existDto != null && existDto.getId() != dto.getId()) {
            throw new DuplicateRecordException("Bus Number already exists");
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
            throw new ApplicationException("Exception in Bus Update " + e.getMessage());
        } finally {
            session.close();
        }
    }

    /**
     * Find By PK
     */
    @Override
    public BusDTO findByPk(long pk) throws ApplicationException {

        Session session = null;
        BusDTO dto = null;

        try {
            session = HibDataSource.getSession();
            dto = (BusDTO) session.get(BusDTO.class, pk);

        } catch (HibernateException e) {
            e.printStackTrace();
            throw new ApplicationException("Exception : Exception in getting Bus by PK");
        } finally {
            session.close();
        }

        return dto;
    }

    /**
     * Find By Bus Number
     */
    @Override
    public BusDTO findByBusNumber(String busNumber) throws ApplicationException {

        Session session = null;
        BusDTO dto = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(BusDTO.class);
            criteria.add(Restrictions.eq("busNumber", busNumber));
            List list = criteria.list();

            if (list.size() == 1) {
                dto = (BusDTO) list.get(0);
            }

        } catch (HibernateException e) {
            e.printStackTrace();
            throw new ApplicationException("Exception in getting Bus by BusNumber " + e.getMessage());
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
            Criteria criteria = session.createCriteria(BusDTO.class);

            if (pageSize > 0) {
                pageNo = (pageNo - 1) * pageSize;
                criteria.setFirstResult(pageNo);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (HibernateException e) {
            e.printStackTrace();
            throw new ApplicationException("Exception : Exception in Bus list");
        } finally {
            session.close();
        }

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

        Session session = null;
        ArrayList<BusDTO> list = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(BusDTO.class);

            if (dto != null) {

                if (dto.getId() != null && dto.getId() > 0) {
                    criteria.add(Restrictions.eq("id", dto.getId()));
                }

                if (dto.getBusNumber() != null && dto.getBusNumber().length() > 0) {
                    criteria.add(Restrictions.like("busNumber", dto.getBusNumber() + "%"));
                }

                if (dto.getBusType() != null && dto.getBusType().length() > 0) {
                    criteria.add(Restrictions.like("busType", dto.getBusType() + "%"));
                }

                if (dto.getTotalSeats() != null && dto.getTotalSeats().length() > 0) {
                    criteria.add(Restrictions.like("totalSeats", dto.getTotalSeats() + "%"));
                }

                if (dto.getSource() != null && dto.getSource().length() > 0) {
                    criteria.add(Restrictions.like("source", dto.getSource() + "%"));
                }

                if (dto.getDestination() != null && dto.getDestination().length() > 0) {
                    criteria.add(Restrictions.like("destination", dto.getDestination() + "%"));
                }
            }

            // Pagination
            if (pageSize > 0) {
                pageNo = (pageNo - 1) * pageSize;
                criteria.setFirstResult(pageNo);
                criteria.setMaxResults(pageSize);
            }

            list = (ArrayList<BusDTO>) criteria.list();

        } catch (HibernateException e) {
            e.printStackTrace();
            throw new ApplicationException("Exception in Bus search");
        } finally {
            session.close();
        }

        return list;
    }
}
