package com.springrest.springrest.models;

import java.sql.Timestamp;

public class ProductModel {
	private String title;
	private String offerPrice;
	private String description;
	private Timestamp time;
	
	public String getTitle() {
		return title;
	}
	public void title(String title) {
		this.title = title;
	}
	public String getofferPrice() {
		return offerPrice;
	}
	public void setPrice(String price) {
		this.offerPrice = price;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
}
