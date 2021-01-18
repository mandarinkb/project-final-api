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

import com.projectfinalapi.config.JwtRequestFilter;
import com.projectfinalapi.config.JwtTokenUtil;
import com.projectfinalapi.function.ApiResponse;
import com.projectfinalapi.function.CheckError;
import com.projectfinalapi.function.DateTime;
import com.projectfinalapi.function.Log;
import com.projectfinalapi.model.Query;
import com.projectfinalapi.model.ScheduleDto;
import com.projectfinalapi.model.SwitchDatabaseDto;
import com.projectfinalapi.model.UserDto;
import com.projectfinalapi.model.WebDto;
import com.projectfinalapi.service.JwtUserDetailsService;
import com.projectfinalapi.service.ServiceWebScrappingControl;

@CrossOrigin(origins = "*", allowedHeaders = "*")

@RestController
@RequestMapping("/api")
public class RestApiController {
  @Autowired
  private JwtRequestFilter jwt;
  
  @Autowired
  private Log log;
	
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
 
  @GetMapping(path = {"/test"}, headers = "Accept=application/json;charset=UTF-8")
  public ResponseEntity<?> test() {
	  //log.createLog(dateTime.datetime() , dateTime.timestamp(), jwt.usernameFromToken, "read", "read users");  // เก็บ log
	  System.out.println("Hello world !");
	  return ResponseEntity.ok("Hello world !");
  }
  
  @PostMapping(path = {"/authenticate"}, headers = "Accept=application/json;charset=UTF-8")    
  public ResponseEntity<?> createAuthenticationToken(@RequestBody UserDto userDto) throws Exception {
	  authenticate(userDto.getUsername(), userDto.getPassword());
      final UserDetails userDetails = userDetailsService.loadUserByUsername(userDto.getUsername());
      final String token = jwtTokenUtil.generateToken(userDetails);      
      String response = apiResponse.authenticate(dateTime.timestamp(), 200, token);
      
      log.createLog(dateTime.datetime() , dateTime.timestamp(), userDto.getUsername(), "login", "Login");  // เก็บ log
      return new ResponseEntity<>(response, HttpStatus.OK);
  }
  
  @PostMapping(path = {"/logout"}, headers = "Accept=application/json;charset=UTF-8")
  public ResponseEntity<?> logOut() {
	  JSONObject json = new JSONObject();
	  json.put("message", "logout");
	  log.createLog(dateTime.datetime() , dateTime.timestamp(), jwt.usernameFromToken, "logout", "Logout");  // เก็บ log
	  return ResponseEntity.ok(json.toString());
  }
  
  //register
  @PostMapping(path = {"/users"}, headers = "Accept=application/json;charset=UTF-8")  
  public ResponseEntity<?> createUsers(@RequestBody UserDto userDto)  throws Exception{ //
      String userDb = q.findOneStrExcuteQuery("select USERNAME from USERS where USERNAME= '"+userDto.getUsername()+"' ");
      // check ว่ามีผู้ใช้นี้อยู่ในระบบหรือไม่
      if (userDb.equals("")) {
    	  
    	  log.createLog(dateTime.datetime() , dateTime.timestamp(), jwt.usernameFromToken, "create", "Create username "+userDto.getUsername());  // เก็บ log
    	  return new ResponseEntity<>(userDetailsService.save(userDto), HttpStatus.CREATED);
      } else {
      	String error = apiResponse.error(dateTime.timestamp(), 400, "Bad Request",
      			                        "That username is already taken. Try using a different name.", "/api/users");
      
      	log.createLog(dateTime.datetime() , dateTime.timestamp(), jwt.usernameFromToken, "error", "That username is already taken. Try using a different name.");  // เก็บ log
      	return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
      }
  }    

  @GetMapping(path = {"/users"}, headers = "Accept=application/json;charset=UTF-8")
  public ResponseEntity<?> readUsers() {
	  //log.createLog(dateTime.datetime() , dateTime.timestamp(), jwt.usernameFromToken, "read", "read users");  // เก็บ log
	  return ResponseEntity.ok(serviceWebScrappongControl.findUsers());
  }
  
