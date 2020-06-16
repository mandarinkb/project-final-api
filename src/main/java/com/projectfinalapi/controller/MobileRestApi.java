package com.projectfinalapi.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.projectfinalapi.function.ApiResponse;
import com.projectfinalapi.function.CheckError;
import com.projectfinalapi.function.DateTime;
import com.projectfinalapi.function.Log;
import com.projectfinalapi.function.SwitchDatabase;
import com.projectfinalapi.model.GoodsDTO;
import com.projectfinalapi.model.Query;
import com.projectfinalapi.service.ServiceMobileApi;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/mobile")
public class MobileRestApi {
	@Autowired
	private Log log;
	
	@Autowired
	private Query q;
	
	@Autowired
	private SwitchDatabase swdb;
	
    @Autowired
    private ServiceMobileApi  serviceMobileApi;   
    
    @Autowired
    private CheckError error;
    
    @Autowired
    private DateTime  dateTime;  
    
    @Autowired
    private ApiResponse  apiResponse; 
    
    //ค้นหา
    @PostMapping(path = {"/name"}, headers = "Accept=application/json;charset=UTF-8")
    public ResponseEntity<?>  name(@RequestBody GoodsDTO goods,
    		                       @RequestParam("from") String from){
    	
    	log.createLog(dateTime.datetime() ,dateTime.timestamp(), "user", "search", "search "+goods.getName());  // เก็บ log
    	String index = q.findOneStrExcuteQuery("select DATABASE_NAME from SWITCH_DATABASE where DATABASE_STATUS = 1");  
        //String index = swdb.getDatabaseRun();
        String name = goods.getName();
        if(index.isEmpty()) {
        	String error = apiResponse.error(dateTime.timestamp(), 400, "Bad Request", "index is empty", "/mobile/name");
        	return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }else if(name.isEmpty()) {
        	String error = apiResponse.error(dateTime.timestamp(), 400, "Bad Request", "name is empty", "/mobile/name");
        	return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }else {
            String serviceValue = serviceMobileApi.listName(index, name, from);
            if(error.isServiceError(serviceValue)) {
            	JSONObject json = new JSONObject(serviceValue);
            	String error = apiResponse.error(dateTime.timestamp(), 400, "Bad Request", json.getString("error"), "/mobile/name");
            	return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
            }else {
            	return ResponseEntity.ok(serviceValue); 
            }	
        } 
    }
    
    //ค้นหาโดยใช้ filter
    @PostMapping(path = {"/name-and-filter"}, headers = "Accept=application/json;charset=UTF-8")
    public ResponseEntity<?>  nameFilter(@RequestBody GoodsDTO goods,
    		                             @RequestParam("from") String from){
    
    	log.createLog(dateTime.datetime() ,dateTime.timestamp(), "user", "filter search", "filter search "+goods.getName());  // เก็บ log
    	String index = q.findOneStrExcuteQuery("select DATABASE_NAME from SWITCH_DATABASE where DATABASE_STATUS = 1");  
        //String index = swdb.getDatabaseRun();
        String name = goods.getName();
        if(index.isEmpty()) {
        	String error = apiResponse.error(dateTime.timestamp(), 400, "Bad Request", "index is empty", "/mobile/name");
        	return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }else if(name.isEmpty()) {
        	String error = apiResponse.error(dateTime.timestamp(), 400, "Bad Request", "name is empty", "/mobile/name");
        	return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }else {
            String serviceValue = serviceMobileApi.listNameAndFilter(index, goods, from);
            if(error.isServiceError(serviceValue)) {
            	JSONObject json = new JSONObject(serviceValue);
            	String error = apiResponse.error(dateTime.timestamp(), 400, "Bad Request", json.getString("error"), "/mobile/name");
            	return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
            }else {
            	return ResponseEntity.ok(serviceValue); 
            }	
        }  
    }
    
    //ค้นหาโดยเลือกหมวดหมู่
    @PostMapping(path = {"/category"}, headers = "Accept=application/json;charset=UTF-8")
    public ResponseEntity<?> category(@RequestBody GoodsDTO goods, 
    		                          @RequestParam("from") String from){
    	
    	log.createLog(dateTime.datetime() ,dateTime.timestamp(), "user", "category", "select category "+goods.getCategory());  // เก็บ log
    	String index = q.findOneStrExcuteQuery("select DATABASE_NAME from SWITCH_DATABASE where DATABASE_STATUS = 1");  
        // String index = swdb.getDatabaseRun();
        String category = goods.getCategory();        
        if(index.isEmpty()) {
        	String error = apiResponse.error(dateTime.timestamp(), 400, "Bad Request", "index is empty", "/mobile/category");
        	return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }else if(category.isEmpty()) {
        	String error = apiResponse.error(dateTime.timestamp(), 400, "Bad Request", "category is empty", "/mobile/category");
        	return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }else {
            String serviceValue = serviceMobileApi.listCategory(index, category ,from);              
            if(error.isServiceError(serviceValue)) {
            	JSONObject json = new JSONObject(serviceValue);
            	String error = apiResponse.error(dateTime.timestamp(), 400, "Bad Request", json.getString("error"), "/mobile/category");
            	return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
            }else {
            	return ResponseEntity.ok(serviceValue); 
            }
        }
    }
    
