package pl.helpdesk.pages;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import pl.helpdesk.api.IAgentDao;
import pl.helpdesk.components.EditPassword;
import pl.helpdesk.components.EditProfile;
import pl.helpdesk.userSession.ApplicationSession;

public class AgentMyProfile extends AgentSuccessPage {

	@SpringBean
	private IAgentDao agentDao;
	
	private static final long serialVersionUID = 1L;

	public AgentMyProfile(PageParameters parameters) {
		super(parameters);
		if (!(ApplicationSession.getInstance().getUser() == null)
				&& agentDao.isAgent(ApplicationSession.getInstance().getUser())) {
		add(new EditProfile("panel"));
		add(new EditPassword("zmianaHasla"));
	}else{
		setResponsePage(LoginPage.class);
	}
	}

}