package pl.helpdesk.components.table;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.ajax.form.OnChangeAjaxBehavior;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import pl.helpdesk.api.ICommentDao;
import pl.helpdesk.api.IEmployeeDao;
import pl.helpdesk.api.IIssueDao;
import pl.helpdesk.entity.Comment;
import pl.helpdesk.entity.Issue;
import pl.helpdesk.userSession.ApplicationSession;

public class TableSearch extends Panel {

	private static final long serialVersionUID = 1L;

	private Form<Void> searchForm;
	private TextField<String> keyWordField;
	private RadioGroup radioGroup;
	private String selectedOption = "id";
	private String keyWord;
	private Button submit;
	private Radio radio1;
	private Radio radio2;
	private Label errorInfo;

	@SpringBean
	IIssueDao issueDao;

	@SpringBean
	IEmployeeDao employeeDao;

	@SpringBean
	ICommentDao commentDao;

	public TableSearch(String id,final Table table) {
		super(id);
		errorInfo = new Label("errorInfo");
		searchForm = new Form<Void>("searchForm");
		keyWordField = new TextField<String>("keyWordField", new PropertyModel<String>(this,"keyWord"));
		keyWordField.add(new OnChangeAjaxBehavior() {
			
			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				// TODO Auto-generated method stub
				
			}
		});
		radio1= new Radio("1",Model.of("id"));
		radio1.add(new AjaxEventBehavior("onclick") {
		    @Override
		    protected void onEvent(AjaxRequestTarget target) {
		        selectedOption="id";
		    }
		});
		radio2 = new Radio("2",Model.of("komentarze"));
		radio2.add(new AjaxEventBehavior("onclick") {
		    @Override
		    protected void onEvent(AjaxRequestTarget target) {
		        selectedOption="komentarze";
		    }
		});
		radioGroup = new RadioGroup("radioGroup", new PropertyModel<String>(this,"selectedOption"));
		radioGroup.add(radio1);
		radioGroup.add(radio2);
		submit = new Button("submit");
		submit.add(new AjaxFormSubmitBehavior(searchForm,"click") {
			@Override
			protected void onEvent(AjaxRequestTarget target) {
				if(selectedOption.equals("id")){
					errorInfo.setDefaultModel(Model.of(""));
					try{
					List<Issue> issueList = issueDao.getIssuesForUserbyId(ApplicationSession.getInstance().getUser(), Integer.parseInt(keyWord));
					if(employeeDao.isEmployee(ApplicationSession.getInstance().getUser())){
						issueList.clear();
						issueList.add(0, issueDao.getById(Integer.parseInt(keyWord)));
					}
					table.setListOfRows(issueList);
					table.setListDataProvider(new ListDataProvider(table.getListOfRows()));
					target.add(table);
					}
					catch(Exception e){
						errorInfo.setDefaultModel(Model.of("Pole wyszukiwania powinno zawierać cyfrę"));
					}
				}
				if(selectedOption.equals("komentarze")){
					errorInfo.setDefaultModel(Model.of(""));
					List<Issue> temp = issueDao.getAllIssuesForUser(ApplicationSession.getInstance().getUser());
					if(employeeDao.isEmployee(ApplicationSession.getInstance().getUser())){
						temp = issueDao.getAll();
					}
					List<Issue> issueList = new ArrayList<Issue>();
					try{
				for(Issue is : temp){
						List<Comment> commentForIssue = commentDao.getCommentByIssue(is);
						for(Comment comment : commentForIssue){
							if(comment.getTresc().contains(keyWord)){
								issueList.add(is);
								break;
							}
						}
					}
					}
					catch(Exception e){
						errorInfo.setDefaultModel(Model.of("Pole wyszukiwania nie może być puste"));
					}

				table.setListOfRows(issueList);
				table.setListDataProvider(new ListDataProvider(table.getListOfRows()));
				target.add(table);
				}
				
				/*else{
					errorInfo.setDefaultModel(Model.of("Wystąpił błąd wyszukiwania"));
				}*/
				super.onEvent(target);
			}
		});
		searchForm.add(keyWordField);
		searchForm.add(radioGroup);
		searchForm.add(submit);
		add(searchForm);
		add(errorInfo);
	}

	public String getSelectedOption() {
		return selectedOption;
	}

	public void setSelectedOption(String selectedOption) {
		this.selectedOption = selectedOption;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

}
