package com.oth.springsecurity.springsecurity.security.util;

import java.util.Arrays;
import java.util.List;

public class SecurityUtil {

    public static final String SECRET = "oth.bouazzaoui@gmail.com";

    public static final String RENEW_TOKENS = "/renewtokens";

    public static final String LOGIN = "/login";

    public static final List<String> PATHS_AUTH = Arrays.asList(RENEW_TOKENS, LOGIN);

    public static final String[] AUTHORIZED_PATHS = { LOGIN, RENEW_TOKENS, "/h2-console/**" };

    public static final String AUTHORIZATION = "Authorization";

    public static final String PREFIX = "Bearer ";

    //One Minute
    public static final Long EXPIRE_ACCES_TOKEN = 5L;

    //One Year
    public static final int EXPIRE_REFRESH_TOKEN = 1;

}
