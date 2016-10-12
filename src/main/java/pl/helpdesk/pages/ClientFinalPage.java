package pl.helpdesk.pages;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import pl.helpdesk.api.IClientDao;
import pl.helpdesk.userSession.ApplicationSession;

public class ClientFinalPage extends ClientSuccessPage {

	@SpringBean
	private IClientDao clientDao;
	
	private static final long serialVersionUID = 1L;

	

	public ClientFinalPage(PageParameters parameters) {
		super(parameters);
		if (!(ApplicationSession.getInstance().getUser() == null)
				&& clientDao.isClient(ApplicationSession.getInstance().getUser())) {
	}else{
		setResponsePage(LoginPage.class);
	}
	}
}
