package pl.helpdesk.api;

import java.util.List;

import pl.helpdesk.entity.Company;
import pl.helpdesk.entity.CompanyProduct;
import pl.helpdesk.entity.Product;

/**
 * Interfejs dla tabeli firma-produkt dao
 * 
 *
 */
public interface ICompanyProductDao extends IGenericDao<CompanyProduct,Integer>{
	
	List<String> getAllProductsForCompany(Company company);
	CompanyProduct findCompanyProductByProductAndCompany(Product Product, Company company);
}
