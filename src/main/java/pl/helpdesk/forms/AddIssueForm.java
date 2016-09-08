package pl.helpdesk.forms;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.bean.validation.PropertyValidator;
import org.apache.wicket.feedback.ExactLevelFeedbackMessageFilter;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import pl.helpdesk.api.IIssueTypeDao;
import pl.helpdesk.api.IPriorityDao;
import pl.helpdesk.components.SelectForm;
import pl.helpdesk.entity.Issue;
import pl.helpdesk.userSession.ApplicationSession;

/**
 * Panel z formularzem dodania nowych zgłoszeń
 * 
 * @author Krzysztof Krocz
 *
 */
public class AddIssueForm extends Panel{
	
	private static final long serialVersionUID = 1L;
	//@NotNull
	private String titleName;
	//@NotNull
	private String contentsName;
	private TextField<String> title;
	private TextArea<String> contents;
	//@NotNull
	private String selectedIssueType; 
	//@NotNull
	private String selectedPriority; 
	private ApplicationSession session;
	
	@SpringBean
	IIssueTypeDao issueTypeDao;
	@SpringBean
	IPriorityDao priorityDao;
	
	public AddIssueForm(String id) {
		super(id);
		this.setOutputMarkupId(true);
		titleName="";
		contentsName="";
		title = new TextField<String>("title",new PropertyModel<String>(this, "titleName"));
		contents = new TextArea<String>("contents", new PropertyModel<String>(this, "contentsName"));
		Form<Void> addIssueForm = new Form<Void>("addIssueForm"){

		/*	private static final long serialVersionUID = 1L;
			protected void onSubmit() {
				System.out.println("SUBMIT : " + titleName + " tresc:  "+ contentsName);
				System.out.println("ID Typu : " + "Priorytet :" );
				Issue newIssue  = new Issue();
				newIssue.setUser(session.getUser());
				DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				Date date = new Date();
				newIssue.setDataDodania(date);
				newIssue.setPriority(priorityDao.getPriorityByName(selectedPriority));
				newIssue.setTemat(titleName);
				newIssue.setTresc(contentsName);
				newIssue.setType(issueTypeDao.getIssueTypeByName(selectedIssueType));
			};*/
			
		};
		AjaxSubmitLink submitLink = new AjaxSubmitLink("ajaxSubmit",addIssueForm) {
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				boolean validForm = true;
				if(selectedIssueType == null){
					validForm=false;
					target.appendJavaScript( "$(\"#issueTypeE\").slideDown(\"medium\")");
				}
				else target.appendJavaScript( "$(\"#issueTypeE\").hide()");
				if(selectedPriority == null){
					validForm=false;
					target.appendJavaScript( "$(\"#priorityE\").slideDown(\"medium\")");
				}
				else target.appendJavaScript( "$(\"#priorityE\").hide()");
				if(titleName == null) {
					validForm=false;
					target.appendJavaScript( "$(\"#titleE\").slideDown(\"medium\")");
				}
				else target.appendJavaScript( "$(\"#titleE\").hide()");
				if(contentsName == null) {
					validForm=false;
					target.appendJavaScript( "$(\"#contentE\").slideDown(\"medium\")");
				}
				else target.appendJavaScript( "$(\"#contentE\").hide()");
				if(validForm){
				System.out.println("SUBMIT : " + titleName + " tresc:  "+ contentsName);
				}
				super.onSubmit(target, form);
			}
		};
		
		SelectForm selectIssueType = new SelectForm("selectIssueType",new PropertyModel<String>(this,"selectedIssueType"),issueTypeDao.getAllToString());
		SelectForm selectPriority = new SelectForm("selectPriority",new PropertyModel<String>(this,"selectedPriority"),priorityDao.getAllToString());
		addIssueForm.add(title.add(new PropertyValidator<>()));
		addIssueForm.add(contents.add(new PropertyValidator<>()));
		addIssueForm.add(selectIssueType.add(new PropertyValidator<>()));
		addIssueForm.add(selectPriority.add(new PropertyValidator<>()));
		addIssueForm.add(submitLink);
		add(addIssueForm);
		FeedbackPanel feedBackPanel = new FeedbackPanel("feedbackErrors", new ExactLevelFeedbackMessageFilter(FeedbackMessage.ERROR));
		feedBackPanel.setOutputMarkupId(true);
		add(feedBackPanel);

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
