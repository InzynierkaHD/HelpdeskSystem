package pl.helpdesk.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import pl.helpdesk.api.ISortingDao;
import pl.helpdesk.api.IStatusHistoryDao;
import pl.helpdesk.entity.Issue;
import pl.helpdesk.entity.Status;
import pl.helpdesk.entity.StatusHistory;
import pl.helpdesk.entity.User;

public class StatusHistoryDao  extends GenericDao<StatusHistory,Integer> implements IStatusHistoryDao, ISortingDao<StatusHistory>{

	@Override
	public Status getCurrentStatus(Issue issue) {
		Status issueStatus = null;
		StatusHistory currentStatusHistory = (StatusHistory)sessionFactory.getCurrentSession().createCriteria(StatusHistory.class)
				.addOrder(Order.desc("id"))
				.add(Restrictions.eqOrIsNull("problemDataModel", issue)).setMaxResults(1).uniqueResult();
		if(currentStatusHistory != null) issueStatus = currentStatusHistory.getStatusDataModel();
		return issueStatus;
	}

	@Override
	public List getSortingAsc(User user, String propertyName) {
		return null;
	}

	@Override
	public List getSortingAsc(String propertyName) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(StatusHistory.class)
				.createAlias("problemDataModel", "issue")
				.createAlias("statusDataModel", "status");
		if(propertyName.equals("nazwa")){
			criteria.addOrder(Order.asc("status."+propertyName));
		}
		else{
			criteria.addOrder(Order.asc("issue."+propertyName));
		}
		List list = criteria.list();
		return list;
	}

	@Override
	public List getSortingDesc(User user, String propertyName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List getSortingDesc(String propertyName) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(StatusHistory.class)
				.createAlias("problemDataModel", "issue")
				.createAlias("statusDataModel", "status");
		if(propertyName.equals("nazwa")){
			criteria.addOrder(Order.desc("status."+propertyName));
		}
		else{
			criteria.addOrder(Order.desc("issue."+propertyName));
		}
		List list = criteria.list();
		return list;
	}

	
}
