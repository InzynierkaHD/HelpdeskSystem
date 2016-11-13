package pl.helpdesk.pages;

import java.util.Date;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import pl.helpdesk.api.IAdminDao;
import pl.helpdesk.api.IEmployeeDao;
import pl.helpdesk.api.IUserDao;
import pl.helpdesk.components.SelectForm;
import pl.helpdesk.entity.Employee;
import pl.helpdesk.entity.User;
import pl.helpdesk.userSession.ApplicationSession;

public class AdminShowRaportDetails extends AdminSuccessPage {

	private static final long serialVersionUID = 1L;
	
	private User userDataModel;

	
	private static final List<String> USERTYPE = Arrays
			.asList(new String[] { "Klient", "Przedstawiciel klienta", "Pracownik Helpdesku"});

	private String uselected= "Klient";
	
	
	@SpringBean
	private IEmployeeDao employeeDao;

	@SpringBean
	private IAdminDao adminDao;
	
	@SpringBean
	private IUserDao userDao;
	


	public AdminShowRaportDetails(PageParameters parameters) {
		super(parameters);

		if (!(ApplicationSession.getInstance().getUser() == null)
				&& adminDao.isAdmin(ApplicationSession.getInstance().getUser())) {

			
		} else {
			setResponsePage(LoginPage.class);
		}

	}

}
