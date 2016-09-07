package pl.helpdesk.dao;

import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import pl.helpdesk.api.ICompanyDao;
import pl.helpdesk.entity.Agent;
import pl.helpdesk.entity.Company;

@Transactional
public class CompanyDao extends GenericDao<Company,Integer> implements ICompanyDao{

	public CompanyDao(){
		super();
	}
	

	
}