package pl.helpdesk.forms;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;

/**
 * Panel z formularzem dodania nowych zgłoszeń
 * 
 * @author Krzysztof Krocz
 *
 */
public class AddIssueForm extends Panel{
	
	private static final long serialVersionUID = 1L;
	private TextField<String> title;
	private TextArea<String> contents;

	public AddIssueForm(String id) {
		super(id);
		title = new TextField<String>("title",new PropertyModel<String>(this, "title"));
		contents = new TextArea<String>("contents", new PropertyModel<String>(this, "contents"));
		Form<Void> addIssueForm = new Form<Void>("addIssueForm"){

			private static final long serialVersionUID = 1L;
			protected void onSubmit() {
				System.out.println("SUBMIT : " + getTitle() + " tresc:  "+getContents());
			};
			
		};
		addIssueForm.add(title);
		addIssueForm.add(contents);
		add(addIssueForm);
		
	}

	public TextField<String> getTitle() {
		return title;
	}

	public void setTitle(TextField<String> title) {
		this.title = title;
	}

	public TextArea<String> getContents() {
		return contents;
	}

	public void setContents(TextArea<String> contents) {
		this.contents = contents;
	}

	
	

}
