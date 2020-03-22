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
import com.projectfinalapi.function.JwtRequest;
import com.projectfinalapi.function.JwtResponse;
import com.projectfinalapi.model.Query;
import com.projectfinalapi.model.User;
import com.projectfinalapi.service.JwtUserDetailsService;
import com.projectfinalapi.service.ServiceWebScrappingControl;

//@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@CrossOrigin(origins = "*", allowedHeaders = "*")

@RestController
@RequestMapping("/api")
public class RestApiController {
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
      return ResponseEntity.ok(new JwtResponse(token));
  }
  
  @PostMapping(path = {"/register"}, headers = "Accept=application/json;charset=UTF-8")  
  public ResponseEntity<?> saveUser(@RequestBody User userdto)  throws Exception{ //
      String userDb = q.findOneStrExcuteQuery("select username from users where username= '"+userdto.getUsername()+"' ");
      // check ว่ามีผู้ใช้นี้อยู่ในระบบหรือไม่
      if ("".equals(userDb)) {
          return ResponseEntity.ok(userDetailsService.save(userdto));
      } else {
          return new ResponseEntity<>(apiResponse.status(400, "มีผู้ใช้นี้แล้วในระบบ"), HttpStatus.BAD_REQUEST);
      }
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

  @GetMapping(path = {"/users"}, headers = "Accept=application/json;charset=UTF-8")
  public String webUsers() {
      return serviceWebScrappongControl.getUsers();
  }
  /*    
  @PostMapping(path = {"/users"}, headers = "Accept=application/json;charset=UTF-8")
  public ResponseEntity<?> createUser(@RequestBody User userdto) throws Exception { //
      String userDb = q.findOneStrExcuteQuery("select username from users where username= '" + userdto.getUsername() + "' ");
      // check ว่ามีผู้ใช้นี้อยู่ในระบบหรือไม่
      if ("".equals(userDb)) {
          return ResponseEntity.ok(userDetailsService.save(userdto));
      } else {
          return new ResponseEntity<>(apiResponse.status(400, "มีผู้ใช้นี้แล้วในระบบ"), HttpStatus.BAD_REQUEST);
      }
  }
  public String createUsers(@RequestBody String strBody) {
      JSONObject obj = new JSONObject(strBody);
      String username = obj.getString("username");
      String password = obj.getString("password");
      String role = obj.getString("role");
      return serviceWebScrappongControl.saveUsers(username, password, role);
  }
*/

  
  @DeleteMapping(path = {"/users/{id}"}, headers = "Accept=application/json;charset=UTF-8")
  public String deleteUsers(@PathVariable("id") int id){
      return serviceWebScrappongControl.deleteUsers(id);
  }

  @GetMapping(path = {"/users/{id}"}, headers = "Accept=application/json;charset=UTF-8")
  public String getUsersId(@PathVariable("id") int id) {
      return serviceWebScrappongControl.findUsersByid(id);
  }    
  
  @PutMapping(path = {"/users/{id}"}, headers = "Accept=application/json;charset=UTF-8")
  public String updateUsers(@RequestBody String strForm, @PathVariable("id") int id) {
      JSONObject obj = new JSONObject(strForm);
      String role = obj.getString("role");
      return serviceWebScrappongControl.updateUsers(id, role);
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