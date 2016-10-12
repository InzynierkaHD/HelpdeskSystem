package pl.helpdesk.pages;

import java.util.HashMap;
import java.util.LinkedList;

import org.apache.wicket.Session;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import pl.helpdesk.api.ILoggingHistoryDao;
import pl.helpdesk.api.INavbarComponent;
import pl.helpdesk.api.IUserDao;
import pl.helpdesk.components.AlertModal;
import pl.helpdesk.components.Dropdown;
import pl.helpdesk.components.Navbar;
import pl.helpdesk.userSession.ApplicationSession;

public abstract class AdminSuccessPage extends WebPage {

	@SpringBean
	private ILoggingHistoryDao loggingHistoryDao;

	@SpringBean
	private IUserDao userSpring;

	@SpringBean
	private IUserDao userDao;

	private static final long serialVersionUID = 1L;
	LinkedList<INavbarComponent> navComponent = new LinkedList<INavbarComponent>();
	protected AlertModal alert;

	public AdminSuccessPage(PageParameters parameters) {
		HashMap<String, String> options = new HashMap<String, String>();
		options.put("Dodaj pracownika", "AdminAddEmployee");
		options.put("Lista pracowników", "AdminEmployeeList");
		INavbarComponent pracownicy = new Dropdown("<span class=\"glyphicon glyphicon-user\"></span> Pracownicy",
				options);
		options.clear();
		options.put("Lista firm", "AdminCompanyList");
		options.put("Dodaj przedstawiciela", "AdminAddAgent");
		INavbarComponent firmy = new Dropdown("<span class=\"glyphicon glyphicon-briefcase\"></span> Firmy", options);
		options.clear();
		options.put("Edycja", "AdminMyProfile");
		INavbarComponent mojProfil = new Dropdown("<span class=\"glyphicon glyphicon-user\"></span> Mój Profil",
				options);
		options.clear();
		options.put("Statystyki pracowników", "AdminEmployeeStats");
		options.put("Statystyki firm", "AdminCompaniesStats");
		options.put("Statystyki przedstawicieli", "AdminAgentsStats");
		options.put("Statystyki klientów", "AdminClientsStats");
		INavbarComponent statystyki = new Dropdown("<span class=\"glyphicon glyphicon-stats\"></span> Statystyki",
				options);

		Form<?> logutForm = new Form<Void>("logutForm");
		Button logOut = new Button("logOut") {

			private static final long serialVersionUID = -1800911970905016411L;

			@Override
			public void onSubmit() {
				super.onSubmit();
				loggingHistoryDao
						.setUserLogOutDate(userDao.getUser(ApplicationSession.getInstance().getUser().getLogin()));
				ApplicationSession.get().invalidateNow();
				setResponsePage(LoginPage.class);
			}
		};
		add(logutForm);
		logutForm.add(logOut);
		navComponent.add(pracownicy);
		navComponent.add(firmy);
		navComponent.add(mojProfil);
		navComponent.add(statystyki);
		add(new Navbar("header", "Internet Helpdesk", navComponent));

	}
}
