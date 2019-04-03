package com.erry.auth.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.erry.auth.model.BPSRole;
import com.erry.auth.model.UserRoleRel;
import com.erry.auth.repository.UserRoleRelRepository;
import com.erry.util.GuidIdUtil;
import com.erry.util.StringUtil;

@Service
@Transactional
public class BPSRoleServiceImpl {
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private UserRoleRelRepository userrolerelrepository;

	public List<BPSRole> findList(String userid) {
		StringBuffer sql =new StringBuffer(" ");
		sql.append("  SELECT  br.*, 'true' checked FROM dbo.T_BPS_Role br LEFT JOIN T_BPS_User_Role_Rel ur ON br.RoleID =ur.RoleID  ");
		sql.append("  WHERE   br.Active='1' ");
		if(!StringUtil.isEmpty(userid)){
			sql.append(" AND ur.UserID='"+userid+"' AND ur.Active='1'   ");
		}
		sql.append(" UNION ");
		
		sql.append("  SELECT br.*,'false'checked  FROM dbo.T_BPS_Role br WHERE  br.Active='1'    ");
		if(!StringUtil.isEmpty(userid)){
			sql.append("   AND br.RoleID NOT IN  (SELECT RoleID FROM dbo.T_BPS_User_Role_Rel  WHERE UserID ='"+userid+"' AND Active='1' )  ");
		}
		Query query = entityManager.createNativeQuery(sql.toString());
		List<BPSRole> r = new ArrayList<BPSRole>();
		List<Object[]> objArrList = query.getResultList();
		for(Object[] objArr : objArrList)
		{
			BPSRole dto = new BPSRole();
			
			dto.setRoleid(String.valueOf(objArr[0]));
			dto.setRolecode(String.valueOf(objArr[1]));
			dto.setRolename(String.valueOf(objArr[2]));
			dto.setRoletype(String.valueOf(objArr[3]));
			dto.setRemark(String.valueOf(objArr[4]));
			dto.setActive(String.valueOf(objArr[5]));
			if(objArr[6].equals("true")){
				dto.setChecked(true);
			}else{
				dto.setChecked(false);
			}
			
			r.add(dto);
		}
		return r;
	}

	public void SaveOrUpdate(String userid, List<String> roleids) {
		// TODO Auto-generated method stub
		List<UserRoleRel> urs = userrolerelrepository.findByUserid(userid);
		UserRoleRel ur=new UserRoleRel();
		if(urs.size()>0){
			UpdateActive(urs);
			for (int i = 0; i < roleids.size(); i++) {
				ur=userrolerelrepository.findByURId(userid, roleids.get(i));
				if(ur!=null){
					ur.setActive("1");
					entityManager.merge(ur);
				}else{
					AddInfo(userid, roleids.get(i));
				}
			}
			
		}else{
			for (int i = 0; i < roleids.size(); i++) {
					AddInfo(userid, roleids.get(i));
			}
		}
		
		
	}
	public void AddInfo(String userid, String roleid){
		UserRoleRel ur=new UserRoleRel();
		ur.setId(GuidIdUtil.getGuId());
		ur.setUserid(userid);
		ur.setRoleid(roleid);
		ur.setActive("1");
		entityManager.persist(ur);
		
	}
	public void UpdateActive(List<UserRoleRel> urs){
		for (UserRoleRel ur:urs) {
			ur.setActive("0");
			entityManager.merge(ur);
		}
		
	}

}
