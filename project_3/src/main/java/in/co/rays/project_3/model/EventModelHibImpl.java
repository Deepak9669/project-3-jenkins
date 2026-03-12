package in.co.rays.project_3.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.EventDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

/**
 * Hibernate implementation of Event Model
 */

public class EventModelHibImpl implements EventModelInt {

    /**
     * Add Event
     */
    @Override
    public long add(EventDTO dto) throws ApplicationException, DuplicateRecordException {

        EventDTO existDto = findByEventName(dto.getEventName());

        if (existDto != null) {
            throw new DuplicateRecordException("Event Name already exists");
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
            throw new ApplicationException("Exception in Event Add " + e.getMessage());
        } finally {
            session.close();
        }

        return dto.getId();
    }

    /**
     * Delete Event
     */
    @Override
    public void delete(EventDTO dto) throws ApplicationException {

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
            throw new ApplicationException("Exception in Event Delete " + e.getMessage());
        } finally {
            session.close();
        }
    }

    /**
     * Update Event
     */
    @Override
    public void update(EventDTO dto) throws ApplicationException, DuplicateRecordException {

        Session session = null;
        Transaction tx = null;

        EventDTO existDto = findByEventName(dto.getEventName());
        if (existDto != null && existDto.getId() != dto.getId()) {
            throw new DuplicateRecordException("Event Name already exists");
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
            throw new ApplicationException("Exception in Event Update " + e.getMessage());
        } finally {
            session.close();
        }
    }

    /**
     * Find Event By PK
     */
    @Override
    public EventDTO findByPk(long pk) throws ApplicationException {

        Session session = null;
        EventDTO dto = null;

        try {
            session = HibDataSource.getSession();
            dto = (EventDTO) session.get(EventDTO.class, pk);

        } catch (HibernateException e) {
            e.printStackTrace();
            throw new ApplicationException("Exception in getting Event by PK");
        } finally {
            session.close();
        }

        return dto;
    }

    /**
     * Find Event By Name
     */
    @Override
    public EventDTO findByEventName(String eventName) throws ApplicationException {

        Session session = null;
        EventDTO dto = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(EventDTO.class);
            criteria.add(Restrictions.eq("eventName", eventName));
            List list = criteria.list();

            if (list.size() == 1) {
                dto = (EventDTO) list.get(0);
            }

        } catch (HibernateException e) {
            e.printStackTrace();
            throw new ApplicationException("Exception in getting Event by Name " + e.getMessage());
        } finally {
            session.close();
        }

        return dto;
    }

    /**
     * List Events
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
            Criteria criteria = session.createCriteria(EventDTO.class);

            if (pageSize > 0) {
                pageNo = (pageNo - 1) * pageSize;
                criteria.setFirstResult(pageNo);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (HibernateException e) {
            e.printStackTrace();
            throw new ApplicationException("Exception in Event list");
        } finally {
            session.close();
        }

        return list;
    }

    /**
     * Search Event
     */
    @Override
    public List search(EventDTO dto) throws ApplicationException {
        return search(dto, 0, 0);
    }

    @Override
    public List search(EventDTO dto, int pageNo, int pageSize) throws ApplicationException {

        Session session = null;
        ArrayList<EventDTO> list = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(EventDTO.class);

            if (dto != null) {

                if (dto.getId() != null && dto.getId() > 0) {
                    criteria.add(Restrictions.eq("id", dto.getId()));
                }

                if (dto.getParticipentName() != null && dto.getParticipentName().length() > 0) {
                    criteria.add(Restrictions.like("participentName", dto.getParticipentName() + "%"));
                }

                if (dto.getEventName() != null && dto.getEventName().length() > 0) {
                    criteria.add(Restrictions.like("eventName", dto.getEventName() + "%"));
                }

                if (dto.getEmail() != null && dto.getEmail().length() > 0) {
                    criteria.add(Restrictions.like("email", dto.getEmail() + "%"));
                }
            }

            // Pagination
            if (pageSize > 0) {
                pageNo = (pageNo - 1) * pageSize;
                criteria.setFirstResult(pageNo);
                criteria.setMaxResults(pageSize);
            }

            list = (ArrayList<EventDTO>) criteria.list();

        } catch (HibernateException e) {
            e.printStackTrace();
            throw new ApplicationException("Exception in Event search");
        } finally {
            session.close();
        }

        return list;
    }
}