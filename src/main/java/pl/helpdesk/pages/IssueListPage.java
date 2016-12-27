package pl.helpdesk.pages;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import pl.helpdesk.api.IAdminDao;
import pl.helpdesk.api.IEmployeeDao;
import pl.helpdesk.api.IIssueDao;
import pl.helpdesk.components.AlertModal;
import pl.helpdesk.components.AlertModal.typeAlert;
import pl.helpdesk.components.table.Table;
import pl.helpdesk.components.table.TableColumn;
import pl.helpdesk.components.tableNew.TableNew;
import pl.helpdesk.entity.Issue;
import pl.helpdesk.forms.AddIssueForm;
import pl.helpdesk.panels.issue.IssuePanel;
import pl.helpdesk.userSession.ApplicationSession;

public class IssueListPage extends ClientSuccessPage {

	private static final long serialVersionUID = 1L;
	private AjaxLink<String> addIssue;
	private AlertModal alert;
	private AddIssueForm addIssueForm;
	private Table<Issue> myIssueTable;
	private IssuePanel issuePanel;

	@SpringBean
	IIssueDao issueDao;
	
	@SpringBean 
	IEmployeeDao employeeDao;
	
	@SpringBean
	IAdminDao adminDao;

	public IssueListPage(PageParameters parameters) {
		super(parameters);
			
		addIssueForm = new AddIssueForm("form");
		alert = new AlertModal("alert", new ArrayList<AjaxLink>(), typeAlert.info, "Dodaj zgłoszenie",
				new StringBuilder("<div wicket:id=\"form\"></div>"));
		alert.add(addIssueForm);
		add(alert);
		alert.setVisible(true);
		add(addIssue = new AjaxLink<String>("addIssue") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				target.appendJavaScript("$('#myModal').modal('show');");

			}

		});
		List listOfRows;
		if(employeeDao.isEmployee(ApplicationSession.getInstance().getUser()) || adminDao.isAdmin(ApplicationSession.getInstance().getUser())){
			listOfRows = issueDao.getAll();
			addIssue.setVisible(false);
		}
		else{
			listOfRows = issueDao.getAllIssuesForUser(ApplicationSession.getInstance().getUser());
		}
		List<TableColumn> listColumnName = new ArrayList<TableColumn>();
		/*listColumnName.add(new TableColumn("Temat","temat"));
		listColumnName.add(new TableColumn("Priorytet","prioritoryDataModel"));
		listColumnName.add(new TableColumn("Typ","typeDataModel"));
		listColumnName.add(new TableColumn("Data Dodania","dataDodania"));
		listColumnName.add(new TableColumn("Pracownik Obsługujący","employeeDataModel"));*/
		/*final Table<Issue> myIssueTable = new Table<Issue>("myIssues", listOfRows,issueDao) {
			@Override
			public void rowClickEvent(AjaxRequestTarget target, Component component) {
				Issue clickedIssue = (Issue) component.getDefaultModel().getObject();
				issuePanel.setIssue(clickedIssue);
				target.add(issuePanel);
				target.appendJavaScript(" $(\"#addIssueButton\").slideUp();");
				target.appendJavaScript(" $(\"#myIssuesTable\").slideUp();");
				target.appendJavaScript(" $(\"#issuePanel\").slideDown();");
				target.appendJavaScript("window.scrollTo(0,document.body.scrollHeight);");
				// super.rowClickEvent();
			}

		};*/
		TableNew table = new TableNew("tableNew");
		add(table);
		/*issuePanel = new IssuePanel("issuePanel", myIssueTable.getEntity());
		issuePanel.setOutputMarkupId(true);*/
		this.myIssueTable = myIssueTable;
		/*issuePanel.getCommentForm().getSubmitButton().add(new AjaxEventBehavior("onclick") {
	        @Override
	        protected void onEvent(final AjaxRequestTarget target) {
	        	System.out.println("AJAXEVENT");
	            target.add(issuePanel);
	        }
	    });*//*
		add(new AjaxFormSubmitBehavior(issuePanel.getCommentForm().getAddCommentForm(),"onsubmit"){

			private static final long serialVersionUID = 1L;
			@Override
			protected void onSubmit(AjaxRequestTarget target) {
				System.out.println("AJAXEVENT2");
				target.add(issuePanel);
				super.onSubmit(target);
			}
			
		});*/
		//add(myIssueTable);
		//add(issuePanel);
	}

}
