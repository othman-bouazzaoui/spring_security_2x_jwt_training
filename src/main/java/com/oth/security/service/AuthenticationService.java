package com.oth.security.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AuthenticationService {

	void generateNewAccessToken(HttpServletRequest request, HttpServletResponse response);
}
