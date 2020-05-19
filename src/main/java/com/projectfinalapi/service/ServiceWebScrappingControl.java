package com.projectfinalapi.service;

import com.projectfinalapi.model.ScheduleDto;
import com.projectfinalapi.model.SwitchDatabaseDto;
import com.projectfinalapi.model.UserDto;
import com.projectfinalapi.model.WebDto;

public interface ServiceWebScrappingControl {
    public String findUsers();
    public String findUserId(int id);
    public String findUsersById(int id); // for edit
    public String updateUsers(int id, UserDto user); 
    public String deleteUsers(int id);
    
    public String saveWeb(WebDto web); 
    public String findWeb();
    public String findWebById(int web_id);
    public String updateWebStatus(int web_id ,  String webStatus);
    public String updateWeb(int web_id ,WebDto web);
    public String deleteWeb(int web_id);
    
    public String saveSchedule(ScheduleDto schedule);
    public String findSchedule();
    public String findScheduleById(int id);
    public String updateSchedule(int id, ScheduleDto schedule);
    public String deleteSchedule(int schedule_id);
    
    public String saveSwitchDatabase(SwitchDatabaseDto switchDatabase);
    public String findSwitchDatabase();
    public String findSwitchDatabaseleById(int id);
    public String updateSwitchDatabaseStatus(int databaseId ,  String databaseStatus);
    public String updateSwitchDatabase(int id, SwitchDatabaseDto switchDatabase);
    public String deleteSwitchDatabase(int databaseId);
    
}
