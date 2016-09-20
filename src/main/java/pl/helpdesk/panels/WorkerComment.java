package pl.helpdesk.panels;

import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

public class WorkerComment extends AComment {

	private static final long serialVersionUID = 1L;

	public WorkerComment(String id, String commentHeader, String commentContent) {
		super(id, commentHeader, commentContent);
		comment.add(new AttributeAppender("class", Model.of(" panel-info")));
		add(new AttributeAppender("class", Model.of("komentarzPracownik")));
	}

}
