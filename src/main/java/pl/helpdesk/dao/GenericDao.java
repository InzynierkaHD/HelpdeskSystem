package pl.helpdesk.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import pl.helpdesk.api.IGenericDao;

/**
 * Generyczne dao 
 * 
 * @author Krzysztof Krocz
 *
 * @param <T> Typ encji
 * @param <ID> typ id encji (np. Integer,Long itd..)
 */
@Transactional
public abstract class GenericDao<T,ID> implements IGenericDao<T,ID>{

	protected Class<? extends T> daoType;

	@Resource
	SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public GenericDao() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        daoType = (Class) pt.getActualTypeArguments()[0];
	}

	@Override
	public void save(T object) {
		sessionFactory.getCurrentSession().save(object);
	}
	
	@Override
	public void update(T object){
		sessionFactory.getCurrentSession().saveOrUpdate(object);
	}
	
	@Override
	public void delete(T object){
		sessionFactory.getCurrentSession().delete(object);
	}

	@Override
	public List<T> getAll() {
		return sessionFactory.getCurrentSession().createCriteria(daoType).list();
	}
	
	@Override
	public T getById(ID id) {
		return (T)sessionFactory.getCurrentSession().get(daoType, (Serializable) id);
	}
	
	
}
