package pl.helpdesk.pages;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import pl.helpdesk.api.IClientDao;
import pl.helpdesk.components.EditPassword;
import pl.helpdesk.components.EditProfile;
import pl.helpdesk.userSession.ApplicationSession;

public class ClientMyProfile extends ClientSuccessPage {

	
	@SpringBean
	private IClientDao clientDao;
	
	private static final long serialVersionUID = 1L;

	public ClientMyProfile(PageParameters parameters) {
		super(parameters);
		if (!(ApplicationSession.getInstance().getUser() == null)
				&& clientDao.isClient(ApplicationSession.getInstance().getUser())) {
		add(new EditProfile("panel"));
		add(new EditPassword("zmianaHasla"));
	}else{
		setResponsePage(LoginPage.class);
	}
	}

}