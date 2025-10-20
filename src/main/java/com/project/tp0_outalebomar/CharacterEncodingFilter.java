package com.project.tp0_outalebomar;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter("/*") // doublé par la déclaration web.xml, OK
public class CharacterEncodingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (request.getCharacterEncoding() == null) {
            request.setCharacterEncoding("UTF-8");
        }
        // Pour forcer aussi la réponse:
        // response.setCharacterEncoding("UTF-8");
        chain.doFilter(request, response);
    }
}

