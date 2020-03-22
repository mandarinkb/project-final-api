package com.projectfinalapi.function;

import org.springframework.stereotype.Component;

@Component
public class DateTime {
    
    public String splitDateFromMobile(String date){
        String[] partsDate = date.split("T");
        String newDate = partsDate[0];
        return newDate;
    }
}
