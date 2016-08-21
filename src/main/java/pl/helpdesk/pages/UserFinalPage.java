package pl.helpdesk.pages;

import java.util.HashMap;
import java.util.LinkedList;

import pl.helpdesk.api.INavbarComponent;
import pl.helpdesk.components.Dropdown;
import pl.helpdesk.components.Navbar;

public class UserFinalPage extends SuccessPage {

	private static final long serialVersionUID = 1L;

	LinkedList<INavbarComponent> navComponent = new LinkedList<INavbarComponent>();

	public UserFinalPage() {
		super();
		HashMap<String, String> options = new HashMap<String, String>();
		options.put("Dodaj zgłoszenie", "/DodajZgloszenie");
		options.put("Moje zgłoszenia", "/MojeZgloszenia");
		options.put("Historia", "/Historia");
		INavbarComponent zgloszenia = new Dropdown("<span class=\"glyphicon glyphicon-bell\"></span> Zgłoszenia",
				options);
		options.clear();
		INavbarComponent firma = new Dropdown("<span class=\"glyphicon glyphicon-briefcase\"></span> Firma", options);
		options.put("Moj Profil", "/MyProfile");
		options.put("Edytuj", "/Edit");
		INavbarComponent mojProfil = new Dropdown("<span class=\"glyphicon glyphicon-user\"></span> Mój Profil",
				options);
		navComponent.add(zgloszenia);
		navComponent.add(firma);
		navComponent.add(mojProfil);
		add(new Navbar("header", "Internet Helpdesk", navComponent));
	}
}
