package pl.helpdesk.api;

import pl.helpdesk.entity.Employee;
import pl.helpdesk.entity.User;

public interface IEmployeeDao extends IGenericDao<Employee,Integer>{

	public Boolean isEmployee(User user);
}
