package pl.helpdesk.api;

import pl.helpdesk.entity.Status;

/**
 * 
 * @author Krzysztof Krocz
 *
 */
public interface IStatusDao extends IGenericDao<Status,Integer>{
	
	Status getStatusByName(String name);
}
