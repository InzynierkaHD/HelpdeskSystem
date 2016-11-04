package pl.helpdesk.dao;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import pl.helpdesk.api.IClientDao;
import pl.helpdesk.entity.Agent;
import pl.helpdesk.entity.Client;
import pl.helpdesk.entity.Company;
import pl.helpdesk.entity.User;

@Transactional
public class ClientDao extends GenericDao<Client, Integer> implements IClientDao {

	private List<Client> activeClientList;


	public ClientDao() {
		super();
	}

	@Override
	public Boolean isClient(User user) {
		if (!sessionFactory.getCurrentSession().createCriteria(Client.class).add(Restrictions.eq("userDataModel", user))
				.list().isEmpty()) {
			return true;
		} else
			return false;
	}

	@Override
	public Client getClientForUser(User user) {
		return (Client) sessionFactory.getCurrentSession().createCriteria(Client.class)
				.add(Restrictions.eq("userDataModel", user)).uniqueResult();
	}


//	@Override
//	public List<Client> clientsFromAgent(Agent agent) {
//		
//		 List<Client> clientList = sessionFactory.getCurrentSession().createCriteria(Client.class)
//				.add(Restrictions.eq("agentDataModel", agent)).list();
//		 System.out.println(clientList.size());
//		 activeClientList = null;
//		 for(Client client : clientList){
//				if(client.getUserDataModel().getCzy_usuniety()!=true){
//					activeClientList.add(client);
//					 System.out.println("Po usunieciu: " +clientList.size());
//				}
//			}
//		 System.out.println("Zwrocilo, koniec funkcji");
//		 return (List<Client>) activeClientList;
//	}
	
	@Override
	public List<Client> clientsFromAgent(Agent agent) {
		return (List<Client>) sessionFactory.getCurrentSession().createCriteria(Client.class)
				.add(Restrictions.eq("agentDataModel", agent)).list();
	}

}
