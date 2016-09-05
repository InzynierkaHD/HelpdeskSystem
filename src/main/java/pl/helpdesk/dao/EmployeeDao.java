package pl.helpdesk.dao;

import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import pl.helpdesk.api.IEmployeeDao;
import pl.helpdesk.entity.Employee;
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
			System.out.println("Pracownik");
			return true;
		}
		else
			System.out.println("NIE PRACOWNIK");
			return false;
	}
}

