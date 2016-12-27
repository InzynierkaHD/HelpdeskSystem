package pl.helpdesk.components.tableNew;

import java.io.Serializable;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.spring.injection.annot.SpringBean;

import pl.helpdesk.api.IIssueDao;
import pl.helpdesk.api.ISortingDao;
import pl.helpdesk.api.ITableNew;
import pl.helpdesk.components.table.Table;
import pl.helpdesk.entity.Issue;

public class TableNew extends Panel implements ITableNew{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private RepeatingView tableHead;
	
	private DataView rows;
	
	private ListDataProvider listOfTableData;
	
	private RepeatingView rowElements;
	
	private TableNew thisTable;
	
	@SpringBean
	private IIssueDao issueDao;
	
	@SpringBean
	private ISortingDao issueSortingDao;
	
	public TableNew(String id) {
		super(id);
		thisTable = this;
		thisTable.setOutputMarkupId(true);
		tableHead = new RepeatingView("tableHead");
		tableHead.add(new TableColumn(tableHead.newChildId(),"Id","id",thisTable,issueSortingDao));
		tableHead.add(new TableColumn(tableHead.newChildId(),"Priorytet","prioritoryDataModel",thisTable,issueSortingDao));
		addOrReplace(tableHead);
		listOfTableData = new ListDataProvider(issueDao.getAll());
	}

	@Override
	protected void onBeforeRender() {
		
		rows = new DataView<Issue>("rows", listOfTableData) {

			  @Override
			  protected void populateItem(Item<Issue> item) {
				Issue issue = (Issue)item.getModelObject();
			    rowElements = new RepeatingView("dataRow");
			    rowElements.add(new Label(rowElements.newChildId(), issue.getId()));
			    rowElements.add(new Label(rowElements.newChildId(), issue.getPriority()));
			    item.add(rowElements);
			  }
			};
			addOrReplace(rows);
		super.onBeforeRender();
	}

	@Override
	public void sortDown(ISortingDao sortingDao,AjaxRequestTarget target,String propertyName) {
		listOfTableData = new ListDataProvider(sortingDao.getSortingAsc(propertyName));
		thisTable.setListOfTableData(listOfTableData);
		target.add(thisTable);
	}

	@Override
	public void sortUp(ISortingDao sortingDao,AjaxRequestTarget target,String propertyName) {
		listOfTableData = new ListDataProvider(sortingDao.getSortingDesc(propertyName));
		thisTable.setListOfTableData(listOfTableData);
		target.add(thisTable);
	}
	
	
	
	public ListDataProvider getListOfTableData() {
		return listOfTableData;
	}

	public void setListOfTableData(ListDataProvider listOfTableData) {
		this.listOfTableData = listOfTableData;
	}

	@Override
	public void renderHead(IHeaderResponse response) {
		PackageResourceReference cssFile = new PackageResourceReference(TableNew.class, "Table.css");
		CssHeaderItem cssItem = CssHeaderItem.forReference(cssFile);

		response.render(cssItem);
	}
}
