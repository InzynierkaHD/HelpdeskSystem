package pl.helpdesk.components.table;

public class TableColumn {

	private String name;
	private String daoColumnName;
	private boolean sortAsc;
	
	public TableColumn(String name, String daoColumnName){
		this.name = name;
		this.daoColumnName = daoColumnName;
		this.sortAsc = false;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDaoColumnName() {
		return daoColumnName;
	}

	public void setDaoColumnName(String daoColumnName) {
		this.daoColumnName = daoColumnName;
	}

	public boolean isSortAsc() {
		return sortAsc;
	}

	public void setSortAsc(boolean sortAsc) {
		this.sortAsc = sortAsc;
	}
	
}
