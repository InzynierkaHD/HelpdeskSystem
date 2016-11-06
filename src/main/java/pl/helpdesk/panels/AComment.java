package pl.helpdesk.panels;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.DownloadLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.PackageResourceReference;

import pl.helpdesk.forms.AddIssueForm;

public abstract class AComment extends Panel {

	private static final long serialVersionUID = 1L;
	Logger log = Logger.getLogger(this.getClass().getName());
	private String commentHeader;
	private String commentContent;
	private Date dateAdded;
	private List<File> listOfAttachments;
	protected WebMarkupContainer comment;
	private RepeatingView attachments;

	public AComment(String id, String commentHeader,Date dateAdded, String commentContent, List<File> listOfAttachments) {
		super(id);
		this.commentContent = commentContent;
		this.commentHeader = commentHeader;
		this.listOfAttachments = listOfAttachments;
		this.dateAdded = dateAdded;
		attachments = new RepeatingView("attachments");
		if(listOfAttachments != null){
		for(File file : listOfAttachments){
			//attachments.add(new Label(attachments.newChildId(),file.getName()));
			DownloadLink attachment = new DownloadLink(attachments.newChildId(), file, file.getName());
			attachment.add(new AttributeAppender("value", file.getName()));
			attachments.add(attachment);
		}
		}
		
		comment = new WebMarkupContainer("comment");
		comment.add(attachments);
		comment.add(new Label("date",Model.of(dateAdded)));
		comment.add(new Label("commentContent", Model.of(commentContent)));
		comment.add(new Label("commentHeader", Model.of(commentHeader)));
		add(comment);
	}
	
	@Override
	public void renderHead(IHeaderResponse response) {
		PackageResourceReference cssFile = new PackageResourceReference(AComment.class, "AComment.css");
		CssHeaderItem cssItem = CssHeaderItem.forReference(cssFile);
		response.render(cssItem);
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
