package pl.helpdesk.dao;

import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import pl.helpdesk.entity.Company;
import pl.helpdesk.entity.User;

public class CompanyDao {

	private EntityManagerFactory entityMF =  Persistence.createEntityManagerFactory("baza");
    private EntityManager entityM =entityMF.createEntityManager();
    private CriteriaBuilder builder = entityM.getCriteriaBuilder();
    private CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);
    private Root <User> user=criteriaQuery.from(User.class);
	
    
    public void closeConection(){
		 entityM.close();
	     entityMF.close();
	}


    /**
     * 
     * Dodaje nową firmę.
     * 
     * @param nazwa Nazwa firmy.
     * @param miejscowosc Miejscowość, w której znajduje się firma.
     * @param ulica Ulica, na której znajduje się firma
     * @param kod_pocztowy Kod pocztowy siedziby firmy.
     * @param numer Numer do firmy.
     */
public void addCompany( String nazwa, String miejscowosc, String ulica, String kod_pocztowy, String numer){
	
    
    Company companyDataModel = new Company();

    companyDataModel.setNazwa(nazwa);
    companyDataModel.setUlica(ulica);
    companyDataModel.setKod_pocztowy(kod_pocztowy);
    companyDataModel.setMiejscowosc(miejscowosc);
    companyDataModel.setNumer(numer);
    
    entityM.getTransaction().begin();
    entityM.persist(companyDataModel);
    entityM.getTransaction().commit();
  
	}
}