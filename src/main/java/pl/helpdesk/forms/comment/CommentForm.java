package pl.helpdesk.forms.comment;

import java.io.File;
import java.net.URL;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

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

import pl.helpdesk.api.ICommentDao;
import pl.helpdesk.entity.Comment;
import pl.helpdesk.entity.Issue;
import pl.helpdesk.panels.IssuePanel;
import pl.helpdesk.userSession.ApplicationSession;

///*//DM start
import pl.helpdesk.api.IEmployeeDao;
import pl.helpdesk.api.INotificationDao;
import pl.helpdesk.api.IUserNotificationsDao;
import pl.helpdesk.entity.Notification;
import pl.helpdesk.entity.UserNotifications;
import pl.helpdesk.mailsender.mailSender;
//*///DM stop

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
	private INotificationDao notificationDao;
	@SpringBean
	private IUserNotificationsDao userNotificationsDao;
	@SpringBean
	private IEmployeeDao employeeDao;
	//*/// DM stop

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
					System.out.println("UPLOADED FILSE: "+uploadedFiles.size());
					//File newFile1 = new File(newFile2.getAbsolutePath() + comment.getId() + "_" + uploadedFile.getClientFileName());
					for(FileUpload file : uploadedFiles){
						File newFileToUpload = new File(newFile2.getAbsolutePath() + comment.getId() + "_" + file.getClientFileName());
						
						if (newFileToUpload.exists()) {
							newFileToUpload.delete();
						}

						try {
							newFileToUpload.createNewFile();
							file.writeTo(newFileToUpload);

							System.out.println("saved file: " + newFileToUpload.getAbsolutePath());
						} catch (Exception e) {
							throw new IllegalStateException("Error");
						}
					}
				
				target.add(panel);
					
				// ajax-update the feedback panel
				// target.add(feedback);
				
				
				///* DM start
				if(employeeDao.isEmployee(ApplicationSession.getInstance().getUser()))
				{
						try {
							wyslanieMaila();
						} catch (AddressException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (MessagingException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
				}
				//} catch(Exception e) {}
				//*/ // DM stop
			} //--------------------------------------------------------------------------------------------------------------
			///*// DM start
			private void wyslanieMaila() throws AddressException, MessagingException {
				
				Notification notification = new Notification();
				notification.setNazwa("Powiadomienie - nowa odpowiedz");
				notification.setTresc(content.getValue());
				notificationDao.save(notification);
				
				
				UserNotifications userNotifications = new UserNotifications();
				userNotifications.setDataWyslania(new Date());
				userNotifications.setNotificationDataModel(notification);
				userNotifications.setUserDataModel(getIssue().getUser());
				userNotifications.setEmail(getIssue().getUser().getEmail());
				userNotificationsDao.save(userNotifications);
				
				System.out.println(notification.getNazwa() + "\n" + notification.getTresc() + "\n" + userNotifications.getDataWyslania().toString() + "\n" + userNotifications.getEmail());
				mailSender mailsender = new mailSender();
				mailsender.sendMail("pracownikhelpdesku", 
						"damian.miacz@gmail.com", 
						notification.getNazwa(), 
						"Adresatem tej wiadomosci jest " + getIssue().getUser().getImie() + " " + getIssue().getUser().getNazwisko() + "\nPracownik HelpDesku odpowiedział na Twoje zgłoszenie!\n\nTresc odpowiedzi jest nastepujaca:\n" + notification.getTresc()
				);
				
			}
			// */// DM stop
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
