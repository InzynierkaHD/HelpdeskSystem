package pl.helpdesk.mailsender;

import javax.mail.*; 
import javax.mail.internet.*;

import pl.helpdesk.entity.User;

import java.util.Properties; 

public class mailSender {
	
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

}

