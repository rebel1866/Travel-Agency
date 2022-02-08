package by.epamtc.stanislavmelnikov.controller.commandimpl.removeorder;

import by.epamtc.stanislavmelnikov.controller.commandinterface.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class RemoveOrderSub4 implements Command {
    private static final String commandPath = "executor?command=remove_order&subcommand=1&order_id=";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subcommand = request.getParameter(subcommandParam);
        if (!subcommand.equals(subcommand4)) {
            request.getRequestDispatcher(homePath).forward(request, response);
            return;
        }
        HttpSession session = request.getSession();
        session.setAttribute(accessAllowedKey, allowedValue);
        response.sendRedirect(commandPath + request.getParameter(orderIdParam));
    }
}
