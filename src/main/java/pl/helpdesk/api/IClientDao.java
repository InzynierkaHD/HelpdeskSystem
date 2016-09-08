package pl.helpdesk.api;

import java.util.List;

import pl.helpdesk.entity.Agent;
import pl.helpdesk.entity.Client;
import pl.helpdesk.entity.Company;
import pl.helpdesk.entity.User;

public interface IClientDao extends IGenericDao<Client,Integer>{

	public Boolean isClient(User user);
	public int numberOfClients(Company company);
	public List <Client> clientsFromAgent(Agent agent);
}
