package pl.helpdesk.panels;

import java.util.Date;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import pl.helpdesk.api.IEmployeeDao;
import pl.helpdesk.api.IStatusDao;
import pl.helpdesk.api.IStatusHistoryDao;
import pl.helpdesk.components.SelectForm;
import pl.helpdesk.entity.Employee;
import pl.helpdesk.entity.Issue;
import pl.helpdesk.entity.Status;
import pl.helpdesk.entity.StatusHistory;
import pl.helpdesk.userSession.ApplicationSession;

public class StatusPanel extends Panel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Label status;
	private SelectForm statusSelect;
	private String selectedStatus;
	private Form setStatusForm;
	private IssuePanel issue;
	@SpringBean 
	private IStatusHistoryDao statusHistoryDao;
	
	@SpringBean 
	private IStatusDao statusDao;
	
	@SpringBean
	private IEmployeeDao employeeDao;
	
	public StatusPanel(String id,IssuePanel issue) {
		super(id);
		this.issue = issue;
		setStatusForm = new Form("setStatus");
		//selectedStatus= statusHistoryDao.getCurrentStatus(this.getIssue().getIssue()).getNazwa();
		status = new Label("status",new PropertyModel<String>(this,"selectedStatus"));
		statusSelect = new SelectForm("statusSelect",new PropertyModel<String>(this,"selectedStatus"),statusDao.getAllToString());
		statusSelect.add(new AjaxFormComponentUpdatingBehavior("onchange"){
			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				StatusHistory statusHistory = new StatusHistory();
				statusHistory.setData(new Date());
				statusHistory.setProblemDataModel(getIssue().getIssue());
				statusHistory.setStatusDataModel(statusDao.getStatusByName(selectedStatus));
				if(employeeDao.getEmployeeByUser(ApplicationSession.getInstance().getUser()) != null){
				Employee employee = employeeDao.getEmployeeByUser(ApplicationSession.getInstance().getUser());
				statusHistory.setEmployeeDataModel(employee);
				statusHistoryDao.save(statusHistory);
				getIssue().getIssue().setEmployee(employee);
				}
				System.out.println("update");
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
		
		Status currentStatus = statusHistoryDao.getCurrentStatus(this.getIssue().getIssue());
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

	

	public IssuePanel getIssue() {
		return issue;
	}

	public void setIssue(IssuePanel issue) {
		this.issue = issue;
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
