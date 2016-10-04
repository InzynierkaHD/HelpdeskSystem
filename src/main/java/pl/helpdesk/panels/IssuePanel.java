package pl.helpdesk.panels;

import java.util.Date;

import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.spring.injection.annot.SpringBean;

import pl.helpdesk.api.ICommentDao;
import pl.helpdesk.api.IEmployeeDao;
import pl.helpdesk.entity.Comment;
import pl.helpdesk.entity.Issue;
import pl.helpdesk.forms.comment.CommentForm;
import pl.helpdesk.userSession.ApplicationSession;

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
	@SpringBean
	private ICommentDao commentDao;
	@SpringBean
	private IEmployeeDao employeDao;

	public IssuePanel(String id, Issue issue) {
		super(id);
		this.issue = issue;
		issueTopic = new Label("issueTopic", new PropertyModel<Issue>(this, "issue.temat"));
		issueContent = new Label("issueContent", new PropertyModel<Issue>(this, "issue.tresc"));
		clientComments = new RepeatingView("clientComments");
		commentForm = new CommentForm("commentForm", this.issue);
		add(clientComments);
		add(issueTopic);
		add(issueContent);
		add(commentForm);
		add(new AjaxLink("backToIssues") {

			@Override
			public void onClick(AjaxRequestTarget target) {
				target.appendJavaScript(" $(\"#issuePanel\").slideUp();");
				target.appendJavaScript(" $(\"#addIssueButton\").slideDown();");
				target.appendJavaScript(" $(\"#myIssuesTable\").slideDown();");
			}

		});
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
		for (Comment comment : commentDao.getCommentByIssue(this.issue)) {
			if (employeDao.isEmployee(comment.getUserDataModel()))
				clientComments.add(new WorkerComment(clientComments.newChildId(), comment.getUserDataModel().getLogin(),
						comment.getTresc()));
			else {
				clientComments.add(new ClientComment(clientComments.newChildId(), comment.getUserDataModel().getLogin(),
						comment.getTresc()));
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