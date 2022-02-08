package by.epamtc.stanislavmelnikov.controller.commandimpl.removehotel;

import by.epamtc.stanislavmelnikov.controller.commandinterface.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RemoveHotelSub3 implements Command {
    private static final RemoveHotelSub4 sub4 = new RemoveHotelSub4();
    private static final String commandPath = "/executor?command=hotels&page=1";
    private static final String backMessage="Назад к просмотру отелей";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subCommand = request.getParameter(subcommandParam);
        if (!subCommand.equals(subcommand3)) {
            sub4.execute(request, response);
            return;
        }
        request.setAttribute(linkKey, commandPath);
        request.setAttribute(messageKey, backMessage);
        request.getRequestDispatcher(jspSuccessfulOperation).forward(request, response);
    }
}
