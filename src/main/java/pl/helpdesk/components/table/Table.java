package pl.helpdesk.components.table;

import java.util.ArrayList;
import java.util.List;

import javax.management.Attribute;

import org.apache.log4j.Logger;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.extensions.ajax.markup.html.AjaxEditableLabel;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.spring.injection.annot.SpringBean;

import pl.helpdesk.api.IEmployeeDao;
import pl.helpdesk.api.IGenericDao;
import pl.helpdesk.api.IIssueDao;
import pl.helpdesk.api.IStatusHistoryDao;
import pl.helpdesk.entity.Issue;
import pl.helpdesk.entity.Status;
import pl.helpdesk.userSession.ApplicationSession;

public class Table<T> extends Panel {

	private static final long serialVersionUID = 1L;

	/**
	 * Lista encji które mają zostać wyświetlone w tabelce.
	 */
	private List listOfRows;
	private DataView<T> dataView;
	private boolean clickableRow;
	Logger log = Logger.getLogger(Table.class.getName());
	/**
	 * Componenty które umieszczamy w kolejnych kolumnach np. add(new
	 * Lable("kol",Model.of("To jest wyswietloona kolumna").
	 */
	private RepeatingView rowElements;
	/**
	 * Componenty jako Nazwy kolumn w tabelce.
	 */
	private RepeatingView tableHead;
	
	private RepeatingView tableHeadC;
	/**
	 * Encja którą będzie reprezentować tabelka np. zgłoszenia.
	 */
	private T entity;
	private Status entityStatus;
	/**
	 * Dao do encji powyższej.
	 */
	private IGenericDao dao;

	private ListDataProvider listDataProvider;

	private List<TableCol> listOfTableColumn;
	private int rowsPerPage;
	private List<TableColumn> listOfTableColumnName;
	private TableSearch tableSearch;
	private Table thisTable;
	
	@SpringBean
	private IIssueDao issueDao;
	
	@SpringBean
	private IEmployeeDao employeeDao;
	
	@SpringBean
	private IStatusHistoryDao statusHistoryDao;
	/**
	 * 
	 * @param id
	 *            wicket id tabelki
	 * @param listOfTableColumn
	 *            lista kolumn w tabelce które mają zostać wyświetlone
	 * @param columnHeaders
	 *            nagłówki kolumn
	 * @param listOfRows
	 *            lista encji które mają być wyświetlone w tabelce np. lista
	 *            zgłoszeń
	 * @param dao
	 *            dao dla encji którą wyświetlamy w tabelce
	 * @param clickableRow
	 *            czy wiersz ma być klikalny (jeśli tak należy przeciążyć metodą
	 *            rowClickEvent() która się wykona po kliknięciu na wiersz
	 *            tabelki) jeśli ta opcja jest włączona wszystkie komponenty w
	 *            tabelce nie są edytowalne!!!
	 */
	public Table(String id, List listOfRows,IGenericDao dao) {
		super(id);
		//this.rowsPerPage = 10;
		//this.listOfTableColumn = listOfTableColumn;
		//this.listOfTableColumnName = listOfTableColumnName;
		//this.clickableRow = clickableRow;
		this.dao = dao;
		this.listOfRows = listOfRows;
		loadConfiguration();
		entityStatus = new Status();
		listDataProvider = new ListDataProvider(listOfRows);
		tableHead = new RepeatingView("tableHead");
		thisTable = this;
		thisTable.setOutputMarkupId(true);
		tableSearch = new TableSearch("search",this);
		add(tableSearch);
		for (final TableColumn headerName : getListOfTableColumnName()) {
			final Label headerLabel = new Label(tableHead.newChildId(), Model.of(headerName.getName()+" <i class=\"glyphicon glyphicon-menu-up\"></i>"));
			headerLabel.setEscapeModelStrings(false);
			headerLabel.add(new AjaxEventBehavior("click"){
				@Override
				protected void onEvent(AjaxRequestTarget target) {
					
					if(!headerName.isSortAsc()){ 
						thisTable.setListOfRows(issueDao.getSortingIssuesForUser(ApplicationSession.getInstance().getUser(), headerName.getDaoColumnName()));
						if(employeeDao.isEmployee(ApplicationSession.getInstance().getUser())) thisTable.setListOfRows(issueDao.getSortingIssuesForall(headerName.getDaoColumnName()));
						headerName.setSortAsc(true);
						thisTable.setListDataProvider(new ListDataProvider(thisTable.getListOfRows()));
						headerLabel.setDefaultModel(Model.of(headerName.getName()+" <i class=\"glyphicon glyphicon-menu-down\"></i>"));
						target.add(thisTable);
						return;
						}
					if(headerName.isSortAsc()){
						thisTable.setListOfRows(issueDao.getSortingIssuesForUserDesc(ApplicationSession.getInstance().getUser(), headerName.getDaoColumnName()));
						if(employeeDao.isEmployee(ApplicationSession.getInstance().getUser())) thisTable.setListOfRows(issueDao.getSortingIssuesForAllDesc(headerName.getDaoColumnName()));
						headerName.setSortAsc(false);
					thisTable.setListDataProvider(new ListDataProvider(thisTable.getListOfRows()));
					headerLabel.setDefaultModel(Model.of(headerName.getName()+" <i class=\"glyphicon glyphicon-menu-up\"></i>"));
					target.add(thisTable);
					return;
					}
					
				}
				
			});
			tableHead.add(headerLabel);
		}
		tableHead.add(new Label(tableHead.newChildId(),Model.of("Status")));
		add(tableHead);
		dataView = new DataView<T>("rows", listDataProvider) {
			@Override
			protected void populateItem(Item<T> item) {
				rowElements = new RepeatingView("dataRow");
				entity = item.getModelObject();
				Issue issue = (Issue)entity;
				for (TableColumn column : listOfTableColumnName) {
					//statusHistoryDao.getCurrentStatus(issue);
						addColumnNoEditable(column.getDaoColumnName(),statusHistoryDao.getCurrentStatus(issue),entity);
						
					column.getDaoColumnName();
				}
				if(statusHistoryDao.getCurrentStatus(issue) != null) addColumnNoEditable(statusHistoryDao.getCurrentStatus(issue).getNazwa(),statusHistoryDao.getCurrentStatus(issue));
				else  addColumnNoEditable("nowe",statusHistoryDao.getCurrentStatus(issue));
				item.add(rowElements);
				if (clickableRow) {
					item.add(new AjaxEventBehavior("onclick") {

						private static final long serialVersionUID = 6720512493017210281L;

						@Override
						protected void onEvent(AjaxRequestTarget target) {

							rowClickEvent(target, getComponent());
						}

					});
				}
			}
		};
		dataView.setItemsPerPage(rowsPerPage);
		add(dataView);
		dataView.setOutputMarkupId(true);
		add(new AjaxPagingNavigator("pagingNavigator", dataView){
			@Override
			protected void onAjaxEvent(AjaxRequestTarget target) {
			//	target.add(dataView);
			//	target.add(thisTable);
				super.onAjaxEvent(target);
			}
		});
	}


