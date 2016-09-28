package pl.helpdesk.pages;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import pl.helpdesk.api.IIssueDao;
import pl.helpdesk.components.AlertModal;
import pl.helpdesk.components.AlertModal.typeAlert;
import pl.helpdesk.components.table.Tabelka;
import pl.helpdesk.components.table.TableCol;
import pl.helpdesk.entity.Issue;
import pl.helpdesk.forms.AddIssueForm;
import pl.helpdesk.panels.IssuePanel;
import pl.helpdesk.userSession.ApplicationSession;

public class MyIssue extends ClientSuccessPage {

	private static final long serialVersionUID = 1L;
	private AjaxLink<String> addIssue;
	private AlertModal alert;
	private AddIssueForm addIssueForm;
	private Tabelka<Issue> myIssueTable;
	private IssuePanel issuePanel;

	@SpringBean
	IIssueDao issueDao;

	public MyIssue(PageParameters parameters) {
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
		TableCol col = new TableCol(true, "temat");
		TableCol col2 = new TableCol(false, "prioritoryDataModel");
		TableCol col3 = new TableCol(false, "typeDataModel");
		TableCol col4 = new TableCol(false, "dataDodania");
		TableCol col5 = new TableCol(false, "employeeDataModel");
		List<TableCol> listaCol = new ArrayList<TableCol>();
		listaCol.add(col);
		listaCol.add(col2);
		listaCol.add(col3);
		listaCol.add(col4);
		listaCol.add(col5);
		Tabelka<Issue> myIssueTable = new Tabelka<Issue>("myIssues", listaCol,
				new String[] { "Temat", "Priorytet", "Typ", "Data Dodania", "Pracownik obsługujący" }, issueDao.getAllIssuesForUser(ApplicationSession.getInstance().getUser()),issueDao, true) {
			@Override
			public void rowClickEvent(AjaxRequestTarget target, Component component) {
				Issue clickedIssue = (Issue) component.getDefaultModel().getObject();
				issuePanel.setIssue(clickedIssue);
				target.add(issuePanel);
				target.appendJavaScript(" $(\"#addIssueButton\").slideUp();");
				target.appendJavaScript(" $(\"#myIssuesTable\").slideUp();");
				target.appendJavaScript(" $(\"#issuePanel\").slideDown();");

				// super.rowClickEvent();
			}

		};
		issuePanel = new IssuePanel("issuePanel", myIssueTable.getEntity());
		issuePanel.setOutputMarkupId(true);
		this.myIssueTable = myIssueTable;
		add(myIssueTable);
		add(issuePanel);
	}

}
