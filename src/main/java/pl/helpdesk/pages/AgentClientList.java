package pl.helpdesk.pages;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import pl.helpdesk.api.IAdminDao;
import pl.helpdesk.api.IAgentDao;
import pl.helpdesk.api.IClientDao;
import pl.helpdesk.api.INotificationDao;
import pl.helpdesk.api.IUserDao;
import pl.helpdesk.api.IUserNotificationsDao;
import pl.helpdesk.entity.Admin;
import pl.helpdesk.entity.Agent;
import pl.helpdesk.entity.Client;
import pl.helpdesk.mailsender.mailSender;
import pl.helpdesk.userSession.ApplicationSession;

public class AgentClientList extends AgentSuccessPage {

	private static final long serialVersionUID = 1L;

	@SpringBean
	private IUserDao userDao;

	@SpringBean
	private IAdminDao adminDao;
	
	@SpringBean
	private IAgentDao agentDao;

	@SpringBean
	private IClientDao clientDao;
	
	@SpringBean
	private IUserNotificationsDao userNotificationsDao;
	
	@SpringBean
	private INotificationDao notificationDao;

	final Agent agent = agentDao.findAgentByUser(ApplicationSession.getInstance().getUser());


	public AgentClientList(PageParameters parameters) {
		super(parameters);
		if (!(ApplicationSession.getInstance().getUser() == null)
				&& agentDao.isAgent(ApplicationSession.getInstance().getUser())) {
		
		final ListView<?> listView;
		listView = new ListView<Client>("listview", clientDao.clientsFromAgent(agent)) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<Client> item) {
				final Client client = (Client) item.getModelObject();
				item.add(new Label("imie", client.getUserDataModel().getImie()));
				item.add(new Label("nazwisko", client.getUserDataModel().getNazwisko()));			
				item.add(new Label("email", client.getUserDataModel().getEmail()));
				item.add(new Label("ostatnie_logowanie", client.getUserDataModel().getOst_logowanie()));
				PageParameters a= new PageParameters();
				a.add("userId", client.getUserDataModel().getId());
				item.add(new BookmarkablePageLink<>("editUser", AgentEditUser.class, a));
				item.add(new BookmarkablePageLink<>("showRaport", AgentShowRaport.class, a));
				
				final Label blokujWyswietl = new Label("blokujWyswietl", new AbstractReadOnlyModel<String>() {
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override
					public String getObject() {
						if (client.getUserDataModel().getCzy_blokowany()) {
							return "Odblokuj";
						} else {
							return "Zablokuj";
						}
					}
				});
				
				final Link zablokujUsera = new Link<Object>("zablokujUsera") {

					private static final long serialVersionUID = 1L;

					@Override
					public void onClick() {
						List<Admin> allAdmins = adminDao.getAll();
						if (client.getUserDataModel().getCzy_blokowany()) {
							client.getUserDataModel().setCzy_blokowany(false);
							userDao.update(client.getUserDataModel());
							for (Admin allAdminss : allAdmins) {
								userNotificationsDao.addNotification(allAdminss.getUserDataModel(),
										notificationDao.getById(17), ApplicationSession.getInstance().getUser().getLogin());
							}
							userNotificationsDao.addNotification(client.getUserDataModel(),
									notificationDao.getById(21), "");
							userNotificationsDao.addNotification(ApplicationSession.getInstance().getUser(),
									notificationDao.getById(17), ApplicationSession.getInstance().getUser().getLogin());
						} else {
							client.getUserDataModel().setCzy_blokowany(true);
							userDao.update(client.getUserDataModel());
							for (Admin allAdminss : allAdmins) {
								userNotificationsDao.addNotification(allAdminss.getUserDataModel(),
										notificationDao.getById(9), ApplicationSession.getInstance().getUser().getLogin());
							}
							userNotificationsDao.addNotification(client.getUserDataModel(),
									notificationDao.getById(20), "");
							userNotificationsDao.addNotification(ApplicationSession.getInstance().getUser(),
									notificationDao.getById(9), ApplicationSession.getInstance().getUser().getLogin());
						}
						
						
						// DM - wyslanie maila
						sendMail(client);
						// DM stop
					}
				};
				item.add(zablokujUsera.add(blokujWyswietl));
				
				Label usunWyswietl = new Label("usunWyswietl", "Usuń");
				Label Usuniety = new Label("czyUsuniety", "Usunięty");
				Usuniety.setVisible(false);

				final Link usunUsera = new Link<Object>("usunUsera") {

					private static final long serialVersionUID = 1L;

					@Override
					public void onClick() {
						List<Admin> allAdmins = adminDao.getAll();
						client.getUserDataModel().setCzy_usuniety(true);
						userDao.update(client.getUserDataModel());
						for (Admin allAdminss : allAdmins) {
							userNotificationsDao.addNotification(allAdminss.getUserDataModel(),
									notificationDao.getById(10), ApplicationSession.getInstance().getUser().getLogin());
						}
						userNotificationsDao.addNotification(ApplicationSession.getInstance().getUser(),
								notificationDao.getById(10), ApplicationSession.getInstance().getUser().getLogin());
						// DM - wyslanie maila
						sendMail(client);
						// DM stop
					}
				};
				usunUsera.setVisible(false);
				item.add(usunUsera.add(usunWyswietl));
				item.add(Usuniety);
				if (client.getUserDataModel().getCzy_usuniety()) {
					zablokujUsera.setVisible(false);
					Usuniety.setVisible(true);
				} else {
					usunUsera.setVisible(true);
					zablokujUsera.setVisible(true);
				}
			}
		};

		add(listView);
	}else{
		setResponsePage(LoginPage.class);
	}
	}
	
	// DM start
		private void sendMail(final Client client) {
			
			String statusKlienta;
			if(client.getUserDataModel().getCzy_blokowany()) statusKlienta = "zablokowane";
			else if (client.getUserDataModel().getCzy_usuniety()) statusKlienta = "usunięte";
			else statusKlienta = "odblokowane";
			
			mailSender mailsender = new mailSender();
			mailsender.sendNotify("Powiadomienie - status konta", 
					"Adresatem tej wiadomości jest " + client.getUserDataModel().getImie() + " " + client.getUserDataModel().getNazwisko() + "\nTwoje konto o loginie " + client.getUserDataModel().getLogin() + " zostało " + statusKlienta + "!", 
					new Date(), 
					client.getUserDataModel());
			
			
		};
	// DM stop
}