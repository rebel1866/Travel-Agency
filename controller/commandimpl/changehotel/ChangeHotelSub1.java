package by.epamtc.stanislavmelnikov.controller.commandimpl.changehotel;

import by.epamtc.stanislavmelnikov.controller.commandinterface.Command;
import by.epamtc.stanislavmelnikov.entity.Hotel;
import by.epamtc.stanislavmelnikov.entity.Resort;
import by.epamtc.stanislavmelnikov.logic.exception.LogicException;
import by.epamtc.stanislavmelnikov.logic.factory.LogicFactory;
import by.epamtc.stanislavmelnikov.logic.logicinterface.HotelLogic;
import by.epamtc.stanislavmelnikov.logic.logicinterface.ResortLogic;
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

public class ChangeHotelSub1 implements Command {
    private static final ChangeHotelSub2 sub2 = new ChangeHotelSub2();
    private static final String jspPath = "WEB-INF/jsp/change-hotel.jsp";
    private static final String commandPath = "/executor?command=full_hotel_info&hotel_id=";
    private static final String backMessage = "Назад к просмотру отеля";
    private static final String exMessage = "Incorrect hotel id";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subCommand = request.getParameter(subcommandParam);
        if (!subCommand.equals(subcommand1)) {
            sub2.execute(request, response);
            return;
        }
        LogicFactory factory = LogicFactory.getInstance();
        HotelLogic hotelLogic = factory.getHotelLogic();
        ResortLogic resortLogic = factory.getResortLogic();
        String idStr = request.getParameter(hotelIdParam);
        if (!Validation.isCorrectInteger(idStr)) throw new ServletException(exMessage);
        int id = Integer.parseInt(idStr);
        Hotel hotel;
        List<Resort> resorts;
        Logger logger = LogManager.getLogger(ChangeHotelSub1.class);
        try {
            hotel = hotelLogic.findHotelById(id);
            resorts = resortLogic.findResortsByParams(new HashMap<>());
        } catch (LogicException e) {
            logger.error(e.getMessage(), e);
            throw new ServletException(e.getMessage(), e);
        }
        goToPage(request, response, hotel, resorts,id);
    }

    private void goToPage(HttpServletRequest request, HttpServletResponse response, Hotel hotel, List<Resort> resorts, int id)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute(accessAllowedKey) != null) {
            request.setAttribute("hotel", hotel);
            request.setAttribute("resorts", resorts);
            request.setAttribute(hotelIdParam, id);
            request.getRequestDispatcher(jspPath).forward(request, response);
            session.removeAttribute(accessAllowedKey);
        } else {
            request.setAttribute(linkKey, commandPath + id);
            request.setAttribute(messageKey, backMessage);
            request.getRequestDispatcher(jspNotAvailable).forward(request, response);
        }
    }
}
