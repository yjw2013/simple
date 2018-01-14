package com.rpc.service;

import coma.rpc.service.IProductService;
import coma.rpc.service.bean.Product;


public class ProductService implements IProductService{

	@Override
	public Product queryById(long id) {
		
		Product product = new Product();
		product.setId(id);
		product.setName("water");
		product.setPrice(1.0);
		
		return product;
	}
	
}
