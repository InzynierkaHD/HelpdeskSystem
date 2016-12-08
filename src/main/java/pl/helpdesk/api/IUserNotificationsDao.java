package pl.helpdesk.api;

import java.util.List;

import pl.helpdesk.entity.Notification;
import pl.helpdesk.entity.User;
import pl.helpdesk.entity.UserNotifications;

public interface IUserNotificationsDao extends IGenericDao<UserNotifications,Integer>{
	List<UserNotifications> getNotificationByUser(User user);
	
	 void addNotification(User user, Notification notification);
}
