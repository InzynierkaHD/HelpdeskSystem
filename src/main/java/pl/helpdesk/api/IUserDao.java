package pl.helpdesk.api;

import pl.helpdesk.entity.User;

/**
 * Interfejs dla dao użytkownika
 * 
 * @author Krzysiek
 *
 */
public interface IUserDao {
	/**
	 * Metoda zapisująca usera w bazie danych
	 * 
	 * @param user 
	 * Przekazywana encja usera do bazy
	 */
	public void saveUser(User user);
	/**
	 * Metoda pobierająca usera z bazy po loginie
	 * 
	 * @param login
	 * Login użytkownika
	 * @return
	 */
	public User getUser(String login);
	/**
	 * Metoda zwracająca usera po loginie i haśle
	 * 
	 * @param login
	 * Login użytkownika
	 * @param password
	 * Haslo użytkownika
	 * @return
	 */
	public User findUserByLoginAndPassword(String login, String password);
}
