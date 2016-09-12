package pl.helpdesk.dao;

import java.util.Date;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import pl.helpdesk.api.ILoggingHistoryDao;
import pl.helpdesk.entity.LoggingHistory;
import pl.helpdesk.entity.User;

@Transactional
public class LoggingHistoryDao extends GenericDao<LoggingHistory,Integer> implements ILoggingHistoryDao{

	@Override
	public void setUserLogOutDate(User user){

		LoggingHistory loggingHistory= (LoggingHistory) sessionFactory.getCurrentSession().createCriteria(LoggingHistory.class).add(Restrictions.eq("userDataModel", user)).addOrder(Order.desc("dataLogowania")).setMaxResults(1).uniqueResult();

		loggingHistory.setDataWylogowania(new Date());
		update(loggingHistory);
	}
}
