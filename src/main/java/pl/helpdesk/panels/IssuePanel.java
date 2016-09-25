package pl.helpdesk.panels;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.IMarkupCacheKeyProvider;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.resource.PackageResourceReference;

import pl.helpdesk.entity.Issue;
import pl.helpdesk.forms.comment.CommentForm;

public class IssuePanel extends Panel {

	private static final long serialVersionUID = 1L;
	
	private RepeatingView clientComments;
	private Issue issue;
	private Label issueTopic;
	private Label issueContent;
	
	public IssuePanel(String id, Issue issue) {
		super(id);
		this.issue = issue;
			issueTopic = new Label("issueTopic",new PropertyModel<Issue>(this,"issue.temat"));
			issueContent = new Label("issueContent",new PropertyModel<Issue>(this,"issue.tresc"));
		clientComments = new RepeatingView("clientComments");
		clientComments.add(new ClientComment(clientComments.newChildId(),"head","content"));
		add(clientComments);
		add(issueTopic);
		add(issueContent);
		add(new AjaxLink("backToIssues"){

			@Override
			public void onClick(AjaxRequestTarget target) {
				target.appendJavaScript(" $(\"#issuePanel\").slideUp();");
				target.appendJavaScript(" $(\"#addIssueButton\").slideDown();");	
				target.appendJavaScript(" $(\"#myIssuesTable\").slideDown();");	
			}
			
		});
		add(new AjaxLink("addComment"){

			@Override
			public void onClick(AjaxRequestTarget target) {
				//CommentForm commentForm = new CommentForm("commentForm",getIssue());
				
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
		System.out.println("before ");
		super.onBeforeRender();
	}


	public Issue getIssue() {
		return issue;
	}

	public void setIssue(Issue issue) {
		this.issue = issue;
	}
	
	

}
