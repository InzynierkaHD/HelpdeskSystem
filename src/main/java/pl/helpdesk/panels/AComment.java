package pl.helpdesk.panels;

import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

public abstract class AComment extends Panel {

	private static final long serialVersionUID = 1L;

	private String commentHeader;
	private String commentContent;
	protected WebMarkupContainer comment;

	public AComment(String id, String commentHeader, String commentContent) {
		super(id);
		this.commentContent = commentContent;
		this.commentHeader = commentHeader;
		
		comment = new WebMarkupContainer("comment");
		comment.add(new Label("commentContent", Model.of(commentContent)));
		comment.add(new Label("commentHeader", Model.of(commentHeader)));
		add(comment);
	}

	public String getCommentHeader() {
		return commentHeader;
	}

	public void setCommentHeader(String commentHeader) {
		this.commentHeader = commentHeader;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public WebMarkupContainer getComment() {
		return comment;
	}

	public void setComment(WebMarkupContainer comment) {
		this.comment = comment;
	}

}