	private void loadConfiguration(){
		this.rowsPerPage = Integer.parseInt(getString("liczbaWierszyNaStrone"));
		this.clickableRow = Boolean.parseBoolean(getString("klikalneWiersze"));
		String columnHeaders[] = getString("naglowkiKolumn").split(",");
		String columnPropertyEntity[] = getString("poleEncjiDlaKolumny").split(",");
		if(columnHeaders.length == columnPropertyEntity.length){
		List<TableColumn> listColumnName = new ArrayList<TableColumn>();
		
		for(int i=0; i< columnHeaders.length; i++){
				listColumnName.add(new TableColumn(columnHeaders[i],columnPropertyEntity[i]));	
		}
		setListOfTableColumnName(listColumnName);
		}
		else{
			log.error("########################### BŁĘDNA KONFIGURACJA 'naglowki kolumn' lub 'pole encji dla kolumny w pliku Table.properties'");
		}
	}
	
	@Override
	protected void onBeforeRender() {
		
		dataView = new DataView<T>("rows", listDataProvider) {
			@Override
			protected void populateItem(Item<T> item) {
				rowElements = new RepeatingView("dataRow");
				entity = item.getModelObject();
				Issue issue = (Issue)entity;
				for (TableColumn column : listOfTableColumnName) {
					//statusHistoryDao.getCurrentStatus(issue);
						addColumnNoEditable(column.getDaoColumnName(),statusHistoryDao.getCurrentStatus(issue),entity);
						
					column.getDaoColumnName();
				}
				if(statusHistoryDao.getCurrentStatus(issue) != null) addColumnNoEditable(statusHistoryDao.getCurrentStatus(issue).getNazwa(),statusHistoryDao.getCurrentStatus(issue));
				else  addColumnNoEditable("nowe",statusHistoryDao.getCurrentStatus(issue));
				item.add(rowElements);
				if (clickableRow) {
					item.add(new AjaxEventBehavior("onclick") {

						private static final long serialVersionUID = 6720512493017210281L;

						@Override
						protected void onEvent(AjaxRequestTarget target) {

							rowClickEvent(target, getComponent());
						}

					});
				}
			}
		};
		
		//dataView.setItemsPerPage(rowsPerPage);
		
		//dataView.setOutputMarkupId(true);
		setDataView(dataView);
		addOrReplace(dataView);
		//addOrReplace(dataView);
		//addOrReplace(dataView);
		/*addOrReplace(new AjaxPagingNavigator("pagingNavigator", dataView){
			@Override
			protected void onAjaxEvent(AjaxRequestTarget target) {
				//target.add(thisTable);
				super.onAjaxEvent(target);
			}
		});*/
		super.onBeforeRender();
	}
	
	


