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

public class AdminShowRaport extends AdminSuccessPage {

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
	


	public AdminShowRaport(PageParameters parameters) {
		super(parameters);

		if (!(ApplicationSession.getInstance().getUser() == null)
				&& adminDao.isAdmin(ApplicationSession.getInstance().getUser())) {

			userDataModel = new User();
			final SelectForm typUser = new SelectForm("typeShowUser", new PropertyModel<String>(this, "uselected"), USERTYPE);

			final TextField<String> serachingSurname = new TextField<String>("serachingSurname",
					new PropertyModel<String>(userDataModel, "nazwisko"));
			final TextField<String> serachingName = new TextField<String>("serachingName",
					new PropertyModel<String>(userDataModel, "imie"));
			final TextField<String> serachingMail = new TextField<String>("serachingMail",
					new PropertyModel<String>(userDataModel, "email"));
			
			Form<?> form = new Form<Void>("form") {
				private static final long serialVersionUID = 1L;

				@Override
				protected void onSubmit() {

					PageParameters parameterss = new PageParameters();
					String serachingSurnameOK = serachingSurname.getInput();
					parameterss.set("serachingSurname", serachingSurnameOK);
					String serachingNameOK = serachingName.getInput();
					parameterss.set("serachingName", serachingNameOK);
					String serachingMailOK = serachingMail.getInput();
					parameterss.set("serachingMail", serachingMailOK);
					setResponsePage(AdminShowRaport.class, parameterss);
				}
			};
			
			

			add(form);
			form.add(typUser);
			form.add(serachingSurname);
			form.add(serachingName);
			form.add(serachingMail);
			
				WebMarkupContainer datacontainer = new WebMarkupContainer("data");
				datacontainer.setOutputMarkupId(true);
				add(datacontainer);
				/*
				if(!parameters.isEmpty())
				{
				
				PageableListView<?> pageableListView = new PageableListView<User>("lista", userDao.createUserList(), 8) {
	
					private static final long serialVersionUID = 1L;
	
					@Override
					protected void populateItem(ListItem<User> item) {
						final User user = (User) item.getModelObject();
	
						item.add(new Label("imie", user.getImie()));
						item.add(new Label("nazwisko", user.getNazwisko()));
						item.add(new Label("email", user.getEmail()));
						item.add(new Label("ostatnie_logowanie", user.getOst_logowanie()));
	
						
					}
	
				};
				datacontainer.add(pageableListView);
				datacontainer.add(new AjaxPagingNavigator("nav", pageableListView));
				datacontainer.setVersioned(false);
			}
			*/
		} else {
			setResponsePage(LoginPage.class);
		}

	}

}
