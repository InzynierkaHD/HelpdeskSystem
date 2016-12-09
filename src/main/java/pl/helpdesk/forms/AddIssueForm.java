package pl.helpdesk.forms;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.feedback.ExactLevelFeedbackMessageFilter;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import pl.helpdesk.api.IAdminDao;
import pl.helpdesk.api.IAgentDao;
import pl.helpdesk.api.IClientDao;
import pl.helpdesk.api.ICompanyProductDao;
import pl.helpdesk.api.IIssueDao;
import pl.helpdesk.api.IIssueTypeDao;
import pl.helpdesk.api.INotificationDao;
import pl.helpdesk.api.IPriorityDao;
import pl.helpdesk.api.IProductDao;
import pl.helpdesk.api.IUserNotificationsDao;
import pl.helpdesk.components.SelectForm;
import pl.helpdesk.components.table.Table;
import pl.helpdesk.components.table.TableCol;
import pl.helpdesk.dao.UserDao;
import pl.helpdesk.entity.Admin;
import pl.helpdesk.entity.Agent;
import pl.helpdesk.entity.Client;
import pl.helpdesk.entity.Company;
import pl.helpdesk.entity.Issue;
import pl.helpdesk.entity.User;
import pl.helpdesk.pages.IssueListPage;
import pl.helpdesk.userSession.ApplicationSession;

/**
 * Panel z formularzem dodania nowych zgłoszeń
 * 
 * @author Krzysztof Krocz
 *
 */
public class AddIssueForm extends Panel implements Serializable{
	
	private static final long serialVersionUID = 1L;
	//@NotNull
	Logger log = Logger.getLogger(AddIssueForm.class.getName());

	private String titleName;
	//@NotNull
	private String contentsName;
	
	private TextField<String> title;
	private TextArea<String> contents;
	//@NotNull
	private String selectedIssueType; 
	//@NotNull
	private String selectedPriority; 
	private String selectedProduct;
	private Company userCompany;
	
	@SpringBean
	IIssueTypeDao issueTypeDao;
	@SpringBean
	IPriorityDao priorityDao;
	@SpringBean
	IClientDao clientDao;
	@SpringBean
	IAgentDao agentDao;
	@SpringBean 
	ICompanyProductDao companyProductDao;
	@SpringBean 
	IProductDao productDao;
	@SpringBean
	IIssueDao issueDao;
	@SpringBean
	private IAdminDao adminDao;
	
	@SpringBean
	private IUserNotificationsDao userNotificationsDao;
	
	@SpringBean
	private INotificationDao notificationDao;
	
	public AddIssueForm(String id) {
		super(id);
		this.setOutputMarkupId(true);
		final User loggedUser = ApplicationSession.getInstance().getUser();
		if(clientDao.getClientForUser(loggedUser) != null){
			Client adder = clientDao.getClientForUser(loggedUser);
			userCompany = adder.getCompanyDataModel();
			}
		if(agentDao.findAgentByUser(loggedUser) != null){
			Agent adder = agentDao.findAgentByUser(loggedUser);
			userCompany = adder.getCompanyDataModel();
		}
		title = new TextField<String>("title",new PropertyModel<String>(this, "titleName"));
		contents = new TextArea<String>("contents", new PropertyModel<String>(this, "contentsName"));
		Form<Void> addIssueForm = new Form<Void>("addIssueForm");
		AjaxSubmitLink submitLink = new AjaxSubmitLink("ajaxSubmit",addIssueForm) {
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				boolean validForm = true;
				if(selectedProduct == null) {
					validForm=false;
					target.appendJavaScript( "$(\"#productE\").slideDown(\"medium\")");
				}
				else target.appendJavaScript( "$(\"#productE\").hide()");
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
					Issue newIssue  = new Issue();
					newIssue.setUser(loggedUser);
					DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					Date date = new Date();
					newIssue.setDataDodania(date);
					newIssue.setPriority(priorityDao.getPriorityByName(selectedPriority));
					newIssue.setTemat(titleName);
					newIssue.setTresc(contentsName);
					newIssue.setType(issueTypeDao.getIssueTypeByName(selectedIssueType));
					newIssue.setCompanyProduct(companyProductDao.findCompanyProductByProductAndCompany(productDao.findProductByName(selectedProduct), userCompany));
					issueDao.save(newIssue);
					getPage().setResponsePage(IssueListPage.class);
					log.info("------------------------------------>>>Dodanie Zgłoszenia<<<----------------------------------------");
					List<Admin> allAdmins = adminDao.getAll();
					for (Admin allAdminss : allAdmins) {
						userNotificationsDao.addNotification(allAdminss.getUserDataModel(),
								notificationDao.getById(23), loggedUser.getLogin());
					}
					userNotificationsDao.addNotification(loggedUser,
							notificationDao.getById(23), loggedUser.getLogin());
					}
					
				
				
				
				super.onSubmit(target, form);
			}
		};
		
		SelectForm selectIssueType = new SelectForm("selectIssueType",new PropertyModel<String>(this,"selectedIssueType"),issueTypeDao.getAllToString());
		SelectForm selectPriority = new SelectForm("selectPriority",new PropertyModel<String>(this,"selectedPriority"),priorityDao.getAllToString());
		SelectForm selectProduct = new SelectForm("selectProduct",new PropertyModel<String>(this,"selectedProduct"),companyProductDao.getAllProductsForCompany(userCompany));
		addIssueForm.add(title);
		addIssueForm.add(contents);
		addIssueForm.add(selectIssueType);
		addIssueForm.add(selectPriority);
		addIssueForm.add(selectProduct);
		addIssueForm.add(submitLink);
		add(addIssueForm);
		FeedbackPanel feedBackPanel = new FeedbackPanel("feedbackErrors", new ExactLevelFeedbackMessageFilter(FeedbackMessage.ERROR));
		feedBackPanel.setOutputMarkupId(true);
		add(feedBackPanel);

	}
	
	
	@Override
	protected void onBeforeRender() {
		titleName=null;
		contentsName=null;
		selectedIssueType=null;
		selectedPriority=null;
		selectedProduct=null;
		super.onBeforeRender();
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
