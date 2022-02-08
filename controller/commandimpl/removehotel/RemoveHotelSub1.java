package by.epamtc.stanislavmelnikov.controller.commandimpl.removehotel;

import by.epamtc.stanislavmelnikov.controller.commandinterface.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RemoveHotelSub1 implements Command {
    private static final RemoveHotelSub2 subCommand2 = new RemoveHotelSub2();
    private static final String denyCommandPath = "executor?command=full_hotel_info&hotel_id=";
    private static final String acceptCommandPath = "executor?command=remove_hotel&subcommand=2&hotel_id=";
    private static final String actionKey = "action";
    private static final String denyKey = "deny";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subCommand = request.getParameter(subcommandParam);
        if (!subCommand.equals(subcommand1)) {
            subCommand2.execute(request, response);
            return;
        }
        String action = request.getParameter(actionKey);
        String idStr = request.getParameter(hotelIdParam);
        if (action.equals(denyKey)) {
            request.getRequestDispatcher(denyCommandPath + idStr).forward(request, response);
        } else {
            request.getRequestDispatcher(acceptCommandPath + idStr).forward(request, response);
        }
    }
}
