package org.data.springbootgrpcserver.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Component;


@Component
public class CustomSecurityContextRepository implements SecurityContextRepository {

    @Override
    public SecurityContext loadContext(HttpRequestResponseHolder requestResponseHolder) {

        System.out.println("CustomSecurityContextRepository loadContext sessionId----->"+requestResponseHolder.getRequest().getSession().getId());

        HttpServletRequest request = requestResponseHolder.getRequest();
        HttpSession session = request.getSession();

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(new CustomAuthentication());
        return context;
    }

    @Override
    public void saveContext(SecurityContext context, HttpServletRequest request, HttpServletResponse response) {

        System.out.println("CustomSecurityContextRepository saveContext");

    }

    @Override
    public boolean containsContext(HttpServletRequest request) {

        System.out.println("CustomSecurityContextRepository containsContext");
        return true;
    }
}
