package pl.helpdesk.dao;

import org.springframework.transaction.annotation.Transactional;

import pl.helpdesk.api.ILoggingHistoryDao;
import pl.helpdesk.entity.LoggingHistory;

@Transactional
public class LoggingHistoryDao extends GenericDao<LoggingHistory,Integer> implements ILoggingHistoryDao{

}
