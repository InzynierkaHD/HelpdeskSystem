package pl.helpdesk.api;

import java.io.Serializable;

import org.apache.wicket.ajax.AjaxRequestTarget;

public interface ITableNew {
	void sortDown(ISortingDao sortingDao,AjaxRequestTarget target,String propertyName);
	void sortUp(ISortingDao sortingDao,AjaxRequestTarget target,String propertyName);
}
