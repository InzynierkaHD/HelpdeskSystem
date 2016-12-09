package pl.helpdesk.pages;

import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import pl.helpdesk.api.IAgentDao;
import pl.helpdesk.api.IClientDao;
import pl.helpdesk.api.IUserNotificationsDao;
import pl.helpdesk.entity.UserNotifications;
import pl.helpdesk.userSession.ApplicationSession;

public class AgentFinalPage extends AgentSuccessPage {

	private static final long serialVersionUID = 1L;

	@SpringBean
	private IAgentDao agentDao;

	@SpringBean
	private IUserNotificationsDao userNotificationsDao;
	
	public AgentFinalPage(PageParameters parameters) {
		super(parameters);
		if (!(ApplicationSession.getInstance().getUser() == null)
				&& agentDao.isAgent(ApplicationSession.getInstance().getUser())) {
			
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
					if(userNotifications.getSprawca().equals(ApplicationSession.getInstance().getUser().getLogin())){
						item.add(new Label("uzytkownik","Ty"));
					} else{
						item.add(new Label("uzytkownik", userNotifications.getSprawca()));
					}		
					item.add(new Label("tresc", userNotifications.getNotificationDataModel().getTresc()));
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
