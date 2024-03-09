package com.codehustle.chatterpark.service;

import com.codehustle.chatterpark.entity.User;
import com.codehustle.chatterpark.security.SecurityUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtService {

    @Value("${auth.secret}")
    private String AUTH_SECRET;

    public String generateToken(){
        User user = SecurityUtils.getLoggedInUser();
        return Jwts
                .builder()
                .setSubject(user.getUsername())
                .setIssuer("Chatters_Park@api")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60)) // 1 min
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token){
        return extractClaim(token,Claims::getSubject);
    }

    public boolean isTokenValid(String token,UserDetails userDetails){
        return extractClaim(token,Claims::getSubject).equals(userDetails.getUsername()) && isTokenExpired(token);
    }

    private boolean isTokenExpired(String token){
        return extractClaim(token,Claims::getExpiration).after(new Date(System.currentTimeMillis()));
    }

    private <T> T extractClaim(String token, Function<Claims,T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey(){
        byte[] keys = Decoders.BASE64.decode(AUTH_SECRET);
        return Keys.hmacShaKeyFor(keys);
    }

}
