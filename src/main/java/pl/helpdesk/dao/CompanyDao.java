package pl.helpdesk.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import pl.helpdesk.api.ICompanyDao;
import pl.helpdesk.entity.Company;
import pl.helpdesk.entity.IssueType;

@Transactional
public class CompanyDao extends GenericDao<Company, Integer> implements ICompanyDao {

	public CompanyDao() {
		super();
	}

	@Override
	public Company getCompanyByName(String companyName) {
		Company company = (Company) sessionFactory.getCurrentSession().createCriteria(Company.class)
				.add(Restrictions.eq("nazwa", companyName)).uniqueResult();
		return company;

	}
	
	@Override 
	public List <Company> getAllCompany(){
		return getAll();
	}
	
	@Override 
	public List<String> getCompaniesWithoutAgent(){

		List <Company> withoutAgent = getAllCompany();
		System.out.println(withoutAgent.size());
		List <Company> allCompanies = getAllCompany();
		for(Company company : allCompanies){
			if(company.getAgent()!=null){
				withoutAgent.remove(company);
			}
		}
		System.out.println("liczba firm bez przedstawieiela: "+withoutAgent.size());
		List<String> listOfString = new ArrayList<String>();
		for(Object value : withoutAgent){
			listOfString.add(value.toString());
		}
		return listOfString;

	}

}