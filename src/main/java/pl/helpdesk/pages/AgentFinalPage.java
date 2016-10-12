package pl.helpdesk.pages;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import pl.helpdesk.api.IAgentDao;
import pl.helpdesk.userSession.ApplicationSession;

public class AgentFinalPage extends AgentSuccessPage {

	private static final long serialVersionUID = 1L;

	@SpringBean
	private IAgentDao agentDao;

	public AgentFinalPage(PageParameters parameters) {
		super(parameters);
		if (!(ApplicationSession.getInstance().getUser() == null)
				&& agentDao.isAgent(ApplicationSession.getInstance().getUser())) {
		} else {
			setResponsePage(LoginPage.class);
		}
	}
}
