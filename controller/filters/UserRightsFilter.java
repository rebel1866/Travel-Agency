package by.epamtc.stanislavmelnikov.controller.filters;

import by.epamtc.stanislavmelnikov.logic.exception.LogicException;
import by.epamtc.stanislavmelnikov.logic.factory.LogicFactory;
import by.epamtc.stanislavmelnikov.logic.logicinterface.UserLogic;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRightsFilter implements Filter {
    private static final String GUEST = "Guest";
    private static final String jspSignInPath = "WEB-INF/jsp/sign-in-demand.jsp";
    private static final String notAllowedPath = "WEB-INF/jsp/not-allowed.jsp";
    private static final String authKey = "authorization";
    private static final String adminKey = "ADMIN";
    private static final String buttonAccess = "button_access";
    private static final String userRoleKey = "user_role";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession(true);
        Map<String, String> authorization = (Map<String, String>) session.getAttribute("authorization");
        String userRole = getUserRole(authorization, session);
        String command = request.getParameter("command").toUpperCase();
        LogicFactory logicFactory = LogicFactory.getInstance();
        UserLogic userLogic = logicFactory.getUserLogic();
        List<String> userRoleRights;
        try {
            userRoleRights = userLogic.findUserRights(userRole);
        } catch (LogicException e) {
            throw new ServletException("fail to get user rights - filter", e);
        }
        boolean isAllowed = userRoleRights.contains(command);
        filterRequest(isAllowed, userRole, authorization, session, filterChain, servletRequest, request, servletResponse);
    }

    public String getUserRole(Map<String, String> authorization, HttpSession session) {
        String userRole;
        if (authorization == null) {
            authorization = new HashMap<>();
            authorization.put(userRoleKey, GUEST);
            session.setAttribute(authKey, authorization);
            userRole = GUEST;
        } else {
            userRole = authorization.get(userRoleKey);
            authorization.remove(buttonAccess);
            session.setAttribute(authKey, authorization);
        }
        return userRole;
    }

    public void filterRequest(boolean isAllowed, String userRole, Map<String, String> authorization, HttpSession session,
                              FilterChain filterChain, ServletRequest request, HttpServletRequest servletRequest,
                              ServletResponse servletResponse) throws ServletException, IOException {
        if (isAllowed & userRole.toUpperCase().contains(adminKey)) {
            authorization.put(buttonAccess, "true");
            session.setAttribute(authKey, authorization);
            filterChain.doFilter(servletRequest, servletResponse);
        } else if (isAllowed & !userRole.toUpperCase().contains(adminKey)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else if (!isAllowed & userRole.equals(GUEST)) {
            request.getRequestDispatcher(jspSignInPath).forward(request, servletResponse);
        } else {
            request.getRequestDispatcher(notAllowedPath).forward(request, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
