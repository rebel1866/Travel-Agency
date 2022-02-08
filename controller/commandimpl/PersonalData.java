package by.epamtc.stanislavmelnikov.controller.commandimpl;

import by.epamtc.stanislavmelnikov.controller.commandinterface.Command;
import by.epamtc.stanislavmelnikov.entity.Order;
import by.epamtc.stanislavmelnikov.entity.User;
import by.epamtc.stanislavmelnikov.logic.exception.LogicException;
import by.epamtc.stanislavmelnikov.logic.factory.LogicFactory;
import by.epamtc.stanislavmelnikov.logic.logicinterface.OrderLogic;
import by.epamtc.stanislavmelnikov.logic.logicinterface.UserLogic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonalData implements Command {
    private static final String jspPath = "WEB-INF/jsp/user-info.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        Map<String, String> authorization = (Map<String, String>) session.getAttribute(authParam);
        int userId = Integer.parseInt(authorization.get(userIdParam));
        LogicFactory factory = LogicFactory.getInstance();
        UserLogic userLogic = factory.getUserLogic();
        OrderLogic orderLogic = factory.getOrderLogic();
        User user;
        List<Order> orders;
        Map<String, String> params = new HashMap<>();
        params.put(restrictionsKey, userIdParam);
        params.put(userIdParam, String.valueOf(userId));
        try {
            user = userLogic.findUsersByParams(params).get(0);
            orders = orderLogic.findOrdersByParams(params);
        } catch (LogicException e) {
            Logger logger = LogManager.getLogger(PersonalData.class);
            logger.error(e.getMessage(), e);
            throw new ServletException(e.getMessage(), e);
        }
        request.setAttribute("user", user);
        request.setAttribute("orders", orders);
        request.getRequestDispatcher(jspPath).forward(request, response);
    }
}
