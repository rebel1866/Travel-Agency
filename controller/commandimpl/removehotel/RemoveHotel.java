package by.epamtc.stanislavmelnikov.controller.commandimpl.removehotel;

import by.epamtc.stanislavmelnikov.controller.commandinterface.Command;
import by.epamtc.stanislavmelnikov.logic.validation.Validation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class RemoveHotel implements Command {
    private static final RemoveHotelSub1 subCommand1 = new RemoveHotelSub1();
    private static final String jspPath = "WEB-INF/jsp/warning-hotel-remove.jsp";
    private static final String backMessage = "Назад к просмотру отелей";
    private static final String commandPath = "/executor?command=hotels&page=1";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subCommand = request.getParameter(subcommandParam);
        if (!subCommand.equals(mainSubcommand)) {
            subCommand1.execute(request, response);
            return;
        }
        String hotelName = request.getParameter(hotelNameKey);
        String hotelId = request.getParameter(hotelIdParam);
        HttpSession session = request.getSession();
        if (session.getAttribute(accessAllowedKey) != null) {
            request.setAttribute(hotelNameKey, hotelName);
            request.setAttribute(hotelIdParam, hotelId);
            request.getRequestDispatcher(jspPath).forward(request, response);
            session.removeAttribute(accessAllowedKey);
        } else {
            request.setAttribute(linkKey, commandPath);
            request.setAttribute(messageKey, backMessage);
            request.getRequestDispatcher(jspNotAvailable).forward(request, response);
        }
    }
}
