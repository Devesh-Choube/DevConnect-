package com.DevConnect.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Component
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;
   private SecretKey getSecretKey() {
        byte[] getBytes=Base64.getDecoder().decode(secret);
        return Keys.hmacShaKeyFor(getBytes);

   }
    public String generateJwtToken(String username)
    {
        HashMap<String, Object> map = new HashMap<>();
        return Jwts.builder()
                .claims(map)
                .subject(username)
                .signWith(getSecretKey())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+1000*60*20))
                .compact();
    }

    public String extractUsername(String jwtToken) {
        return extractClaim(jwtToken, Claims::getSubject);

    }
    public <T> T extractClaim(String jwtToken, Function<Claims, T> claimsResolver) {
        final Claims claims= extractAllClaims(jwtToken);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String jwtToken) {
        return Jwts.parser().verifyWith(getSecretKey()).build().parseSignedClaims(jwtToken).getPayload();
    }


    public boolean validateToken(UserDetails details, String jwtToken) {
        final String username=extractUsername(jwtToken);
        return (username.equals(details.getUsername()) && !isTokenExpired(jwtToken));
    }

    private boolean isTokenExpired(String jwtToken) {
        return extractExpiration(jwtToken).before(new Date());
    }

    private Date extractExpiration(String jwtToken) {
        return extractClaim(jwtToken, Claims::getExpiration);
    }
}
