package pl.helpdesk.api;

import java.util.List;

import pl.helpdesk.entity.User;

public interface ISortingDao<T> {
	
	List<T> getSortingAsc(User user,String propertyName);
	List<T> getSortingAsc(String propertyName);
	List<T> getSortingDesc(User user, String propertyName);
	List<T> getSortingDesc(String propertyName);
}
