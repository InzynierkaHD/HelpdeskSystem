package pl.helpdesk.userSession;

import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;

import pl.helpdesk.entity.User;


public class ApplicationSession extends WebSession {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */


	private User user;
	
	public ApplicationSession(Request request) {
		super(request);
		// TODO Auto-generated constructor stub
	}
	
	public static ApplicationSession getInstance(){
		return (ApplicationSession)Session.get();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User uSerDataModel) {
		this.user = uSerDataModel;
	}

}
