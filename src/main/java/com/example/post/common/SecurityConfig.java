package com.example.post.common;

import com.example.post.common.handler.JwtAuthenticationHandler;
import jakarta.servlet.http.HttpServlet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import software.amazon.awssdk.services.s3.model.CORSConfiguration;

import java.util.Arrays;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {//체인설정, 쓸객체
    private JwtTokenfilter jwtTokenfilter;
    private JwtAuthenticationHandler jwtAuthenticationHandler;

    public SecurityConfig(JwtTokenfilter jwtTokenfilter, JwtAuthenticationHandler jwtAuthenticationHandler) {
        this.jwtTokenfilter = jwtTokenfilter;
        this.jwtAuthenticationHandler = jwtAuthenticationHandler;
    }


    @Bean //필터를위한 객체
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .cors(c -> c.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)//클래스명::메서드명
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(a -> a.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtTokenfilter, UsernamePasswordAuthenticationFilter.class)// 토큰 검증작업 수행
                .exceptionHandling(e-> e.authenticationEntryPoint(jwtAuthenticationHandler)) //핸들러?
                .authorizeHttpRequests(a -> a.requestMatchers("/member/create", "/member/doLogin").permitAll().anyRequest().authenticated())//제외하는 부분
                .build();
    }

    @Bean
    public PasswordEncoder pwEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();//주입받아서 쓸거다
    }

    public CorsConfigurationSource corsConfigurationSource(){ //cors문제
        CorsConfiguration configuration =new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000","https://")); //제일 중요한 설정//도메인 설정(변경)
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;

}
}