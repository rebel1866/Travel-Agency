package by.epamtc.stanislavmelnikov.controller.commandimpl.addtour;

import by.epamtc.stanislavmelnikov.controller.commandinterface.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AddTourSub3 implements Command {
    private static final String commandPath = "executor?command=add_tour&subcommand=1";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subcommand = request.getParameter(subcommandParam);
        if (!subcommand.equals(subcommand3)) {
            throw new ServletException(exceptionMessage);
        }
        HttpSession session = request.getSession();
        session.setAttribute(accessAllowedKey, allowedValue);
        response.sendRedirect(commandPath);
    }
}
