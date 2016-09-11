package pl.helpdesk.dao;

import org.hibernate.criterion.Restrictions;

import pl.helpdesk.api.IProductDao;
import pl.helpdesk.entity.Product;

public class ProductDao extends GenericDao<Product,Integer> implements IProductDao{

	@Override
	public Product findProductByName(String productName) {
		return (Product)sessionFactory.getCurrentSession().createCriteria(Product.class)
		.add(Restrictions.eq("nazwa", productName))
		.setMaxResults(1)
		.uniqueResult();
	}
	

}
