package pl.helpdesk.core;

import org.apache.wicket.Session;
import org.apache.wicket.bean.validation.BeanValidationConfiguration;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

import pl.helpdesk.pages.AdminAddAgent;
import pl.helpdesk.pages.AdminAddEmployee;
import pl.helpdesk.pages.AdminClientList;
import pl.helpdesk.pages.AdminCompanyList;
import pl.helpdesk.pages.AdminEmployeeList;
import pl.helpdesk.pages.AdminFinalPage;
import pl.helpdesk.pages.AdminMyProfile;
import pl.helpdesk.pages.AgentAddClient;
import pl.helpdesk.pages.AgentClientList;
import pl.helpdesk.pages.AgentFinalPage;
import pl.helpdesk.pages.AgentMyProfile;
import pl.helpdesk.pages.ClientFinalPage;
import pl.helpdesk.pages.ClientMyProfile;
import pl.helpdesk.pages.EmployeeFinalPage;
import pl.helpdesk.pages.EmployeeIssuesList;
import pl.helpdesk.pages.EmployeeMyProfile;
import pl.helpdesk.pages.LoginPage;
import pl.helpdesk.pages.IssueListPage;
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
		new BeanValidationConfiguration().configure(this);
		mountPage("/Home", LoginPage.class);
		mountPage("/userPage", ClientFinalPage.class);
		mountPage("/adminPage", AdminFinalPage.class);
		mountPage("/employeePage", EmployeeFinalPage.class);
		mountPage("/agentPage", AgentFinalPage.class);
		mountPage("/IssuesList", IssueListPage.class);
		mountPage("/AgentAddClient", AgentAddClient.class);
		mountPage("/AdminAddEmployee", AdminAddEmployee.class);
		mountPage("/AgentClientList", AgentClientList.class);
		mountPage("/AdminEmployeeList", AdminEmployeeList.class);
		mountPage("/AdminMyProfile", AdminMyProfile.class);
		mountPage("/ClientMyProfile", ClientMyProfile.class);
		mountPage("/AgentMyProfile", AgentMyProfile.class);
		mountPage("/EmployeeMyProfile", EmployeeMyProfile.class);
		mountPage("/AdminAddAgent", AdminAddAgent.class);
		mountPage("/AdminCompanyList", AdminCompanyList.class);	
		mountPage("/AdminClientList", AdminClientList.class);	
	}
}
