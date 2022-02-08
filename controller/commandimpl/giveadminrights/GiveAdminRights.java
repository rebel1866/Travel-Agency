package by.epamtc.stanislavmelnikov.controller.commandimpl.giveadminrights;

import by.epamtc.stanislavmelnikov.controller.commandinterface.Command;
import by.epamtc.stanislavmelnikov.logic.exception.LogicException;
import by.epamtc.stanislavmelnikov.logic.factory.LogicFactory;
import by.epamtc.stanislavmelnikov.logic.logicinterface.UserLogic;
import com.sun.net.httpserver.HttpsServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class GiveAdminRights implements Command {
    private static final GiveAdminSub1 sub1 = new GiveAdminSub1();
    private static final String commandPath = "/executor?command=give_admin_rights&subcommand=2";
    private static final String roleIdKey = "user_role_id";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subcommand = request.getParameter(subcommandParam);
        if (!subcommand.equals(mainSubcommand)) {
            sub1.execute(request, response);
            return;
        }
        HttpSession session = request.getSession();
        int userRoleId = Integer.parseInt(request.getParameter(roleIdKey));
        int userId = Integer.parseInt((String) session.getAttribute(giveAdminRights));
        LogicFactory factory = LogicFactory.getInstance();
        UserLogic userLogic = factory.getUserLogic();
        try {
            userLogic.giveAdminRights(userId, userRoleId);
        } catch (LogicException e) {
            Logger logger = LogManager.getLogger(GiveAdminRights.class);
            logger.error(e.getMessage(), e);
            throw new ServletException(e.getMessage(), e);
        }
        request.getRequestDispatcher(commandPath).forward(request, response);
    }
}
