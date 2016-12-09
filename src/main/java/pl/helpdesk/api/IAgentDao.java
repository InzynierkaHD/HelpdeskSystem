package pl.helpdesk.api;

import java.util.List;

import pl.helpdesk.entity.Agent;
import pl.helpdesk.entity.Client;
import pl.helpdesk.entity.Company;
import pl.helpdesk.entity.User;

/**
 * Interfejs dla użytkownika rodzaju Przedstawiciel dao
 * 
 * @author Adam Ulidowski
 *
 */
public interface IAgentDao extends IGenericDao<Agent,Integer>{

	/**
	 * Metoda sprawdza czy user podany w argumencie jest przedstawicielem
	 * 
	 * @param user
	 *            Sprawdzany user
	 * 
	 * @return True, jeżeli użytkownik jest przedstawicielem, fasle w przeciwnym
	 *         wypadku
	 */
	public Boolean isAgent(User user);
	
	/**
	 * Metoda wyszukuje przedstawiciela przez użytkownika
	 * 
	 * @param user
	 *            Szukany przedstawiciel
	 * 
	 * @return Szukany przedstawiciel
	 */
	public Agent findAgentByUser(User user);

	
	public List <String> getCompanyWithoutAgent();
	
	public String numOfAg(String surname);
	public List<Agent> getSortedAgents(String sortBy, String surname);
}
