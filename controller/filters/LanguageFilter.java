package by.epamtc.stanislavmelnikov.controller.filters;

import by.epamtc.stanislavmelnikov.logic.validation.Validation;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LanguageFilter implements Filter {
    private static final String switchLangKey = "switch_lang";
    private static final int substringIndex = 28;
    private static final String langKey = "lang";
    private static final String commandPath = "executor?";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String lang = servletRequest.getParameter(switchLangKey);
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession(true);
        if (session.getAttribute(langKey) == null) {
            session.setAttribute(langKey, "ru");
        }
        if (lang == null) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        if (!Validation.isCorrectLang(lang)) {
            throw new ServletException("Incorrect lang value");
        }
        session.setAttribute(langKey, lang);
        String queryString = request.getQueryString().substring(substringIndex);
        if (!request.getQueryString().contains("jsp")) {
            queryString = commandPath + queryString;
        }
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.sendRedirect(queryString);
    }

    @Override
    public void destroy() {

    }
}
