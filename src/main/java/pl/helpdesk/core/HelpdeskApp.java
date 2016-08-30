package pl.helpdesk.core;

import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

import pl.helpdesk.pages.LoginPage;
import pl.helpdesk.pages.MyIssue;
import pl.helpdesk.pages.UserFinalPage;
import pl.helpdesk.userSession.ApplicationSession;

//Klasa startowa w niej definiujemy z jakiej klasy startujemy aplikacje
public class HelpdeskApp extends WebApplication{

	public final static String applicationName="HelpdeskSystem";
	@Override
	public Class<LoginPage> getHomePage() {
		// TODO Auto-generated method stub
		return LoginPage.class;
	}
	
	@Override
	public Session newSession(Request request, Response response) {
		// TODO Auto-generated method stub
		return new ApplicationSession(request);
	}
	

	@Override
	public void init()
	{
		super.init();
		getComponentInstantiationListeners().add(new SpringComponentInjector(this));
		mountPage("/Home", LoginPage.class);
		mountPage("/userPage", UserFinalPage.class);
		mountPage("/MyIssues", MyIssue.class);
	}
}
