package pl.helpdesk.panels;

import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

public class ClientComment extends AComment {

	private static final long serialVersionUID = 1L;

	public ClientComment(String id, String commentHeader, String commentContent) {
		super(id, commentHeader, commentContent);
		comment.add(new AttributeAppender("class", Model.of(" panel-success")));
		add(new AttributeAppender("class", Model.of("komentarzKlient")));
	}

}
