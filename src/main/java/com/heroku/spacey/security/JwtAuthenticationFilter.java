package com.heroku.spacey.security;

import com.heroku.spacey.models.UserInfoModel;
import com.heroku.spacey.services.UserInfoService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserInfoService userInfoService;

    private final String TOKEN_PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(header != null && header.startsWith(TOKEN_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }
        String authToken = header.replace(TOKEN_PREFIX, "");
//        try{
//            String userId = jwtTokenProvider.getUserIdFromToken(authToken);
//        } catch (ExpiredJwtException e){
//            logger.warn("Token has expired", e);
//        } catch (SignatureException e){
//            logger.error("Authentication failed");
//        } catch (Exception e){
//            logger.error("Error", e);
//        }

        UserDetails userDetails = userInfoService.loadUserByUsername(jwtTokenProvider.getUsernameFromToken(authToken));
        if (userDetails != null && SecurityContextHolder.getContext().getAuthentication() == null){

            if (jwtTokenProvider.validateToken(authToken)) {
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails,
                        null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        filterChain.doFilter(request, response);
    }
}
