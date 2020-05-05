package com.projectfinalapi.service;

import com.projectfinalapi.model.GoodsDTO;

public interface ServiceMobileApi {
	//search
    String listCategory(String index,String category);
    String listName(String index,String name);
    String listItemByDesc(String index, String from);
    String listWebName();
    String listNameAndFilter(String index, GoodsDTO goods);
    
    //count
    String listCountCategory(String index,String category);
    String listCountName(String index,String name);
    String listCountItemByDesc(String index);
    String listCountNameAndFilter(String index, GoodsDTO goods);
    
}
