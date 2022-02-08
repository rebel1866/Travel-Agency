package by.epamtc.stanislavmelnikov.controller.commandimpl.addrole;

import by.epamtc.stanislavmelnikov.controller.commandinterface.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AddRoleSub1 implements Command {
    private static final AddRoleSub2 sub2 = new AddRoleSub2();
    private static final String jspPath = "WEB-INF/jsp/add-user-role.jsp";
    private static final String commandPath = "/executor?command=personal_data";
    private static final String backMessage = "Назад в личный кабинет";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subcommand = request.getParameter(subcommandParam);
        if (!subcommand.equals(subcommand1)) {
            sub2.execute(request, response);
            return;
        }
        HttpSession session = request.getSession();
        if (session.getAttribute(accessAllowedKey) != null) {
            request.getRequestDispatcher(jspPath).forward(request, response);
            session.removeAttribute(accessAllowedKey);
        } else {
            request.setAttribute(linkKey, commandPath);
            request.setAttribute(messageKey, backMessage);
            request.getRequestDispatcher(jspNotAvailable).forward(request, response);
        }
    }
}
