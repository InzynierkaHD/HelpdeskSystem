package pl.helpdesk.pages;

import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import pl.helpdesk.api.IAdminDao;
import pl.helpdesk.api.ICompanyDao;
import pl.helpdesk.api.IEmployeeDao;
import pl.helpdesk.api.IUserDao;
import pl.helpdesk.entity.Company;
import pl.helpdesk.entity.Employee;
import pl.helpdesk.userSession.ApplicationSession;

public class AdminCompanyList extends AdminSuccessPage {

	private static final long serialVersionUID = 1L;

	@SpringBean
	private IEmployeeDao employeeDao;

	@SpringBean
	private IAdminDao adminDao;

	@SpringBean
	private IUserDao userDao;
	
	@SpringBean
	private ICompanyDao companyDao;

	public AdminCompanyList(PageParameters parameters) {
		super(parameters);

		if (!(ApplicationSession.getInstance().getUser() == null)
				&& adminDao.isAdmin(ApplicationSession.getInstance().getUser())) {

			WebMarkupContainer datacontainer = new WebMarkupContainer("data");
			datacontainer.setOutputMarkupId(true);
			add(datacontainer);

			PageableListView<?> pageableListView = new PageableListView<Company>("lista", companyDao.getAll(), 8) {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				protected void populateItem(ListItem<Company> item) {
					final Company company = (Company) item.getModelObject();

					item.add(new Label("nazwa", company.getNazwa()));
					item.add(new Label("mejscowosc", company.getMiejscowosc()));
					item.add(new Label("kod_pocztowy", company.getKod_pocztowy()));
					item.add(new Label("ulica",company.getUlica()));
					item.add(new Label("numer",company.getNumer()));
					
				}

			};
			datacontainer.add(pageableListView);
			datacontainer.add(new AjaxPagingNavigator("nav", pageableListView));
			datacontainer.setVersioned(false);
		} else {
			setResponsePage(LoginPage.class);
		}
	}

}
