package pl.helpdesk.dao;

import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import pl.helpdesk.api.IAdminDao;
import pl.helpdesk.entity.Admin;
import pl.helpdesk.entity.User;

/**
 * 
 * @author Adam Ulidowski
 *
 */
@Transactional
public class AdminDao  extends GenericDao<Admin,Integer> implements IAdminDao{
	
	public AdminDao(){
		super();
	}
	
	@Override
	public Boolean isAdmin(User user) {
		if(!sessionFactory.getCurrentSession().createCriteria(Admin.class)
			.add(Restrictions.eq("userDataModel", user)).list().isEmpty()){
			return true;
		}
		else
			return false;
	}
}
