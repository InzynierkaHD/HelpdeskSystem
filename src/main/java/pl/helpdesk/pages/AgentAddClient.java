package pl.helpdesk.pages;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import pl.helpdesk.api.IAgentDao;
import pl.helpdesk.forms.AddUserForm;
import pl.helpdesk.userSession.ApplicationSession;

public class AgentAddClient extends AgentSuccessPage {

	private static final long serialVersionUID = 1L;

	@SpringBean
	private IAgentDao agentDao;


	public AgentAddClient(PageParameters parameters) {
		super(parameters);
		if (!(ApplicationSession.getInstance().getUser() == null)
				&& agentDao.isAgent(ApplicationSession.getInstance().getUser())) {		
			add(new AddUserForm("addUser", "client"));
	}else{
		setResponsePage(LoginPage.class);
	}
	}

}
