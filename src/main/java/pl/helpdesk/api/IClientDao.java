package pl.helpdesk.api;

import pl.helpdesk.entity.Client;
import pl.helpdesk.entity.User;

public interface IClientDao extends IGenericDao<Client,Integer>{

	public Boolean isClient(User user);
	Client getClientForUser(User user);
}
