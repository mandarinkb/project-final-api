package com.projectfinalapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projectfinalapi.model.Goods;
import com.projectfinalapi.model.GoodsDTO;
import com.projectfinalapi.service.ServiceMobileApi;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/mobile")
public class MobileRestApi {
    @Autowired
    private ServiceMobileApi  serviceMobileApi;   
      
    @PostMapping(path = {"/search-name"}, headers = "Accept=application/json;charset=UTF-8")
    public List<Goods> name(@RequestBody GoodsDTO goods){
        String index = goods.getIndex();
        String name = goods.getName();
        return serviceMobileApi.listName(index, name);
    }
    @PostMapping(path = {"/search-category"}, headers = "Accept=application/json;charset=UTF-8")
    public List<Goods> category(@RequestBody GoodsDTO goods){
        String index = goods.getIndex();
        String category = goods.getCategory();
        return serviceMobileApi.listCategory(index, category);
    }
}

