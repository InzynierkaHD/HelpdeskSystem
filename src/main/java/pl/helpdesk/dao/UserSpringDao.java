package pl.helpdesk.dao;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import pl.helpdesk.api.IUserDao;
import pl.helpdesk.entity.User;

/**
 * Dao dla encji użytkownika 
 * 
 * @author Krzysztof Krocz
 *
 */
@Transactional
public class UserSpringDao implements IUserDao {

	@Resource
	SessionFactory sessionFactory;

	@Override
	public void saveUser(User user) {
		sessionFactory.getCurrentSession().save(user);
	}

	@Override
	public User getUser(String login) {
		return (User) sessionFactory.getCurrentSession().createCriteria(User.class)
				.add(Restrictions.eq("login", login)).uniqueResult();
	}

	@Override
	public User findUserByLoginAndPassword(String login, String password) {

		return (User) sessionFactory.getCurrentSession().createCriteria(User.class)
				.add(Restrictions.eq("login", login)).add(Restrictions.eq("haslo", password)).uniqueResult();
		
	}

}
