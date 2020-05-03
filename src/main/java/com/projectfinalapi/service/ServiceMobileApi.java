package com.projectfinalapi.service;

import com.projectfinalapi.model.GoodsDTO;

public interface ServiceMobileApi {
    String listCategory(String index,String category);
    String listName(String index,String name);
    String listItemByDesc(String index);
    String listWebName();
    String listNameAndFilter(String index, GoodsDTO goods);
    
}
