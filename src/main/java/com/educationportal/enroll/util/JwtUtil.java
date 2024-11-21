package com.educationportal.enroll.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    private final String SECRET_KEY = "YRlIorqILmPuFqhZsW3B3NzcT7JQXx9HT9iM8SJM/U0=" ; // Same as Node.js secret

    // Validate the JWT token
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(SECRET_KEY.getBytes()).build().parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            System.out.println("Invalid JWT signature: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println("Invalid JWT token: " + e.getMessage());
            return false;
        }
    }

    // Extract claims from the JWT token
    public Claims extractClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(SECRET_KEY.getBytes()).build().parseClaimsJws(token).getBody();
    }
}
