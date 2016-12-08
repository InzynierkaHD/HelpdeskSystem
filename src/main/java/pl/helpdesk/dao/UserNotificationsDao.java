package pl.helpdesk.dao;

import java.util.Date;
import java.util.List;


import org.apache.wicket.spring.injection.annot.SpringBean;
import org.hibernate.criterion.Restrictions;

import pl.helpdesk.api.INotificationDao;
import pl.helpdesk.api.IUserDao;
import pl.helpdesk.api.IUserNotificationsDao;
import pl.helpdesk.entity.UserNotifications;
import pl.helpdesk.entity.Comment;
import pl.helpdesk.entity.Issue;
import pl.helpdesk.entity.Notification;
import pl.helpdesk.entity.User;


public class UserNotificationsDao extends GenericDao<UserNotifications,Integer> implements IUserNotificationsDao{

	
	
	public List<UserNotifications> getNotificationByUser(User user){
		List<UserNotifications> listOfUserNotifications = sessionFactory.getCurrentSession().createCriteria(UserNotifications.class)
		.add(Restrictions.eq("userDataModel", user)).list();
		return listOfUserNotifications;
		
	};

	
	public void addNotification(User user, Notification notification){
		UserNotifications newUN = new UserNotifications();
		newUN.setEmail(user.getEmail());
		newUN.setDataWyslania(new Date());
		newUN.setUserDataModel(user);
		newUN.setNotificationDataModel(notification);
		save(newUN);
	}

	

	
}