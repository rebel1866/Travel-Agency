package by.epamtc.stanislavmelnikov.controller.commandimpl.addhotel;

import by.epamtc.stanislavmelnikov.controller.commandinterface.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AddHotelSub3 implements Command {
    private static final String commandPath = "executor?command=add_hotel&subcommand=1";


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!request.getParameter(subcommandParam).equals(subcommand3)) {
            request.getRequestDispatcher(homePath).forward(request, response);
            return;
        }
        HttpSession session = request.getSession();
        session.setAttribute(accessAllowedKey, allowedValue);
        response.sendRedirect(commandPath);
    }
}
