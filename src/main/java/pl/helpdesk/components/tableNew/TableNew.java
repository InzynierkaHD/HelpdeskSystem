package pl.helpdesk.components.tableNew;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.spring.injection.annot.SpringBean;

import pl.helpdesk.api.IIssueDao;
import pl.helpdesk.api.ISearchDao;
import pl.helpdesk.api.ISortingDao;
import pl.helpdesk.api.ITableNew;
import pl.helpdesk.components.table.TableSearch;
import pl.helpdesk.entity.Issue;
import pl.helpdesk.entity.Status;
import pl.helpdesk.entity.StatusHistory;

public class TableNew extends Panel implements ITableNew{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private RepeatingView tableHead;
	
	private DataView rows;
	
	
	private RepeatingView rowElements;
	
	private TableNew thisTable;
	
	private AjaxPagingNavigator pagingNav;
	
	private TableSearch tableSearch;
	
	@SpringBean
	private IIssueDao issueDao;
	
	//@SpringBean
	//private ISortingDao issueSortingDao;
	
	@SpringBean
	private ISortingDao statusSortingHistoryDao;
	
	@SpringBean 
	private ISearchDao statusHistoryDao;
	
	private ListDataProvider listOfTableData;
	
	private WebMarkupContainer tableContain;
	
	public TableNew(String id) {
		super(id);
		tableSearch = new TableSearch("search",this);
		add(tableSearch);
		List<Integer> lista = new ArrayList<Integer>();
		lista.add(1);
		thisTable = this;
		thisTable.setOutputMarkupId(true);
		tableHead = new RepeatingView("tableHead");
		tableContain = new WebMarkupContainer("tableContain");
		tableContain.setOutputMarkupId(true);
		tableHead.add(new TableColumn(tableHead.newChildId(),"Id","id",thisTable,statusSortingHistoryDao));
		tableHead.add(new TableColumn(tableHead.newChildId(),"Priorytet","prioritoryDataModel",thisTable,statusSortingHistoryDao));
		tableHead.add(new TableColumn(tableHead.newChildId(),"Status","nazwa",thisTable,statusSortingHistoryDao));
		tableHead.add(new TableColumn(tableHead.newChildId(),"Data dodania","dataDodania",thisTable,statusSortingHistoryDao));
		tableHead.add(new TableColumn(tableHead.newChildId(),"Pracownik obsługujący","employeeDataModel",thisTable,statusSortingHistoryDao));
		addOrReplace(tableHead);
		listOfTableData = new ListDataProvider(statusSortingHistoryDao.getSortingDesc("id"));
	}

	@Override
	protected void onBeforeRender() {
		rows = new DataView<StatusHistory>("rows", listOfTableData) {

			  @Override
			  protected void populateItem(final Item<StatusHistory> item) {
				StatusHistory statusHistory = (StatusHistory)item.getModelObject();
				Issue issue = statusHistory.getProblemDataModel();
				Status status = statusHistory.getStatusDataModel();
			    rowElements = new RepeatingView("dataRow");
			    Label label;
			    rowElements.add(label = new Label(rowElements.newChildId(), issue.getId()));
			    label.add(new AttributeAppender("style","background:"+getColorForStatus(status.getNazwa())));
			    rowElements.add(label = new Label(rowElements.newChildId(), issue.getPriority()));
			    label.add(new AttributeAppender("style","background:"+getColorForStatus(status.getNazwa())));
			    rowElements.add(label = new Label(rowElements.newChildId(), status.getNazwa()));
			    label.add(new AttributeAppender("style","background:"+getColorForStatus(status.getNazwa())));
			    rowElements.add(label = new Label(rowElements.newChildId(), issue.getDataDodania()));
			    label.add(new AttributeAppender("style","background:"+getColorForStatus(status.getNazwa())));
			    rowElements.add(label = new Label(rowElements.newChildId(), issue.getEmployee()));
			    label.add(new AttributeAppender("style","background:"+getColorForStatus(status.getNazwa())));
			    item.add(rowElements);
			    item.add(new AjaxEventBehavior("onclick") {

					private static final long serialVersionUID = 6720512493017210281L;

					@Override
					protected void onEvent(AjaxRequestTarget target) {

						onRowClick(target, item.getModelObject());
					}

				});
			  }
			};
			rows.setItemsPerPage(10);
			tableContain.addOrReplace(rows);
			addOrReplace(tableContain);
		pagingNav = new AjaxPagingNavigator("pagingNavigator", rows);
		addOrReplace(pagingNav);
		super.onBeforeRender();
	}
	
	private String getColorForStatus(String status){
		String statusy[] = getString("Statusy").split(",");
		String kolory[] = getString("Kolory").split(",");
		for(int i = 0; i<statusy.length;i++){
			if(statusy[i].equals(status)) return kolory[i];
		}
		return "";
	}

	@Override
	public void sortDown(ISortingDao sortingDao,AjaxRequestTarget target,String propertyName) {
		listOfTableData = new ListDataProvider(sortingDao.getSortingAsc(propertyName));
		thisTable.setListOfTableData(listOfTableData);
		target.add(tableContain);
		target.add(thisTable);
		//target.add(pagingNav);
	}

	@Override
	public void sortUp(ISortingDao sortingDao,AjaxRequestTarget target,String propertyName) {
		listOfTableData = new ListDataProvider(sortingDao.getSortingDesc(propertyName));
		thisTable.setListOfTableData(listOfTableData);
		target.add(tableContain);
		target.add(thisTable);
		//target.add(pagingNav);
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

	@Override
	public void search(String propertyName, String keyWord,AjaxRequestTarget target) {
		listOfTableData = new ListDataProvider(statusHistoryDao.search(propertyName, keyWord));
		thisTable.setListOfTableData(listOfTableData);
		target.add(tableContain);
		target.add(thisTable);
	}

	@Override
	public void onRowClick(AjaxRequestTarget target,StatusHistory comp) {
		System.out.println("kliknieto: "+comp.getProblemDataModel());
		
	}
}
