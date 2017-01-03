package pl.helpdesk.pages;

import java.util.List;

import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import pl.helpdesk.api.IAdminDao;
import pl.helpdesk.api.IPriorityDao;
import pl.helpdesk.entity.Priority;
import pl.helpdesk.entity.Status;
import pl.helpdesk.userSession.ApplicationSession;

public class AdminEditPriority extends AdminSuccessPage {

	private static final long serialVersionUID = 1L;

	@SpringBean
	private IPriorityDao priorityDao;
	
	
	@SpringBean
	private IAdminDao adminDao;

	public AdminEditPriority(PageParameters parameters) {
		super(parameters);

		if (!(ApplicationSession.getInstance().getUser() == null)
				&& adminDao.isAdmin(ApplicationSession.getInstance().getUser())) {

			WebMarkupContainer datacontainer = new WebMarkupContainer("data");
			datacontainer.setOutputMarkupId(true);
			add(datacontainer);

			PageableListView<?> pageableListView = new PageableListView<Priority>("lista", priorityDao.getAll(), 5) {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				protected void populateItem(ListItem<Priority> item) {
					final Priority priority = (Priority) item.getModelObject();

					item.add(new Label("nazwa", priority.getNazwa()));
					item.add(new Label("stopien", priority.getStopienWaznosci()));
				
					final Label usunWyswietl = new Label("usunWyswietl", "Usuń");
					final Label usuniety = new Label("czyUsuniety", "Usunięty");
					usuniety.setVisible(false);
					final Link usunStatus = new Link<Object>("usunPriorytet") {

					private static final long serialVersionUID = 1L;

					@Override
					public void onClick() {
						List<Priority> allAdmins = priorityDao.getAll();
						priorityDao.delete(priority);
						setResponsePage(AdminEditPriority.class);
					}
				};
				item.add(usunStatus.add(usunWyswietl));
				item.add(usuniety);
				}
			};

			datacontainer.add(pageableListView);
			datacontainer.add(new AjaxPagingNavigator("nav", pageableListView));
			datacontainer.setVersioned(false);
			
			final Form<?> addPriority;
			final TextField newPriority;
			final TextField newPriorityNumber;
			Priority priority = new Priority();
			newPriority= new TextField("nazwa", new PropertyModel<String>(priority, "nazwa"));
			newPriorityNumber= new TextField("stopienWaznosci", new PropertyModel<String>(priority, "stopienWaznosci"));
			addPriority = new Form<Object>("addPriority") {
				private static final long serialVersionUID = 1L;

				@Override
				public void onSubmit() {
					super.onSubmit();
					String nowyPriorytet = null;
					String nowyPriorytetStopien = null;
					nowyPriorytet = newPriority.getInput();
					nowyPriorytetStopien = newPriorityNumber.getInput();
					Priority nowy = new Priority();
					nowy.setNazwa(nowyPriorytet);
					nowy.setStopienWaznosci(Integer.parseInt(nowyPriorytetStopien));
					priorityDao.save(nowy);	
					setResponsePage(AdminEditPriority.class);
				}
			};
			add(addPriority);
			addPriority.add(newPriority);
			addPriority.add(newPriorityNumber);
		} else {
			setResponsePage(LoginPage.class);
		}
	}

}
