package pl.helpdesk.panels.issuestatus;

import java.util.Date;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import pl.helpdesk.api.IAdminDao;
import pl.helpdesk.api.IEmployeeDao;
import pl.helpdesk.api.IIssueDao;
import pl.helpdesk.api.INotificationDao;
import pl.helpdesk.api.IStatusDao;
import pl.helpdesk.api.IStatusHistoryDao;
import pl.helpdesk.api.IUserNotificationsDao;
import pl.helpdesk.components.SelectForm;
import pl.helpdesk.entity.Admin;
import pl.helpdesk.entity.Employee;
import pl.helpdesk.entity.Issue;
import pl.helpdesk.entity.Status;
import pl.helpdesk.entity.StatusHistory;
import pl.helpdesk.panels.issue.IssuePanel;
import pl.helpdesk.userSession.ApplicationSession;

public class IssueStatusPanel extends Panel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Label status;
	private SelectForm statusSelect;
	private String selectedStatus;
	private Form setStatusForm;
	private IssuePanel issuePanel;
	@SpringBean 
	private IStatusHistoryDao statusHistoryDao;
	
	@SpringBean
	private IAdminDao adminDao;
	
	@SpringBean 
	private IStatusDao statusDao;
	
	@SpringBean
	private IUserNotificationsDao userNotificationsDao;

	@SpringBean
	private INotificationDao notificationDao;
	@SpringBean
	private IEmployeeDao employeeDao;
	
	@SpringBean
	private IIssueDao issueDao;
	
	public IssueStatusPanel(String id,IssuePanel issue) {
		super(id);
		this.issuePanel = issue;
		setStatusForm = new Form("setStatus");
		//selectedStatus= statusHistoryDao.getCurrentStatus(this.getIssue().getIssue()).getNazwa();
		statusSelect = new SelectForm("statusSelect",new PropertyModel<String>(this,"selectedStatus"),statusDao.getAllToString());
		status = new Label("status",new PropertyModel<String>(this,"selectedStatus"));
		status.setOutputMarkupId(true);
		statusSelect.add(new AjaxFormComponentUpdatingBehavior("onchange"){
			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				StatusHistory statusHistory = new StatusHistory();
				statusHistory.setData(new Date());
				statusHistory.setProblemDataModel(getIssuePanel().getIssue());
				statusHistory.setStatusDataModel(statusDao.getStatusByName(selectedStatus));
				if(employeeDao.getEmployeeByUser(ApplicationSession.getInstance().getUser()) != null){
				Employee employee = employeeDao.getEmployeeByUser(ApplicationSession.getInstance().getUser());
				statusHistory.setEmployeeDataModel(employee);
				statusHistoryDao.update(statusHistory);
				getIssuePanel().getIssue().setEmployee(employee);
				Issue issueToSave = issueDao.getById(getIssuePanel().getIssue().getId());
				issueToSave.setEmployee(employee);
				issueDao.update(issueToSave);
				List<Admin> allAdmins = adminDao.getAll();
				for (Admin allAdminss : allAdmins) {
					userNotificationsDao.addNotification(allAdminss.getUserDataModel(),
							notificationDao.getById(24), ApplicationSession.getInstance().getUser().getLogin());
				}
				userNotificationsDao.addNotification(ApplicationSession.getInstance().getUser(),
						notificationDao.getById(24), ApplicationSession.getInstance().getUser().getLogin());
				target.add(status);
				
				}
			}
		});
		statusSelect.setVisible(false);
		if(employeeDao.isEmployee(ApplicationSession.getInstance().getUser())) statusSelect.setVisible(true);
		setStatusForm.add(statusSelect);
		add(status);
		add(setStatusForm);
	}
	@Override
	protected void onBeforeRender() {
		selectedStatus = "nowe";
		Status currentStatus = statusHistoryDao.getCurrentStatus(this.getIssuePanel().getIssue());
		if(currentStatus != null){ selectedStatus = currentStatus.getNazwa();
		System.out.println("zmieniam status na: "+selectedStatus);
		}
		super.onBeforeRender();
	}

	public Label getStatus() {
		return status;
	}

	public void setStatus(Label status) {
		this.status = status;
	}

	public SelectForm getStatusSelect() {
		return statusSelect;
	}

	public void setStatusSelect(SelectForm statusSelect) {
		this.statusSelect = statusSelect;
	}

	public String getSelectedStatus() {
		return selectedStatus;
	}

	public void setSelectedStatus(String selectedStatus) {
		this.selectedStatus = selectedStatus;
	}

	public Form getSetStatusForm() {
		return setStatusForm;
	}

	public void setSetStatusForm(Form setStatusForm) {
		this.setStatusForm = setStatusForm;
	}

	

	public IssuePanel getIssuePanel() {
		return issuePanel;
	}

	public void setIssuePanel(IssuePanel issue) {
		this.issuePanel = issue;
	}

	public IStatusHistoryDao getStatusHistoryDao() {
		return statusHistoryDao;
	}

	public void setStatusHistoryDao(IStatusHistoryDao statusHistoryDao) {
		this.statusHistoryDao = statusHistoryDao;
	}

	public IStatusDao getStatusDao() {
		return statusDao;
	}

	public void setStatusDao(IStatusDao statusDao) {
		this.statusDao = statusDao;
	}

	public IEmployeeDao getEmployeeDao() {
		return employeeDao;
	}

	public void setEmployeeDao(IEmployeeDao employeeDao) {
		this.employeeDao = employeeDao;
	}


}
