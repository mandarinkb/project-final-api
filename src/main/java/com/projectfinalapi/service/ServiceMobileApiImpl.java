package com.projectfinalapi.service;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projectfinalapi.function.Json;
import com.projectfinalapi.model.Database;
import com.projectfinalapi.model.Elasticsearch;
import com.projectfinalapi.model.GoodsDTO;

@Service
public class ServiceMobileApiImpl implements ServiceMobileApi{
    @Autowired
    private Elasticsearch  elasticsearch;
    
    @Autowired
    private Database db;
    
	@Autowired
	private Json json;
 

	@Override
	public String listCategory(String index,String inputCategory,String from) {
        String elsValue = elasticsearch.getByCategory(index, inputCategory,from);        
        return getGoods(elsValue);
	}

	@Override
	public String listName(String index,String name,String from) {
	    String elsValue = elasticsearch.getByName(index, name,from);
	    return getGoods(elsValue);
	}
	
	@Override
	public String listHistory(String index, String[] history, String from) {
		String elsValue = elasticsearch.getByNameHistory(index, history,from);
		return getGoods(elsValue);
	}
	
	@Override
	public String listNameAndFilter(String index, GoodsDTO goods,String from) {		
	    String elsValue = elasticsearch.getByNameAndFilter(index, goods.getName(),goods.getMinPrice(),goods.getMaxPrice(),goods.getWebName(),from);
	    return getGoods(elsValue);
	}
	
	@Override
	public String listWebName() {
        String sql = "select WEB_NAME from WEB";
        List<String> listVarchar = new ArrayList<String>();
        List<String> listChar = new ArrayList<String>();
        List<String> listInt = new ArrayList<String>();
        List<JSONObject> list = new ArrayList<>();
        try {
            ResultSet rs = db.getResultSet(db.connectDatase(), sql);
            if (rs != null) {
                ResultSetMetaData columns = rs.getMetaData();
                int i = 0;
                while (i < columns.getColumnCount()) {  // แยก data type พร้อมเก็บลง list
                    i++;
                    // เก็บชื่อ column
                    if (columns.getColumnTypeName(i) == ("INTEGER")) {  
                        listInt.add(columns.getColumnName(i).toLowerCase());
                    } else if (columns.getColumnTypeName(i) == "CHAR") {
                        listChar.add(columns.getColumnName(i).toLowerCase()); //columns.getColumnName(i).toLowerCase()
                    } else {
                        listVarchar.add(columns.getColumnName(i).toLowerCase());
                    }
                }
                while (rs.next()) {
                    JSONObject obj = new JSONObject();
                    for (i = 0; i < listInt.size(); i++) {
                        obj.put(json.changeKeyUpperCase(listInt.get(i)), rs.getInt(listInt.get(i)));
                    }
                    for (i = 0; i < listVarchar.size(); i++) {
                        obj.put(json.changeKeyUpperCase(listVarchar.get(i)), rs.getString(listVarchar.get(i)));
                    }
                    for (i = 0; i < listChar.size(); i++) {
                        String value = rs.getString(listChar.get(i));
                        if ("1".equals(value)) {
                            obj.put(json.changeKeyUpperCase(listChar.get(i)), true);
                        } else {
                            obj.put(json.changeKeyUpperCase(listChar.get(i)), false);
                        }
                    }

                    list.add(obj);
                }
            }
        } catch (Exception e) {
        	System.out.println(e.getMessage());
        } finally {
            db.closeConnect(db.connectDatase());
        }
        return list.toString();
	
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
	            json.put("review", objSource.getString("review"));
	            json.put("ratingScore", objSource.getString("ratingScore"));
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
