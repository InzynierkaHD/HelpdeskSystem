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
import pl.helpdesk.components.AdminEditPassword;
import pl.helpdesk.components.AdminEditProfile;
import pl.helpdesk.entity.Employee;
import pl.helpdesk.entity.LoggingHistory;
import pl.helpdesk.entity.User;
import pl.helpdesk.userSession.ApplicationSession;

public final class AdminShowRaport extends AdminSuccessPage {

	
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

	private static final long serialVersionUID = 1L;

	public AdminShowRaport(PageParameters parameters) {
		super(parameters);
		if (!(ApplicationSession.getInstance().getUser() == null)
				&& adminDao.isAdmin(ApplicationSession.getInstance().getUser())) {
			
			
			String userIdString = "";
			userIdString = String.valueOf(parameters.get("userId"));
			int id = Integer.parseInt(userIdString);
			
			WebMarkupContainer datacontainer = new WebMarkupContainer("data");
			datacontainer.setOutputMarkupId(true);
			add(datacontainer);
			
			String typ = "";
			if (employeeDao.isEmployee(userDao.getById(id))) typ = "(Pracownik)";
			else if (agentDao.isAgent(userDao.getById(id))) typ = "(Przedstawiciel klienta)";
			else typ = "(Klient)";
			
			datacontainer.add(new Label("theWho", "Raport użytkownika: " + userDao.getById(id).getImie() + " " + userDao.getById(id).getNazwisko() + " " + typ));
			
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
			
		} else {
			setResponsePage(LoginPage.class);
		}
	}

}
