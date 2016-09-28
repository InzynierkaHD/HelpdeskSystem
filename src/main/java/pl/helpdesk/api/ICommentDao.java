package pl.helpdesk.api;

import java.util.List;

import pl.helpdesk.entity.Comment;
import pl.helpdesk.entity.Issue;

public interface ICommentDao extends IGenericDao<Comment,Integer>{
	List<Comment> getCommentByIssue(Issue issue);
}
