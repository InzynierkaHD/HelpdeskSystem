package pl.helpdesk.pages;

import org.apache.wicket.request.mapper.parameter.PageParameters;

import pl.helpdesk.components.EditProfile;

public class ClientMyProfile extends ClientSuccessPage {

	private static final long serialVersionUID = 1L;

	public ClientMyProfile(PageParameters parameters) {
		super(parameters);
		add(new EditProfile("panel"));
	}

}