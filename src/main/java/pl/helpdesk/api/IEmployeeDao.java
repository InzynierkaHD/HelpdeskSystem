package pl.helpdesk.api;

import pl.helpdesk.entity.Employee;
import pl.helpdesk.entity.User;

/**
 * Interfejs dla użytkownika rodzaju Pracownik dao
 * 
 * @author Adam Ulidowski
 *
 */
public interface IEmployeeDao extends IGenericDao<Employee,Integer>{

	/**
	 * Metoda sprawdza czy user podany w argumencie jest pracownikiem
	 * 
	 * @param user
	 *            Sprawdzany user
	 * 
	 * @return True, jeżeli użytkownik jest pracownikiem, fasle w przeciwnym
	 *         wypadku
	 */
	public Boolean isEmployee(User user);
	
	Employee getEmployeeByUser(User user);
}
