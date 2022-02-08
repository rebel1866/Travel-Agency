package by.epamtc.stanislavmelnikov.controller.commandimpl.signin;

import by.epamtc.stanislavmelnikov.controller.commandinterface.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignInSub3 implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subcommand = request.getParameter(subcommandParam);
        if (!subcommand.equals(subcommand3)) {
            throw new ServletException(exceptionMessage);
        }
        request.getRequestDispatcher(homePath).forward(request, response);
    }
}
