package com.learn.spring.withdurgesh.demo.blog.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenHelper jwtTokenHelper;

//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        try{
//            String requestToken=request.getHeader("Authorization");
//            System.out.println("token: "+requestToken);
//            String username=null;
//            String token=null;
//            if(requestToken!=null && requestToken.startsWith("Bearer")){
//                token = requestToken.substring(7);
//
//                //Lấy thông tin user
//                username=this.jwtTokenHelper.getUserFromJWT(token);
//                System.out.println("username: "+username);
//                if(username!=null&& SecurityContextHolder.getContext().getAuthentication()==null){
//                    UserDetails userDetails=this.userDetailsService.loadUserByUsername(username);
//                    if(userDetails!=null){
//                        //Nếu người dùng hợp lê, set thông tin
//                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken= new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
//                        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//
//                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//                    }
//                }
//            }
//        }catch (Exception ex){
//            System.out.println("failed on set user authentication" + ex.getMessage());
//        }
//        filterChain.doFilter(request,response);
//    }



    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {


        //1. get token

        String requestToken=request.getHeader("Authorization");

        System.out.println("token: "+requestToken);
        //Bearer 23525sdsg

        String username=null;

        String token=null;

        if (requestToken != null && requestToken.startsWith("Bearer ")) {
            token = requestToken.substring(7);

            try {
                username = this.jwtTokenHelper.getUsernameFromToken(token);
            } catch (IllegalArgumentException e) {
                System.out.println("Không thể lấy Jwt token");
            } catch (ExpiredJwtException e) {
                System.out.println("Jwt token đã hết hạn");
            } catch (MalformedJwtException e) {
                System.out.println("Jwt không hợp lệ");
            }
        } else {
            System.out.print("Jwt token không bắt đầu bằng Bearer");
        }

        // once we get the token, now validate

        if(username!=null&& SecurityContextHolder.getContext().getAuthentication()==null){

            UserDetails user= this.userDetailsService.loadUserByUsername(username);
            if(this.jwtTokenHelper.validateToken(token, user)){

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken= new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));


                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }else{
                System.out.println("Invalid jwt token");
            }
        }else{
                System.out.println("username is null or context is not null");
        }

        filterChain.doFilter(request, response);

    }
}
