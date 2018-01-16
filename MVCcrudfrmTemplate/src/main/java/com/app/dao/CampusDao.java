package com.app.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.app.common.Constants;
import com.app.model.TblCampus;




@Repository
@Transactional
public class CampusDao implements ICampusDao {
    @Autowired
    SessionFactory factory;

    /* (non-Javadoc)
	 * @see com.app.dao.ICampusDao#getAll()
	 */
    
	@Override
    public List<TblCampus> getAll() {
        Criteria criteria = getSession().createCriteria(TblCampus.class);
        criteria.add(Restrictions.eq("deletionIndicator", Constants.N));
        return criteria.list();
    }

    /* (non-Javadoc)
	 * @see com.app.dao.ICampusDao#getByCampusCode(java.lang.String)
	 */
   
	@Override
    public TblCampus getByCampusCode(final String campusCode) {
        Criteria criteria = getSession().createCriteria(TblCampus.class);
        criteria.add(Restrictions.eq("deletionIndicator", Constants.N));
        criteria.add(Restrictions.eq("campusCode", campusCode));
        return (TblCampus) criteria.uniqueResult();
    }

    /* (non-Javadoc)
	 * @see com.app.dao.ICampusDao#getById(int)
	 */
  
	@Override
    public TblCampus getById(final int id) {
        return (TblCampus) getSession().get(TblCampus.class, id);

    }

    /**
     * @return
     */
    private Session getSession() {
        return factory.getCurrentSession();
    }

    /* (non-Javadoc)
	 * @see com.app.dao.ICampusDao#isCampusExist(java.lang.String, int)
	 */
    
	@Override
    public boolean isCampusExist(final String campusCode, final int campusId) {
        Criteria criteria = getSession().createCriteria(TblCampus.class);
        criteria.add(Restrictions.ne("id", campusId));
        criteria.add(Restrictions.eq("deletionIndicator", Constants.N));
        criteria.add(Restrictions.eq("campusCode", campusCode));
        TblCampus tblCampus = (TblCampus) criteria.uniqueResult();
        if (tblCampus != null) {
            return true;
        }
        else {
            return false;
        }
    }

    /* (non-Javadoc)
	 * @see com.app.dao.ICampusDao#saveOrUpdate(com.app.model.TblCampus)
	 */
 
	@Override
    public void saveOrUpdate(final TblCampus campus) {
        getSession().saveOrUpdate(campus);
    }
}