  // get for role user
  @GetMapping(path = {"/user/{id}"}, headers = "Accept=application/json;charset=UTF-8")
  public ResponseEntity<?> readUserId(@PathVariable("id") int id) {
	  //log.createLog(dateTime.datetime() ,dateTime.timestamp(), jwt.usernameFromToken, "read", "read from id (for role user)");  // เก็บ log
	  return ResponseEntity.ok(serviceWebScrappongControl.findUserId(id));
  } 
  
  //get for role admin
  @GetMapping(path = {"/users/{id}"}, headers = "Accept=application/json;charset=UTF-8")
  public ResponseEntity<?> readUsersById(@PathVariable("id") int id) {
	  String serviceValue = serviceWebScrappongControl.findUsersById(id);
	  if(error.isFindUsersByIdError(serviceValue)) {
	      String error = apiResponse.error(dateTime.timestamp(), 400, "Bad Request",
                                           "Not found users", "/api/users/");
	      log.createLog(dateTime.datetime() ,dateTime.timestamp(), jwt.usernameFromToken, "error", "Not found users");  // เก็บ log
          return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	  }else {
		  //log.createLog(dateTime.datetime() ,dateTime.timestamp(), jwt.usernameFromToken, "read", "read from id (for role admin)");  // เก็บ log
		  return ResponseEntity.ok(serviceValue);
	  }
  }   

  @PutMapping(path = {"/users/{id}"}, headers = "Accept=application/json;charset=UTF-8")
  public ResponseEntity<?> updateUsers(@RequestBody UserDto userDto, @PathVariable("id") int id) {
	  log.createLog(dateTime.datetime() ,dateTime.timestamp(), jwt.usernameFromToken, "update", "Update username "+userDto.getUsername()+" role "+userDto.getRole()+" or chanege new password");  // เก็บ log
      return ResponseEntity.ok(serviceWebScrappongControl.updateUsers(id, userDto));
  }  
  
  @DeleteMapping(path = {"/users/{id}"}, headers = "Accept=application/json;charset=UTF-8")
  public ResponseEntity<?> deleteUsers(@PathVariable("id") int id){
	  String userDb = q.findOneStrExcuteQuery("select USERNAME from USERS where USER_ID="+id);
	  log.createLog(dateTime.datetime() ,dateTime.timestamp(), jwt.usernameFromToken, "delete", "Delete username "+userDb);  // เก็บ log
	  return new ResponseEntity<>(serviceWebScrappongControl.deleteUsers(id), HttpStatus.NO_CONTENT);
  }

  @PostMapping(path = {"/web"}, headers = "Accept=application/json;charset=UTF-8")
  public ResponseEntity<?> createWeb(@RequestBody WebDto web) {
	  log.createLog(dateTime.datetime() ,dateTime.timestamp(), jwt.usernameFromToken, "create", "Create web "+web.getWebName());  // เก็บ log
	  return new ResponseEntity<>(serviceWebScrappongControl.saveWeb(web), HttpStatus.CREATED);
  }   
  
  @GetMapping(path = {"/web"}, headers = "Accept=application/json;charset=UTF-8")
  public ResponseEntity<?> findWeb() {
	  //log.createLog(dateTime.datetime() ,dateTime.timestamp(), jwt.usernameFromToken, "read", "read web");  // เก็บ log
      return ResponseEntity.ok(serviceWebScrappongControl.findWeb());
  }
  
  @GetMapping(path = {"/web/{id}"}, headers = "Accept=application/json;charset=UTF-8")
  public ResponseEntity<?> findWebId(@PathVariable("id") int id) {
	  //log.createLog(dateTime.datetime() ,dateTime.timestamp(), jwt.usernameFromToken, "read", "read web from id");  // เก็บ log
	  return ResponseEntity.ok(serviceWebScrappongControl.findWebById(id));
  }
  
