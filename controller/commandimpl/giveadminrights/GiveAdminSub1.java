package by.epamtc.stanislavmelnikov.controller.commandimpl.giveadminrights;

import by.epamtc.stanislavmelnikov.controller.commandinterface.Command;
import by.epamtc.stanislavmelnikov.entity.UserRole;
import by.epamtc.stanislavmelnikov.logic.exception.LogicException;
import by.epamtc.stanislavmelnikov.logic.factory.LogicFactory;
import by.epamtc.stanislavmelnikov.logic.logicinterface.UserLogic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.stylesheets.LinkStyle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class GiveAdminSub1 implements Command {
    private static final GiveAdminSub2 sub2 = new GiveAdminSub2();
    private static final String backMessage = "Назад к просмотру пользователя";
    private static final String commandPath = "/executor?command=full_user_info&user_id=";
    private static final String jspPath = "WEB-INF/jsp/give-admin-rights.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subcommand = request.getParameter(subcommandParam);
        if (!subcommand.equals(subcommand1)) {
            sub2.execute(request, response);
            return;
        }
        List<UserRole> roles;
        LogicFactory factory = LogicFactory.getInstance();
        UserLogic userLogic = factory.getUserLogic();
        try {
            roles = userLogic.findUserRoles();
        } catch (LogicException e) {
            Logger logger = LogManager.getLogger(GiveAdminSub1.class);
            logger.error(e.getMessage(), e);
            throw new ServletException(e.getMessage(), e);
        }
        goToPage(request, response, roles);
    }

    private void goToPage(HttpServletRequest request, HttpServletResponse response, List<UserRole> roles)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute(accessAllowedKey) != null) {
            request.setAttribute("roles", roles);
            request.getRequestDispatcher(jspPath).forward(request, response);
            session.removeAttribute(accessAllowedKey);
        } else {
            String userId = (String) session.getAttribute(giveAdminRights);
            session.removeAttribute(giveAdminRights);
            request.setAttribute(linkKey, commandPath + userId);
            request.setAttribute(messageKey, backMessage);
            request.getRequestDispatcher(jspNotAvailable).forward(request, response);
        }
    }
}
