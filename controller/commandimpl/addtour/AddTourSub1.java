package by.epamtc.stanislavmelnikov.controller.commandimpl.addtour;

import by.epamtc.stanislavmelnikov.controller.commandinterface.Command;
import by.epamtc.stanislavmelnikov.entity.Hotel;
import by.epamtc.stanislavmelnikov.logic.exception.LogicException;
import by.epamtc.stanislavmelnikov.logic.factory.LogicFactory;
import by.epamtc.stanislavmelnikov.logic.logicinterface.HotelLogic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;


public class AddTourSub1 implements Command {
    private static final AddTourSub2 sub2 = new AddTourSub2();
    private static final String backMessage = "Назад к просмотру туров";
    private static final String commandPath = "/executor?command=tours&page=1";
    private static final String jspPath = "WEB-INF/jsp/add-tour.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subCommand = request.getParameter(subcommandParam);
        if (!subCommand.equals(subcommand1)) {
            sub2.execute(request, response);
            return;
        }
        LogicFactory factory = LogicFactory.getInstance();
        HotelLogic hotelLogic = factory.getHotelLogic();
        List<Hotel> hotels;
        try {
            hotels = hotelLogic.findHotelsByParams(new HashMap<>());
        } catch (LogicException e) {
            Logger logger = LogManager.getLogger(AddTourSub1.class);
            logger.error(e.getMessage(), e);
            throw new ServletException(e.getMessage(), e);
        }
        goToPage(request, response, hotels);
    }

    private void goToPage(HttpServletRequest request, HttpServletResponse response, List<Hotel> hotels)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute(accessAllowedKey) != null) {
            request.setAttribute("hotels", hotels);
            request.getRequestDispatcher(jspPath).forward(request, response);
            session.removeAttribute(accessAllowedKey);
        } else {
            request.setAttribute(linkKey, commandPath);
            request.setAttribute(messageKey, backMessage);
            request.getRequestDispatcher(jspNotAvailable).forward(request, response);
        }
    }
}
