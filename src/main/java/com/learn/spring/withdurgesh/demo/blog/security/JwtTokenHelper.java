package com.learn.spring.withdurgesh.demo.blog.security;

import io.jsonwebtoken.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

;import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenHelper {

    //thời gian hiệu lực của jwt
    public static final long JWT_TOKEN_VALIDITY= 5* 60 *60;

    //đây là khóa bí mật chỉ có mình biết
    public String secret= "jwtTokenKey";

    //generate token for user
    public String generateToken(UserDetails userDetails){
        Map<String, Object> claims=new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    // Tạo jwt từ user
    // while creating the token
    // 1. Define claims of the token, like Issuer, Expiration, Subject, and the ID
    // 2. Sign the JWT using the HS521 algorithm and secret key
    // 3. According to JWS Compact Serialization(https:// tools.ietf.org/html/draft-ietf-
    //    compaction of the JWT to a URL-safe string
    private String doGenerateToken(Map<String, Object> claims, String subject){
        Date now=new Date();
        Date expiration=new Date(now.getTime()+JWT_TOKEN_VALIDITY*100);
        return Jwts.builder()
                .setClaims(claims).setSubject(subject).setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    //retrieve username from jwt token
    public String getUsernameFromToken(String token){
        return getClaimFromToken(token, Claims::getSubject);
    }

    //retrieve expiration data from jwt token
    public Date getExpirationDateFromToken(String token){
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    //for retrieveing any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    //check if the token has expired
    private Boolean isTokenExpired(String token){
        final Date expiration= getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    //validate token
    public Boolean validateToken(String token, UserDetails userDetails){
        final String username=getUsernameFromToken(token);
        return (username.equalsIgnoreCase(userDetails.getUsername())&&!isTokenExpired(token));
    }

//    //Tạo ra jwt từ thông tin user
//    public String generateToken(UserDetails userDetails){
//        Date now=new Date();
//        Date expiryDate =new Date(now.getTime()+JWT_TOKEN_VALIDITY);
//        //Tạo chuỗi json web token từ user
//        return Jwts.builder()
//                .setSubject(userDetails.getUsername())
//                .setIssuedAt(now).setExpiration(expiryDate)
//                .signWith(SignatureAlgorithm.HS512,secret)
//                .compact();
//    }
//
//    //Lấy thông tin user từ jwt
//    public String getUserFromJWT(String token){
//        Claims claims=Jwts.parser().setSigningKey(secret)
//                .parseClaimsJws(token).getBody();
//        return claims.getSubject();
//    }
//
//    public boolean validateToken(String authToken){
//        try{
//            Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken);
//            return  true;
//        }catch (MalformedJwtException ex){
//            System.out.println("Invalid JWT token");
//        }catch (ExpiredJwtException ex){
//            System.out.println("Expired JWT token");
//        }catch (UnsupportedJwtException ex){
//            System.out.println("Unsupported JWT token");
//        }catch (IllegalArgumentException ex){
//            System.out.println("JWT claims string is empty");
//        }
//        return  false;
//    }
}
