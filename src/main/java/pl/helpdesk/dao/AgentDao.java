package pl.helpdesk.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import pl.helpdesk.api.IAgentDao;
import pl.helpdesk.api.IGenericDao;
import pl.helpdesk.entity.Agent;
import pl.helpdesk.entity.Company;
import pl.helpdesk.entity.User;

@Transactional
public class AgentDao extends GenericDao<Agent, Integer> implements IAgentDao {

	public AgentDao() {
		super();
	}

	@Override
	public Boolean isAgent(User user) {
		if (!sessionFactory.getCurrentSession().createCriteria(Agent.class).add(Restrictions.eq("userDataModel", user))
				.list().isEmpty()) {
			return true;
		} else
			return false;
	}

	@Override
	public Agent findAgentByUser(User user) {
		return (Agent) sessionFactory.getCurrentSession().createCriteria(Agent.class)
				.add(Restrictions.eq("userDataModel", user)).uniqueResult();
	}

	public List<String> getCompanyWithoutAgent() {
		CompanyDao compDao = new CompanyDao();
		List<Company> compList = compDao.getAll();
		List<Company> compList2 = new ArrayList<Company>();
		for (Company companyList : compList) {
			if (!(boolean) sessionFactory.getCurrentSession().createCriteria(Agent.class)
					.add(Restrictions.eq("companyDataModel", companyList)).uniqueResult()) {
				compList2.add(companyList);
			}
		}
		return ((GenericDao<Company,Integer>) compList2).getAllToString();
	}

}
