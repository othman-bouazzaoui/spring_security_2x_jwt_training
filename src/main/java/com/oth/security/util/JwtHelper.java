package com.oth.security.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.oth.security.entities.RoleEntity;
import com.oth.security.entities.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.oth.security.util.SecurityConstants.*;

@Slf4j
@Component
public class JwtHelper {

    private static String secretKey;

    @Value("${security.secretKey:}")
    private void setSecretKey(String secret) {
        log.info("hello from secret setter = {}", secret);
        JwtHelper.secretKey = secret;
    }

    private JwtHelper() {

    }

    /**
     * get Tokens access and refresh
     * @param requestUrl
     * @param user
     * @return
     */
    public static Map<String, String> getTokens(String requestUrl, User user){

        String accessToken = generateAccessToken(requestUrl, user);
        String refreshToken = generateRefreshToken(requestUrl, user);

        log.info("accessToken : {}", accessToken);
        log.info("refreshToken : {}", refreshToken);

        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);

        return tokens;
    }

    /**
     * generate refresh Token => I use it to renew my access-token
     * to avoid sending username and password when access token expired
     * it's like (remember me :) ) but we can't access to app resources, is juste to renew access token
     * Expire after 1 Year
     * @param requestUrl
     * @param user
     * @return
     */
    public static String generateRefreshToken(String requestUrl, User user) {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, Integer.parseInt(EXPIRE_REFRESH_TOKEN.getValue()));

        return JWT.create().withSubject(user.getUsername())
                .withExpiresAt(new Date(calendar.getTimeInMillis()))
                .withIssuer(requestUrl)
                .sign(getAlgorithm());
    }

    /**
     * generate access Token
     * @param requestUrl
     * @param user
     * @return
     */
    public static String generateAccessToken(String requestUrl, User user) {
        return JWT.create().withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * Long.parseLong(EXPIRE_ACCESS_TOKEN.getValue())))
                .withIssuer(requestUrl)
                .withClaim("roles",
                        user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .sign(getAlgorithm());
    }

    /**
     * generate access Token
     * @param requestUrl
     * @param user
     * @return
     */
    public static String generateAccessToken(String requestUrl, UserEntity user) {
        return JWT.create().withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * Long.parseLong(EXPIRE_ACCESS_TOKEN.getValue())))
                .withIssuer(requestUrl)
                .withClaim("roles",
                        user.getRoles().stream().map(RoleEntity::getRoleName).toList())
                .sign(getAlgorithm());
    }

    public static Algorithm getAlgorithm() {
        log.info("secret key {}", secretKey);
        return Algorithm.HMAC256(secretKey);
    }
}
