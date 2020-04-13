package com.projectfinalapi.model;

public class GoodsDTO {

	private String index;
	private String name;
	private String category;
	public GoodsDTO() {
		super();
	}
	public GoodsDTO(String index, String name, String category) {
		super();
		this.index = index;
		this.name = name;
		this.category = category;
	}
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
}
