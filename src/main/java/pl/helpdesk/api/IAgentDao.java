package pl.helpdesk.api;

import pl.helpdesk.entity.Agent;
import pl.helpdesk.entity.Company;
import pl.helpdesk.entity.User;

public interface IAgentDao extends IGenericDao<Agent,Integer>{

	public Boolean isAgent(User user);
	public Agent findAgentByUser(User user);
}
