package pl.helpdesk.pages;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import pl.helpdesk.api.IAdminDao;
import pl.helpdesk.api.IEmployeeDao;
import pl.helpdesk.api.IUserDao;
import pl.helpdesk.components.AdminEditPassword;
import pl.helpdesk.components.AdminEditProfile;
import pl.helpdesk.userSession.ApplicationSession;

public final class AdminEditUser extends AdminSuccessPage {

	@SpringBean
	private IEmployeeDao employeeDao;

	@SpringBean
	private IAdminDao adminDao;

	@SpringBean
	private IUserDao userDao;

	private static final long serialVersionUID = 1L;

	public AdminEditUser(PageParameters parameters) {
		super(parameters);
		if (!(ApplicationSession.getInstance().getUser() == null)
				&& adminDao.isAdmin(ApplicationSession.getInstance().getUser())) {
			String userIdString = "";
			userIdString = String.valueOf(parameters.get("userId"));
			int id = Integer.parseInt(userIdString);
			add(new AdminEditProfile("panel", id));
			add(new AdminEditPassword("password", id));
		} else {
			setResponsePage(LoginPage.class);
		}
	}

}
