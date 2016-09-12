package pl.helpdesk.api;

import pl.helpdesk.entity.Admin;
import pl.helpdesk.entity.User;

/**
 * Interfejs dla użytkownika rodzaju Administrator dao
 * 
 * @author Adam Ulidowski
 *
 */
public interface IAdminDao extends IGenericDao<Admin, Integer> {

	/**
	 * Metoda sprawdza czy user podany w argumencie jest administratorem
	 * 
	 * @param user
	 *            Sprawdzany user
	 *            
	 * @return True, jeżeli użytkownik jest administraotrem, fasle w przeciwnym
	 *         wypadku
	 */
	public Boolean isAdmin(User user);
}
