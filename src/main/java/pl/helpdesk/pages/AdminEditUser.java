package pl.helpdesk.pages;


import org.apache.wicket.markup.html.basic.Label;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.StringValue;

import pl.helpdesk.api.IAdminDao;
import pl.helpdesk.api.IEmployeeDao;
import pl.helpdesk.api.IUserDao;
import pl.helpdesk.components.EditProfile;
import pl.helpdesk.entity.Employee;



public final class AdminEditUser extends AdminSuccessPage {

	
	@SpringBean
	private IEmployeeDao employeeDao;

	@SpringBean
	private IAdminDao adminDao;
	
	@SpringBean
	private IUserDao userDao;
	
	private static final long serialVersionUID = 1L;

	  public AdminEditUser( PageParameters parameters) {
		  super(parameters);
		  String userIdString="";
		  userIdString = String.valueOf(parameters.get("userId"));
		  int id =Integer.parseInt(userIdString);
		  add(new Label("label", id));
		  //add(new EditProfile("panel", true, Id));
	  }
	  
	
}
