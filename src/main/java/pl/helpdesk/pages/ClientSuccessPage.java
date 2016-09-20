package pl.helpdesk.pages;

import java.util.HashMap;
import java.util.LinkedList;

import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
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

public abstract class ClientSuccessPage extends WebPage {

	@SpringBean
	private ILoggingHistoryDao loggingHistoryDao;

	@SpringBean
	private IUserDao userDao;

	private static final long serialVersionUID = 1L;
	LinkedList<INavbarComponent> navComponent = new LinkedList<INavbarComponent>();
	protected AlertModal alert;

	public ClientSuccessPage(PageParameters parameters) {
		HashMap<String, String> options = new HashMap<String, String>();
		options.put("Moje zgłoszenia", "ClientMyIssues");
		options.put("Historia", "ClientHistory");
		INavbarComponent zgloszenia = new Dropdown("<span class=\"glyphicon glyphicon-bell\"></span> Zgłoszenia",
				options);
		options.clear();
		INavbarComponent firma = new Dropdown("<span class=\"glyphicon glyphicon-briefcase\"></span> Firma", options);
		options.put("Edycja", "ClientMyProfile");
		INavbarComponent mojProfil = new Dropdown("<span class=\"glyphicon glyphicon-user\"></span> Mój Profil",
				options);

		Form<?> logutForm = new Form<Void>("logutForm");
		Button logOut = new Button("logOut") {

			private static final long serialVersionUID = -1800911970905016411L;

			@Override
			public void onSubmit() {
				super.onSubmit();
				loggingHistoryDao
						.setUserLogOutDate(userDao.getUser(ApplicationSession.getInstance().getUser().getLogin()));
				ApplicationSession.getInstance().invalidate();
				setResponsePage(LoginPage.class);
			}
		};
		add(logutForm);
		logutForm.add(logOut);
		navComponent.add(zgloszenia);
		navComponent.add(firma);
		navComponent.add(mojProfil);
		add(new Navbar("header", "Internet Helpdesk", navComponent));
	}

}
