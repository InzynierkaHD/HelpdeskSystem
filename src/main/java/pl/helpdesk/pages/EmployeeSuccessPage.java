package pl.helpdesk.pages;

import java.util.HashMap;
import java.util.LinkedList;

import org.apache.wicket.markup.IMarkupCacheKeyProvider;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import pl.helpdesk.api.INavbarComponent;
import pl.helpdesk.components.AlertModal;
import pl.helpdesk.components.Dropdown;
import pl.helpdesk.components.Navbar;
import pl.helpdesk.userSession.ApplicationSession;



public abstract class EmployeeSuccessPage extends WebPage {

	private static final long serialVersionUID = 1L;
	LinkedList<INavbarComponent> navComponent = new LinkedList<INavbarComponent>();
	protected AlertModal alert;
	
	public EmployeeSuccessPage(PageParameters parameters){
		HashMap<String, String> options = new HashMap<String, String>();
		options.put("Moje zgłoszenia", "EmployeeMyIssues");
		options.put("Przeglądaj", "EmployeeIssuesList");
		options.put("Historia", "EmployeeHistory");
		INavbarComponent zgloszenia = new Dropdown("<span class=\"glyphicon glyphicon-bell\"></span> Zgłoszenia",
				options);
		options.clear();
		options.put("Mój profil", "EmployeeMyProfile");
		options.put("Edytuj", "EmployeeEdit");
		INavbarComponent mojProfil = new Dropdown("<span class=\"glyphicon glyphicon-user\"></span> Mój Profil",
				options);
		
		Form<?> logutForm = new Form<Void>("logutForm");
		Button logOut = new Button("logOut") {

			private static final long serialVersionUID = -1800911970905016411L;

			@Override
			public void onSubmit() {
				super.onSubmit();
				ApplicationSession.getInstance().invalidate();
				setResponsePage(LoginPage.class);
			}
		};
		add(logutForm);
		logutForm.add(logOut);
		
		navComponent.add(zgloszenia);
		navComponent.add(mojProfil);
		add(new Navbar("header", "Internet Helpdesk", navComponent));
	}
	



	

}
