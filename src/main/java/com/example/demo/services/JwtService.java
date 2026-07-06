package com.example.demo.services;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {
    @Value("${spring.jwt.secret}")
    private String secret;

    public String generateToken(String email) {
        final long tokenExpiration = 86400;// 1 day
        return Jwts.builder()
                .subject(email)/*唯一标识*/
                .issuedAt(new Date())/*时间戳*/
                /*过期时间*/
                /*使用的是毫秒*/
                .expiration(new Date(System.currentTimeMillis() + 1000 * tokenExpiration))
                /*密钥签名*/
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }

    public boolean validateToken(String token) {
        try{
            var claims = Jwts.parser()
                    //指定使用的密钥
                    .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                    //构建器
                    .build()
                    //解析token
                    .parseSignedClaims(token)
                    //负载
                    .getPayload();
            return claims.getExpiration().after(new Date());
        }catch (JwtException e){
            return false;
        }
    }

}
