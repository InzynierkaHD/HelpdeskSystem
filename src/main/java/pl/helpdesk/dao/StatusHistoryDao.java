package pl.helpdesk.dao;

import java.util.List;

import org.apache.wicket.spring.injection.annot.SpringBean;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.expression.spel.ast.Projection;

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
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(StatusHistory.class,"h1")
				.setProjection(Property.forName("data").max())
						.add(Property.forName("h1.problemDataModel.id").eqProperty("h2.problemDataModel.id"));
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(StatusHistory.class,"h2")
				.setProjection( Projections.projectionList()
						.add(Projections.property("problemDataModel.id"))
						.add(Projections.property("employeeDataModel"))
						.add(Projections.property("data"))
						.add(Projections.groupProperty("problemDataModel"))
				.add(Projections.property("statusDataModel")))
				.add(Property.forName("data").in(detachedCriteria))
				.createAlias("problemDataModel", "issue")
				.createAlias("statusDataModel", "status")
				.add(Restrictions.eq("issue.user", user));
		if(propertyName.equals("nazwa")){
			criteria.addOrder(Order.asc("status."+propertyName));
		}
		else{
			criteria.addOrder(Order.asc("issue."+propertyName));
		}
		return criteria.list();
	}

	@Override
	public List<StatusHistory> getSortingAsc(String propertyName) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(StatusHistory.class,"h1")
				.setProjection(Property.forName("data").max())
						.add(Property.forName("h1.problemDataModel.id").eqProperty("h2.problemDataModel.id"));
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(StatusHistory.class,"h2")
				.setProjection( Projections.projectionList()
						.add(Projections.property("problemDataModel.id"))
						.add(Projections.property("employeeDataModel"))
						.add(Projections.property("data"))
						.add(Projections.groupProperty("problemDataModel"))
				.add(Projections.property("statusDataModel")))
				.add(Property.forName("data").in(detachedCriteria))
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
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(StatusHistory.class,"h1")
				.setProjection(Property.forName("data").max())
						.add(Property.forName("h1.problemDataModel.id").eqProperty("h2.problemDataModel.id"));
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(StatusHistory.class,"h2")
				.setProjection( Projections.projectionList()
						.add(Projections.property("problemDataModel.id"))
						.add(Projections.property("employeeDataModel"))
						.add(Projections.property("data"))
						.add(Projections.groupProperty("problemDataModel"))
				.add(Projections.property("statusDataModel")))
				.add(Property.forName("data").in(detachedCriteria))
				.createAlias("problemDataModel", "issue")
				.createAlias("statusDataModel", "status")
				.add(Restrictions.eq("issue.user", user));

		if(propertyName.equals("nazwa")){
			criteria.addOrder(Order.desc("status."+propertyName));
		}
		else{
			criteria.addOrder(Order.desc("issue."+propertyName));
		}
		List lista = criteria.list();
		//criteria.setProjection(Projections.distinct(Projections.property("issue.id")));
		return lista;
	}

	@Override
	public List<StatusHistory> getSortingDesc(String propertyName) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(StatusHistory.class,"h1")
				.setProjection(Property.forName("data").max())
						.add(Property.forName("h1.problemDataModel.id").eqProperty("h2.problemDataModel.id"));
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(StatusHistory.class,"h2")
				.setProjection( Projections.projectionList()
						.add(Projections.property("problemDataModel.id"))
						.add(Projections.property("employeeDataModel"))
						.add(Projections.property("data"))
						.add(Projections.groupProperty("problemDataModel"))
				.add(Projections.property("statusDataModel")))
				.add(Property.forName("data").in(detachedCriteria))
				.createAlias("problemDataModel", "issue")
				.createAlias("statusDataModel", "status");
	
		if(propertyName.equals("nazwa")){
			criteria.addOrder(Order.desc("status."+propertyName));
		}
		else{
			criteria.addOrder(Order.desc("issue."+propertyName));
		}
		List lista = criteria.list();
		//criteria.setProjection(Projections.distinct(Projections.property("issue.id")));
		return lista;
	}

	@Override
	public List<StatusHistory> search(String propertyName,String keyWord) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(StatusHistory.class,"h1")
				.setProjection(Property.forName("data").max())
						.add(Property.forName("h1.problemDataModel.id").eqProperty("h2.problemDataModel.id"));
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(StatusHistory.class,"h2")
				.setProjection( Projections.projectionList()
						.add(Projections.property("problemDataModel.id"))
						.add(Projections.property("employeeDataModel"))
						.add(Projections.property("data"))
						.add(Projections.groupProperty("problemDataModel"))
				.add(Projections.property("statusDataModel")))
				.add(Property.forName("data").in(detachedCriteria))
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
		//criteria.setProjection(Projections.distinct(Projections.property("issue.id")));
		return criteria.list();
	}

	@Override
	public List<StatusHistory> search(User user, String propertyName, String keyword) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(StatusHistory.class,"h1")
				.setProjection(Property.forName("data").max())
						.add(Property.forName("h1.problemDataModel.id").eqProperty("h2.problemDataModel.id"));
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(StatusHistory.class,"h2")
				.setProjection( Projections.projectionList()
						.add(Projections.property("problemDataModel.id"))
						.add(Projections.property("employeeDataModel"))
						.add(Projections.property("data"))
						.add(Projections.groupProperty("problemDataModel"))
				.add(Projections.property("statusDataModel")))
				.add(Property.forName("data").in(detachedCriteria))
				.createAlias("problemDataModel", "issue")
				.createAlias("statusDataModel", "status")
				.add(Restrictions.eq("issue.user", user));
		if(propertyName.equals("nazwa")){
			criteria.add(Restrictions.eqOrIsNull("status.nazwa", keyword));
		}
		else if(propertyName.equals("id")){
			criteria.add(Restrictions.eqOrIsNull("issue."+propertyName, Integer.parseInt(keyword)));
		}
		else if(propertyName.equals("comment")){

		}
		else {
			criteria.add(Restrictions.eqOrIsNull("issue."+propertyName, keyword));
		}
		//criteria.setProjection(Projections.distinct(Projections.property("issue.id")));
		return criteria.list();
	}

	@Override
	public List<StatusHistory> searchIssueByComment(String commentContent) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Comment.class,"comment")
				.setProjection(Property.forName("issue"))
		.add(Restrictions.like("tresc", "%"+commentContent+"%"));
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(StatusHistory.class,"h2")
				.setProjection( Projections.projectionList()
						.add(Projections.property("problemDataModel.id"))
						.add(Projections.property("employeeDataModel"))
						.add(Projections.property("data"))
						.add(Projections.groupProperty("problemDataModel"))
				.add(Projections.property("statusDataModel")))
				.add(Property.forName("problemDataModel").in(detachedCriteria))
				.createAlias("problemDataModel", "issue")
				.createAlias("statusDataModel", "status");
		return criteria.list();
	}

	@Override
	public List<StatusHistory> searchIssueByComment(User user, String commentContent) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Comment.class,"comment")
				.setProjection(Property.forName("issue"))
		.add(Restrictions.like("tresc", "%"+commentContent+"%"));
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(StatusHistory.class,"h2")
				.setProjection( Projections.projectionList()
						.add(Projections.property("problemDataModel.id"))
						.add(Projections.property("employeeDataModel"))
						.add(Projections.property("data"))
						.add(Projections.groupProperty("problemDataModel"))
				.add(Projections.property("statusDataModel")))
				.add(Property.forName("problemDataModel").in(detachedCriteria))
				.createAlias("problemDataModel", "issue")
				.createAlias("statusDataModel", "status")
				.add(Restrictions.eq("issue.user", user));
		return criteria.list();
	}

	
}
