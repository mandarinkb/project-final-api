package com.projectfinalapi.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projectfinalapi.config.JwtTokenUtil;
import com.projectfinalapi.function.ApiResponse;
import com.projectfinalapi.function.CheckError;
import com.projectfinalapi.function.DateTime;
import com.projectfinalapi.function.JwtRequest;
import com.projectfinalapi.model.Query;
import com.projectfinalapi.model.User;
import com.projectfinalapi.model.UserDto;
import com.projectfinalapi.service.JwtUserDetailsService;
import com.projectfinalapi.service.ServiceWebScrappingControl;

@CrossOrigin(origins = "*", allowedHeaders = "*")

@RestController
@RequestMapping("/api")
public class RestApiController {
  @Autowired
  private CheckError error;
	
  @Autowired
  private DateTime  dateTime;  
	
  @Autowired
  private ApiResponse apiResponse;   
  
  @Autowired
  private Query q;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @Autowired
  private JwtUserDetailsService userDetailsService;
  
  @Autowired
  private ServiceWebScrappingControl serviceWebScrappongControl;
  
  @PostMapping(path = {"/authenticate"}, headers = "Accept=application/json;charset=UTF-8")    
  public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
      authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
      final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
      final String token = jwtTokenUtil.generateToken(userDetails);      
      String response = apiResponse.authenticate(dateTime.timestamp(), 200, token);
      return new ResponseEntity<>(response, HttpStatus.OK);
  }
  
  @PostMapping(path = {"/register"}, headers = "Accept=application/json;charset=UTF-8")  
  public ResponseEntity<?> createUsers(@RequestBody User user)  throws Exception{ //
      String userDb = q.findOneStrExcuteQuery("select USERNAME from USERS where USERNAME= '"+user.getUsername()+"' ");
      // check ว่ามีผู้ใช้นี้อยู่ในระบบหรือไม่
      if (userDb.equals("")) {
    	  return new ResponseEntity<>(userDetailsService.save(user), HttpStatus.CREATED);
      } else {
      	String error = apiResponse.error(dateTime.timestamp(), 400, "Bad Request",
      			                        "That username is already taken. Try using a different name.", "/api/register");
      	return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
      }
  }    

  @GetMapping(path = {"/users"}, headers = "Accept=application/json;charset=UTF-8")
  public ResponseEntity<?> readUsers() {
	  return ResponseEntity.ok(serviceWebScrappongControl.findUsers());
  }
  
  @GetMapping(path = {"/users/{id}"}, headers = "Accept=application/json;charset=UTF-8")
  public ResponseEntity<?> getUsersId(@PathVariable("id") int id) {
	  String serviceValue = serviceWebScrappongControl.findUsersById(id);
	  if(error.isFindUsersByIdError(serviceValue)) {
	      String error = apiResponse.error(dateTime.timestamp(), 400, "Bad Request",
                                           "Not found users", "/api/users/");
          return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	  }else {
		  return ResponseEntity.ok(serviceValue);
	  }
  }   

  @PutMapping(path = {"/users/{id}"}, headers = "Accept=application/json;charset=UTF-8")
  public ResponseEntity<?> updateUsers(@RequestBody UserDto userDto, @PathVariable("id") int id) {
      return ResponseEntity.ok(serviceWebScrappongControl.updateUsers(id, userDto.getRole()));
  }  
  
  @DeleteMapping(path = {"/users/{id}"}, headers = "Accept=application/json;charset=UTF-8")
  public ResponseEntity<?> deleteUsers(@PathVariable("id") int id){
	  return new ResponseEntity<>(serviceWebScrappongControl.deleteUsers(id), HttpStatus.NO_CONTENT);//NO_CONTENT
  }

   
  
  
  
  
  
  
  @GetMapping(path = {"/web"}, headers = "Accept=application/json;charset=UTF-8")
  public String webStatus() {
      return serviceWebScrappongControl.getWeb();
  }
  
  @PostMapping(path = {"/web"}, headers = "Accept=application/json;charset=UTF-8")
  public String createWeb(@RequestBody String strBody) {
      JSONObject obj = new JSONObject(strBody);
      String web_name = obj.getString("web_name");
      String url = obj.getString("url");
      String type = obj.getString("type");
      String type_detail = obj.getString("type_detail");
      String web_status = obj.getString("web_status");
      String season = obj.getString("season");
      String base_url = obj.getString("base_url");
      String detail = obj.getString("detail");
      return serviceWebScrappongControl.saveWeb(web_name, url, type, type_detail, web_status, season, base_url, detail);
  }    
  
  @PutMapping(path = {"/web/{id}"}, headers = "Accept=application/json;charset=UTF-8")
  public String updateWeb(@RequestBody String strBody, @PathVariable("id") int id) {
      JSONObject obj = new JSONObject(strBody);
      String web_name = obj.getString("web_name");
      String url = obj.getString("url");
      String type = obj.getString("type");
      String type_detail = obj.getString("type_detail");
      String web_status = obj.getString("web_status");
      String season = obj.getString("season");
      String base_url = obj.getString("base_url");
      String detail = obj.getString("detail");
      return serviceWebScrappongControl.updateWeb(id, web_name, url, type, type_detail, web_status, season, base_url, detail);
  }    
  @GetMapping(path = {"/web/{id}"}, headers = "Accept=application/json;charset=UTF-8")
  public String findWebId(@PathVariable("id") int id) {
      return serviceWebScrappongControl.findWebById(id);
  }
  
  @PutMapping(path = {"/web-status/{id}"}, headers = "Accept=application/json;charset=UTF-8")
  public String updateWebStatus(@RequestBody String strForm, @PathVariable("id") int id) {
      JSONObject obj = new JSONObject(strForm);
      String webStatus = obj.getString("web_status");
      return serviceWebScrappongControl.updateWebStatus(id, webStatus);
  }
     
  @DeleteMapping(path = {"/web/{id}"}, headers = "Accept=application/json;charset=UTF-8")
  public String deleteWeb(@PathVariable("id") int id){
      return serviceWebScrappongControl.deleteWeb(id);
  }
  @GetMapping(path = {"/schedule"}, headers = "Accept=application/json;charset=UTF-8")
  public String webSchedule() {
      return serviceWebScrappongControl.getSchedule();
  }

  @GetMapping(path = {"/schedule/{id}"}, headers = "Accept=application/json;charset=UTF-8")
  public String webScheduleId(@PathVariable("id") int id) {
      return serviceWebScrappongControl.findScheduleById(id);
  }

  @PutMapping(path = {"/schedule/{id}"}, headers = "Accept=application/json;charset=UTF-8")
  public String updateSchedule(@RequestBody String strForm, @PathVariable("id") int id) {
      JSONObject obj = new JSONObject(strForm);
      String schedule_name = obj.getString("schedule_name");
      String cron_expression = obj.getString("cron_expression");
      String function_name = obj.getString("function_name");
      String project_name = obj.getString("project_name");
      String detail = obj.getString("detail");
      return serviceWebScrappongControl.updateSchedule(id, schedule_name, cron_expression, function_name, project_name, detail);
  }

  @PostMapping(path = {"/schedule"}, headers = "Accept=application/json;charset=UTF-8")
  public String createSchedule(@RequestBody String strBody) {
      JSONObject obj = new JSONObject(strBody);
      String schedule_name = obj.getString("schedule_name");
      String cron_expression = obj.getString("cron_expression");
      String function_name = obj.getString("function_name");
      String project_name = obj.getString("project_name");
      String detail = obj.getString("detail");
      return serviceWebScrappongControl.saveSchedule(schedule_name, cron_expression, function_name, project_name, detail);
  }
  
  @DeleteMapping(path = {"/schedule/{id}"}, headers = "Accept=application/json;charset=UTF-8")
  public String deleteSchedule(@PathVariable("id") int id){
      return serviceWebScrappongControl.deleteSchedule(id);
  }

  
  
  private void authenticate(String username, String password) throws Exception {
      try {
          authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
      } catch (DisabledException e) {
          throw new Exception("USER_DISABLED", e);
      } catch (BadCredentialsException e) {
          throw new Exception("INVALID_CREDENTIALS", e);
      }
  }
}