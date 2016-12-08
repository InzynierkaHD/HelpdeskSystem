package pl.helpdesk.pages;

import java.util.List;

import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import pl.helpdesk.api.IClientDao;
import pl.helpdesk.api.IUserNotificationsDao;
import pl.helpdesk.entity.Company;
import pl.helpdesk.entity.User;
import pl.helpdesk.entity.UserNotifications;
import pl.helpdesk.userSession.ApplicationSession;

public class ClientFinalPage extends ClientSuccessPage {

	@SpringBean
	private IClientDao clientDao;
	
	@SpringBean
	private IUserNotificationsDao userNotificationsDao;
	
	private static final long serialVersionUID = 1L;

	

	public ClientFinalPage(PageParameters parameters) {
		super(parameters);
		if (!(ApplicationSession.getInstance().getUser() == null)
				&& clientDao.isClient(ApplicationSession.getInstance().getUser())) {
			
			
			WebMarkupContainer datacontainer = new WebMarkupContainer("data");
			datacontainer.setOutputMarkupId(true);
			add(datacontainer);
			PageableListView<?> pageableListView = new PageableListView<UserNotifications>("lista", userNotificationsDao.getNotificationByUser(ApplicationSession.getInstance().getUser()), 8) {
				
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				protected void populateItem(ListItem<UserNotifications> item) {
					final UserNotifications userNotifications = (UserNotifications) item.getModelObject();
					item.add(new Label("dataa", userNotifications.getDataWyslania()));
					item.add(new Label("tresc", userNotifications.getNotificationDataModel().getTresc()));
				}
			};
			datacontainer.add(pageableListView);
			datacontainer.add(new AjaxPagingNavigator("nav", pageableListView));
			datacontainer.setVersioned(false);
			
			
	}else{
		setResponsePage(LoginPage.class);
	}
	}
}
