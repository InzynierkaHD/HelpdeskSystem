package pl.helpdesk.components.table;

import java.io.Serializable;

public class TableCol implements Serializable{

	private boolean editable;
	private String propertyName;
	
	public TableCol(boolean editable, String propertyName){
		this.editable = editable;
		this.propertyName = propertyName;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	
}
