package pl.helpdesk.dao;


import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import pl.helpdesk.api.IEmployeeDao;
import pl.helpdesk.entity.Employee;
import pl.helpdesk.entity.User;

@Transactional
public class EmployeeDao extends GenericDao<Employee, Integer> implements IEmployeeDao {

	public EmployeeDao() {
		super();
	}

	@Override
	public Boolean isEmployee(User user) {
		if (!sessionFactory.getCurrentSession().createCriteria(Employee.class)
				.add(Restrictions.eq("userDataModel", user)).list().isEmpty()) {
			return true;
		} else
			return false;
	}

	@Override
	public Employee getEmployeeByUser(User user) {
		Employee employee = (Employee) sessionFactory.getCurrentSession().createCriteria(Employee.class)
				.add(Restrictions.eq("userDataModel", user)).uniqueResult();
		return employee;
	}

	@Override
	public String numOfEmpl(String surname){
		
		if (surname.equals("")) {
			return String.valueOf(sessionFactory.getCurrentSession().createCriteria(Employee.class).createCriteria("userDataModel", "user").add(Restrictions.eq("user.czy_usuniety", false))
					.setProjection(Projections.rowCount()).uniqueResult());
		} else
			return String.valueOf(sessionFactory.getCurrentSession().createCriteria(Employee.class).createCriteria("userDataModel", "user").add(Restrictions.eq("user.czy_usuniety", false))
					.add(Restrictions.like("user.nazwisko", surname, MatchMode.ANYWHERE)).setProjection(Projections.rowCount()).uniqueResult());

	}
		
	
	@Override
	public List<Employee> getSortedEmployees(String sortBy, String surname) {
		if (sortBy.equals("Nazwisko") || sortBy.equals("0")) {
			if (surname.equals("")) {
				return sessionFactory.getCurrentSession().createCriteria(Employee.class)
						.createCriteria("userDataModel", "user").add(Restrictions.eq("user.czy_usuniety", false))
						.addOrder(Order.asc("user.nazwisko")).list();
			} else
				return sessionFactory.getCurrentSession().createCriteria(Employee.class)
						.createCriteria("userDataModel", "user").add(Restrictions.eq("user.czy_usuniety", false))
						.add(Restrictions.like("user.nazwisko", surname, MatchMode.ANYWHERE))
						.addOrder(Order.asc("user.nazwisko")).list();
		}
		if (sortBy.equals("1")) {
			if (surname.equals("")) {
				return sessionFactory.getCurrentSession().createCriteria(Employee.class)
						.createCriteria("userDataModel", "user").add(Restrictions.eq("user.czy_usuniety", false))
						.addOrder(Order.asc("user.email")).list();
			} else
				return sessionFactory.getCurrentSession().createCriteria(Employee.class)
						.createCriteria("userDataModel", "user").add(Restrictions.eq("user.czy_usuniety", false))
						.add(Restrictions.like("user.nazwisko", surname, MatchMode.ANYWHERE))
						.addOrder(Order.asc("user.email")).list();
		}
		if (sortBy.equals("2")) {
			if (surname.equals("")) {
				return sessionFactory.getCurrentSession().createCriteria(Employee.class)
						.createCriteria("userDataModel", "user").add(Restrictions.eq("user.czy_usuniety", false))
						.addOrder(Order.asc("user.ost_logowanie")).list();
			} else
				return sessionFactory.getCurrentSession().createCriteria(Employee.class)
						.createCriteria("userDataModel", "user").add(Restrictions.eq("user.czy_usuniety", false))
						.add(Restrictions.like("user.nazwisko", surname, MatchMode.ANYWHERE))
						.addOrder(Order.asc("user.ost_logowanie")).list();
		}
		if (sortBy.equals("3")) {
			if (surname.equals("")) {
				return sessionFactory.getCurrentSession().createCriteria(Employee.class)
						.createCriteria("userDataModel", "user").add(Restrictions.eq("user.czy_usuniety", false))
						.addOrder(Order.asc("user.czy_blokowany")).list();
			} else
				return sessionFactory.getCurrentSession().createCriteria(Employee.class)
						.createCriteria("userDataModel", "user").add(Restrictions.eq("user.czy_usuniety", false))
						.add(Restrictions.like("user.nazwisko", surname, MatchMode.ANYWHERE))
						.addOrder(Order.asc("user.czy_blokowany")).list();
		} else {
			return sessionFactory.getCurrentSession().createCriteria(Employee.class)
					.createCriteria("userDataModel", "user").add(Restrictions.eq("user.czy_usuniety", false)).list();
		}

	}
}
