package com.ethan.system.users.service.implementation;

import com.ethan.system.users.service.JWTService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JWTServiceImpl implements JWTService{
    @Value("${jwt.token.secret}")
    private String SECRET_KEY;

    /* Returns a generated jwt token
    * */
    public String generateToken(UserDetails userDetails){
        return Jwts.builder()
                .setSubject(userDetails.getUsername())  // the current principal (authenticated user)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (1000 * 60) * 15)) // expiration for 15 minutes
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)  // sign with SHA-256
                .compact();
    }
    public String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails){
        return Jwts.builder()
                .setClaims(extraClaims) // set the JWT payload to be a JSON Claims instance with name/values pairs
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (1000 * 60) * 60 * 24)) // a whole day
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    /* Return a generic type claim
    * */
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    /*Creating the signing key for jwt with a byte stream
    * */
    private Key getSigningKey(){
        byte[] key = Decoders.BASE64.decode(SECRET_KEY); // a byte stream for the key
        return Keys.hmacShaKeyFor(key);
    }
    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public Boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public Boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }
}