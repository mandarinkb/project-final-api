package com.projectfinalapi.function;
import org.springframework.stereotype.Component;

@Component
public class CheckError {

	public boolean isServiceError(String value) {
        String firstStr = value.substring(0, 1);
        String str = "{";
        //ถ้าตัวอักษรแรกเป็น {  แสดงว่ามี error
        if(firstStr.equals(str)) {
        	return true;
        }else {
        	return false; 
        }
	}
	
	public boolean isFindUsersByIdError(String value) {
		String str = "{}";
        if(value.equals(str)) {
        	return true;
        }else {
        	return false; 
        }
	}
}
