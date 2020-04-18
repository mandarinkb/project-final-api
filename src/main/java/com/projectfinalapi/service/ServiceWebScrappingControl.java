package com.projectfinalapi.service;

public interface ServiceWebScrappingControl {
    public String findUsers();
    public String findUsersById(int id);
    //public String saveUsers(String username, String password, String role);
    public String updateUsers(int id, String role);    
    public String deleteUsers(int id);

	
    public String getWeb();
    public String findWebById(int web_id);
    public String saveWeb(String webName, String url, String type, String typeDetail, String webStatus, String season, String baseUrl, String detail);
    public String updateWebStatus(int id ,String webStatus);
    public String updateWeb(int web_id ,String webName, String url, String type, String typeDetail, String webStatus, String season, String baseUrl, String detail);
    public String deleteWeb(int web_id);
    public String getSchedule();
    public String findScheduleById(int id);
    public String saveSchedule(String scheduleName, String cronExpression, String functionName, String projectName, String detail);
    public String updateSchedule(int id, String scheduleName, String cronExpression, String functionName, String projectName, String detail);
    public String deleteSchedule(int schedule_id);
    

}
