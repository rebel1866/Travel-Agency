package by.epamtc.stanislavmelnikov.controller.commandimpl.registration;

import by.epamtc.stanislavmelnikov.controller.commandinterface.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class RegistrationSub1 implements Command {
    private static final RegistrationSub2 sub2 = new RegistrationSub2();
    private static final String commandPath = "executor?command=registration&subcommand=2";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subcommand = request.getParameter(subcommandParam);
        if (!subcommand.equals(subcommand1)) {
            sub2.execute(request, response);
            return;
        }
        HttpSession session = request.getSession();
        session.setAttribute(accessAllowedKey, allowedValue);
        response.sendRedirect(commandPath);
    }
}
