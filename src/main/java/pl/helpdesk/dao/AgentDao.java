package pl.helpdesk.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import pl.helpdesk.api.IAgentDao;
import pl.helpdesk.api.IGenericDao;
import pl.helpdesk.entity.Agent;
import pl.helpdesk.entity.Client;
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
	
	@Override
	public String numOfAg(String surname){
		
		if (surname.equals("")) {
			return String.valueOf(sessionFactory.getCurrentSession().createCriteria(Agent.class).createCriteria("userDataModel", "user").add(Restrictions.eq("user.czy_usuniety", false))
					.setProjection(Projections.rowCount()).uniqueResult());
		} else
			return String.valueOf(sessionFactory.getCurrentSession().createCriteria(Agent.class).createCriteria("userDataModel", "user").add(Restrictions.eq("user.czy_usuniety", false))
					.add(Restrictions.like("user.nazwisko", surname, MatchMode.ANYWHERE)).setProjection(Projections.rowCount()).uniqueResult());

	}
		
	
	@Override
	public List<Agent> getSortedAgents(String sortBy, String surname) {
		if (sortBy.equals("Nazwisko") || sortBy.equals("0")) {
			if (surname.equals("")) {
				return sessionFactory.getCurrentSession().createCriteria(Agent.class)
						.createCriteria("userDataModel", "user").add(Restrictions.eq("user.czy_usuniety", false))
						.addOrder(Order.asc("user.nazwisko")).list();
			} else
				return sessionFactory.getCurrentSession().createCriteria(Agent.class)
						.createCriteria("userDataModel", "user").add(Restrictions.eq("user.czy_usuniety", false))
						.add(Restrictions.like("user.nazwisko", surname, MatchMode.ANYWHERE))
						.addOrder(Order.asc("user.nazwisko")).list();
		}
		if (sortBy.equals("1")) {
			if (surname.equals("")) {
				return sessionFactory.getCurrentSession().createCriteria(Agent.class)
						.createCriteria("userDataModel", "user").add(Restrictions.eq("user.czy_usuniety", false))
						.addOrder(Order.asc("user.email")).list();
			} else
				return sessionFactory.getCurrentSession().createCriteria(Agent.class)
						.createCriteria("userDataModel", "user").add(Restrictions.eq("user.czy_usuniety", false))
						.add(Restrictions.like("user.nazwisko", surname, MatchMode.ANYWHERE))
						.addOrder(Order.asc("user.email")).list();
		}
		if (sortBy.equals("2")) {
			if (surname.equals("")) {
				return sessionFactory.getCurrentSession().createCriteria(Agent.class)
						.createCriteria("userDataModel", "user").add(Restrictions.eq("user.czy_usuniety", false))
						.addOrder(Order.asc("user.ost_logowanie")).list();
			} else
				return sessionFactory.getCurrentSession().createCriteria(Agent.class)
						.createCriteria("userDataModel", "user").add(Restrictions.eq("user.czy_usuniety", false))
						.add(Restrictions.like("user.nazwisko", surname, MatchMode.ANYWHERE))
						.addOrder(Order.asc("user.ost_logowanie")).list();
		}
		if (sortBy.equals("3")) {
			if (surname.equals("")) {
				return sessionFactory.getCurrentSession().createCriteria(Agent.class)
						.createCriteria("userDataModel", "user").add(Restrictions.eq("user.czy_usuniety", false))
						.addOrder(Order.asc("user.czy_blokowany")).list();
			} else
				return sessionFactory.getCurrentSession().createCriteria(Agent.class)
						.createCriteria("userDataModel", "user").add(Restrictions.eq("user.czy_usuniety", false))
						.add(Restrictions.like("user.nazwisko", surname, MatchMode.ANYWHERE))
						.addOrder(Order.asc("user.czy_blokowany")).list();
		}
		if (sortBy.equals("4")) {
			if (surname.equals("")) {
				
				return sessionFactory.getCurrentSession().createCriteria(Agent.class, "agent")
						.createCriteria("agent.userDataModel", "user").add(Restrictions.eq("user.czy_usuniety", false))
						.createCriteria("agent.companyDataModel", "company").addOrder(Order.asc("company.nazwa")).list();
			} else
				return sessionFactory.getCurrentSession().createCriteria(Agent.class, "agent")
						.createCriteria("agent.userDataModel", "user").add(Restrictions.eq("user.czy_usuniety", false))
						.add(Restrictions.like("user.nazwisko", surname, MatchMode.ANYWHERE))
						.createCriteria("agent.companyDataModel", "company").addOrder(Order.asc("company.nazwa")).list();
		} else {
			return sessionFactory.getCurrentSession().createCriteria(Agent.class)
					.createCriteria("userDataModel", "user").add(Restrictions.eq("user.czy_usuniety", false)).list();
		}

	}

}
