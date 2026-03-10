package in.co.rays.project_3.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.VendorDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

/**
 * Hibernate implementation of Vendor Model
 * 
 * @author Deepak Verma
 */
public class VendorModelHibImpl implements VendorModelInt {

    /**
     * Add Vendor
     */
    @Override
    public long add(VendorDTO dto) throws ApplicationException, DuplicateRecordException {

        VendorDTO existDto = findByVendorName(dto.getVendorName());

        if (existDto != null) {
            throw new DuplicateRecordException("Vendor Name already exists");
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
            throw new ApplicationException("Exception in Vendor Add " + e.getMessage());
        } finally {
            session.close();
        }

        return dto.getId();
    }

    /**
     * Delete Vendor
     */
    @Override
    public void delete(VendorDTO dto) throws ApplicationException {

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
            throw new ApplicationException("Exception in Vendor Delete " + e.getMessage());
        } finally {
            session.close();
        }
    }

    /**
     * Update Vendor
     */
    @Override
    public void update(VendorDTO dto) throws ApplicationException, DuplicateRecordException {

        Session session = null;
        Transaction tx = null;

        VendorDTO existDto = findByVendorName(dto.getVendorName());
        if (existDto != null && existDto.getId() != dto.getId()) {
            throw new DuplicateRecordException("Vendor Name already exists");
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
            throw new ApplicationException("Exception in Vendor Update " + e.getMessage());
        } finally {
            session.close();
        }
    }

    /**
     * Find Vendor By PK
     */
    @Override
    public VendorDTO findByPk(long pk) throws ApplicationException {

        Session session = null;
        VendorDTO dto = null;

        try {
            session = HibDataSource.getSession();
            dto = (VendorDTO) session.get(VendorDTO.class, pk);

        } catch (HibernateException e) {
            e.printStackTrace();
            throw new ApplicationException("Exception in getting Vendor by PK");
        } finally {
            session.close();
        }

        return dto;
    }

    /**
     * Find Vendor By Name
     */
    @Override
    public VendorDTO findByVendorName(String vendorName) throws ApplicationException {

        Session session = null;
        VendorDTO dto = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(VendorDTO.class);
            criteria.add(Restrictions.eq("vendorName", vendorName));
            List list = criteria.list();

            if (list.size() == 1) {
                dto = (VendorDTO) list.get(0);
            }

        } catch (HibernateException e) {
            e.printStackTrace();
            throw new ApplicationException("Exception in getting Vendor by Name " + e.getMessage());
        } finally {
            session.close();
        }

        return dto;
    }

    /**
     * List Vendors
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
            Criteria criteria = session.createCriteria(VendorDTO.class);

            if (pageSize > 0) {
                pageNo = (pageNo - 1) * pageSize;
                criteria.setFirstResult(pageNo);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (HibernateException e) {
            e.printStackTrace();
            throw new ApplicationException("Exception in Vendor list");
        } finally {
            session.close();
        }

        return list;
    }

    /**
     * Search Vendor
     */
    @Override
    public List search(VendorDTO dto) throws ApplicationException {
        return search(dto, 0, 0);
    }

    @Override
    public List search(VendorDTO dto, int pageNo, int pageSize) throws ApplicationException {

        Session session = null;
        ArrayList<VendorDTO> list = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(VendorDTO.class);

            if (dto != null) {

                if (dto.getId() != null && dto.getId() > 0) {
                    criteria.add(Restrictions.eq("id", dto.getId()));
                }

                if (dto.getVendorName() != null && dto.getVendorName().length() > 0) {
                    criteria.add(Restrictions.like("vendorName", dto.getVendorName() + "%"));
                }

                if (dto.getContactNumber() != null && dto.getContactNumber().length() > 0) {
                    criteria.add(Restrictions.like("contactNumber", dto.getContactNumber() + "%"));
                }

                if (dto.getVendorStatus() != null && dto.getVendorStatus().length() > 0) {
                    criteria.add(Restrictions.like("vendorStatus", dto.getVendorStatus() + "%"));
                }
            }

            // Pagination
            if (pageSize > 0) {
                pageNo = (pageNo - 1) * pageSize;
                criteria.setFirstResult(pageNo);
                criteria.setMaxResults(pageSize);
            }

            list = (ArrayList<VendorDTO>) criteria.list();

        } catch (HibernateException e) {
            e.printStackTrace();
            throw new ApplicationException("Exception in Vendor search");
        } finally {
            session.close();
        }

        return list;
    }
}