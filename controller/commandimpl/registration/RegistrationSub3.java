package by.epamtc.stanislavmelnikov.controller.commandimpl.registration;

import by.epamtc.stanislavmelnikov.controller.commandinterface.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegistrationSub3 implements Command {
    private static final RegistrationSub4 sub4 = new RegistrationSub4();
    private static final String jspPath = "WEB-INF/jsp/registration-greeting-page.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subCommand = request.getParameter(subcommandParam);
        if (!subCommand.equals(subcommand3)) {
            sub4.execute(request, response);
            return;
        }
        request.getRequestDispatcher(jspPath).forward(request, response);
    }
}
