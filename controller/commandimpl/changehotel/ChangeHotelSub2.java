package by.epamtc.stanislavmelnikov.controller.commandimpl.changehotel;

import by.epamtc.stanislavmelnikov.controller.commandinterface.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ChangeHotelSub2 implements Command {
    private static final ChangeHotelSub3 sub3 = new ChangeHotelSub3();
    private static final String backMessage = "Назад к просмотру отеля";
    private static final String commandPath = "/executor?command=full_hotel_info&hotel_id=";

    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subCommand = request.getParameter(subcommandParam);
        if (!subCommand.equals(subcommand2)) {
            sub3.execute(request, response);
            return;
        }
        request.setAttribute(linkKey, commandPath + request.getParameter(hotelIdParam));
        request.setAttribute(messageKey, backMessage);
        request.getRequestDispatcher(jspSuccessfulOperation).forward(request, response);
    }
}
