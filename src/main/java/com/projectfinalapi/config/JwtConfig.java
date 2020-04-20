package com.projectfinalapi.config;

import org.springframework.stereotype.Component;

@Component
public class JwtConfig {
	public static final long jwtExpires = 24 * 60 * 60 * 1000; // 1 day
}
