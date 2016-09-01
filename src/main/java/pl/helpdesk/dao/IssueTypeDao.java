package pl.helpdesk.dao;

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
public class IssueTypeDao extends GenericDao<IssueType> implements IIssueTypeDao{

	public IssueTypeDao(){
		super();
	}

	
}
