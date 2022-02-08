package by.epamtc.stanislavmelnikov.controller.commandimpl.removeuser;

import by.epamtc.stanislavmelnikov.controller.commandinterface.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class RemoveUserSub1 implements Command {
    private static final RemoveUserSub2 sub2 = new RemoveUserSub2();
    private static final String jspPath = "WEB-INF/jsp/warning-user-remove.jsp";
    private static final String backMessage = "Назад к просмотру пользователей";
    private static final String commandPath = "/executor?command=users&page=1";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subcommand = request.getParameter(subcommandParam);
        if (!subcommand.equals(subcommand1)) {
            sub2.execute(request, response);
            return;
        }
        String userId = request.getParameter(userIdParam);
        HttpSession session = request.getSession();
        if (session.getAttribute(accessAllowedKey) != null) {
            request.setAttribute(userIdParam, userId);
            request.getRequestDispatcher(jspPath).forward(request, response);
            session.removeAttribute(accessAllowedKey);
        } else {
            request.setAttribute(linkKey, commandPath);
            request.setAttribute(messageKey, backMessage);
            request.getRequestDispatcher(jspNotAvailable).forward(request, response);
        }
    }
}
