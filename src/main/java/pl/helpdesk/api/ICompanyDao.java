package pl.helpdesk.api;

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

		
}
