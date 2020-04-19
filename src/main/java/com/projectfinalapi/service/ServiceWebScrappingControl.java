package com.projectfinalapi.service;

import com.projectfinalapi.model.WebDto;

public interface ServiceWebScrappingControl {
    public String findUsers();
    public String findUsersById(int id);
    public String updateUsers(int id, String role);    
    public String deleteUsers(int id);
    public String saveWeb(WebDto web); 
    public String findWeb();
    public String findWebById(int web_id);
    public String updateWebStatus(int web_id ,  String webStatus);
    public String updateWeb(int web_id ,WebDto web);
    public String deleteWeb(int web_id);
    
    public String getSchedule();
    public String findScheduleById(int id);
    public String saveSchedule(String scheduleName, String cronExpression, String functionName, String projectName, String detail);
    public String updateSchedule(int id, String scheduleName, String cronExpression, String functionName, String projectName, String detail);
    public String deleteSchedule(int schedule_id);
    

}
