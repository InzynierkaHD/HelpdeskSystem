package pl.helpdesk.components.table;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.ajax.form.OnChangeAjaxBehavior;
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

import pl.helpdesk.api.IIssueDao;
import pl.helpdesk.userSession.ApplicationSession;

public class TableSearch extends Panel{

	private static final long serialVersionUID = 1L;
	
	private Form<Void> searchForm;
	private TextField<String> keyWordField;
	private RadioGroup radioGroup;
	private String selectedOption="";
	private String keyWord;
	private Button submit;
	
	@SpringBean
	IIssueDao issueDao;

	public TableSearch(String id,Table table) {
		super(id);
		searchForm = new Form<Void>("searchForm");
		keyWordField = new TextField<String>("keyWordField", new PropertyModel<String>(this,"keyWord"));
		keyWordField.add(new OnChangeAjaxBehavior() {
			
			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				// TODO Auto-generated method stub
				
			}
		});
		radioGroup = new RadioGroup("radioGroup", new PropertyModel<String>(this,"selectedOption"));
		radioGroup.add(new Radio("1",Model.of("id zgłoszenia")));
		radioGroup.add(new Radio("2",Model.of("komentarze")));
		radioGroup.add(new OnChangeAjaxBehavior() {
			
			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				// TODO Auto-generated method stub
				
			}
		});
		submit = new Button("submit");
		submit.add(new AjaxFormSubmitBehavior(searchForm,"click") {
			@Override
			protected void onEvent(AjaxRequestTarget target) {
				/*table.setListOfRows(issueDao.getSortingIssuesForUser(ApplicationSession.getInstance().getUser(), headerName.getDaoColumnName()));
				thisTable.setListDataProvider(new ListDataProvider(thisTable.getListOfRows()));
				target.add(thisTable);*/
				System.out.println("opcja: "+keyWord + " radio: " + selectedOption);
				super.onEvent(target);
			}
		});
		searchForm.add(keyWordField);
		searchForm.add(radioGroup);
		searchForm.add(submit);
		add(searchForm);
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