  @PutMapping(path = {"/web-status/{id}"}, headers = "Accept=application/json;charset=UTF-8")
  public ResponseEntity<?> updateWebStatus(@RequestBody WebDto web, @PathVariable("id") int id) {
	  String webNameDb = q.findOneStrExcuteQuery("select WEB_NAME from WEB where Web_ID="+id);
	  String strStatus = " is disable";
	  if(web.getWebStatus().equals("1")) {
		  strStatus = " is enable";
	  }
	  log.createLog(dateTime.datetime() ,dateTime.timestamp(), jwt.usernameFromToken, "update", "Update web status "+webNameDb+strStatus);  // เก็บ log
	  return ResponseEntity.ok(serviceWebScrappongControl.updateWebStatus(id, web.getWebStatus()));
  }

  @PutMapping(path = {"/web/{id}"}, headers = "Accept=application/json;charset=UTF-8")
  public ResponseEntity<?>  updateWeb(@RequestBody WebDto webDto, @PathVariable("id") int id) {
	  log.createLog(dateTime.datetime() ,dateTime.timestamp(), jwt.usernameFromToken, "update", "Update web "+webDto.getWebName());  // เก็บ log
	  return ResponseEntity.ok(serviceWebScrappongControl.updateWeb(id , webDto));
  }    

  @DeleteMapping(path = {"/web/{id}"}, headers = "Accept=application/json;charset=UTF-8")
  public ResponseEntity<?>  deleteWeb(@PathVariable("id") int id){
	  String webDb = q.findOneStrExcuteQuery("select WEB_NAME from WEB where Web_ID="+id);
	  log.createLog(dateTime.datetime() ,dateTime.timestamp(), jwt.usernameFromToken, "delete", "Delete web name "+webDb);  // เก็บ log
      return new ResponseEntity<>(serviceWebScrappongControl.deleteWeb(id), HttpStatus.NO_CONTENT);
  }

  @PostMapping(path = {"/schedule"}, headers = "Accept=application/json;charset=UTF-8")
  public ResponseEntity<?>  createSchedule(@RequestBody ScheduleDto scheduleDto) {
	  log.createLog(dateTime.datetime() ,dateTime.timestamp(), jwt.usernameFromToken, "create", "Create schedule "+scheduleDto.getScheduleName());  // เก็บ log
      return new ResponseEntity<>(serviceWebScrappongControl.saveSchedule(scheduleDto), HttpStatus.CREATED);
  }
  
  @GetMapping(path = {"/schedule"}, headers = "Accept=application/json;charset=UTF-8")
  public ResponseEntity<?>  readSchedule() {
	  //log.createLog(dateTime.datetime() ,dateTime.timestamp(), jwt.usernameFromToken, "read", "read schedule");  // เก็บ log
      return ResponseEntity.ok(serviceWebScrappongControl.findSchedule());
  }

  @GetMapping(path = {"/schedule/{id}"}, headers = "Accept=application/json;charset=UTF-8")
  public ResponseEntity<?>  readScheduleId(@PathVariable("id") int id) {
	  //log.createLog(dateTime.datetime() ,dateTime.timestamp(), jwt.usernameFromToken, "read", "read schedule from id");  // เก็บ log
	  return ResponseEntity.ok(serviceWebScrappongControl.findScheduleById(id));
  }

  @PutMapping(path = {"/schedule/{id}"}, headers = "Accept=application/json;charset=UTF-8")
  public ResponseEntity<?>  updateSchedule(@RequestBody ScheduleDto scheduleDto, @PathVariable("id") int id) {
	  log.createLog(dateTime.datetime() ,dateTime.timestamp(), jwt.usernameFromToken, "update", "Update schedule "+scheduleDto.getScheduleName());  // เก็บ log
	  return ResponseEntity.ok(serviceWebScrappongControl.updateSchedule(id, scheduleDto));
  }

  @DeleteMapping(path = {"/schedule/{id}"}, headers = "Accept=application/json;charset=UTF-8")
  public ResponseEntity<?>  deleteSchedule(@PathVariable("id") int id){
	  String scheduleDb = q.findOneStrExcuteQuery("select SCHEDULE_NAME from SCHEDULE where SCHEDULE_ID="+id);
	  log.createLog(dateTime.datetime() ,dateTime.timestamp(), jwt.usernameFromToken, "delete", "Delete schedule name "+scheduleDb);  // เก็บ log
      return new ResponseEntity<>(serviceWebScrappongControl.deleteSchedule(id), HttpStatus.NO_CONTENT);
  }
  
