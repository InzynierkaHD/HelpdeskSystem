package pl.helpdesk.dao;

import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import pl.helpdesk.api.IAgentDao;
import pl.helpdesk.entity.Agent;
import pl.helpdesk.entity.Client;
import pl.helpdesk.entity.Company;
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
			return true;
		}
		else
			return false;
	}
	
	@Override
	public Agent findAgentByUser(User user){
		return (Agent) sessionFactory.getCurrentSession().createCriteria(Agent.class).add(Restrictions.eq("userDataModel", user)).uniqueResult();
	}
	

}
