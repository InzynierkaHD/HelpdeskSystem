package pl.helpdesk.pages;

import java.util.HashMap;
import java.util.LinkedList;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import pl.helpdesk.api.INavbarComponent;
import pl.helpdesk.components.Dropdown;
import pl.helpdesk.components.Navbar;
import pl.helpdesk.userSession.ApplicationSession;

public class ClientFinalPage extends ClientSuccessPage {

	private static final long serialVersionUID = 1L;

	

	public ClientFinalPage(PageParameters parameters) {
		super(parameters);
		add(new Label("username", ApplicationSession.getInstance().getUser().getImie()));
		
	}
}
