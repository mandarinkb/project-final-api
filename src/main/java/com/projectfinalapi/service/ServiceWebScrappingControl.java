package com.projectfinalapi.service;

import com.projectfinalapi.model.ScheduleDto;
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
    public String saveSchedule(ScheduleDto schedule);
    public String findSchedule();
    public String findScheduleById(int id);
    public String updateSchedule(int id, ScheduleDto schedule);
    public String deleteSchedule(int schedule_id);
}
