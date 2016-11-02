package pl.helpdesk.api;

import pl.helpdesk.entity.Issue;
import pl.helpdesk.entity.Status;
import pl.helpdesk.entity.StatusHistory;

public interface IStatusHistoryDao extends IGenericDao<StatusHistory,Integer>{
	Status getCurrentStatus(Issue issue);
}
