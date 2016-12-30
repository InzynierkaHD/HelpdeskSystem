package pl.helpdesk.dao;

import java.util.List;

import org.apache.wicket.spring.injection.annot.SpringBean;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import pl.helpdesk.api.ICommentDao;
import pl.helpdesk.api.ISearchDao;
import pl.helpdesk.api.ISortingDao;
import pl.helpdesk.api.IStatusHistoryDao;
import pl.helpdesk.entity.Comment;
import pl.helpdesk.entity.Issue;
import pl.helpdesk.entity.Status;
import pl.helpdesk.entity.StatusHistory;
import pl.helpdesk.entity.User;

public class StatusHistoryDao  extends GenericDao<StatusHistory,Integer> implements IStatusHistoryDao, ISortingDao<StatusHistory>, ISearchDao<StatusHistory>{

	@SpringBean
	ICommentDao commentDao;
	
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
	public List<StatusHistory> getSortingAsc(User user, String propertyName) {
		return null;
	}

	@Override
	public List<StatusHistory> getSortingAsc(String propertyName) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(StatusHistory.class)
				.createAlias("problemDataModel", "issue")
				.createAlias("statusDataModel", "status");
		if(propertyName.equals("nazwa")){
			criteria.addOrder(Order.asc("status."+propertyName));
		}
		else{
			criteria.addOrder(Order.asc("issue."+propertyName));
		}

		return criteria.list();
	}

	@Override
	public List<StatusHistory> getSortingDesc(User user, String propertyName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StatusHistory> getSortingDesc(String propertyName) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(StatusHistory.class)
				.createAlias("problemDataModel", "issue")
				.createAlias("statusDataModel", "status");
		if(propertyName.equals("nazwa")){
			criteria.addOrder(Order.desc("status."+propertyName));
		}
		else{
			criteria.addOrder(Order.desc("issue."+propertyName));
		}
		return criteria.list();
	}

	@Override
	public List<StatusHistory> search(String propertyName,String keyWord) {
		/*Criteria commentCriteria = sessionFactory.getCurrentSession().createCriteria(Comment.class)
				.createAlias("issue","commentIssue")
				.add(Restrictions.like("tresc", "%"+keyWord+"%"));*/
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(StatusHistory.class)
				.createAlias("problemDataModel", "issue")
				.createAlias("statusDataModel", "status");
		if(propertyName.equals("nazwa")){
			criteria.add(Restrictions.eqOrIsNull("status.nazwa", keyWord));
		}
		else if(propertyName.equals("id")){
			criteria.add(Restrictions.eqOrIsNull("issue."+propertyName, Integer.parseInt(keyWord)));
		}
		else if(propertyName.equals("comment")){

		}
		else {
			criteria.add(Restrictions.eqOrIsNull("issue."+propertyName, keyWord));
		}
		return criteria.list();
	}

	
}
