package pl.helpdesk.api;

import java.util.List;

import pl.helpdesk.entity.Agent;
import pl.helpdesk.entity.Client;
import pl.helpdesk.entity.Company;
import pl.helpdesk.entity.User;

/**
 * Interfejs dla użytkownika rodzaju Klient dao
 * 
 * @author Adam Ulidowski
 *
 */
public interface IClientDao extends IGenericDao<Client,Integer>{

	/**
	 * Metoda sprawdza czy user podany w argumencie jest klientem
	 * 
	 * @param user
	 *            Sprawdzany user
	 * 
	 * @return True, jeżeli użytkownik jest klientem, fasle w przeciwnym
	 *         wypadku
	 */
	public Boolean isClient(User user);
	
	
	public Client getClientForUser(User user);

	
	/**
	 * Metoda zlicza klientów z danej firmy
	 * 
	 * @param company
	 *            Firma z której zliczamy klientów 
	 * 
	 * @return lista klientów danego przedstawiciela
	 */
	public List <Client> clientsFromAgent(Agent agent);
}
