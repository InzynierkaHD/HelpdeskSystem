package pl.helpdesk.api;

import java.util.List;

import pl.helpdesk.entity.LoggingHistory;
import pl.helpdesk.entity.User;

/**
 * Interfejs dla historii logowań dao
 * 
 * @author Adam Ulidowski
 *
 */
public interface ILoggingHistoryDao  extends IGenericDao<LoggingHistory,Integer>{
	
	/**
	 * Ustawia datę wylogowania użytkownika
	 * 
	 * @param user
	 *           Użytkownik, który się wylogowuje
	 *            
	 */
	public void setUserLogOutDate(User user);
	
	public List<LoggingHistory> getDateSortedUserLoggingHistory(int id);
}
