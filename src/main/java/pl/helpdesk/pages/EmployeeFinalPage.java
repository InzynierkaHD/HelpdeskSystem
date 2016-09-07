package pl.helpdesk.pages;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import pl.helpdesk.userSession.ApplicationSession;

public class EmployeeFinalPage extends EmployeeSuccessPage {

	private static final long serialVersionUID = 1L;

	

	public EmployeeFinalPage(PageParameters parameters) {
		super(parameters);
		add(new Label("username", ApplicationSession.getInstance().getUser().getImie()));
		
	}
}
