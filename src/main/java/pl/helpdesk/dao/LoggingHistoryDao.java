package pl.helpdesk.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import pl.helpdesk.api.ILoggingHistoryDao;
import pl.helpdesk.entity.Employee;
import pl.helpdesk.entity.LoggingHistory;
import pl.helpdesk.entity.User;

@Transactional
public class LoggingHistoryDao extends GenericDao<LoggingHistory,Integer> implements ILoggingHistoryDao{
	
	public LoggingHistoryDao() {
		super();
	}

	@Override
	public void setUserLogOutDate(User user){

		LoggingHistory loggingHistory= (LoggingHistory) sessionFactory.getCurrentSession().createCriteria(LoggingHistory.class).add(Restrictions.eq("userDataModel", user)).addOrder(Order.desc("dataLogowania")).setMaxResults(1).uniqueResult();

		loggingHistory.setDataWylogowania(new Date());
		update(loggingHistory);
	}
	
	@Override
	public List<LoggingHistory> getDateSortedUserLoggingHistory(int id){

		return sessionFactory.getCurrentSession().createCriteria(LoggingHistory.class)
				.createCriteria("userDataModel", "user")
				.add(Restrictions.eq("user.czy_usuniety", false))
				.add(Restrictions.eq("user.id", id))
				.addOrder(Order.asc("user.ost_logowanie")).list();
	}
}
