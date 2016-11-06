package pl.helpdesk.api;


import pl.helpdesk.entity.Notification;

public interface INotificationDao extends IGenericDao<Notification,Integer>{
	Notification getNotificationByName(String notificationName);
}
