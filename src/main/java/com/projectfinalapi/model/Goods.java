package com.projectfinalapi.model;

import org.springframework.stereotype.Repository;

@Repository
public class Goods {
	private String image;
	private Double originalPrice;
	private Double price;
	private String name;
	private String icon;
	private Double discount;
	private String category;
	private String productUrl;
	public Goods() {
		super();
	}
	public Goods(String image, Double originalPrice, Double price, String name, String icon, Double discount,
			     String category, String productUrl) {
		super();
		this.image = image;
		this.originalPrice = originalPrice;
		this.price = price;
		this.name = name;
		this.icon = icon;
		this.discount = discount;
		this.category = category;
		this.productUrl = productUrl;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Double getOriginalPrice() {
		return originalPrice;
	}
	public void setOriginalPrice(Double originalPrice) {
		this.originalPrice = originalPrice;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public Double getDiscount() {
		return discount;
	}
	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getProductUrl() {
		return productUrl;
	}
	public void setProductUrl(String productUrl) {
		this.productUrl = productUrl;
	}
	@Override
	public String toString() {
		return "Goods{image=" + image + ", originalPrice=" + originalPrice + ", price=" + price + ", name=" + name
				+ ", icon=" + icon + ", discount=" + discount + ", category=" + category + ", productUrl=" + productUrl
				+ "}";
	}
		
}
