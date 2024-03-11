package com.demoProject.crp.SecurityConfig;

import com.demoProject.crp.SecurityConfig.Util.JwtUtils;
import com.demoProject.crp.UserDetailsService.UserDetailService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserDetailService userDetailsService;
    private final JwtUtils jwtUtils;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //fetch token from request
        var jwtTokenOptional = getTokenFromRequest(request);
        //Validate jwt Tokens from utils
        jwtTokenOptional.ifPresent(jwtToken -> {
            if(jwtUtils.validateToken(jwtToken)){
                //get username from token
                var usernameOptional = jwtUtils.getUsernameFromToken(jwtToken);

                usernameOptional.ifPresent(username -> {
                    //fetching details with the help of username
                    var userDetails = userDetailsService.loadUserByUsername(username);

                    // create authentication token
                    var authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    //set Authentication token to security context
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                });

            }
        });

        //else pass request to next filter
        filterChain.doFilter(request,response);
    }

    private Optional<String> getTokenFromRequest(HttpServletRequest request) {
        //Extract Authentication Header
        var authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        //Bearer<JWT Token>
        if(StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")){
            return Optional.of(authHeader.substring(7));
        }
        return Optional.empty();
    }
}
