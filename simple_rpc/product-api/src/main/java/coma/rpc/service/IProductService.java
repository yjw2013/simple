package coma.rpc.service;

import coma.rpc.service.bean.Product;

public interface IProductService {
		
	public Product queryById(long id);
	
}
