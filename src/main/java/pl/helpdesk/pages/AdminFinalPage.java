package pl.helpdesk.pages;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import pl.helpdesk.api.IAdminDao;
import pl.helpdesk.api.IAgentDao;
import pl.helpdesk.userSession.ApplicationSession;




public class AdminFinalPage extends AdminSuccessPage {

	@SpringBean
	private IAdminDao adminDao;
	
	@SpringBean
	private IAgentDao agentDao;
	
	private static final long serialVersionUID = 1L;

	public AdminFinalPage(PageParameters parameters) {
		super(parameters);
		if(!(ApplicationSession.getInstance().getUser()==null) && adminDao.isAdmin(ApplicationSession.getInstance().getUser())){
			
		} else{
			
			setResponsePage(LoginPage.class);
		}
			
		
	}
}
