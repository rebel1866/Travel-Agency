package by.epamtc.stanislavmelnikov.controller.commandimpl.signin;

import by.epamtc.stanislavmelnikov.controller.commandinterface.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SignInSub2 implements Command {
    private static final SignInSub3 sub3 = new SignInSub3();
    private static final String jspPath = "WEB-INF/jsp/authorization.jsp";
    private static final String homeMessage = "HOME";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subcommand = request.getParameter(subcommandParam);
        if (!subcommand.equals(subcommand2)) {
            sub3.execute(request, response);
            return;
        }
        HttpSession session = request.getSession();
        if (session.getAttribute(accessAllowedKey) != null) {
            request.getRequestDispatcher(jspPath).forward(request, response);
            session.removeAttribute(accessAllowedKey);
        } else {
            request.setAttribute(linkKey, homePath);
            request.setAttribute(messageKey, homeMessage);
            request.getRequestDispatcher(jspNotAvailable).forward(request, response);
        }
    }
}
