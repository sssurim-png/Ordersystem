package com.example.post.common.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JwtTokenfilter extends GenericFilter {

    @Value("${jwt.secretKey}")
    private String st_secret_key;


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            HttpServletRequest req = (HttpServletRequest) request;
            String bearerToken = req.getHeader("Authorization");
            if (bearerToken == null) {
                chain.doFilter(request, response);

                return;
            }
            String token = bearerToken.substring(7);

            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(st_secret_key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();


            List<GrantedAuthority> authorities = new ArrayList<>(); //권한식별

            authorities.add(new SimpleGrantedAuthority("ROLE_" + claims.get("role")));
            Authentication authentication = new UsernamePasswordAuthenticationToken(claims.getSubject(), "", authorities); //인증객체 완성 //정상토큰(3)
            SecurityContextHolder.getContext().setAuthentication(authentication);//요청의 사용자를 로그인된 상태로 등록한다

        } catch (Exception e) {
            e.printStackTrace();
        }
        chain.doFilter(request,response);
    }
}