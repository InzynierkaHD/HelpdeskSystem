package pl.helpdesk.pages;

import java.security.NoSuchAlgorithmException;
import java.util.Date;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.hibernate.HibernateException;

import pl.helpdesk.api.IAdminDao;
import pl.helpdesk.api.IAgentDao;
import pl.helpdesk.api.IClientDao;
import pl.helpdesk.api.IEmployeeDao;
import pl.helpdesk.api.ILoggingHistoryDao;
import pl.helpdesk.api.IUserDao;
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

	public LoginPage() {
		final TextField<String> login = new TextField<String>("login",
				new PropertyModel<String>(userDataModel, "login"));

		PasswordTextField haslo = new PasswordTextField("haslo", new PropertyModel<String>(userDataModel, "haslo"));

		final Label badInfo = new Label("message", "Złe dane!");
		badInfo.setVisible(Boolean.FALSE);

		final Label userBlocked = new Label("blocked", "Konto zablokowane!");
		userBlocked.setVisible(Boolean.FALSE);

		Form<?> form = new Form<Void>("form") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit() {

				super.onSubmit();
				User findedUser = null;
				LoggingHistory loggingHistory = new LoggingHistory();
				try {
					findedUser = userSpring.findUserByLoginAndPassword(userDataModel.getLogin(),
							userDataModel.getHaslo());
				} catch (HibernateException | NoSuchAlgorithmException e) {
					e.printStackTrace();
				}
				if (findedUser != null) {
					Date date = new Date();
					loggingHistory.setDataLogowania(date);
					loggingHistory.setUserDataModel(findedUser);
					loggingHistoryDao.save(loggingHistory);
					findedUser.setOst_logowanie(date);
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

				} else {
					System.out.println("Bledne dane");
					badInfo.setVisible(Boolean.TRUE);
				}
			}

		};
		form.add(login);
		form.add(haslo);
		add(form);
		add(badInfo);
		add(userBlocked);
	}
}
