package pl.helpdesk.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import pl.helpdesk.api.IIssueDao;
import pl.helpdesk.api.ISortingDao;
import pl.helpdesk.entity.Issue;
import pl.helpdesk.entity.User;

public class IssueDao extends GenericDao<Issue,Integer> implements IIssueDao, ISortingDao<Issue>{

	@Override
	public List<Issue> getAllIssuesForUser(User user) {
		return sessionFactory.getCurrentSession().createCriteria(Issue.class)
		.add(Restrictions.eqOrIsNull("user", user)).list();
	}
	
	@Override
	public List<Issue> getSortingIssuesForUser(User user,String issuePropertyName){
		return sessionFactory.getCurrentSession().createCriteria(Issue.class)
				.add(Restrictions.eqOrIsNull("user", user))
				.addOrder(Order.asc(issuePropertyName)).list();
	}

	@Override
	public List<Issue> getSortingIssuesForUserDesc(User user, String issuePropertyName) {
		return sessionFactory.getCurrentSession().createCriteria(Issue.class)
				.add(Restrictions.eqOrIsNull("user", user))
				.addOrder(Order.desc(issuePropertyName)).list();
	}

	@Override
	public List<Issue> getSortingIssuesForall(String issuePropertyName) {
		return sessionFactory.getCurrentSession().createCriteria(Issue.class)
				.addOrder(Order.asc(issuePropertyName)).list();
	}

	@Override
	public List<Issue> getSortingIssuesForAllDesc(String issuePropertyName) {
		return sessionFactory.getCurrentSession().createCriteria(Issue.class)
				.addOrder(Order.desc(issuePropertyName)).list();
	}

	@Override
	public List<Issue> getIssuesForUserbyId(User user, Integer id) {
		return sessionFactory.getCurrentSession().createCriteria(Issue.class)
				.add(Restrictions.eqOrIsNull("user", user))
				.add(Restrictions.eqOrIsNull("id", id)).list();
	}

	@Override
	public List<Issue> getSortingAsc(User user, String propertyName) {
		return sessionFactory.getCurrentSession().createCriteria(Issue.class)
				.add(Restrictions.eqOrIsNull("user", user))
				.addOrder(Order.asc(propertyName)).list();
	}

	@Override
	public List<Issue> getSortingAsc(String propertyName) {
		return sessionFactory.getCurrentSession().createCriteria(Issue.class)
				.addOrder(Order.asc(propertyName)).list();// TODO Auto-generated method stub
	}

	@Override
	public List<Issue> getSortingDesc(User user, String propertyName) {
		return sessionFactory.getCurrentSession().createCriteria(Issue.class)
				.add(Restrictions.eqOrIsNull("user", user))
				.addOrder(Order.desc(propertyName)).list();
	}

	@Override
	public List<Issue> getSortingDesc(String propertyName) {
		return sessionFactory.getCurrentSession().createCriteria(Issue.class)
				.addOrder(Order.desc(propertyName)).list();
	}

	
	
	
	
	
}
