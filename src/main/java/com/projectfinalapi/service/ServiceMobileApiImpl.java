package com.projectfinalapi.service;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.projectfinalapi.model.Elasticsearch;

@Service
public class ServiceMobileApiImpl implements ServiceMobileApi{
    @Autowired
    private Elasticsearch  elasticsearch;
 

	@Override
	public String listCategory(String index,String inputCategory) {
        String elsValue = elasticsearch.getByCategory(index, inputCategory);        
        return getGoods(elsValue);
	}

	@Override
	public String listName(String index,String name) {
	    String elsValue = elasticsearch.getByName(index, name);
	    return getGoods(elsValue);
	}

	@Override
	public String listItemByDesc(String index) {
	    String elsValue = elasticsearch.getItemDesc(index);
	    return getGoods(elsValue);
	}
	
	//method
	public String getGoods(String elsValue){
		List<JSONObject> list = new ArrayList<>();
		try {
	        //ดึงเอาาค่าที่ต้องการเพื่อส่งไปยัง api	        
	        JSONObject objResultsValue = new JSONObject(elsValue);
	        JSONObject objHits = objResultsValue.getJSONObject("hits");
	        JSONArray arrHits = objHits.getJSONArray("hits");
	        JSONObject json = new JSONObject();
	        for (int i = 0; i < arrHits.length(); i++) {
	        	json = new JSONObject();
	            JSONObject objSource = arrHits.getJSONObject(i).getJSONObject("_source");	            
	            json.put("image", objSource.getString("image"));
	            json.put("originalPrice", objSource.getDouble("originalPrice"));
	            json.put("price", objSource.getDouble("price"));
	            json.put("name", objSource.getString("name"));
	            json.put("icon", objSource.getString("icon"));
	            json.put("discount", objSource.getDouble("discount"));
	            json.put("category", objSource.getString("category"));
	            json.put("productUrl", objSource.getString("productUrl"));
	            list.add(json);   
	        }	  
	        return list.toString();
		}catch(Exception e) {
			// ส่ง error กลับ
			JSONObject json = new JSONObject();
			json.put("error", e.getMessage());			
			return json.toString();
		}		
	}
	
}
