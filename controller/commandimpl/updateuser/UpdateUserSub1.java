package by.epamtc.stanislavmelnikov.controller.commandimpl.updateuser;

import by.epamtc.stanislavmelnikov.controller.commandinterface.Command;
import by.epamtc.stanislavmelnikov.entity.User;
import by.epamtc.stanislavmelnikov.logic.exception.LogicException;
import by.epamtc.stanislavmelnikov.logic.factory.LogicFactory;
import by.epamtc.stanislavmelnikov.logic.logicinterface.UserLogic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UpdateUserSub1 implements Command {
    private static final UpdateUserSub2 sub2 = new UpdateUserSub2();
    private static final String jspPath = "WEB-INF/jsp/change-user.jsp";
    private static final String commandPath ="/executor?command=full_user_info&user_id=";
    private static final String backMessage = "Назад к просмотру пользователя";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subcommand = request.getParameter(subcommandParam);
        if (!subcommand.equals(subcommand1)) {
            sub2.execute(request, response);
            return;
        }
        HttpSession session = request.getSession(true);
        int userId = Integer.parseInt(request.getParameter(userIdParam));
        LogicFactory factory = LogicFactory.getInstance();
        UserLogic userLogic = factory.getUserLogic();
        User user;
        Map<String, String> params = new HashMap<>();
        params.put(restrictionsKey, userIdParam);
        params.put(userIdParam, String.valueOf(userId));
        try {
            user = userLogic.findUsersByParams(params).get(0);
        } catch (LogicException e) {
            Logger logger = LogManager.getLogger(UpdateUserSub1.class);
            logger.error(e.getMessage(), e);
            throw new ServletException(e.getMessage(), e);
        }
        goToPage(user, response, request, session, userId);
    }

    private void goToPage(User user, HttpServletResponse response, HttpServletRequest request, HttpSession session, int userId)
            throws ServletException, IOException {
        if (session.getAttribute(accessAllowedKey) != null) {
            request.setAttribute("user", user);
            request.setAttribute("command", "update_user");
            request.getRequestDispatcher(jspPath).forward(request, response);
            session.removeAttribute(accessAllowedKey);
        } else {
            request.setAttribute(linkKey, commandPath + userId);
            request.setAttribute(messageKey, backMessage);
            request.getRequestDispatcher(jspNotAvailable).forward(request, response);
        }
    }
}
