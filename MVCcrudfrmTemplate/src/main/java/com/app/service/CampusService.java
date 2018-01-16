package com.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.ICampusDao;
import com.app.model.TblCampus;

@Service
public class CampusService implements ICampusService {
    @Autowired
    ICampusDao campusDao;
    @Autowired
  //  IRuleService iRuleService;

    /* (non-Javadoc)
	 * @see com.app.service.ICampusService#getAll()
	 */
   
	@Override
    public List<TblCampus> getAll() {
        return campusDao.getAll();
    }

   
	/*@Override
    public List<ParentChildTo> getAllCampusParentChildList(final int ruleId) {
      List<ParentChildTo> campusList = new ArrayList<ParentChildTo>();
        List<TblCampus> allCampus = getAll();
        List<TblRuleApply> byRuleIdType = iRuleService.getByRuleIdType(ruleId, "CAMPUS");
        for (TblCampus tblCampus : allCampus) {
            ParentChildTo tblCampusTo = new ParentChildTo();
            tblCampusTo.setId("CAMPUS-" + String.valueOf(tblCampus.getId()));
            tblCampusTo.setText(tblCampus.getCampusName());
            tblCampusTo.setParent("#");
            // tblCampusTo.setIcon(tblCampus.);
            boolean anyMatch = byRuleIdType.stream().anyMatch(ruleApply -> ruleApply.getKeyId() == tblCampus.getId());
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("opened", anyMatch);
            map.put("selected", anyMatch);

            tblCampusTo.setState(map);

            campusList.add(tblCampusTo);
        }
        return campusList;
    }
*/
    /* (non-Javadoc)
	 * @see com.app.service.ICampusService#getByCampusCode(java.lang.String)
	 */
    @Override
    public TblCampus getByCampusCode(final String campusCode) {
        return campusDao.getByCampusCode(campusCode);
    }

    /* (non-Javadoc)
	 * @see com.app.service.ICampusService#getById(int)
	 */
    @Override
    public TblCampus getById(final int id) {
        return campusDao.getById(id);
    }

    /* (non-Javadoc)
	 * @see com.app.service.ICampusService#isCampusExist(java.lang.String, int)
	 */
   
	@Override
    public boolean isCampusExist(final String campusCode, final int campusId) {
        return campusDao.isCampusExist(campusCode, campusId);
    }

    /* (non-Javadoc)
	 * @see com.app.service.ICampusService#saveOrUpdate(com.app.model.TblCampus)
	 */
    
	@Override
    public void saveOrUpdate(final TblCampus campus) {
        campusDao.saveOrUpdate(campus);
    }
}