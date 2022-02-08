package by.epamtc.stanislavmelnikov.controller.commandimpl.changetour;


import by.epamtc.stanislavmelnikov.controller.commandinterface.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ChangeTourSub2 implements Command {
    private static final ChangeTourSub3 sub3 = new ChangeTourSub3();
    private static final String backMessage = "Назад к просмотру тура";
    private static final String commandPath = "/executor?command=full_tour_info&id=";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subCommand = request.getParameter(subcommandParam);
        if (!subCommand.equals(subcommand2)) {
            sub3.execute(request, response);
            return;
        }
        request.setAttribute(linkKey, commandPath + request.getParameter(tourIdParam));
        request.setAttribute(messageKey, backMessage);
        request.getRequestDispatcher(jspSuccessfulOperation).forward(request, response);
    }
}
