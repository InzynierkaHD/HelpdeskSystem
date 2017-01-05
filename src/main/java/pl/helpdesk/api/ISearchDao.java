package pl.helpdesk.api;

import java.util.List;

import pl.helpdesk.entity.User;

public interface ISearchDao<T> {
	List<T> search(String propertyName,String keyword);
	List<T> search(User user,String propertyName,String keyword);
	List<T> searchIssueByComment(String commentContent);
	List<T> searchIssueByComment(User user,String commentContent);
}
