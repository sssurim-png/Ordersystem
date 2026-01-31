package com.example.post.common.exception;


import com.example.post.common.dto.CommonErrorDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

@Component
public class JwtAuthenticationHandler implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    public JwtAuthenticationHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        //    startline + header조립
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);//401상태코드세팅
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        CommonErrorDto dto= CommonErrorDto.builder()
                .status_code(401)
                .error_message("token이 없거나 유효하지 않습니다")
                .build();
//        ObjectMapper objectMapper =new ObjectMapper();          //원래 싱글톤으로 되어 잇어서 주입받기만하면 되긴함//사용자마다 만드니까 이렇게 X, 싱글톤 만들어라//내가 만든 클래스는 @Bean
        String data =objectMapper.writeValueAsString(dto); //write해주기
        PrintWriter printWriter = response.getWriter(); //문법자체가 중요하지는 않다 여기서만 써ㅓ
        printWriter.write(data);
        printWriter.flush();
    }
}
