package com.projectfinalapi.function;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

@Component
public class DateTime {
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
    private static final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    public String splitDateFromMobile(String date){
        String[] partsDate = date.split("T");
        String newDate = partsDate[0];
        return newDate;
    }
    
    public String timestamp() {
    	return ZonedDateTime.now().format(dtf);
    }
    
    public String datetime() {
    	return ZonedDateTime.now().format(df);
    }
}
