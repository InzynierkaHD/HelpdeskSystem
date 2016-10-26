package pl.helpdesk.panels;

import java.io.File;
import java.util.List;

import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.model.Model;

public class ClientComment extends AComment {

	private static final long serialVersionUID = 1L;

	public ClientComment(String id, String commentHeader, String commentContent, List<File> listOfAttachments) {
		super(id, commentHeader, commentContent, listOfAttachments);
		comment.add(new AttributeAppender("class", Model.of(" panel-success")));
		add(new AttributeAppender("class", Model.of("komentarzKlient")));
	}

}
