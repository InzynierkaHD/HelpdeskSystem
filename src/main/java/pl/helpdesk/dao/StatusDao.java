package pl.helpdesk.dao;

import org.hibernate.criterion.Restrictions;

import pl.helpdesk.api.IStatusDao;
import pl.helpdesk.entity.Status;

public class StatusDao extends GenericDao<Status,Integer> implements IStatusDao{
	@Override
	public Status getStatusByName(String name) {
		
		return (Status)sessionFactory.getCurrentSession().createCriteria(Status.class).
				add(Restrictions.eq("nazwa", name))
				.uniqueResult();
	}
}
