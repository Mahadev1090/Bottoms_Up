package com.ins.web.security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Component
public class JwtService {
    private static final String SECERET = "!@#$FDGSDFGSGSGSGSHSHSHSSHGFFDSGSFGSSGHSDFSDFSFSFSFSDFSFSFSF";
	private static final Logger logger = LogManager.getLogger(JwtService.class);

    public String generateToken(String userName){
		logger.log(Level.INFO, "From JwtService class -> START -> (JwtService)-> (generateToken)");
        Map<String, Objects> claims = new HashMap<>();
		logger.log(Level.INFO, "From JwtService class -> END -> (JwtService)-> (generateToken)");
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    private Key getSignKey() {
		logger.log(Level.INFO, "From JwtService class -> START -> (JwtService)-> (getSignKey)");
        byte[] keyBytes = Decoders.BASE64.decode(SECERET);
		logger.log(Level.INFO, "From JwtService class -> END -> (JwtService)-> (getSignKey)");
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public String extractUserName(String token){
		logger.log(Level.INFO, "From JwtService class -> START-END -> (JwtService)-> (extractUserName)");
        return extractClaim(token,Claims::getSubject);
    }

    public Date extractExpiration(String token){
		logger.log(Level.INFO, "From JwtService class -> START-END -> (JwtService)-> (extractExpiration)");
        return extractClaim(token,Claims::getExpiration);
    }
    private <T> T extractClaim(String token, Function<Claims,T> claimResolver) {
		logger.log(Level.INFO, "From JwtService class -> START-END -> (JwtService)-> (extractClaim)");
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token){
		logger.log(Level.INFO, "From JwtService class -> START-END -> (JwtService)-> (extractAllClaims)");
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    private Boolean isTokenExpired(String token){
		logger.log(Level.INFO, "From JwtService class -> START-END -> (JwtService)-> (isTokenExpired)");
        return extractExpiration(token).before(new Date());
    }
    public Boolean validateToken(String token, UserDetails userDetails){
		logger.log(Level.INFO, "From JwtService class -> START-END -> (JwtService)-> (validateToken)");
        final String userName= extractUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
