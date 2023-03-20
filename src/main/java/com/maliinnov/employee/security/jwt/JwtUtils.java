package com.maliinnov.employee.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.maliinnov.employee.security.services.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.maliinnov.employee.security.SecurityConstants.*;


@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);


    public String generateJwtToken(Authentication authentication) {
        String username = ((UserDetailsImpl) authentication.getPrincipal()).getUsername();

        Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET_KEY);
        return JWT.create()
                .withIssuer(GET_COMPANY_LLC)
                .withAudience(GET_ARRAYS_ADMINISTRATION)
                .withSubject(username)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(algorithm);
    }

    public String getUserNameFromJwtToken(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET_KEY);
            JWT.require(algorithm).build().verify(authToken);
            return true;
        } catch (JWTVerificationException e) {
            logger.error("Signature de token invalide: {}", e.getMessage());
        }
        return false;
    }
}
