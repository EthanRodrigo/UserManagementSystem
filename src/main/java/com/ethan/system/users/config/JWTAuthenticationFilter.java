package com.ethan.system.users.config;

import com.ethan.system.users.service.JWTService;
import com.ethan.system.users.service.UserService;
import com.ethan.system.users.utils.GlobalErrorHandler;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    private final JWTService jwtService;
    private final UserService userService;
    private final GlobalErrorHandler errorHandler;
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
    throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        try{
            jwt = authHeader.substring(7); // jwt token begins after 7 characters
            username = jwtService.extractUsername(jwt);

            // if the user isn't authenticated
            if(!username.isEmpty() && SecurityContextHolder.getContext().getAuthentication() == null){
                UserDetails userDetails = userService.userDetailsService().loadUserByUsername(username);

                if(jwtService.isTokenValid(jwt, userDetails)){
                    SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

                    // A token that has the username and password
                    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()
                    );
                    token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    securityContext.setAuthentication(token);
                    SecurityContextHolder.setContext(securityContext);
                }
            }
            filterChain.doFilter(request, response);
        }catch (ExpiredJwtException exception){
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().println(exception.getMessage());
            response.setContentType("application/json");
        }
    }
}
