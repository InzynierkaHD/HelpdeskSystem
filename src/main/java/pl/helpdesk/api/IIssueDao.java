package pl.helpdesk.api;

import java.util.List;

import pl.helpdesk.entity.Issue;
import pl.helpdesk.entity.User;

public interface IIssueDao extends IGenericDao<Issue,Integer>{
	/**
	 * Metoda pobiera wszystkie zgłoszenia danego usera
	 * 
	 * @param user
	 * 		user dla którego szukamy zgłoszeń
	 * @return
	 * 		Lista zgłoszeń
	 */
	List<Issue> getAllIssuesForUser(User user);
}
