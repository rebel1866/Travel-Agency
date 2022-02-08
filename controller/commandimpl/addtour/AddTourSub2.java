package by.epamtc.stanislavmelnikov.controller.commandimpl.addtour;

import by.epamtc.stanislavmelnikov.controller.commandinterface.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddTourSub2 implements Command {
    private static final AddTourSub3 sub3 = new AddTourSub3();
    private static final String backMessage = "Назад к просмотру туров";
    private static final String commandPath = "/executor?command=tours&page=1";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subCommand = request.getParameter(subcommandParam);
        if (!subCommand.equals(subcommand2)) {
            sub3.execute(request, response);
            return;
        }
        request.setAttribute(linkKey, commandPath);
        request.setAttribute(messageKey, backMessage);
        request.getRequestDispatcher(jspSuccessfulOperation).forward(request, response);
    }
}
