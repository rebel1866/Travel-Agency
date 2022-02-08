package by.epamtc.stanislavmelnikov.controller.commandimpl.changetour;

import by.epamtc.stanislavmelnikov.controller.commandinterface.Command;
import by.epamtc.stanislavmelnikov.entity.Hotel;
import by.epamtc.stanislavmelnikov.entity.Tour;
import by.epamtc.stanislavmelnikov.logic.exception.LogicException;
import by.epamtc.stanislavmelnikov.logic.factory.LogicFactory;
import by.epamtc.stanislavmelnikov.logic.logicinterface.HotelLogic;
import by.epamtc.stanislavmelnikov.logic.logicinterface.TourLogic;
import by.epamtc.stanislavmelnikov.logic.validation.Validation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;


public class ChangeTourSub1 implements Command {
    private static final ChangeTourSub2 sub2 = new ChangeTourSub2();
    private static final String exMessage = "Incorrect tour id";
    private static final String jspPath = "WEB-INF/jsp/change-tour.jsp";
    private static final String commandPath = "/executor?command=full_tour_info&id=";
    private static final String backMessage = "Назад к просмотру тура";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subCommand = request.getParameter(subcommandParam);
        if (!subCommand.equals(subcommand1)) {
            sub2.execute(request, response);
            return;
        }
        LogicFactory factory = LogicFactory.getInstance();
        TourLogic tourLogic = factory.getTourLogic();
        String idStr = request.getParameter(tourIdParam);
        if (!Validation.isCorrectInteger(idStr)) throw new ServletException(exMessage);
        int id = Integer.parseInt(idStr);
        Tour tour;
        List<Hotel> hotels;
        HotelLogic hotelLogic = factory.getHotelLogic();
        Logger logger = LogManager.getLogger(ChangeTourSub1.class);
        try {
            tour = tourLogic.findTourById(id);
            hotels = hotelLogic.findHotelsByParams(new HashMap<>());
        } catch (LogicException e) {
            logger.error(e.getMessage(), e);
            throw new ServletException(e.getMessage(), e);
        }
        HttpSession session = request.getSession();
        goToPage(request, response, session, id, tour, hotels);
    }

    public void goToPage(HttpServletRequest request, HttpServletResponse response, HttpSession session, int id,
                         Tour tour, List<Hotel> hotels) throws ServletException, IOException {
        if (session.getAttribute(accessAllowedKey) != null) {
            request.setAttribute("tour", tour);
            request.setAttribute("hotels", hotels);
            request.setAttribute("tour_id", id);
            request.getRequestDispatcher(jspPath).forward(request, response);
            session.removeAttribute(accessAllowedKey);
        } else {
            request.setAttribute("link", commandPath + id);
            request.setAttribute("message", backMessage);
            request.getRequestDispatcher(jspNotAvailable).forward(request, response);
        }
    }
}
