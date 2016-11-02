package pl.helpdesk.dao;

import org.hibernate.criterion.Restrictions;

import pl.helpdesk.api.IStatusHistoryDao;
import pl.helpdesk.entity.Issue;
import pl.helpdesk.entity.Status;
import pl.helpdesk.entity.StatusHistory;

public class StatusHistoryDao  extends GenericDao<StatusHistory,Integer> implements IStatusHistoryDao{

	@Override
	public Status getCurrentStatus(Issue issue) {
		Status currentStatus = (Status)sessionFactory.getCurrentSession().createCriteria(StatusHistoryDao.class)
				.add(Restrictions.eqOrIsNull("problemDataModel", issue)).uniqueResult();
		return currentStatus;
	}

	
}
