package pl.helpdesk.api;

import java.io.Serializable;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;

import pl.helpdesk.entity.StatusHistory;

public interface ITableNew {
	void sortDown(ISortingDao sortingDao,AjaxRequestTarget target,String propertyName);
	void sortUp(ISortingDao sortingDao,AjaxRequestTarget target,String propertyName);
	void search(String propertyName,String keyWord,AjaxRequestTarget target);
	void onRowClick(AjaxRequestTarget target,StatusHistory comp);
}
