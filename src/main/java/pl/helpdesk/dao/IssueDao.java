package pl.helpdesk.dao;

import java.util.List;

import org.hibernate.criterion.Restrictions;

import pl.helpdesk.api.IIssueDao;
import pl.helpdesk.entity.Issue;
import pl.helpdesk.entity.User;

public class IssueDao extends GenericDao<Issue,Integer> implements IIssueDao{

	@Override
	public List<Issue> getAllIssuesForUser(User user) {
		return sessionFactory.getCurrentSession().createCriteria(Issue.class)
		.add(Restrictions.eqOrIsNull("user", user)).list();

	}
	
	
}
