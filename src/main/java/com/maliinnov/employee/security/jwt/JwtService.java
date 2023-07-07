package com.maliinnov.employee.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.maliinnov.employee.exception.TokenExpiredException;
import com.maliinnov.employee.security.services.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.function.Function;

import static com.maliinnov.employee.security.SecurityConstants.*;


@Service
public class JwtService {

    public String extractUsername(String token) {
        return extractClaim(token, Claim::asString);
    }

    public <T> T extractClaim(String token, Function<Claim, T> claimsResolver) {
        final DecodedJWT decodedJWT = JWT.decode(token);
        final Claim claim = decodedJWT.getClaim("sub");
        return claimsResolver.apply(claim);
    }

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

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        final DecodedJWT decodedJWT = JWT.decode(token);
        final Date expirationDate = decodedJWT.getExpiresAt();

        if (expirationDate != null && expirationDate.before(new Date())) {
            throw new TokenExpiredException("Le Token a expiré");
        }

        return false;
    }
}

