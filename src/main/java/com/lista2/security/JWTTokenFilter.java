package com.lista2.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * Filter class for JWT token authentication.
 */
public class JWTTokenFilter extends OncePerRequestFilter {
    private String key;

    public JWTTokenFilter(String key) {
        this.key = key;
    }

    /**
     * Performs token authentication for each incoming request.
     *
     * @param request     The HTTP request.
     * @param response    The HTTP response.
     * @param filterChain The filter chain.
     * @throws ServletException If a servlet-related error occurs.
     * @throws IOException      If an I/O error occurs.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String headerAuth = request.getHeader(HttpHeaders.AUTHORIZATION); // header z zapytania
        if (headerAuth != null && headerAuth.startsWith("Bearer ")) { //czy zostal zalaczony i odpowiednio sie zaczyna
            String token = headerAuth.split(" ")[1]; //odczytujemy token
            Claims claims = Jwts.parser() //odczytujemy zawartosc tokena
                    .setSigningKey(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload(); //pobieranie info zapisanych w tokenie
            String userID = String.valueOf(claims.get("id")); // to bedzie zapisane w tokenie przy tworzeniu
            String role = String.valueOf(claims.get("role"));

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    userID, null, List.of(new SimpleGrantedAuthority(role))
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        filterChain.doFilter(request, response);
    }
}
