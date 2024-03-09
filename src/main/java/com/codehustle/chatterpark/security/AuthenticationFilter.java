package com.codehustle.chatterpark.security;

import com.codehustle.chatterpark.entity.User;
import com.codehustle.chatterpark.service.JwtService;
import com.codehustle.chatterpark.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }
        final String authToken = authHeader.substring(7);
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            final String username = jwtService.extractUsername(authToken);
            if(username != null && authentication == null){
                UserDetails userDetails = userService.loadUserByUsername(username);
                if (userDetails != null){
                    if(jwtService.isTokenValid(authToken,userDetails)){
                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                null
                        );
                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.clearContext();
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }else{
                        logger.info("Invalid JWT");
                        response.sendError(HttpStatus.UNAUTHORIZED.value(),"Unauthorized");
                    }
                }
            }
            filterChain.doFilter(request,response);
        }catch (Exception e){
            if (e instanceof ExpiredJwtException){
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.getWriter().write("Session Expired");
            }else{
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
            }
        }
    }
}
