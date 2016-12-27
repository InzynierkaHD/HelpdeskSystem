package pl.helpdesk.components.tableNew;

import java.io.Serializable;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import pl.helpdesk.api.ISortingDao;

public class TableColumn extends Panel{

	private AjaxLink sortUp;
	private AjaxLink sortDown;
	private String titleName;
	private Label title;
	private String sortingProperty;
	private TableNew table;
	
	private ISortingDao sortingDao;
	
	public TableColumn(String id,String titleName,String sortingProperty,TableNew table,ISortingDao sortingDao) {
		super(id);
		this.sortingProperty = sortingProperty;
		this.table = table;
		this.sortingDao = sortingDao;
		title = new Label("title",new PropertyModel(this,"titleName"));
		sortUp = new AjaxLink("sortUp"){
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				getTable().sortUp(getSortingDao(),target,getSortingProperty());
				
			}
		};
		sortDown = new AjaxLink("sortDown"){
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				getTable().sortDown(getSortingDao(),target,getSortingProperty());
				
			}
		};
		add(title);
		add(sortUp);
		add(sortDown);
	}
	
	/*public void sortUp(AjaxRequestTarget target){
		
		getTable().setListOfTableData(new ListDataProvider(issueSortingDao.getSortingAsc(this.getSortingProperty())));
		target.add(table);
	}
	
	public void sortDown(AjaxRequestTarget target){
		getTable().setListOfTableData(new ListDataProvider(issueSortingDao.getSortingDesc(this.getSortingProperty())));
		target.add(table);
	}*/

	public String getTitleName() {
		return titleName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}

	public Label getTitle() {
		return title;
	}

	public void setTitle(Label title) {
		this.title = title;
	}

	public String getSortingProperty() {
		return sortingProperty;
	}

	public void setSortingProperty(String sortingProperty) {
		this.sortingProperty = sortingProperty;
	}

	public TableNew getTable() {
		return table;
	}

	public void setTable(TableNew table) {
		this.table = table;
	}

	public ISortingDao getSortingDao() {
		return sortingDao;
	}

	public void setSortingDao(ISortingDao sortingDao) {
		this.sortingDao = sortingDao;
	}
	
}
