package pl.helpdesk.forms;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import pl.helpdesk.api.IIssueTypeDao;
import pl.helpdesk.api.IPriorityDao;
import pl.helpdesk.components.SelectForm;

/**
 * Panel z formularzem dodania nowych zgłoszeń
 * 
 * @author Krzysztof Krocz
 *
 */
public class AddIssueForm extends Panel{
	
	private static final long serialVersionUID = 1L;
	private String titleName;
	private String contentsName;
	private TextField<String> title;
	private TextArea<String> contents;
	private String selectedIssueType; 
	private String selectedPriority; 
	@SpringBean
	IIssueTypeDao issueTypeDao;
	@SpringBean
	IPriorityDao priorityDao;
	
	public AddIssueForm(String id) {
		super(id);
		titleName="";
		contentsName="";
		title = new TextField<String>("title",new PropertyModel<String>(this, "titleName"));
		contents = new TextArea<String>("contents", new PropertyModel<String>(this, "contentsName"));
		Form<Void> addIssueForm = new Form<Void>("addIssueForm"){

			private static final long serialVersionUID = 1L;
			protected void onSubmit() {
				System.out.println("SUBMIT : " + titleName + " tresc:  "+ contentsName);
				System.out.println("ID Typu : "+ selectedIssueType);
			};
			
		};
		SelectForm selectIssueType = new SelectForm("selectIssueType",new PropertyModel<String>(this,selectedIssueType),issueTypeDao.getAllToString());
		SelectForm selectPriority = new SelectForm("selectPriority",new PropertyModel<String>(this,selectedPriority),priorityDao.getAllToString());
		addIssueForm.add(title);
		addIssueForm.add(contents);
		addIssueForm.add(selectIssueType);
		addIssueForm.add(selectPriority);
		add(addIssueForm);
		
	}

	public String getTitleName() {
		return titleName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}

	public String getContentsName() {
		return contentsName;
	}

	public void setContentsName(String contentsName) {
		this.contentsName = contentsName;
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
