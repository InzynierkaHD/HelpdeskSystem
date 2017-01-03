package pl.helpdesk.pages;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import pl.helpdesk.api.IAdminDao;
import pl.helpdesk.api.ICompanyDao;
import pl.helpdesk.api.IEmployeeDao;
import pl.helpdesk.api.IStatusDao;
import pl.helpdesk.api.IUserDao;
import pl.helpdesk.entity.Admin;
import pl.helpdesk.entity.Company;
import pl.helpdesk.entity.Employee;
import pl.helpdesk.entity.Status;
import pl.helpdesk.passwordHash.HashPassword;
import pl.helpdesk.userSession.ApplicationSession;

public class AdminEditStatus extends AdminSuccessPage {

	private static final long serialVersionUID = 1L;

	@SpringBean
	private IStatusDao statusDao;
	
	
	@SpringBean
	private IAdminDao adminDao;

	public AdminEditStatus(PageParameters parameters) {
		super(parameters);

		if (!(ApplicationSession.getInstance().getUser() == null)
				&& adminDao.isAdmin(ApplicationSession.getInstance().getUser())) {

			WebMarkupContainer datacontainer = new WebMarkupContainer("data");
			datacontainer.setOutputMarkupId(true);
			add(datacontainer);

			PageableListView<?> pageableListView = new PageableListView<Status>("lista", statusDao.getAll(), 5) {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				protected void populateItem(ListItem<Status> item) {
					final Status status = (Status) item.getModelObject();

					item.add(new Label("nazwa", status.getNazwa()));
				
					final Label usunWyswietl = new Label("usunWyswietl", "Usuń");
					final Label usuniety = new Label("czyUsuniety", "Usunięty");
					usuniety.setVisible(false);
					final Link usunStatus = new Link<Object>("usunStatus") {

					private static final long serialVersionUID = 1L;

					@Override
					public void onClick() {
						List<Status> allAdmins = statusDao.getAll();
						statusDao.delete(status);
						setResponsePage(AdminEditStatus.class);
					}
				};
				item.add(usunStatus.add(usunWyswietl));
				item.add(usuniety);
				}
			};

			datacontainer.add(pageableListView);
			datacontainer.add(new AjaxPagingNavigator("nav", pageableListView));
			datacontainer.setVersioned(false);
			
			final Form<?> addStatus;
			final TextField newStatus;
			Status status = new Status();
			newStatus= new TextField("nazwa", new PropertyModel<String>(status, "nazwa"));
			addStatus = new Form<Object>("addStatus") {
				private static final long serialVersionUID = 1L;

				@Override
				public void onSubmit() {
					super.onSubmit();
					String nowyStatus = null;
					nowyStatus = newStatus.getInput();
					Status nowy = new Status();
					nowy.setNazwa(nowyStatus);
					statusDao.save(nowy);	
					setResponsePage(AdminEditStatus.class);
				}
			};
			add(addStatus);
			addStatus.add(newStatus);
		} else {
			setResponsePage(LoginPage.class);
		}
	}

}
