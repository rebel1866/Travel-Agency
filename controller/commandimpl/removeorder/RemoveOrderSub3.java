package by.epamtc.stanislavmelnikov.controller.commandimpl.removeorder;

import by.epamtc.stanislavmelnikov.controller.commandinterface.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RemoveOrderSub3 implements Command {
    private static final RemoveOrderSub4 sub4 = new RemoveOrderSub4();
    private static final String backMessage = "Назад к просмотру регистраций";
    private static final String commandPath = "/executor?command=orders&page=1";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subcommand = request.getParameter(subcommandParam);
        if (!subcommand.equals(subcommand3)) {
            sub4.execute(request, response);
            return;
        }
        request.setAttribute(linkKey, commandPath);
        request.setAttribute(messageKey, backMessage);
        request.getRequestDispatcher(jspSuccessfulOperation).forward(request, response);
    }
}
