package com.projectfinalapi.service;

import com.projectfinalapi.model.GoodsDTO;

public interface ServiceMobileApi {
	//search
    String listCategory(String index,String category,String from);
    String listName(String index,String name,String from);
    String listHistory(String index,String[] history,String from);
    String listWebName();
    String listNameAndFilter(String index, GoodsDTO goods,String from);
/*    
    //count
    String listCountCategory(String index,String category);
    String listCountName(String index,String name);
    String listCountItemByDesc(String index);
    String listCountNameAndFilter(String index, GoodsDTO goods);
*/    
}
