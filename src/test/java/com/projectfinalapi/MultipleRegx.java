package com.projectfinalapi;

public class MultipleRegx {

	String regx(String strInput) {
        String[] arrOfStr = strInput.split(" "); 
        String regxJson = "";
        if(arrOfStr.length == 1) {
            for (String name : arrOfStr) {
            	if(!name.isEmpty()) {
            		regxJson = regxJson+"{\"regexp\": {\"name\": {\"value\": \"(.*)"+name+"(.*)\"}}}";
            	}
            }
        }else if(arrOfStr.length > 1) {
            for (String name : arrOfStr) {
            	if(!name.isEmpty()) {
            		regxJson = regxJson+"{\"regexp\": {\"name\": {\"value\": \"(.*)"+name+"(.*)\"}}},";
            	}
            }
        }
		// ตัดตัวสุดท้ายออกถ้าเป็น ,
	    if (regxJson != null && regxJson.length() > 0 && regxJson.charAt(regxJson.length() - 1) == ',') {
	    	regxJson = regxJson.substring(0, regxJson.length() - 1);
	    }
		return regxJson;
	}
	
	public static void main(String args[]) {
		MultipleRegx r = new MultipleRegx();
		String s = "ทีวี 4k";
		r.regx(s);
	}
	
}
