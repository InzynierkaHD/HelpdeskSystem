package pl.helpdesk.api;

import java.util.List;

public interface IGenericDao<T> {
	
	void save(T object);
	void update(T object);
	void delete(T object);
	List<T> getAll();
	
}
