package com.projectfinalapi.service;

import java.util.List;
import com.projectfinalapi.model.Goods;

public interface ServiceMobileApi {
    List<Goods> listCategory(String index,String category);
    List<Goods> listName(String index,String name);
}
