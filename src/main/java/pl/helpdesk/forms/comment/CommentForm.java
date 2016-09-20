package pl.helpdesk.forms.comment;

import java.io.File;
import java.io.IOException;

import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.panel.Panel;

public class CommentForm extends Panel{

	private static final long serialVersionUID = 1L;
	
	private Form<Void> addCommentForm;
	private FileUploadField fileUploadField;
	private Button submitButton;
	
	public CommentForm(String id) {
		super(id);
		submitButton = new Button("submit");
		fileUploadField = new FileUploadField("fileUpload");
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
		addCommentForm.add(submitButton);
		addCommentForm.add(fileUploadField);
		addCommentForm.setMultiPart(true);
		add(addCommentForm);
	}

}
