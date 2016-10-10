package pl.helpdesk.pages;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import pl.helpdesk.api.IEmployeeDao;
import pl.helpdesk.components.EditPassword;
import pl.helpdesk.components.EditProfile;
import pl.helpdesk.userSession.ApplicationSession;

public class EmployeeMyProfile extends EmployeeSuccessPage {

	@SpringBean
	private IEmployeeDao employeeDao;
	
	private static final long serialVersionUID = 1L;

	public EmployeeMyProfile(PageParameters parameters) {
		super(parameters);
		if (!(ApplicationSession.getInstance().getUser() == null)
				&& employeeDao.isEmployee(ApplicationSession.getInstance().getUser())) {
		add(new EditProfile("panel"));
		add(new EditPassword("zmianaHasla"));
	}else{
		setResponsePage(LoginPage.class);
	}
	}

}