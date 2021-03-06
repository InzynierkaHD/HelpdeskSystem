package pl.helpdesk.pages;

import java.security.NoSuchAlgorithmException;
import java.util.Date;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.hibernate.HibernateException;

import pl.helpdesk.api.IAdminDao;
import pl.helpdesk.api.IAgentDao;
import pl.helpdesk.api.IClientDao;
import pl.helpdesk.api.IEmployeeDao;
import pl.helpdesk.api.ILoggingHistoryDao;
import pl.helpdesk.api.INotificationDao;
import pl.helpdesk.api.IUserDao;
import pl.helpdesk.api.IUserNotificationsDao;
import pl.helpdesk.entity.LoggingHistory;
import pl.helpdesk.entity.User;
import pl.helpdesk.userSession.ApplicationSession;

public class LoginPage extends WebPage {

	private static final long serialVersionUID = 1L;

	@SpringBean
	private IUserDao userSpring;

	@SpringBean
	private IAdminDao adminDao;

	@SpringBean
	private IAgentDao agentDao;

	@SpringBean
	private IEmployeeDao employeeDao;

	@SpringBean
	private IClientDao clientDao;

	@SpringBean
	private ILoggingHistoryDao loggingHistoryDao;


	private User userDataModel = new User();

	public LoginPage(PageParameters parameters) {
		super(parameters);
		// setResponsePage(LoginPage.class);
		if (!(ApplicationSession.getInstance().getUser() == null)
				&& employeeDao.isEmployee(ApplicationSession.getInstance().getUser())) {
			setResponsePage(EmployeeFinalPage.class);
		} else if (!(ApplicationSession.getInstance().getUser() == null)
				&& agentDao.isAgent(ApplicationSession.getInstance().getUser())) {
			setResponsePage(AgentFinalPage.class);
		} else if (!(ApplicationSession.getInstance().getUser() == null)
				&& adminDao.isAdmin(ApplicationSession.getInstance().getUser())) {
			setResponsePage(AdminFinalPage.class);
		} else if (!(ApplicationSession.getInstance().getUser() == null)
				&& clientDao.isClient(ApplicationSession.getInstance().getUser())) {
			setResponsePage(ClientFinalPage.class);

		} else {
			final TextField<String> login = new TextField<String>("login",
					new PropertyModel<String>(userDataModel, "login"));

			PasswordTextField haslo = new PasswordTextField("haslo", new PropertyModel<String>(userDataModel, "haslo"));

			final Label badLogin = new Label("badLogin", "Nie ma takiego użytkownika!");
			badLogin.setVisible(Boolean.FALSE);

			final Label badPass = new Label("badPass", "Błędne hasło!");
			badPass.setVisible(Boolean.FALSE);

			final Label userBlocked = new Label("userBlocked", "Konto zablokowane!");
			userBlocked.setVisible(Boolean.FALSE);

			Form<?> form = new Form<Void>("form") {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void onSubmit() {

					super.onSubmit();

					userBlocked.setVisible(Boolean.FALSE);
					badPass.setVisible(Boolean.FALSE);
					badLogin.setVisible(Boolean.FALSE);

					if (userSpring.getUser(userDataModel.getLogin()) == null) {
						System.out.println("Nie ma takiego usera");
						badLogin.setVisible(Boolean.TRUE);
					} else {
						boolean badPassword = false;
						try {
							badPassword = userSpring.incorrectPassword(userDataModel.getLogin(),
									userDataModel.getHaslo());
						} catch (HibernateException | NoSuchAlgorithmException e) {
							e.printStackTrace();
						}

						LoggingHistory loggingHistory = new LoggingHistory();
						User findedUser = null;

						try {
							findedUser = userSpring.findUserByLoginAndPassword(userDataModel.getLogin(),
									userDataModel.getHaslo());
						} catch (HibernateException | NoSuchAlgorithmException e) {
							e.printStackTrace();
						}
						if (badPassword == false) {
							if (findedUser.getCzy_usuniety() == true) {
								System.out.println("Nie ma takiego usera");
								badLogin.setVisible(Boolean.TRUE);
							} else if (findedUser.getCzy_blokowany() == true) {
								System.out.println("Konto zablokowane");
								userBlocked.setVisible(Boolean.TRUE);
							} else {
								Date date = new Date();
								loggingHistory.setDataLogowania(date);
								loggingHistory.setUserDataModel(findedUser);
								loggingHistoryDao.save(loggingHistory);
								findedUser.setOst_logowanie(date);
								findedUser.setBadPassword(0);
								userSpring.update(findedUser);
								ApplicationSession.getInstance().setUser(findedUser);
								if (adminDao.isAdmin(findedUser)) {
									setResponsePage(AdminFinalPage.class);
								} else if (employeeDao.isEmployee(findedUser)) {
									setResponsePage(EmployeeFinalPage.class);
								} else if (agentDao.isAgent(findedUser)) {
									setResponsePage(AgentFinalPage.class);
								} else if (clientDao.isClient(findedUser)) {
									setResponsePage(ClientFinalPage.class);
								}
							}
						}
						if (badPassword == true) {
							System.out.println("Bledne haslo");
							badPass.setVisible(Boolean.TRUE);
							if(clientDao.isClientFromLogin(userDataModel.getLogin())){
								userSpring.incrementBadPassowrd(userDataModel.getLogin());
							}
						}
					}
				}
			};
			form.add(login);
			form.add(haslo);
			add(form);
			add(badLogin);
			add(userBlocked);
			add(badPass);
		}
	}
}
