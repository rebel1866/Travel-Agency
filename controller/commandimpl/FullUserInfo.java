package by.epamtc.stanislavmelnikov.controller.commandimpl;

import by.epamtc.stanislavmelnikov.controller.commandinterface.Command;
import by.epamtc.stanislavmelnikov.entity.Hotel;
import by.epamtc.stanislavmelnikov.entity.Order;
import by.epamtc.stanislavmelnikov.entity.Tour;
import by.epamtc.stanislavmelnikov.entity.User;
import by.epamtc.stanislavmelnikov.logic.exception.LogicException;
import by.epamtc.stanislavmelnikov.logic.factory.LogicFactory;
import by.epamtc.stanislavmelnikov.logic.logicinterface.HotelLogic;
import by.epamtc.stanislavmelnikov.logic.logicinterface.TourLogic;
import by.epamtc.stanislavmelnikov.logic.logicinterface.UserLogic;
import by.epamtc.stanislavmelnikov.logic.validation.Validation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FullUserInfo implements Command {
    private static final String exMessage = "Incorrect user id param";
    private static final String jspPath = "WEB-INF/jsp/full-user-info.jsp";
    private static final String backMessage = "Назад к просмотру пользователей";
    private static final String commandPath = "/executor?command=users&page=1";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter(userIdParam);
        if (!Validation.isCorrectInteger(userId)) throw new ServletException(exMessage);
        LogicFactory logicFactory = LogicFactory.getInstance();
        UserLogic userLogic = logicFactory.getUserLogic();
        Map<String, String> params = new HashMap<>();
        params.put(restrictionsKey, userIdParam);
        params.put(userIdParam, userId);
        List<User> users;
        try {
            users = userLogic.findUsersByParams(params);
        } catch (LogicException e) {
            Logger logger = LogManager.getLogger(FullUserInfo.class);
            logger.error(e.getMessage(), e);
            throw new ServletException(e.getMessage(), e);
        }
        goToPage(users, request, response);
    }

    private void goToPage(List<User> users, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user;
        if (users.size() == 0) {
            request.setAttribute(linkKey, commandPath);
            request.setAttribute(messageKey, backMessage);
            request.getRequestDispatcher(jspNotAvailable).forward(request, response);
        } else {
            user = users.get(0);
            request.setAttribute("user", user);
            request.getRequestDispatcher(jspPath).forward(request, response);
        }
    }
}
