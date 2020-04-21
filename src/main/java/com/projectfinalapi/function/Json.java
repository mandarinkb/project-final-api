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
}
