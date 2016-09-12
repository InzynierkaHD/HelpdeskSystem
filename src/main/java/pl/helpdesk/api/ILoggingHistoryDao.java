package pl.helpdesk.api;

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
}
