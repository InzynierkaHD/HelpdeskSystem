package pl.helpdesk.dao;

import java.security.NoSuchAlgorithmException;

import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import pl.helpdesk.api.IUserDao;
import pl.helpdesk.entity.User;
import pl.helpdesk.passwordHash.HashPassword;

/**
 * Dao dla encji użytkownika
 * 
 * @author Krzysztof Krocz
 *
 */
@Transactional
public class UserSpringDao extends GenericDao<User, Integer> implements IUserDao {

	@Override
	public void saveUser(User user) {
		sessionFactory.getCurrentSession().save(user);
	}

	@Override
	public User getUser(String login) {
		return (User) sessionFactory.getCurrentSession().createCriteria(User.class).add(Restrictions.eq("login", login))
				.uniqueResult();
	}

	@Override
	public User findUserByLoginAndPassword(String login, String password)
			throws HibernateException, NoSuchAlgorithmException {

		return (User) sessionFactory.getCurrentSession().createCriteria(User.class).add(Restrictions.eq("login", login))
				.add(Restrictions.eq("haslo", HashPassword.PasswordHash(password))).uniqueResult();

	}

	@Override
	public boolean emailExist(String email) {
		if (!sessionFactory.getCurrentSession().createCriteria(User.class).add(Restrictions.eq("email", email)).list()
				.isEmpty()) {
			return true;
		} else
			return false;
	}
	
	@Override
	public boolean loginExist(String login) {
		if (!sessionFactory.getCurrentSession().createCriteria(User.class).add(Restrictions.eq("login", login)).list()
				.isEmpty()) {
			return true;
		} else
			return false;
	}

}
