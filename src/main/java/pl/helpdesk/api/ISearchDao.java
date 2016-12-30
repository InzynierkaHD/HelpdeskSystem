package pl.helpdesk.api;

import java.util.List;

public interface ISearchDao<T> {
	List<T> search(String propertyName,String keyword);
}
