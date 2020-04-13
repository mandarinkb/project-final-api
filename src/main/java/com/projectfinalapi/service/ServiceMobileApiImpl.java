package com.projectfinalapi.service;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.projectfinalapi.model.Elasticsearch;
import com.projectfinalapi.model.Goods;

@Service
public class ServiceMobileApiImpl implements ServiceMobileApi{
    @Autowired
    private Elasticsearch  elasticsearch;
     
	@Override
	public List<Goods> listCategory(String index,String inputCategory) {
        String elsValue = elasticsearch.getByCategory(index, inputCategory);        
        return getGoods(elsValue);
	}

	@Override
	public List<Goods> listName(String index,String name) {
	    String elsValue = elasticsearch.getByName(index, name);
	    return getGoods(elsValue);
	}
	
	//method
	public List<Goods> getGoods(String elsValue){
		List<Goods> list = new ArrayList<>();
		try {
	        //ดึงเอาาค่าที่ต้องการเพื่อส่งไปยัง api	        
	        JSONObject objResultsValue = new JSONObject(elsValue);
	        JSONObject objHits = objResultsValue.getJSONObject("hits");
	        JSONArray arrHits = objHits.getJSONArray("hits");
	        for (int i = 0; i < arrHits.length(); i++) {
	            JSONObject objSource = arrHits.getJSONObject(i).getJSONObject("_source");
	            
	            String image = objSource.getString("image");
	            Double originalPrice = objSource.getDouble("originalPrice");
	            Double price = objSource.getDouble("price");
	            String name = objSource.getString("name");
	            String icon = objSource.getString("icon");
	            Double discount = objSource.getDouble("discount");
	            String category = objSource.getString("category");
	            String productUrl = objSource.getString("productUrl");
	            
	            //ใช้ constructer toString ที่overrideไว้
	            Goods results = new Goods(image, originalPrice, price, name, icon, discount, category, productUrl);
	            list.add(results);
	        }	        
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return list;
	}

}
