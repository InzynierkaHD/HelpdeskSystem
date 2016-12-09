package pl.helpdesk.forms.comment;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.OnChangeAjaxBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import pl.helpdesk.api.IAdminDao;
import pl.helpdesk.api.ICommentDao;
import pl.helpdesk.api.IEmployeeDao;
import pl.helpdesk.api.INotificationDao;
import pl.helpdesk.api.IUserNotificationsDao;
import pl.helpdesk.entity.Admin;
import pl.helpdesk.entity.Comment;
import pl.helpdesk.entity.Issue;
import pl.helpdesk.mailsender.mailSender;
import pl.helpdesk.panels.issue.IssuePanel;
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
	private AjaxButton submitButton;
	/**
	 * Lista uploadowanych załączników
	 */
	private List<FileUpload> listOfAttachments;
	/**
	 * Zgłoszenie którego tyczy się komentarz
	 */
	private Issue issue;
	
	private IssuePanel panel;
	@SpringBean
	private ICommentDao commentDao;
	
	///*// DM start
	@SpringBean
	private IEmployeeDao employeeDao;
	
	//*/// DM stop
	
	
	
	@SpringBean
	private IAdminDao adminDao;
	
	@SpringBean
	private IUserNotificationsDao userNotificationsDao;
	
	@SpringBean
	private INotificationDao notificationDao;

	public CommentForm(String id, Issue issue, final IssuePanel panel) {
		super(id);
		this.issue = issue;
		this.panel = panel;
		fileUploadField = new FileUploadField("fileUpload");
		content = new TextArea<String>("content", Model.of(""));
		content.add(new OnChangeAjaxBehavior() {

			private static final long serialVersionUID = 2462233190993745889L;

			@Override
			protected void onUpdate(final AjaxRequestTarget target) {

				content.setDefaultModelObject(getComponent().getDefaultModelObject());

			}
		});
		addCommentForm = new Form<Void>("addCommentForm");
		addCommentForm.setMultiPart(true);
		addCommentForm.add(submitButton = new AjaxButton("ajaxSubmit") {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				System.out.println("This request was processed using AJAX");
				Comment comment = new Comment();
				comment.setCzyWewnetrzny(false);
				comment.setDataDodania(new Date());
				comment.setProblemDataModel(getIssue());
				comment.setTresc(content.getValue());
				comment.setUserDataModel(ApplicationSession.getInstance().getUser());
				commentDao.save(comment);
				List<FileUpload> uploadedFiles = fileUploadField.getFileUploads();
					URL url = this.getClass().getClassLoader().getResource("/Attachments");
					File newFile2 = new File("/");
					//File newFile1 = new File(newFile2.getAbsolutePath() + comment.getId() + "_" + uploadedFile.getClientFileName());
					for(FileUpload file : uploadedFiles){
						File newFileToUpload = new File(newFile2.getAbsolutePath() + comment.getId() + "_" + file.getClientFileName());
						
						if (newFileToUpload.exists()) {
							newFileToUpload.delete();
						}

							try {
								newFileToUpload.createNewFile();
								file.writeTo(newFileToUpload);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						
					}
				
				target.add(panel);
					
				// ajax-update the feedback panel
				// target.add(feedback);
				
				///* DM start
				if(employeeDao.isEmployee(ApplicationSession.getInstance().getUser()))
				{
					mailSender mailsender = new mailSender();
					mailsender.sendNotify("Powiadomienie - nowa odpowiedź", 
							"Adresatem tej wiadomości jest " + getIssue().getUser().getImie() + " " + getIssue().getUser().getNazwisko() + "\nPracownik HelpDesku odpowiedział na Twoje zgłoszenie!\n\nTreść odpowiedzi jest nastepująca:\n" + content.getValue(), 
							new Date(), 
							getIssue().getUser());
				}
				//*/ // DM stop
				
				
				
				List<Admin> allAdmins = adminDao.getAll();
				for (Admin allAdminss : allAdmins) {
					userNotificationsDao.addNotification(allAdminss.getUserDataModel(),
							notificationDao.getById(22), ApplicationSession.getInstance().getUser().getLogin());
				}
				userNotificationsDao.addNotification(ApplicationSession.getInstance().getUser(),
						notificationDao.getById(22), ApplicationSession.getInstance().getUser().getLogin());	
			}

		});
		addCommentForm.add(content);
		addCommentForm.add(fileUploadField);
		add(addCommentForm);
	}

	public AjaxButton getSubmitButton() {
		return submitButton;
	}

	public void setSubmitButton(AjaxButton submitButton) {
		this.submitButton = submitButton;
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
