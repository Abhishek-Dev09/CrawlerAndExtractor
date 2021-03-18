package com.springrest.springrest.models;

import java.sql.Timestamp;

public class PriceModel {
	private Timestamp timestamp;
	private String price;
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
}
