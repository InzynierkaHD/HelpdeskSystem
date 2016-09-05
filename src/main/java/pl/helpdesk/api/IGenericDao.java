package pl.helpdesk.api;

import java.util.List;

/**
 * Interfejs dla generycznego dao
 * 
 * @author Krzysztof Krocz
 *
 * @param <T>
 *            Klasa encji
 */
public interface IGenericDao<T, ID> {
	/**
	 * Metoda zapisująca obiekt w bazie danych
	 * 
	 * @param object
	 *            obiekt do zapisania
	 */
	void save(T object);

	/**
	 * Update obiektu w bazie danych
	 * 
	 * @param object
	 *            obiket do update'u
	 */
	void update(T object);

	/**
	 * Usuniecie obiektu z bazy danych
	 * 
	 * @param object
	 *            obiekt do usunięcia
	 */
	void delete(T object);

	/**
	 * Pobranie wszystkich elementów danej encji
	 * 
	 * @return lista obiektów
	 */
	List<T> getAll();

	/**
	 * Pobierz jeden elemnet po id
	 * 
	 * @param id
	 *            id elementu
	 * @return
	 */
	T getById(ID id);
}
