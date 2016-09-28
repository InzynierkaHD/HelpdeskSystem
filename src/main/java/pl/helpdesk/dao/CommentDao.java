package pl.helpdesk.dao;

import java.util.List;

import org.hibernate.criterion.Restrictions;

import pl.helpdesk.api.ICommentDao;
import pl.helpdesk.entity.Comment;
import pl.helpdesk.entity.Issue;

public class CommentDao extends GenericDao<Comment,Integer> implements ICommentDao {
	
	
	public List<Comment> getCommentByIssue(Issue issue){
		if(issue != null) System.out.println("issue w dao "+ issue);
		else System.out.println("issue jest null");
		List<Comment> listOfComments = sessionFactory.getCurrentSession().createCriteria(Comment.class)
		.add(Restrictions.eq("issue",issue)).list();
		return listOfComments;
		
	};
}
