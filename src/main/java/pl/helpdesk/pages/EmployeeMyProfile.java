package pl.helpdesk.pages;

import org.apache.wicket.request.mapper.parameter.PageParameters;

import pl.helpdesk.components.EditProfile;

public class EmployeeMyProfile extends EmployeeSuccessPage {

	private static final long serialVersionUID = 1L;

	public EmployeeMyProfile(PageParameters parameters) {
		super(parameters);
		add(new EditProfile("panel"));
	}

}