  @PostMapping(path = {"/switch-database"}, headers = "Accept=application/json;charset=UTF-8")
  public ResponseEntity<?>  createSwitchDatabase(@RequestBody SwitchDatabaseDto switchDatabaseDto) {
	  log.createLog(dateTime.datetime() ,dateTime.timestamp(), jwt.usernameFromToken, "create", "Create switch-database "+switchDatabaseDto.getDatabaseName());  // เก็บ log
	  return new ResponseEntity<>(serviceWebScrappongControl.saveSwitchDatabase(switchDatabaseDto), HttpStatus.CREATED);
  }
  
  @GetMapping(path = {"/switch-database"}, headers = "Accept=application/json;charset=UTF-8")
  public ResponseEntity<?>  readSwitchDatabase() {
	  //log.createLog(dateTime.datetime() ,dateTime.timestamp(), jwt.usernameFromToken, "read", "read switch-database");  // เก็บ log
      return ResponseEntity.ok(serviceWebScrappongControl.findSwitchDatabase());
  }

  @GetMapping(path = {"/switch-database/{id}"}, headers = "Accept=application/json;charset=UTF-8")
  public ResponseEntity<?>  readSwitchDatabaseId(@PathVariable("id") int id) {
	  //log.createLog(dateTime.datetime() ,dateTime.timestamp(), jwt.usernameFromToken, "read", "read switch-database from id");  // เก็บ log
	  return ResponseEntity.ok(serviceWebScrappongControl.findSwitchDatabaseleById(id));
  }

  @PutMapping(path = {"/switch-database-status/{id}"}, headers = "Accept=application/json;charset=UTF-8")
  public ResponseEntity<?> updateSwitchDatabaseStatus(@RequestBody SwitchDatabaseDto switchDatabaseDto, @PathVariable("id") int id) {
	  String swDb = q.findOneStrExcuteQuery("select DATABASE_NAME from SWITCH_DATABASE where DATABASE_ID="+id);
	  log.createLog(dateTime.datetime() ,dateTime.timestamp(), jwt.usernameFromToken, "update", "Update switch-database-status "+swDb+" is working");  // เก็บ log
	  return ResponseEntity.ok(serviceWebScrappongControl.updateSwitchDatabaseStatus(id, switchDatabaseDto.getDatabaseStatus()));
  }
  
  @PutMapping(path = {"/switch-database/{id}"}, headers = "Accept=application/json;charset=UTF-8")
  public ResponseEntity<?>  updateSwitchDatabase(@RequestBody SwitchDatabaseDto switchDatabaseDto, @PathVariable("id") int id) {
	  log.createLog(dateTime.datetime() ,dateTime.timestamp(), jwt.usernameFromToken, "update", "Update switch-database "+switchDatabaseDto.getDatabaseName());  // เก็บ log
      return ResponseEntity.ok(serviceWebScrappongControl.updateSwitchDatabase(id, switchDatabaseDto));
  }

  @DeleteMapping(path = {"/switch-database/{id}"}, headers = "Accept=application/json;charset=UTF-8")
  public ResponseEntity<?>  deleteSwitchDatabase(@PathVariable("id") int id){
	  String swDb = q.findOneStrExcuteQuery("select DATABASE_NAME from SWITCH_DATABASE where DATABASE_ID="+id);
	  log.createLog(dateTime.datetime() ,dateTime.timestamp(), jwt.usernameFromToken, "delete", "Delete switch-database name "+swDb);  // เก็บ log
      return new ResponseEntity<>(serviceWebScrappongControl.deleteSwitchDatabase(id), HttpStatus.NO_CONTENT);
  }

  @PostMapping(path = {"/log"}, headers = "Accept=application/json;charset=UTF-8")
  public ResponseEntity<?>  getLog(@RequestBody String bodyStr) {
	  JSONObject json = new JSONObject(bodyStr);
	  String datetime = json.getString("datetime");
	  return ResponseEntity.ok(serviceWebScrappongControl.listLog(datetime));
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