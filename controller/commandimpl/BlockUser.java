package by.epamtc.stanislavmelnikov.controller.commandimpl;

import by.epamtc.stanislavmelnikov.controller.commandinterface.Command;
import by.epamtc.stanislavmelnikov.logic.exception.LogicException;
import by.epamtc.stanislavmelnikov.logic.factory.LogicFactory;
import by.epamtc.stanislavmelnikov.logic.logicinterface.UserLogic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BlockUser implements Command {
    private static final String commandPath = "/executor?command=full_user_info&user_id=";
    private static final String backMessage = "Назад к просмотру информации о пользователе";
    private static final String commandPathRedirect = "executor?command=block_user&subcommand=1&user_id=";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subcommand = request.getParameter(subcommandParam);
        int userId = Integer.parseInt(request.getParameter(userIdParam));
        if (subcommand.equals(subcommand1)) {
            request.setAttribute(linkKey, commandPath + userId);
            request.setAttribute(messageKey, backMessage);
            request.getRequestDispatcher(jspSuccessfulOperation).forward(request, response);
            return;
        }
        if (!subcommand.equals(mainSubcommand)) {
            throw new ServletException(exceptionMessage);
        }
        LogicFactory factory = LogicFactory.getInstance();
        UserLogic userLogic = factory.getUserLogic();
        try {
            userLogic.blockUser(userId);
        } catch (LogicException e) {
            Logger logger = LogManager.getLogger(BlockUser.class);
            logger.error(e.getMessage(), e);
            throw new ServletException(e.getMessage(), e);
        }
        response.sendRedirect(commandPathRedirect + userId);
    }
}
