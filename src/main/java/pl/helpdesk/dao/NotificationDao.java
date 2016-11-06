package pl.helpdesk.dao;

import org.hibernate.criterion.Restrictions;

import pl.helpdesk.api.INotificationDao;
import pl.helpdesk.entity.Notification;

public class NotificationDao extends GenericDao<Notification,Integer> implements INotificationDao{

	@Override
	public Notification getNotificationByName(String notificationName) {
		Notification notification=(Notification)sessionFactory.getCurrentSession().createCriteria(Notification.class).
				add(Restrictions.eq("nazwa", notificationName))
				.uniqueResult();
		return notification;
	}

}
