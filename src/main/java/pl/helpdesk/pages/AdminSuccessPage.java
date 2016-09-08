package pl.helpdesk.pages;

import java.util.HashMap;
import java.util.LinkedList;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import pl.helpdesk.api.INavbarComponent;
import pl.helpdesk.components.AlertModal;
import pl.helpdesk.components.Dropdown;
import pl.helpdesk.components.Navbar;
import pl.helpdesk.userSession.ApplicationSession;



public abstract class AdminSuccessPage extends WebPage {

	private static final long serialVersionUID = 1L;
	LinkedList<INavbarComponent> navComponent = new LinkedList<INavbarComponent>();
	protected AlertModal alert;

	public AdminSuccessPage(PageParameters parameters){
		HashMap<String, String> options = new HashMap<String, String>();
		options.put("Lista pracowników", "AdminEmployeeList");
		options.put("Dodaj pracownika", "AdminAddEmployee");
		INavbarComponent pracownicy = new Dropdown("<span class=\"glyphicon glyphicon-user\"></span> Pracownicy",
				options);
		options.clear();
		options.put("Lista firm", "AdminCompanyList");
		options.put("Dodaj firmę", "AdminAddCompany");
		INavbarComponent firmy = new Dropdown("<span class=\"glyphicon glyphicon-briefcase\"></span> Firmy", options);
		options.clear();
		options.put("Moj Profil", "AdminMyProfile");
		options.put("Edytuj", "AdminEditProfile");
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
				ApplicationSession.getInstance().invalidate();
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



