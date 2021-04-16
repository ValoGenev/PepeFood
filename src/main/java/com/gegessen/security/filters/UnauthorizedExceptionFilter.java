package com.gegessen.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gegessen.exception.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UnauthorizedExceptionFilter extends OncePerRequestFilter {

    @Autowired
    private HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        System.out.println("UNAUTHORIZED FILTER CALLED");

        try{
            filterChain.doFilter(httpServletRequest,httpServletResponse);
        }
        catch (BadCredentialsException | UsernameNotFoundException e) {

            System.out.println("CATCHING EXCEPTIONS");
            e.printStackTrace();

            handlerExceptionResolver.resolveException(httpServletRequest, httpServletResponse, null, e);

            System.out.println(e.getMessage());
            httpServletResponse.resetBuffer();
            httpServletResponse.setStatus(401);
            httpServletResponse.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            httpServletResponse.getOutputStream();
            httpServletResponse.getOutputStream().print(new ObjectMapper().writeValueAsString(new ErrorMessage("Wrong username or password",401)));
            httpServletResponse.flushBuffer();

            //tova filter chain beshe zakomentirano
           // filterChain.doFilter(httpServletRequest, httpServletResponse);
        }
        catch (Exception e){
            e.printStackTrace();

            handlerExceptionResolver.resolveException(httpServletRequest, httpServletResponse, null, e);

            System.out.println(e.getMessage());
            httpServletResponse.resetBuffer();
            httpServletResponse.setStatus(500);
            httpServletResponse.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            httpServletResponse.getOutputStream();
            httpServletResponse.getOutputStream().print(new ObjectMapper().writeValueAsString(new ErrorMessage("Wrong username or password",401)));
            httpServletResponse.flushBuffer();
        }
    }
}
