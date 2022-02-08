package by.epamtc.stanislavmelnikov.controller.commandimpl.registration;

import by.epamtc.stanislavmelnikov.controller.commandinterface.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegistrationSub5 implements Command {
    private static final String jspPath = "WEB-INF/jsp/reg-email-send.jsp";
    private static final String subcommand5 = "5";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subCommand = request.getParameter(subcommandParam);
        if (!subCommand.equals(subcommand5)) {
           request.getRequestDispatcher(homePath).forward(request,response);
           return;
        }
        request.getRequestDispatcher(jspPath).forward(request, response);
    }
}
