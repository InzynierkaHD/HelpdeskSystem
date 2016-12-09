package pl.helpdesk.pages;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import pl.helpdesk.api.IAdminDao;
import pl.helpdesk.api.IAgentDao;
import pl.helpdesk.api.INotificationDao;
import pl.helpdesk.api.IUserDao;
import pl.helpdesk.api.IUserNotificationsDao;
import pl.helpdesk.components.SelectForm;
import pl.helpdesk.entity.Admin;
import pl.helpdesk.entity.Agent;
import pl.helpdesk.entity.Client;
import pl.helpdesk.entity.User;
import pl.helpdesk.mailsender.mailSender;
import pl.helpdesk.userSession.ApplicationSession;

public class AdminAgentList extends AdminSuccessPage {

	private static final long serialVersionUID = 1L;

	private User userDataModel;

	private static final List<String> SORTUJ = Arrays
			.asList(new String[] { "Nazwisko", "E-mail", "Ostatnie logowanie", "Blokowany", "Firma" });

	private String selected= "Nazwisko";
	
	@SpringBean
	private IAgentDao agentDao;

	@SpringBean
	private IAdminDao adminDao;

	@SpringBean
	private IUserDao userDao;
	
	@SpringBean
	private IUserNotificationsDao userNotificationsDao;
	
	@SpringBean
	private INotificationDao notificationDao;

	public AdminAgentList(PageParameters parameters) {
		super(parameters);

		if (!(ApplicationSession.getInstance().getUser() == null)
				&& adminDao.isAdmin(ApplicationSession.getInstance().getUser())) {

			userDataModel = new User();
			final SelectForm sortBy = new SelectForm("sortowanie", new PropertyModel<String>(this, "selected"), SORTUJ);

			final TextField<String> serachingSurname = new TextField<String>("serachingSurname",
					new PropertyModel<String>(userDataModel, "nazwisko"));
			Form<?> form = new Form<Void>("form") {
				private static final long serialVersionUID = 1L;

				@Override
				protected void onSubmit() {

					PageParameters parameterss = new PageParameters();
					String sortByOK = sortBy.getValue();
					parameterss.set("sortBy", sortByOK);
					String serachingSurnameOK = serachingSurname.getInput();
					parameterss.set("serachingSurname", serachingSurnameOK);
					setResponsePage(AdminAgentList.class, parameterss);
				}
			};

			add(form);
			form.add(sortBy);
			form.add(serachingSurname);

			WebMarkupContainer datacontainer = new WebMarkupContainer("data");
			datacontainer.setOutputMarkupId(true);
			add(datacontainer);

			String sorting = "";
			if (parameters.get("sortBy").isEmpty()) {
				sorting = "Nazwisko";
			} else {
				sorting = String.valueOf(parameters.get("sortBy"));
			}

			String surnamee = "";
			if (parameters.get("serachingSurname").isEmpty()) {
				surnamee = "";
			} else {
				surnamee = String.valueOf(parameters.get("serachingSurname"));
			}
			
			datacontainer.add(new Label("liczbaPrzedstawicieli", agentDao.numOfAg(surnamee)));
			PageableListView<?> pageableListView = new PageableListView<Agent>("lista",
					agentDao.getSortedAgents(sorting, surnamee), 8) {

				private static final long serialVersionUID = 1L;

				@Override
				protected void populateItem(ListItem<Agent> item) {
					final Agent agent = (Agent) item.getModelObject();

					item.add(new Label("imie", agent.getUserDataModel().getImie()));
					item.add(new Label("nazwisko", agent.getUserDataModel().getNazwisko()));
					item.add(new Label("firma", agent.getCompanyDataModel().getNazwa()));
					item.add(new Label("email", agent.getUserDataModel().getEmail()));
					item.add(new Label("ostatnie_logowanie", agent.getUserDataModel().getOst_logowanie()));
					PageParameters a= new PageParameters();
					a.add("userId", agent.getUserDataModel().getId());
					item.add(new BookmarkablePageLink<>("editUser", AdminEditUser.class, a));
					item.add(new BookmarkablePageLink<>("showRaport", AdminShowRaport.class, a));
					
					final Label blokujWyswietl = new Label("blokujWyswietl", new AbstractReadOnlyModel<String>() {

						private static final long serialVersionUID = 1L;

						@Override
						public String getObject() {
							if (agent.getUserDataModel().getCzy_blokowany()) {
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
							if (agent.getUserDataModel().getCzy_blokowany()) {
								agent.getUserDataModel().setCzy_blokowany(false);
								userDao.update(agent.getUserDataModel());			
								for (Admin allAdminss : allAdmins) {
									userNotificationsDao.addNotification(allAdminss.getUserDataModel(),
											notificationDao.getById(14), ApplicationSession.getInstance().getUser().getLogin());
								}
								userNotificationsDao.addNotification(agent.getUserDataModel(),
										notificationDao.getById(19), "");
							} else {
								agent.getUserDataModel().setCzy_blokowany(true);
								userDao.update(agent.getUserDataModel());
								for (Admin allAdminss : allAdmins) {
									userNotificationsDao.addNotification(allAdminss.getUserDataModel(),
											notificationDao.getById(6), ApplicationSession.getInstance().getUser().getLogin());
								}
								userNotificationsDao.addNotification(agent.getUserDataModel(),
										notificationDao.getById(18), "");
								// DM - wyslanie maila
								sendMail(agent);
								// DM stop
							}
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
							agent.getUserDataModel().setCzy_usuniety(true);
							userDao.update(agent.getUserDataModel());
							for (Admin allAdminss : allAdmins) {
								userNotificationsDao.addNotification(allAdminss.getUserDataModel(),
										notificationDao.getById(13), ApplicationSession.getInstance().getUser().getLogin());
							}
							
							// DM - wyslanie maila
							sendMail(agent);
							// DM stop
						}
					};
					usunUsera.setVisible(false);
					item.add(usunUsera.add(usunWyswietl));
					item.add(Usuniety);
					if (agent.getUserDataModel().getCzy_usuniety()) {
						zablokujUsera.setVisible(false);
						Usuniety.setVisible(true);
					} else {
						usunUsera.setVisible(true);
						zablokujUsera.setVisible(true);
					}
				}

			};
			datacontainer.add(pageableListView);
			datacontainer.add(new AjaxPagingNavigator("nav", pageableListView));
			datacontainer.setVersioned(false);
		} else {
			setResponsePage(LoginPage.class);
		}
	}
	
	
	// DM start
			private void sendMail(final Agent agent) {
				
				String statusAgenta;
				if(agent.getUserDataModel().getCzy_blokowany()) statusAgenta = "zablokowane";
				else if (agent.getUserDataModel().getCzy_usuniety()) statusAgenta = "usunięte";
				else statusAgenta = "odblokowane";
				
				mailSender mailsender = new mailSender();
				mailsender.sendNotify("Powiadomienie - status konta", 
						"Adresatem tej wiadomości jest " + agent.getUserDataModel().getImie() + " " + agent.getUserDataModel().getNazwisko() + "\nTwoje konto o loginie " + agent.getUserDataModel().getLogin() + " zostało " + statusAgenta + "!", 
						new Date(), 
						agent.getUserDataModel());
				
				
			};
		// DM stop

}