package pl.helpdesk.dao;

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
	

}