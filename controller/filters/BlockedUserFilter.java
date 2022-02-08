package by.epamtc.stanislavmelnikov.controller.filters;

import by.epamtc.stanislavmelnikov.logic.exception.LogicException;
import by.epamtc.stanislavmelnikov.logic.factory.LogicFactory;
import by.epamtc.stanislavmelnikov.logic.logicinterface.UserLogic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BlockedUserFilter implements Filter {
    private static final String blocked = "BLOCKED";
    private static final String guestRole = "Guest";
    private static final String userRoleKey = "user_role";
    private static final String jspPath = "WEB-INF/jsp/blocked-message.jsp";
    private static final String statusNameKey = "status_name";
    private static final String commandKey = "command";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession(true);
        Map<String, String> authorization = (Map<String, String>) session.getAttribute("authorization");
        if (authorization.get(userRoleKey).equals(guestRole)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        Logger logger = LogManager.getLogger(BlockedUserFilter.class);
        int userId = Integer.parseInt(authorization.get("user_id"));
        LogicFactory factory = LogicFactory.getInstance();
        UserLogic userLogic = factory.getUserLogic();
        Map<String, String> userStatus;
        try {
            userStatus = userLogic.findUserStatus(userId);
        } catch (LogicException e) {
            logger.error(e.getMessage(), e);
            throw new ServletException(e.getMessage(), e);
        }
        String statusName = userStatus.get(statusNameKey);
        if (!statusName.equals(blocked)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            filterRequest(request, filterChain, servletResponse, userStatus);
        }

    }

    public void filterRequest(HttpServletRequest request, FilterChain filterChain, ServletResponse servletResponse,
                               Map<String, String> userStatus) throws ServletException, IOException {
        String command = request.getParameter(commandKey);
        String delim =",";
        List<String> rightsList = Arrays.stream(userStatus.get("status_rights").split(delim)).
                map(String::trim).collect(Collectors.toList());
        if (!rightsList.contains(command.toUpperCase())) {
            filterChain.doFilter(request, servletResponse);
        } else {
            request.getRequestDispatcher(jspPath).forward(request, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
