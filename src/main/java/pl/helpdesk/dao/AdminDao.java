package pl.helpdesk.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import pl.helpdesk.entity.Admin;
import pl.helpdesk.entity.User;

public class AdminDao {
	
	private EntityManagerFactory entityMF =  Persistence.createEntityManagerFactory("baza");
    private EntityManager entityM =entityMF.createEntityManager();
    private CriteriaBuilder builder = entityM.getCriteriaBuilder();
    private CriteriaQuery<Admin> criteriaQuery = builder.createQuery(Admin.class);
    private Root <Admin> admin=criteriaQuery.from(Admin.class);

    
	public void closeConection(){
		 entityM.close();
	     entityMF.close();
	}
	/**
	 * Tworzy listę administratorów.
	 * 
	 * @return lista administratorów.
	 */
    public List <Admin> createAdminList(){
    	
    	criteriaQuery.select(admin);
		
		TypedQuery<Admin> query=entityM.createQuery(criteriaQuery);
		
		List <Admin> admins=query.getResultList();
		return admins;
    }
}
