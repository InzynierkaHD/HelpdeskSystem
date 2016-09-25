package pl.helpdesk.forms.comment;

import java.io.File;
import java.io.IOException;

import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import pl.helpdesk.api.ICommentDao;
import pl.helpdesk.entity.Issue;

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
	@SpringBean
	private ICommentDao commentDao;
	
	public CommentForm(String id, Issue issue) {
		super(id);
		submitButton = new Button("submit");
		fileUploadField = new FileUploadField("fileUpload");
		content = new TextArea("content");
		addCommentForm = new Form<Void>("addCommentForm"){
			@Override
			protected void onSubmit() {
				final FileUpload uploadedFile = fileUploadField.getFileUpload();
				try {
					 ClassLoader classLoader = getClass().getClassLoader(); 
					File file = new File(classLoader .getResource("Attachments").toURI().getPath() +
							uploadedFile.getClientFileName());

					uploadedFile.writeTo(file);
					System.out.println("uploadedFile " + uploadedFile.getClientFileName());
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				super.onSubmit();
			}
		};
		
		//submitButton.add(new AjaxFormSubmitBehavior(addCommentForm, "click"){});
		addCommentForm.add(content);
		addCommentForm.add(submitButton);
		addCommentForm.add(fileUploadField);
		addCommentForm.setMultiPart(true);
		add(addCommentForm);
	}

}
