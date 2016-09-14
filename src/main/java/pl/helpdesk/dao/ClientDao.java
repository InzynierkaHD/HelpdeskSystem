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


	@Override
	public List<Client> clientsFromAgent(Agent agent) {
		return (List<Client>) sessionFactory.getCurrentSession().createCriteria(Client.class)
				.add(Restrictions.eq("agentDataModel", agent)).list();
	}

}
