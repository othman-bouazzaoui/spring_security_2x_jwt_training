package com.oth.springsecurity.springsecurity.security.entities;

import lombok.Getter;

@Getter
public enum Gender {
    MALE(1,"MAN"), FEMALE(2,"WOMEN");
	 
    private final int code;
    private final String value;
     
    Gender(int code,String value) {
        this.code = code;
        this.value = value;
    }
   
}
