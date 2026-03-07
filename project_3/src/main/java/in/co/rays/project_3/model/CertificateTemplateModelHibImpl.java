package in.co.rays.project_3.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.CertificateTemplateDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

/**
 * Hibernate implementation of CertificateTemplate Model
 * 
 * @author Deepak Verma
 *
 */
public class CertificateTemplateModelHibImpl implements CertificateTemplateModelInt {

    /**
     * Add Certificate Template
     */
    @Override
    public long add(CertificateTemplateDTO dto) throws ApplicationException, DuplicateRecordException {

        CertificateTemplateDTO existDto = findByTemplateName(dto.getTemplateName());

        if (existDto != null) {
            throw new DuplicateRecordException("Template Name already exists");
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
            throw new ApplicationException("Exception in CertificateTemplate Add " + e.getMessage());
        } finally {
            session.close();
        }

        return dto.getId();
    }

    /**
     * Delete Certificate Template
     */
    @Override
    public void delete(CertificateTemplateDTO dto) throws ApplicationException {

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
            throw new ApplicationException("Exception in CertificateTemplate Delete " + e.getMessage());
        } finally {
            session.close();
        }
    }

    /**
     * Update Certificate Template
     */
    @Override
    public void update(CertificateTemplateDTO dto) throws ApplicationException, DuplicateRecordException {

        Session session = null;
        Transaction tx = null;

        CertificateTemplateDTO existDto = findByTemplateName(dto.getTemplateName());
        if (existDto != null && existDto.getId() != dto.getId()) {
            throw new DuplicateRecordException("Template Name already exists");
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
            throw new ApplicationException("Exception in CertificateTemplate Update " + e.getMessage());
        } finally {
            session.close();
        }
    }

    /**
     * Find By PK
     */
    @Override
    public CertificateTemplateDTO findByPk(long pk) throws ApplicationException {

        Session session = null;
        CertificateTemplateDTO dto = null;

        try {
            session = HibDataSource.getSession();
            dto = (CertificateTemplateDTO) session.get(CertificateTemplateDTO.class, pk);

        } catch (HibernateException e) {
            e.printStackTrace();
            throw new ApplicationException("Exception in getting CertificateTemplate by PK");
        } finally {
            session.close();
        }

        return dto;
    }

    /**
     * Find By Template Name
     */
    @Override
    public CertificateTemplateDTO findByTemplateName(String templateName) throws ApplicationException {

        Session session = null;
        CertificateTemplateDTO dto = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(CertificateTemplateDTO.class);
            criteria.add(Restrictions.eq("templateName", templateName));

            List list = criteria.list();

            if (list.size() == 1) {
                dto = (CertificateTemplateDTO) list.get(0);
            }

        } catch (HibernateException e) {
            e.printStackTrace();
            throw new ApplicationException("Exception in getting CertificateTemplate by TemplateName " + e.getMessage());
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
            Criteria criteria = session.createCriteria(CertificateTemplateDTO.class);

            if (pageSize > 0) {
                pageNo = (pageNo - 1) * pageSize;
                criteria.setFirstResult(pageNo);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (HibernateException e) {
            e.printStackTrace();
            throw new ApplicationException("Exception in CertificateTemplate list");
        } finally {
            session.close();
        }

        return list;
    }

    /**
     * Search
     */
    @Override
    public List search(CertificateTemplateDTO dto) throws ApplicationException {
        return search(dto, 0, 0);
    }

    @Override
    public List search(CertificateTemplateDTO dto, int pageNo, int pageSize) throws ApplicationException {

        Session session = null;
        ArrayList<CertificateTemplateDTO> list = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(CertificateTemplateDTO.class);

            if (dto != null) {

                if (dto.getId() != null && dto.getId() > 0) {
                    criteria.add(Restrictions.eq("id", dto.getId()));
                }

                if (dto.getTemplateName() != null && dto.getTemplateName().length() > 0) {
                    criteria.add(Restrictions.like("templateName", dto.getTemplateName() + "%"));
                }

                if (dto.getFormat() != null && dto.getFormat().length() > 0) {
                    criteria.add(Restrictions.like("format", dto.getFormat() + "%"));
                }
            }

            // Pagination
            if (pageSize > 0) {
                pageNo = (pageNo - 1) * pageSize;
                criteria.setFirstResult(pageNo);
                criteria.setMaxResults(pageSize);
            }

            list = (ArrayList<CertificateTemplateDTO>) criteria.list();

        } catch (HibernateException e) {
            e.printStackTrace();
            throw new ApplicationException("Exception in CertificateTemplate search");
        } finally {
            session.close();
        }

        return list;
    }
}