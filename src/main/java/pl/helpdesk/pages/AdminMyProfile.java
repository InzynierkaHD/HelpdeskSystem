package pl.helpdesk.pages;

import org.apache.wicket.request.mapper.parameter.PageParameters;

import pl.helpdesk.components.EditPassword;
import pl.helpdesk.components.EditProfile;

public class AdminMyProfile extends AdminSuccessPage {

	private static final long serialVersionUID = 1L;

	public AdminMyProfile(PageParameters parameters) {
		super(parameters);
		add(new EditProfile("panel"));
		add(new EditPassword("zmianaHasla"));
	}

}