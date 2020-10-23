package com.spring.jwt;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class JwtUtils {
	 public static Date getExpirationTime(Long expireHours) {
	        Date now = new Date();
	        Long expireInMilis = TimeUnit.HOURS.toMillis(expireHours);
	        return new Date(expireInMilis + now.getTime());
	    }

}
