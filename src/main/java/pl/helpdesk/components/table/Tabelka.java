package pl.helpdesk.components.table;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxCheckBox;
import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.extensions.ajax.markup.html.AjaxEditableLabel;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.resource.PackageResourceReference;

import pl.helpdesk.api.IGenericDao;
import pl.helpdesk.forms.AddIssueForm;

public class Tabelka<T> extends Panel {

	private static final long serialVersionUID = 1L;

	/**
	 * Lista encji które mają zostać wyświetlone w tabelce.
	 */
	private List listOfRows;
	private DataView<T> dataView;
	private boolean clickableRow;
	Logger log = Logger.getLogger(Tabelka.class.getName());
	/**
	 * Componenty które umieszczamy w kolejnych kolumnach np. add(new
	 * Lable("kol",Model.of("To jest wyswietloona kolumna").
	 */
	private RepeatingView rowElements;
	/**
	 * Componenty jako Nazwy kolumn w tabelce.
	 */
	private RepeatingView tableHead;
	/**
	 * Encja którą będzie reprezentować tabelka np. zgłoszenia.
	 */
	private T entity;
	/**
	 * Dao do encji powyższej.
	 */
	private IGenericDao dao;

	private ListDataProvider listDataProvider;

	private List<TableCol> listOfTableColumn;
	private int rowsPerPage;
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
	public Tabelka(String id, final List<TableCol> listOfTableColumn, final String columnHeaders[], List listOfRows,
			IGenericDao dao, final boolean clickableRow) {
		super(id);
		this.rowsPerPage = 10;
		this.dao = dao;
		this.listOfRows = listOfRows;
		this.clickableRow = clickableRow;
		this.listOfTableColumn = listOfTableColumn;
		listDataProvider = new ListDataProvider(listOfRows);
		tableHead = new RepeatingView("tableHead");
		for (String headerName : columnHeaders) {
			tableHead.add(new Label(tableHead.newChildId(), Model.of(headerName)));
		}
		add(tableHead);

		dataView = new DataView<T>("rows", listDataProvider) {
			@Override
			protected void populateItem(Item<T> item) {
				rowElements = new RepeatingView("dataRow");
				entity = item.getModelObject();

				for (TableCol column : listOfTableColumn) {
					if (column.isEditable() && !clickableRow) {
						addColumnEditable(column.getPropertyName());
					} else {
						addColumnNoEditable(column.getPropertyName());
					}
					column.getPropertyName();
				}
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
		add(new AjaxPagingNavigator("pagingNavigator", dataView));
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
	void addColumnNoEditable(String entityPropertyName) {
		Label label = new Label(rowElements.newChildId(), new PropertyModel(entity, entityPropertyName));
		this.rowElements.add(label);

	}

	void addColumnEditableCheckBox(String entityPropertyName) {
		this.rowElements.add(new AjaxCheckBox("searchByFio", new PropertyModel(entity, "entityPropertyName")) {
			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				System.out.println("wartosc checkboxa: " + getModelValue());
				// target.add(nameField);
			}
		});
	}

	@Override
	public void renderHead(IHeaderResponse response) {
		PackageResourceReference cssFile = new PackageResourceReference(Tabelka.class, "Tabelka.css");
		CssHeaderItem cssItem = CssHeaderItem.forReference(cssFile);

		response.render(cssItem);
	}

	@Override
	protected void onRender() {
		System.out.println("render");
		super.onRender();
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
