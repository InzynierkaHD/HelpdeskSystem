package pl.helpdesk.api;

import pl.helpdesk.entity.Admin;
import pl.helpdesk.entity.User;

public interface IAdminDao extends IGenericDao<Admin,Integer>{

	public Boolean isAdmin(User user);
}
