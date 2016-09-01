package pl.helpdesk.api;

import java.util.List;
/**
 * Interfejs dla generycznego dao
 * 
 * @author Krzysztof Krocz
 *
 * @param <T> Klasa encji
 */
public interface IGenericDao<T> {
	
	void save(T object);
	void update(T object);
	void delete(T object);
	List<T> getAll();
	
}
