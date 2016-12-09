package pl.helpdesk.pages;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import pl.helpdesk.api.IAdminDao;
import pl.helpdesk.api.IAgentDao;
import pl.helpdesk.api.IEmployeeDao;
import pl.helpdesk.api.ILoggingHistoryDao;
import pl.helpdesk.api.IUserDao;
import pl.helpdesk.api.IUserNotificationsDao;
import pl.helpdesk.components.AdminEditPassword;
import pl.helpdesk.components.AdminEditProfile;
import pl.helpdesk.entity.Employee;
import pl.helpdesk.entity.LoggingHistory;
import pl.helpdesk.entity.User;
import pl.helpdesk.entity.UserNotifications;
import pl.helpdesk.userSession.ApplicationSession;

public final class AgentShowRaport extends AgentSuccessPage {

	
	@SpringBean
	private IEmployeeDao employeeDao;

	@SpringBean
	private IAdminDao adminDao;

	@SpringBean
	private IUserDao userDao;
	
	@SpringBean
	private IAgentDao agentDao;
	
	@SpringBean
	private ILoggingHistoryDao loggingHistoryDao;
	
	@SpringBean
	private IUserNotificationsDao userNotificationsDao;

	private static final long serialVersionUID = 1L;

	public AgentShowRaport(PageParameters parameters) {
		super(parameters);
		if (!(ApplicationSession.getInstance().getUser() == null)
				&& agentDao.isAgent(ApplicationSession.getInstance().getUser())) {
			
			
			String userIdString = "";
			userIdString = String.valueOf(parameters.get("userId"));
			int id = Integer.parseInt(userIdString);
			String typ = "";
			if (employeeDao.isEmployee(userDao.getById(id))) typ = "(Pracownik)";
			else if (agentDao.isAgent(userDao.getById(id))) typ = "(Przedstawiciel klienta)";
			else typ = "(Klient)";
			
			add(new Label("theWho", userDao.getById(id).getImie() + " " + userDao.getById(id).getNazwisko() + " " + typ));
			
			WebMarkupContainer datacontainer = new WebMarkupContainer("data");
			datacontainer.setOutputMarkupId(true);
			add(datacontainer);
			
			PageableListView<?> pageableListView = new PageableListView<LoggingHistory>("lista", loggingHistoryDao.getDateSortedUserLoggingHistory(id), 10) {

				private static final long serialVersionUID = 1L;

				@Override
				protected void populateItem(ListItem<LoggingHistory> item) {
					final LoggingHistory loggingHistory = (LoggingHistory) item.getModelObject();
					item.add(new Label("loginDatetime", loggingHistory.getDataLogowania()));
					item.add(new Label("logoutDatetime", loggingHistory.getDataWylogowania()));
					if(loggingHistory.getDataWylogowania() != null && loggingHistory.getDataLogowania() != null)
						item.add(new Label("sessionTime", DurationFormatUtils.formatDuration(loggingHistory.getDataWylogowania().getTime() - loggingHistory.getDataLogowania().getTime(), "HH:mm:ss")));
					else item.add(new Label("sessionTime", "-"));
					
					
				}

			};
			
			datacontainer.add(pageableListView);
			datacontainer.add(new AjaxPagingNavigator("nav", pageableListView));
			datacontainer.setVersioned(false);
			
			
			
			WebMarkupContainer datacontainer2 = new WebMarkupContainer("powiadomienia");
			datacontainer.setOutputMarkupId(true);
			add(datacontainer2);
			PageableListView<?> pageableListView2 = new PageableListView<UserNotifications>("listaPowiadomien", userNotificationsDao.getNotificationByUser(userDao.getById(id)), 8) {
				
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
			datacontainer2.add(pageableListView2);
			datacontainer2.add(new AjaxPagingNavigator("nav2", pageableListView2));
			datacontainer2.setVersioned(false);
			
		} else {
			setResponsePage(LoginPage.class);
		}
	}

}
