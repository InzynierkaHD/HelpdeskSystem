package pl.helpdesk.forms.comment;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.ajax.form.OnChangeAjaxBehavior;
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
public class CommentForm extends Panel {

	private static final long serialVersionUID = 1L;
	/**
	 * Formularz zawierający wszystkie kontrolki dotyczące dodania zgłoszenia
	 */
	private Form<Void> addCommentForm;
	/**
	 * TextArea przechowująca treść komentarza
	 */
	private TextArea content;
	/**
	 * Browser dla załączników
	 */
	private FileUploadField fileUploadField;
	/**
	 * Button Zatwierdzający dodanie komentarza
	 */
	private Button submitButton;
	/**
	 * Lista uploadowanych załączników
	 */
	private List<FileUpload> listOfAttachments;
	/**
	 * Zgłoszenie którego tyczy się komentarz
	 */
	private Issue issue;
	@SpringBean
	private ICommentDao commentDao;

	public CommentForm(String id, Issue issue) {
		super(id);
		this.issue = issue;
		submitButton = new Button("submit");
		fileUploadField = new FileUploadField("fileUpload");
		content = new TextArea<String>("content", Model.of(""));
		//Update modelu zawartości komentarza przy każdym wpisaniu /usunieciu litery 
		content.add(new OnChangeAjaxBehavior() {

			private static final long serialVersionUID = 2462233190993745889L;

			@Override
			protected void onUpdate(final AjaxRequestTarget target) {

				content.setDefaultModelObject(getComponent().getDefaultModelObject());

			}
		});
		addCommentForm = new Form<Void>("addCommentForm");
		submitButton.add(new AjaxFormSubmitBehavior(addCommentForm, "click") {
			@Override
			protected void onEvent(AjaxRequestTarget target) {
				Comment comment = new Comment();
				comment.setCzyWewnetrzny(false);
				comment.setDataDodania(new Date());
				comment.setProblemDataModel(getIssue());
				comment.setTresc(content.getValue());
				comment.setUserDataModel(ApplicationSession.getInstance().getUser());
				commentDao.save(comment);
				final FileUpload uploadedFile = fileUploadField.getFileUpload();
				if (uploadedFile != null) {

					// write to a new file
					File newFile = new File("Attachments/"
						+ uploadedFile.getClientFileName());

					if (newFile.exists()) {
						newFile.delete();
					}

					try {
						newFile.createNewFile();
						uploadedFile.writeTo(newFile);

						info("saved file: " + uploadedFile.getClientFileName());
					} catch (Exception e) {
						throw new IllegalStateException("Error");
					}
				 }
				super.onEvent(target);
			}
		});
		addCommentForm.setMultiPart(true);
		addCommentForm.add(content);
		addCommentForm.add(submitButton);
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
