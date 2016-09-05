package pl.helpdesk.dao;

import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import pl.helpdesk.api.IAgentDao;
import pl.helpdesk.entity.Agent;
import pl.helpdesk.entity.User;

@Transactional
public class AgentDao extends GenericDao<Agent,Integer> implements IAgentDao{
	
	public AgentDao(){
		super();
	}
	
	@Override
	public Boolean isAgent(User user) {
		if(!sessionFactory.getCurrentSession().createCriteria(Agent.class)
			.add(Restrictions.eq("userDataModel", user)).list().isEmpty()){
			System.out.println("Przedstawiciel");
			return true;
		}
		else
			System.out.println("NIE PRZEDSTAWICIEL");
			return false;
	}
}
