package pl.helpdesk.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Restrictions;

import pl.helpdesk.api.ICompanyProductDao;
import pl.helpdesk.entity.Company;
import pl.helpdesk.entity.CompanyProduct;
import pl.helpdesk.entity.Product;

public class CompanyProductDao extends GenericDao<CompanyProduct,Integer> implements ICompanyProductDao{
	
	public List<String> getAllProductsForCompany(Company company){
		List<String> listOfProducts = new ArrayList<String>();
		
		List<CompanyProduct> list= sessionFactory.getCurrentSession().createCriteria(CompanyProduct.class)
		.add(Restrictions.eq("companyDataModel", company)).list();
		for(CompanyProduct value : list){
			listOfProducts.add(value.getProductDataModel().toString());
		}
		return listOfProducts;
	}

	@Override
	public CompanyProduct findCompanyProductByProductAndCompany(Product product,Company company) {
		CompanyProduct foundCompanyProduct = (CompanyProduct)sessionFactory.getCurrentSession().createCriteria(CompanyProduct.class)
				.add(Restrictions.eq("companyDataModel", company))
				.add(Restrictions.eq("productDataModel", product))
				.setMaxResults(1).uniqueResult();
		return foundCompanyProduct;
	}
}
