package pl.helpdesk.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import pl.helpdesk.entity.Employee;


public class EmployeeDao {
	
	private EntityManagerFactory entityMF =  Persistence.createEntityManagerFactory("baza");
    private EntityManager entityM =entityMF.createEntityManager();
    private CriteriaBuilder builder = entityM.getCriteriaBuilder();
    private CriteriaQuery<Employee> criteriaQuery = builder.createQuery(Employee.class);
    private Root <Employee> employee=criteriaQuery.from(Employee.class);

    
	public void closeConection(){
		 entityM.close();
	     entityMF.close();
	}
	
	/**
	 * Tworzy listę pracowników Helpdesku.
	 * 
	 * @return Lista pracowników.
	 */
    public List <Employee> createEmployeeList(){
    	
    	criteriaQuery.select(employee);
		
		TypedQuery<Employee> query=entityM.createQuery(criteriaQuery);
		
		List <Employee> employees=query.getResultList();
		return employees;
    }
}

