package by.epamtc.stanislavmelnikov.controller.commandimpl.updateuser;

import by.epamtc.stanislavmelnikov.controller.commandinterface.Command;
import by.epamtc.stanislavmelnikov.controller.controllerutils.ParamsGenerator;
import by.epamtc.stanislavmelnikov.entity.User;
import by.epamtc.stanislavmelnikov.logic.exception.LogicException;
import by.epamtc.stanislavmelnikov.logic.factory.LogicFactory;
import by.epamtc.stanislavmelnikov.logic.logicinterface.UserLogic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UpdateUser implements Command {
    private static final UpdateUserSub1 sub1 = new UpdateUserSub1();
    private static final String key = "fields.user";
    private static final String commandPath = "executor?command=update_user&subcommand=2&user_id=";
    private static final String jspPath = "WEB-INF/jsp/change-user.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subcommand = request.getParameter(subcommandParam);
        if (!subcommand.equals(mainSubcommand)) {
            sub1.execute(request, response);
            return;
        }
        Map<String, String> updateParams = ParamsGenerator.getFormKeyValue(request, key);
        UserLogic userLogic = LogicFactory.getInstance().getUserLogic();
        User user;
        int userId = Integer.parseInt(request.getParameter(userIdParam));
        Map<String, String> userParams = new HashMap<>();
        userParams.put(restrictionsKey, userIdParam);
        userParams.put(userIdParam, String.valueOf(userId));
        try {
            user = userLogic.findUsersByParams(userParams).get(0);
        } catch (LogicException e) {
            Logger logger = LogManager.getLogger(UpdateUser.class);
            logger.error(e.getMessage(), e);
            throw new ServletException(e.getMessage(), e);
        }
        try {
            userLogic.updateUser(updateParams);
        } catch (LogicException e) {
            request.setAttribute("e_message", e.getMessage());
            request.setAttribute("user", user);
            request.setAttribute("command", "update_user");
            request.getRequestDispatcher(jspPath).forward(request, response);
            return;
        }
        response.sendRedirect(commandPath + userId);
    }
}
