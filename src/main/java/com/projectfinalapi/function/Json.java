package com.projectfinalapi.function;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class Json {
	public String changeKeyUpperCase (String inputStr) {
        String[] splitStr = inputStr.toLowerCase().split("_");
        int index = 0;
        List<String> listStr = new ArrayList<>();
        for (String split : splitStr) {
        	if(index > 0) {
        		split = split.substring(0, 1).toUpperCase() + split.substring(1);
        	}
        	index++;
        	listStr.add(split);
        }  
        String newStr = ""; 
        for(String str:listStr) {
        	newStr = newStr.concat(str);
        }
        return newStr;
	}	
	public String webNameJson(String[] webName) {
		String str = "";
		if(webName.length == 1) {
			for(String s : webName) {
				str = str+"{\"match_phrase\": {\"webName\": \""+s+"\"}}";
			}
		}
		else if(webName.length > 1){
			for(String s : webName) {
				str = str+"{\"match_phrase\": {\"webName\": \""+s+"\"}},";
			}
		}
		// ตัดตัวสุดท้ายออกถ้าเป็น ,
	    if (str != null && str.length() > 0 && str.charAt(str.length() - 1) == ',') {
	    	str = str.substring(0, str.length() - 1);
	    }
		
		return str;
	}
}
