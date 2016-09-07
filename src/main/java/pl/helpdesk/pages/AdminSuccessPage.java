package pl.helpdesk.pages;

import java.util.HashMap;
import java.util.LinkedList;

import org.apache.wicket.markup.IMarkupCacheKeyProvider;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import pl.helpdesk.api.INavbarComponent;
import pl.helpdesk.components.AlertModal;
import pl.helpdesk.components.Dropdown;
import pl.helpdesk.components.Navbar;



public abstract class AdminSuccessPage extends WebPage {

	private static final long serialVersionUID = 1L;
	LinkedList<INavbarComponent> navComponent = new LinkedList<INavbarComponent>();
	protected AlertModal alert;
	
	public AdminSuccessPage(PageParameters parameters){
		HashMap<String, String> options = new HashMap<String, String>();
		options.put("Lista pracowników", "adminEmployeeList");
		options.put("Dodaj pracownika", "adminEmployeeAdd");
		INavbarComponent pracownicy = new Dropdown("<span class=\"glyphicon glyphicon-user\"></span> Pracownicy",
				options);
		options.clear();
		options.put("Lista firm", "adminCompanyList");
		options.put("Dodaj firmę", "adminCompanyAdd");
		INavbarComponent firmy = new Dropdown("<span class=\"glyphicon glyphicon-briefcase\"></span> Firmy", options);
		options.clear();
		options.put("Moj Profil", "adminMyProfile");
		options.put("Edytuj", "adminEditProfile");
		INavbarComponent mojProfil = new Dropdown("<span class=\"glyphicon glyphicon-user\"></span> Mój Profil",
				options);
		options.clear();
		options.put("Statystyki pracowników", "AdminEmployeeStats");
		options.put("Statystyki firm", "AdminCompaniesStats");
		options.put("Statystyki przedstawicieli", "AdminAgentsStats");
		options.put("Statystyki klientów", "AdminClientsStats");
		INavbarComponent statystyki = new Dropdown("<span class=\"glyphicon glyphicon-stats\"></span> Statystyki",
				options);
		navComponent.add(pracownicy);
		navComponent.add(firmy);
		navComponent.add(mojProfil);
		navComponent.add(statystyki);
		add(new Navbar("header", "Internet Helpdesk", navComponent));
	}
	



	

}
