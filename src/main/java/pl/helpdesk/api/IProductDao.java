package pl.helpdesk.api;

import pl.helpdesk.entity.Product;

public interface IProductDao extends IGenericDao<Product,Integer>{

	Product findProductByName(String productName);
}
