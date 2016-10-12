package pl.helpdesk.pages;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import pl.helpdesk.api.IAdminDao;
import pl.helpdesk.components.EditPassword;
import pl.helpdesk.components.EditProfile;
import pl.helpdesk.userSession.ApplicationSession;

public class AdminMyProfile extends AdminSuccessPage {

	@SpringBean
	private IAdminDao adminDao;

	private static final long serialVersionUID = 1L;

	public AdminMyProfile(PageParameters parameters) {
		super(parameters);

		if (!(ApplicationSession.getInstance().getUser() == null)
				&& adminDao.isAdmin(ApplicationSession.getInstance().getUser())) {
			add(new EditProfile("panel"));
			add(new EditPassword("zmianaHasla"));
		} else {

			setResponsePage(LoginPage.class);
		}

	}

}