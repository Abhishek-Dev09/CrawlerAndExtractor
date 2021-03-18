package com.springrest.springrest.services;

import java.util.ArrayList;

import com.springrest.springrest.models.PriceModel;

import com.springrest.springrest.models.ProductModel;

public interface ProductService {

	public ProductModel getProductDetailsById(String id) throws Exception;
	public String getHtml(String id) throws Exception;

	ArrayList<ProductModel> getList() throws Exception;

	ArrayList<PriceModel> getPriceTrend(String id) throws Exception;

	public ProductModel GetProductDetailsHistory(String id,String time) throws Exception;
}
