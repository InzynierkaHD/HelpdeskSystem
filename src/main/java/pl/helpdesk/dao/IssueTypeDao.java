package pl.helpdesk.dao;

import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import pl.helpdesk.api.IIssueTypeDao;
import pl.helpdesk.entity.IssueType;
/**
 * 
 * 
 * @author Krzysztof Krocz
 *
 */
@Transactional
public class IssueTypeDao extends GenericDao<IssueType,Integer> implements IIssueTypeDao{

	public IssueTypeDao(){
		super();
	}

	@Override
	public IssueType getIssueTypeByName(String issueTypeName){
		IssueType issueType=(IssueType)sessionFactory.getCurrentSession().createCriteria(IssueType.class).
				add(Restrictions.eq("nazwa", issueTypeName))
				.uniqueResult();
		return issueType;
		
	}
	
}
