package com.chaton.service.exception;

/*
 *@author Ovuefe
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class RestAuthenticationEntryPoint extends CustomFilterResponse implements AuthenticationEntryPoint {

     @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {

             new CustomFilterResponse("Unauthorized",httpServletResponse);
//             httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Unauthorized");


    }



}
