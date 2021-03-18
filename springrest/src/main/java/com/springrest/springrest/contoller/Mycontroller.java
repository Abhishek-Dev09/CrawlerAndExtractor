package com.springrest.springrest.contoller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.springrest.springrest.services.ProductService;
import com.springrest.springrest.models.PriceModel;
import com.springrest.springrest.models.ProductModel;

@RestController
public class Mycontroller {
	@Autowired
	private ProductService productService;
	
	@GetMapping("/GetHtml/{sku}")
	//This api takes a sku ,crawls it and stores the html and returns it. 
	public String gethtml(@PathVariable String sku) throws Exception {
		//This api takes a sku ,crawls it and stores the html and returns it. 
		return this.productService.getHtml(sku);
	}
	@GetMapping("/GetProductDetails/{sku}")
	public ProductModel getProductDetails(@PathVariable String sku) throws Exception {
		/*This api takes an input amazon sku. Return a json of below attributes. 
		 * You might also need to store this info in a db of your choice as well. 
		 */
		return this.productService.getProductDetailsById(sku);
	}
	@GetMapping("/GetProductDetailsHistory/{sku}/{time}")
	
	public ProductModel GetProductDetailsHistory(@PathVariable String sku,@PathVariable String time) throws Exception {
		/*This api will take a timestamp(YYYY-MM-DD HH:MM:SS) as well, this will 
		 * return the info for the newest page crawled before the given timestamp
		 */
		return this.productService.GetProductDetailsHistory(sku,time);
	}
	@GetMapping("/ListAll")
	public ArrayList<ProductModel> ListAll() throws Exception {
		//List All crawled Products and details till now.
		return this.productService.getList();
	}
	@GetMapping("/PriceTrend/{sku}")
	public ArrayList<PriceModel> getPriceTrend(@PathVariable String sku) throws Exception {
		//For product's sku pages, return a json which shows all prices collected till now sorted by timestamp. 
		return this.productService.getPriceTrend(sku);
	}
}
