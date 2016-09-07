package pl.helpdesk.components;

import java.util.List;

import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

public class SelectForm extends DropDownChoice {

	public SelectForm(String id, IModel model, List choices) {
		super(id, model, choices);
		add(new AttributeAppender("class", Model.of("btn btn-primary dropdown-toggle")));
	}

	

	

}
