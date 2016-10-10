package pl.helpdesk.pages;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import pl.helpdesk.api.IEmployeeDao;
import pl.helpdesk.userSession.ApplicationSession;

public class EmployeeFinalPage extends EmployeeSuccessPage {

	@SpringBean
	private IEmployeeDao employeeDao;
	
	private static final long serialVersionUID = 1L;

	

	public EmployeeFinalPage(PageParameters parameters) {
		super(parameters);
		if (!(ApplicationSession.getInstance().getUser() == null)
				&& employeeDao.isEmployee(ApplicationSession.getInstance().getUser())) {
	}else{
		setResponsePage(LoginPage.class);
	}
	}
}
