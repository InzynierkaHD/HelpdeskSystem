package pl.helpdesk.dao;

import org.hibernate.criterion.Restrictions;

import pl.helpdesk.api.IPriorityDao;
import pl.helpdesk.entity.Priority;

public class PriorityDao extends GenericDao<Priority,Integer> implements IPriorityDao{

	@Override
	public Priority getPriorityByName(String priorityName) {
		Priority priority=(Priority)sessionFactory.getCurrentSession().createCriteria(Priority.class).
				add(Restrictions.eq("nazwa", priorityName))
				.uniqueResult();
		return priority;
	}

}
