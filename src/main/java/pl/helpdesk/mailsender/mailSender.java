package pl.helpdesk.mailsender;

import javax.mail.*; 
import javax.mail.internet.*;

import org.apache.wicket.spring.injection.annot.SpringBean;

import pl.helpdesk.entity.Notification;
import pl.helpdesk.entity.User;
import pl.helpdesk.entity.UserNotifications;
import pl.helpdesk.api.INotificationDao;
import pl.helpdesk.api.IUserNotificationsDao;
import pl.helpdesk.mailsender.mailSender;

import java.util.Date;
import java.util.Properties; 

public class mailSender {
	
	@SpringBean
	private INotificationDao notificationDao;
	
	@SpringBean
	private IUserNotificationsDao userNotificationsDao;
	
	public void sendMail(String from, String to, String subject, String messageBody) throws MessagingException, AddressException 
	{ 
		  Properties ustawienia = new Properties();
		  ustawienia.put("mail.transport.protocol", "smtps");
		  ustawienia.put("mail.smtps.auth", "true");

		  // init sesji
		  Session sesjaMail = Session.getDefaultInstance(ustawienia);

		  // stworzenie ciala maila
		  MimeMessage wiadomosc = new MimeMessage(sesjaMail);
		  wiadomosc.setSubject(subject);
		  wiadomosc.setContent(messageBody, "text/plain; charset=ISO-8859-2");
		  wiadomosc.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

		  Transport trsp = sesjaMail.getTransport();
		  trsp.connect("smtp.gmail.com", 465, from, "inzynierka");

		  // wysłanie
		  trsp.sendMessage(wiadomosc, wiadomosc
		    .getRecipients(Message.RecipientType.TO));
		  trsp.close();

	}
	
	public void sendNotify(String nazwa, String tresc, Date dataWyslania, User user)
	{ 
		
		Notification notification = new Notification();
		notification.setNazwa(nazwa);
		notification.setTresc(tresc);
		//notificationDao.save(notification);
		
		
		UserNotifications userNotifications = new UserNotifications();
		userNotifications.setDataWyslania(dataWyslania);
		userNotifications.setNotificationDataModel(notification);
		userNotifications.setUserDataModel(user);
		userNotifications.setEmail(user.getEmail());
		//userNotificationsDao.save(userNotifications);
		
		try {
			sendMail("pracownikhelpdesku", 
					userNotifications.getEmail(), 
					nazwa, 
					tresc
			);
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}

