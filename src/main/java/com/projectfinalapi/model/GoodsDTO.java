package com.projectfinalapi.model;

public class GoodsDTO {
	private String index;
	private String name;
	private String category;
	private String[] webName;
	private String minPrice;
	private String maxPrice;
	
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String[] getWebName() {
		return webName;
	}
	public void setWebName(String[] webName) {
		this.webName = webName;
	}
	public String getMinPrice() {
		return minPrice;
	}
	public void setMinPrice(String minPrice) {
		this.minPrice = minPrice;
	}
	public String getMaxPrice() {
		return maxPrice;
	}
	public void setMaxPrice(String maxPrice) {
		this.maxPrice = maxPrice;
	}
	
}
