package pl.helpdesk.dao;

import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import pl.helpdesk.api.IEmployeeDao;
import pl.helpdesk.entity.Employee;
import pl.helpdesk.entity.Priority;
import pl.helpdesk.entity.User;

@Transactional
public class EmployeeDao extends GenericDao<Employee,Integer> implements IEmployeeDao{

	public EmployeeDao(){
		super();
	}
	
	@Override
	public Boolean isEmployee(User user) {
		if(!sessionFactory.getCurrentSession().createCriteria(Employee.class)
			.add(Restrictions.eq("userDataModel", user)).list().isEmpty()){
			return true;
		}
		else
			return false;
	}

	@Override
	public Employee getEmployeeByUser(User user) {
		Employee employee=(Employee)sessionFactory.getCurrentSession().createCriteria(Employee.class).
				add(Restrictions.eq("userDataModel", user))
				.uniqueResult();
		return employee;
	}
	
	
}

