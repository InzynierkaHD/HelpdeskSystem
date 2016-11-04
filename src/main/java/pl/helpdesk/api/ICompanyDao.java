package pl.helpdesk.api;

import java.util.List;

import pl.helpdesk.entity.Company;
import pl.helpdesk.entity.IssueType;

/**
 * Interfejs dla Firmy dao
 * 
 * @author Adam Ulidowski
 *
 */
public interface ICompanyDao extends IGenericDao<Company,Integer>{
	
	public Company getCompanyByName(String companyName);

	public List <Company> getAllCompany();
	
	public List<String> getCompaniesWithoutAgent();
		
}
