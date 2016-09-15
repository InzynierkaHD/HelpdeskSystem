package pl.helpdesk.pages;

import org.apache.wicket.request.mapper.parameter.PageParameters;

import pl.helpdesk.components.EditProfile;

public class AgentMyProfile extends AgentSuccessPage {

	private static final long serialVersionUID = 1L;

	public AgentMyProfile(PageParameters parameters) {
		super(parameters);
		add(new EditProfile("panel"));
	}

}