package com.oth.springsecurity.springsecurity.security.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class JwtTokens {

    Logger LOG = LoggerFactory.getLogger(JwtTokens.class);

    private Algorithm algorithm = Algorithm.HMAC256(SecurityUtil.SECRET);

    public Map<String, String> getTokens(HttpServletRequest request, User user){

        String accesToken = getAccessToken(request, user);

        String refreshToken = getRefreshToken(request, user);

        LOG.info("accesToken : {}",accesToken);
        LOG.info("refreshToken : {}",refreshToken);

        Map<String, String> tokens = new HashMap<>();
        tokens.put("accesToken", accesToken);
        tokens.put("refreshToken", refreshToken);

        return tokens;
    }

    private String getRefreshToken(HttpServletRequest request, User user) {

        /*
         * Refresh Token : je l'utilise pour renouveler mon acces-token en cas d'expiration
         * pour eviter de ne pas envoyer le username et le mot de passe à chaque fois
         * C'est presque le même comportement de "remembre me"
         * Sauf qu'avec le refresh-token on pourront que renouvler le acces token et non faire accéder à des ressources sécurisés
         * Expire after 1 Year
         */

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, +1);

        return JWT.create().withSubject(user.getUsername())
                .withExpiresAt(new Date(calendar.getTimeInMillis()))
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);
    }

    private String getAccessToken(HttpServletRequest request, User user) {
        /* Access Token */
        return JWT.create().withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * SecurityUtil.EXPIRE_ACCES_TOKEN))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles",
                        user.getAuthorities().stream().map(ga -> ga.getAuthority()).collect(Collectors.toList()))
                .sign(algorithm);
    }
}
