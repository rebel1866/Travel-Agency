package by.epamtc.stanislavmelnikov.controller.commandimpl.removeuser;

import by.epamtc.stanislavmelnikov.controller.commandimpl.removeorder.RemoveOrder;
import by.epamtc.stanislavmelnikov.controller.commandinterface.Command;
import by.epamtc.stanislavmelnikov.logic.exception.LogicException;
import by.epamtc.stanislavmelnikov.logic.factory.LogicFactory;
import by.epamtc.stanislavmelnikov.logic.logicinterface.OrderLogic;
import by.epamtc.stanislavmelnikov.logic.logicinterface.UserLogic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RemoveUser implements Command {
    private static final RemoveUserSub1 sub1 = new RemoveUserSub1();
    private static final String commandPath = "executor?command=remove_user&subcommand=3";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subcommand = request.getParameter(subcommandParam);
        if (!subcommand.equals(mainSubcommand)) {
            sub1.execute(request, response);
            return;
        }
        LogicFactory logicFactory = LogicFactory.getInstance();
        UserLogic userLogic = logicFactory.getUserLogic();
        int userId = Integer.parseInt(request.getParameter(userIdParam));
        try {
            userLogic.removeUser(userId);
        } catch (LogicException e) {
            Logger logger = LogManager.getLogger(RemoveUser.class);
            logger.error(e.getMessage(), e);
            throw new ServletException(e.getMessage(), e);
        }
        response.sendRedirect(commandPath);
    }
}
