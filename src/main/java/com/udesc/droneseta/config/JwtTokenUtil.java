package com.udesc.droneseta.config;

import com.udesc.droneseta.model.Customer;
import java.io.Serializable;

import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import java.util.Base64;
import javax.crypto.spec.SecretKeySpec;

@Component
public class JwtTokenUtil implements Serializable {

    private static final long serialVersionUID = -2550185165626007488L;
    public static final long JWT_TOKEN_VALIDITY = 60 * 60 * 1000; // 1 hora

    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(Customer user) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowInMillis = System.currentTimeMillis();
        Date now = new Date(nowInMillis);

        byte[] base64 = Base64.getEncoder().encode(secret.getBytes());

        SecretKeySpec signingKey = new SecretKeySpec(base64, signatureAlgorithm.getJcaName());

        JwtBuilder jwtBuilder = Jwts.builder()
                .setIssuedAt(now)
                .setSubject(String.valueOf(user.getId()))
                .signWith(signingKey, signatureAlgorithm);

        jwtBuilder.setExpiration(new Date(nowInMillis + JWT_TOKEN_VALIDITY));

        return jwtBuilder.compact();
    }

    public boolean validateAccessToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(Base64.getEncoder().encode(secret.getBytes()))
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException ex) {
            System.out.println("JWT expired");
        } catch (IllegalArgumentException ex) {
            System.out.println("Token is null, empty or only whitespace");
        } catch (MalformedJwtException ex) {
            System.out.println("JWT is invalid");
        } catch (UnsupportedJwtException ex) {
            System.out.println("JWT is not supported");
        } catch (SignatureException ex) {
            System.out.println(ex.getMessage());
        }

        return false;
    }

    public String getSubject(String token) {
        return parseClaims(token).getSubject();
    }

    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Base64.getEncoder().encode(secret.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}