	/**
	 * Metoda dodająca edytowalną labelkę
	 * 
	 * @param entityPropertyName
	 *            Nazwa pola encji, którą chcemy wyświetlić w tabelce np "id",
	 *            "name"
	 */
	void addColumnEditable(String entityPropertyName) {
		AjaxEditableLabel editableLabel = new AjaxEditableLabel(rowElements.newChildId(),
				new PropertyModel(entity, entityPropertyName)) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onModelChanged() {
				dao.update(entity);
				// dao.save()
				super.onModelChanged();
			}
		};
		this.rowElements.add(editableLabel);
	}

	/**
	 * Metoda dodająca nieedytowalną labelke
	 * 
	 * @param entityPropertyName
	 *            Nazwa pola encji, którą chcemy wyświetlić w tabelce np "id",
	 *            "name"
	 */
	void addColumnNoEditable(String entityPropertyName,Status status,Object entity) {
		Label label = new Label(rowElements.newChildId(), new PropertyModel(entity, entityPropertyName));
		if(status == null){
			label.add(new AttributeAppender("style", "background:#ffff99"));
		}
		else if(status.getNazwa().equals("Przyjęte")) label.add(new AttributeAppender("style", "background:lightblue"));
		else if(status.getNazwa().equals("Odrzucone")) label.add(new AttributeAppender("style", "background:tomato"));
		else if(status.getNazwa().equals("Zrealizowane")) label.add(new AttributeAppender("style", "background:green; color:white"));
		this.rowElements.add(label);

	}
	
	void addColumnNoEditable(String value,Status status) {
		Label label = new Label(rowElements.newChildId(), Model.of(value));
		if(status == null){
			label.add(new AttributeAppender("style", "background:#ffff99"));
		}
		else if(status.getNazwa().equals("Przyjęte")) label.add(new AttributeAppender("style", "background:lightblue"));
		else if(status.getNazwa().equals("Odrzucone")) label.add(new AttributeAppender("style", "background:tomato"));
		else if(status.getNazwa().equals("Zrealizowane")) label.add(new AttributeAppender("style", "background:green; color:white"));
		this.rowElements.add(label);

	}

	@Override
	public void renderHead(IHeaderResponse response) {
		PackageResourceReference cssFile = new PackageResourceReference(Table.class, "Table.css");
		CssHeaderItem cssItem = CssHeaderItem.forReference(cssFile);

		response.render(cssItem);
	}

	/**
	 * Metoda wykonywana po kliknięciu na wierszs
	 */
	public void rowClickEvent(AjaxRequestTarget target, Component component) {
		log.info("Kliknięto na wiersz");
	}

	public List getListOfRows() {
		return listOfRows;
	}

	public void setListOfRows(List listOfRows) {
		this.listOfRows = listOfRows;
	}

	public DataView<T> getDataView() {
		return dataView;
	}

	public void setDataView(DataView<T> dataView) {
		this.dataView = dataView;
	}

	public RepeatingView getRepeatingView() {
		return rowElements;
	}

	public void setRepeatingView(RepeatingView repeatingView) {
		this.rowElements = repeatingView;
	}

	public RepeatingView getTableHead() {
		return tableHead;
	}

	public void setTableHead(RepeatingView tableHead) {
		this.tableHead = tableHead;
	}

	public T getEntity() {
		return entity;
	}

	public void setEntity(T entity) {
		this.entity = entity;
	}




	public RepeatingView getRowElements() {
		return rowElements;
	}




	public boolean isClickableRow() {
		return clickableRow;
	}


	public void setClickableRow(boolean clickableRow) {
		this.clickableRow = clickableRow;
	}


	public List<TableColumn> getListOfTableColumnName() {
		return listOfTableColumnName;
	}


	public void setListOfTableColumnName(List<TableColumn> listOfTableColumnName) {
		this.listOfTableColumnName = listOfTableColumnName;
	}


	public IIssueDao getIssueDao() {
		return issueDao;
	}


	public void setIssueDao(IIssueDao issueDao) {
		this.issueDao = issueDao;
	}


	public void setRowElements(RepeatingView rowElements) {
		this.rowElements = rowElements;
	}




	public IGenericDao getDao() {
		return dao;
	}




	public void setDao(IGenericDao dao) {
		this.dao = dao;
	}




	public ListDataProvider getListDataProvider() {
		return listDataProvider;
	}




	public void setListDataProvider(ListDataProvider listDataProvider) {
		this.listDataProvider = listDataProvider;
	}




	public List<TableCol> getListOfTableColumn() {
		return listOfTableColumn;
	}




	public void setListOfTableColumn(List<TableCol> listOfTableColumn) {
		this.listOfTableColumn = listOfTableColumn;
	}




	public int getRowsPerPage() {
		return rowsPerPage;
	}




	public void setRowsPerPage(int rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}

}
