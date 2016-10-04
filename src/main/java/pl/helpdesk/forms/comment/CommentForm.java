package pl.helpdesk.forms.comment;

import java.util.Date;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import pl.helpdesk.api.ICommentDao;
import pl.helpdesk.entity.Comment;
import pl.helpdesk.entity.Issue;
import pl.helpdesk.userSession.ApplicationSession;

/**
 * Panel zawierający formularz dodania zgłoszenia
 * 
 * @author Krzysztof Krocz
 *
 */
public class CommentForm extends Panel{

	private static final long serialVersionUID = 1L;
	
	private Form<Void> addCommentForm;
	private TextArea content;
	private FileUploadField fileUploadField;
	private Button submitButton;
	private List<FileUpload> listOfAttachments;
	private Issue issue;
	@SpringBean
	private ICommentDao commentDao;
	
	public CommentForm(String id, Issue issue) {
		super(id);
		this.issue = issue;
		submitButton = new Button("submit");
		fileUploadField = new FileUploadField("fileUpload");
		content = new TextArea<String>("content",Model.of(""));
		addCommentForm = new Form<Void>("addCommentForm");
		submitButton.add(new AjaxFormSubmitBehavior(addCommentForm, "click"){
			@Override
			protected void onEvent(AjaxRequestTarget target) {
				System.out.println("Submit komentarza");
				Comment comment = new Comment();
				comment.setCzyWewnetrzny(false);
				comment.setDataDodania(new Date());
				comment.setProblemDataModel(getIssue());
				comment.setTresc(content.getValue());
				comment.setUserDataModel(ApplicationSession.getInstance().getUser());
				commentDao.save(comment);
				super.onEvent(target);
			}
		});
		addCommentForm.add(content);
		add(submitButton);
		addCommentForm.add(fileUploadField);
		addCommentForm.setMultiPart(true);
		add(addCommentForm);
	}
	

	public Issue getIssue() {
		return issue;
	}

	public void setIssue(Issue issue) {
		this.issue = issue;
	}

	public Form<Void> getAddCommentForm() {
		return addCommentForm;
	}

	public void setAddCommentForm(Form<Void> addCommentForm) {
		this.addCommentForm = addCommentForm;
	}

	public TextArea getContent() {
		return content;
	}

	public void setContent(TextArea content) {
		this.content = content;
	}

	public FileUploadField getFileUploadField() {
		return fileUploadField;
	}

	public void setFileUploadField(FileUploadField fileUploadField) {
		this.fileUploadField = fileUploadField;
	}

	public Button getSubmitButton() {
		return submitButton;
	}

	public void setSubmitButton(Button submitButton) {
		this.submitButton = submitButton;
	}

	public List<FileUpload> getListOfAttachments() {
		return listOfAttachments;
	}

	public void setListOfAttachments(List<FileUpload> listOfAttachments) {
		this.listOfAttachments = listOfAttachments;
	}

	public ICommentDao getCommentDao() {
		return commentDao;
	}

	public void setCommentDao(ICommentDao commentDao) {
		this.commentDao = commentDao;
	}
	

}
