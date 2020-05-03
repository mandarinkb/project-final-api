package com.projectfinalapi;

import java.util.ArrayList;
import java.util.List;

public class Json {

	public static String changeKeyUpperCase (String inputStr) {
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
	
	
	
	public static void main(String args[]) {
		String str = "USER_NAME";
		Json obj = new Json();
		//obj.changeKeyUpperCase(str);
		//String s = "{\"match_phrase\": {\"category\": \"เครื่องใช้ไฟฟ้า อุปกรณ์ภายในบ้าน\"}},\n{\"match_phrase\": {\"category\": \"ผลิตภัณฑ์เพื่อสุขภาพ ความงาม\"}},";
		String s = "{\"match_phrase\": {\"category\": \"เครื่องใช้ไฟฟ้า อุปกรณ์ภายในบ้าน\"}}";
		System.out.println(s);
		System.out.println("last character: " +s.substring(s.length() - 1)); 
		
	    if (s != null && s.length() > 0 && s.charAt(s.length() - 1) == ',') {
	        s = s.substring(0, s.length() - 1);
	    }
	    System.out.println("after => "+ s);
	}
}