    //สินค้าหน้าแรก
    @GetMapping(path = {"/items"}, headers = "Accept=application/json;charset=UTF-8")
    public ResponseEntity<?> item(@RequestParam("from") String from){
    	
        String index = q.findOneStrExcuteQuery("select DATABASE_NAME from SWITCH_DATABASE where DATABASE_STATUS = 1");     
        if(index.isEmpty()) {
        	String error = apiResponse.error(dateTime.timestamp(), 400, "Bad Request", "index is empty", "/mobile/item");
        	return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }else {
            String serviceValue = serviceMobileApi.listItemByDesc(index,from);              
            if(error.isServiceError(serviceValue)) {
            	JSONObject json = new JSONObject(serviceValue);
            	String error = apiResponse.error(dateTime.timestamp(), 400, "Bad Request", json.getString("error"), "/mobile/item");
            	return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
            }else {
            	return ResponseEntity.ok(serviceValue); 
            }
        }
    }
    // เลือก web name from filter
    @GetMapping(path = {"/web-name"}, headers = "Accept=application/json;charset=UTF-8")
    public ResponseEntity<?> webName(){
    	return ResponseEntity.ok(serviceMobileApi.listWebName()); 
    }
/*    
    //count
    @PostMapping(path = {"/count-name"}, headers = "Accept=application/json;charset=UTF-8")
    public ResponseEntity<?>  countName(@RequestBody GoodsDTO goods){
    	String index = q.findOneStrExcuteQuery("select DATABASE_NAME from SWITCH_DATABASE where DATABASE_STATUS = 1");  
        //String index = swdb.getDatabaseRun();
        String name = goods.getName();
        if(index.isEmpty()) {
        	String error = apiResponse.error(dateTime.timestamp(), 400, "Bad Request", "index is empty", "/mobile/name");
        	return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }else if(name.isEmpty()) {
        	String error = apiResponse.error(dateTime.timestamp(), 400, "Bad Request", "name is empty", "/mobile/name");
        	return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }else {
            String serviceValue = serviceMobileApi.listCountName(index, name);
            if(error.isServiceError(serviceValue)) {
            	JSONObject json = new JSONObject(serviceValue);
            	String error = apiResponse.error(dateTime.timestamp(), 400, "Bad Request", json.getString("error"), "/mobile/name");
            	return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
            }else {
            	return ResponseEntity.ok(serviceValue); 
            }	
        }  
    }
    
    @PostMapping(path = {"/count-name-and-filter"}, headers = "Accept=application/json;charset=UTF-8")
    public ResponseEntity<?>  countNameFilter(@RequestBody GoodsDTO goods){
    	String index = q.findOneStrExcuteQuery("select DATABASE_NAME from SWITCH_DATABASE where DATABASE_STATUS = 1");  
        //String index = swdb.getDatabaseRun();
        String name = goods.getName();
        if(index.isEmpty()) {
        	String error = apiResponse.error(dateTime.timestamp(), 400, "Bad Request", "index is empty", "/mobile/name");
        	return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }else if(name.isEmpty()) {
        	String error = apiResponse.error(dateTime.timestamp(), 400, "Bad Request", "name is empty", "/mobile/name");
        	return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }else {
            String serviceValue = serviceMobileApi.listCountNameAndFilter(index, goods);
            if(error.isServiceError(serviceValue)) {
            	JSONObject json = new JSONObject(serviceValue);
            	String error = apiResponse.error(dateTime.timestamp(), 400, "Bad Request", json.getString("error"), "/mobile/name");
            	return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
            }else {
            	return ResponseEntity.ok(serviceValue); 
            }	
        }  
    }
    @PostMapping(path = {"/count-category"}, headers = "Accept=application/json;charset=UTF-8")
    public ResponseEntity<?> countCategory(@RequestBody GoodsDTO goods){
    	String index = q.findOneStrExcuteQuery("select DATABASE_NAME from SWITCH_DATABASE where DATABASE_STATUS = 1");  
        // String index = swdb.getDatabaseRun();
        String category = goods.getCategory();        
        if(index.isEmpty()) {
        	String error = apiResponse.error(dateTime.timestamp(), 400, "Bad Request", "index is empty", "/mobile/category");
        	return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }else if(category.isEmpty()) {
        	String error = apiResponse.error(dateTime.timestamp(), 400, "Bad Request", "category is empty", "/mobile/category");
        	return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }else {
            String serviceValue = serviceMobileApi.listCountCategory(index, category);              
            if(error.isServiceError(serviceValue)) {
            	JSONObject json = new JSONObject(serviceValue);
            	String error = apiResponse.error(dateTime.timestamp(), 400, "Bad Request", json.getString("error"), "/mobile/category");
            	return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
            }else {
            	return ResponseEntity.ok(serviceValue); 
            }
        }
    }
    
    @GetMapping(path = {"/count-items"}, headers = "Accept=application/json;charset=UTF-8")
    public ResponseEntity<?> countItem(){
        String index = q.findOneStrExcuteQuery("select DATABASE_NAME from SWITCH_DATABASE where DATABASE_STATUS = 1");     
        if(index.isEmpty()) {
        	String error = apiResponse.error(dateTime.timestamp(), 400, "Bad Request", "index is empty", "/mobile/item");
        	return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }else {
            String serviceValue = serviceMobileApi.listCountItemByDesc(index);              
            if(error.isServiceError(serviceValue)) {
            	JSONObject json = new JSONObject(serviceValue);
            	String error = apiResponse.error(dateTime.timestamp(), 400, "Bad Request", json.getString("error"), "/mobile/item");
            	return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
            }else {
            	return ResponseEntity.ok(serviceValue); 
            }
        }
    }
*/    
}

