package pl.helpdesk.dao;

import java.util.List;

import org.hibernate.criterion.Restrictions;

import pl.helpdesk.api.IUserNotificationsDao;
import pl.helpdesk.entity.UserNotifications;
import pl.helpdesk.entity.Comment;
import pl.helpdesk.entity.Issue;
import pl.helpdesk.entity.User;

public class UserNotificationsDao extends GenericDao<UserNotifications,Integer> implements IUserNotificationsDao{

	public List<UserNotifications> getNotificationByUser(User user){
		if(user != null) System.out.println("user w dao "+ user);
		else System.out.println("user jest null");
		List<UserNotifications> listOfUserNotifications = sessionFactory.getCurrentSession().createCriteria(UserNotifications.class)
		.add(Restrictions.eq("user", user)).list();
		return listOfUserNotifications;
		
	};
	
	
}