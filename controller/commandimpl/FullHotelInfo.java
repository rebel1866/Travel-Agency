package by.epamtc.stanislavmelnikov.controller.commandimpl;

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
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FullHotelInfo implements Command {
    private static final String exMessage = "Incorrect hotel id param";
    private static final String commandPath = "/executor?command=hotels&page=1";
    private static final String jspPath = "WEB-INF/jsp/full-hotel-info.jsp";
    private static final String backMessage = "Назад к просмотру отелей";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String hotelId = request.getParameter(hotelIdParam);
        if (!Validation.isCorrectInteger(hotelId)) throw new ServletException(exMessage);
        int id = Integer.parseInt(hotelId);
        Hotel hotel;
        List<Tour> tours;
        LogicFactory logicFactory = LogicFactory.getInstance();
        HotelLogic hotelLogic = logicFactory.getHotelLogic();
        TourLogic tourLogic = logicFactory.getTourLogic();
        Map<String, String> params = generateParams(hotelId);
        try {
            hotel = hotelLogic.findHotelById(id);
            tours = tourLogic.findToursByParams(params);
        } catch (LogicException e) {
            Logger logger = LogManager.getLogger(FullHotelInfo.class);
            logger.error(e.getMessage(), e);
            request.setAttribute(linkKey, commandPath);
            request.setAttribute(messageKey, backMessage);
            request.getRequestDispatcher(jspNotAvailable).forward(request, response);
            return;
        }
        request.setAttribute("tours", tours);
        request.setAttribute("hotel", hotel);
        request.getRequestDispatcher(jspPath).forward(request, response);
    }

    private Map<String, String> generateParams(String hotelId) {
        Map<String, String> params = new HashMap<>();
        params.put(restrictionsKey, hotelIdParam);
        params.put(hotelIdParam, hotelId);
        return params;
    }
}
