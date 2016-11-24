package pl.helpdesk.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import pl.helpdesk.api.IStatusHistoryDao;
import pl.helpdesk.entity.Issue;
import pl.helpdesk.entity.Status;
import pl.helpdesk.entity.StatusHistory;

public class StatusHistoryDao  extends GenericDao<StatusHistory,Integer> implements IStatusHistoryDao{

	@Override
	public Status getCurrentStatus(Issue issue) {
		Status issueStatus = null;
		StatusHistory currentStatusHistory = (StatusHistory)sessionFactory.getCurrentSession().createCriteria(StatusHistory.class)
				.addOrder(Order.desc("id"))
				.add(Restrictions.eqOrIsNull("problemDataModel", issue)).setMaxResults(1).uniqueResult();
		if(currentStatusHistory != null) issueStatus = currentStatusHistory.getStatusDataModel();
		return issueStatus;
	}

	
}
