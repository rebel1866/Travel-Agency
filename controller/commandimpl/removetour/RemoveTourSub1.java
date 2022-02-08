package by.epamtc.stanislavmelnikov.controller.commandimpl.removetour;

import by.epamtc.stanislavmelnikov.controller.commandinterface.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RemoveTourSub1 implements Command {
    private static final RemoveTourSub2 subCommand2 = new RemoveTourSub2();
    private static final String denyPath = "executor?command=full_tour_info&id=";
    private static final String acceptPath = "executor?command=remove_tour&subcommand=2&tour_id=";
    private static final String denyKey = "deny";
    private static final String actionKey = "action";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subCommand = request.getParameter(subcommandParam);
        if (!subCommand.equals(subcommand1)) {
            subCommand2.execute(request, response);
            return;
        }
        String action = request.getParameter(actionKey);
        String idStr = request.getParameter(tourIdParam);
        if (action.equals(denyKey)) {
            request.getRequestDispatcher(denyPath + idStr).forward(request, response);
        } else {
            request.getRequestDispatcher(acceptPath + idStr).forward(request, response);
        }
    }
}
