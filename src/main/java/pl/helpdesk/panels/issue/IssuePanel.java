package pl.helpdesk.panels.issue;

import java.io.File;
import java.net.URL;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.spring.injection.annot.SpringBean;

import pl.helpdesk.api.ICommentDao;
import pl.helpdesk.api.IEmployeeDao;
import pl.helpdesk.entity.Comment;
import pl.helpdesk.entity.Issue;
import pl.helpdesk.filefinder.FileFinder;
import pl.helpdesk.forms.comment.CommentForm;
import pl.helpdesk.panels.comment.ClientComment;
import pl.helpdesk.panels.comment.EmployeeComment;
import pl.helpdesk.panels.issuestatus.IssueStatusPanel;

/**
 * Panel na którym zostaje wyświetlona informacja o zgłoszeniu, jego komentarze
 * oraz możliwość dodania komentarza do zgłoszenia
 * 
 * @author Krzysiek
 *
 */
public class IssuePanel extends Panel {

	private static final long serialVersionUID = 1L;

	private RepeatingView clientComments;
	private Issue issue;
	private Label issueTopic;
	private Label issueContent;
	private CommentForm commentForm;
	private IssueStatusPanel statusPanel;
	private Label issueOwner;
	private String issueOwnerName;
	@SpringBean
	private ICommentDao commentDao;
	@SpringBean
	private IEmployeeDao employeDao;

	public IssuePanel(String id, Issue issue) {
		super(id);
		this.issue = issue;
		issueTopic = new Label("issueTopic", new PropertyModel<Issue>(this, "issue.id"));
		issueContent = new Label("issueContent", new PropertyModel<Issue>(this, "issue.tresc"));
		clientComments = new RepeatingView("clientComments");
		commentForm = new CommentForm("commentForm", this.issue, this);
		statusPanel = new IssueStatusPanel("statusPanel",this);
		issueOwner = new Label("issueOwner",new PropertyModel<Issue>(this, "issue.employeeDataModel"));
		add(issueOwner);
		add(statusPanel);
		add(clientComments);
		add(issueTopic);
		add(issueContent);
		add(commentForm);
		add(new AjaxLink("backToIssues") {

			@Override
			public void onClick(AjaxRequestTarget target) {
				onClickBackButton(target);
				//target.appendJavaScript(" $(\"#issuePanel\").slideUp();");
				//target.appendJavaScript(" $(\"#addIssueButton\").slideDown();");
				//target.appendJavaScript(" $(\"#myIssuesTable\").slideDown();");
			}

		});
	}

	public void onClickBackButton(AjaxRequestTarget target){
		
	}
	
	@Override
	public void renderHead(IHeaderResponse response) {
		PackageResourceReference cssFile = new PackageResourceReference(IssuePanel.class, "issuePanel.css");
		CssHeaderItem cssItem = CssHeaderItem.forReference(cssFile);

		response.render(cssItem);
	}

	@Override
	protected void onBeforeRender() {
		commentForm.setIssue(this.getIssue());
		clientComments.removeAll();
		/*if(issue != null)
			issueOwner = new Label("issueOwner",Model.of(issue.getEmployee().getUserDataModel().getLogin()));
			else{
				issueOwner = new Label("issueOwner",Model.of("Brak"));
			}*/
		 URL url = this.getClass().getClassLoader().getResource("/Attachments");
		 File newFile = new File("/");
		 FileFinder finder = new FileFinder(newFile.getAbsolutePath());
		 List<File> foundFiles;
		for (Comment comment : commentDao.getCommentByIssue(this.issue)) {
			foundFiles = finder.getAllFilesFromFolderBeforeSeparator("_",String.valueOf(comment.getId()));
			if (employeDao.isEmployee(comment.getUserDataModel())){
				clientComments.add(new EmployeeComment(clientComments.newChildId(), comment.getUserDataModel().getImie()+" "+comment.getUserDataModel().getNazwisko(),comment.getDataDodania(),
						comment.getTresc(),foundFiles));
			}
			else {
				clientComments.add(new ClientComment(clientComments.newChildId(), comment.getUserDataModel().getImie()+" "+comment.getUserDataModel().getNazwisko(),comment.getDataDodania(),
						comment.getTresc(),foundFiles));
			}
		}

		super.onBeforeRender();
	}

	public Issue getIssue() {
		return issue;
	}

	public void setIssue(Issue issue) {
		this.issue = issue;
	}

	public RepeatingView getClientComments() {
		return clientComments;
	}

	public void setClientComments(RepeatingView clientComments) {
		this.clientComments = clientComments;
	}

	public Label getIssueTopic() {
		return issueTopic;
	}

	public void setIssueTopic(Label issueTopic) {
		this.issueTopic = issueTopic;
	}

	public Label getIssueContent() {
		return issueContent;
	}

	public void setIssueContent(Label issueContent) {
		this.issueContent = issueContent;
	}

	public CommentForm getCommentForm() {
		return commentForm;
	}

	public void setCommentForm(CommentForm commentForm) {
		this.commentForm = commentForm;
	}

	public ICommentDao getCommentDao() {
		return commentDao;
	}

	public void setCommentDao(ICommentDao commentDao) {
		this.commentDao = commentDao;
	}

	public IEmployeeDao getEmployeDao() {
		return employeDao;
	}

	public void setEmployeDao(IEmployeeDao employeDao) {
		this.employeDao = employeDao;
	}

}
