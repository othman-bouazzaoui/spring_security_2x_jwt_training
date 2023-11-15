package com.oth.security.util;

import java.util.Arrays;
import java.util.List;

public enum SecurityConstants {
	RENEW_TOKENS("/api/v1/tokens/new-access-token"),
	LOGIN("/login"),
	AUTHORIZATION("Authorization"),
	PREFIX("Bearer "),
	EXPIRE_ACCESS_TOKEN("5"),
	EXPIRE_REFRESH_TOKEN("1");

	private final String value;

	private SecurityConstants(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	public static List<String> getAuthenticatePaths() {
		return Arrays.asList(RENEW_TOKENS.value, LOGIN.value);
	}

	public static String[] getAuthorizedPaths() {
		return new String[] { LOGIN.value, RENEW_TOKENS.value, "/h2-console/**" };
	}
}
