package by.epamtc.stanislavmelnikov.controller.commandimpl.addhotel;

import by.epamtc.stanislavmelnikov.controller.commandinterface.Command;
import by.epamtc.stanislavmelnikov.entity.Resort;
import by.epamtc.stanislavmelnikov.logic.exception.LogicException;
import by.epamtc.stanislavmelnikov.logic.factory.LogicFactory;
import by.epamtc.stanislavmelnikov.logic.logicinterface.HotelLogic;
import by.epamtc.stanislavmelnikov.logic.logicinterface.ResortLogic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddHotel implements Command {
    private final AddHotelSub1 sub1 = new AddHotelSub1();
    private static final String keyParams = "params";
    private static final String keyExMessage = "e_message";
    private static final String resortsKey = "resorts";
    private static final String jspPath = "WEB-INF/jsp/add-hotel.jsp";
    private static final String commandPath = "executor?command=add_hotel&subcommand=2";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> params = (Map<String, String>) request.getAttribute(keyParams);
        if (params == null) {
            sub1.execute(request, response);
            return;
        }
        LogicFactory factory = LogicFactory.getInstance();
        HotelLogic hotelLogic = factory.getHotelLogic();
        try {
            hotelLogic.addHotel(params);
        } catch (LogicException e) {
            Logger logger = LogManager.getLogger(AddHotel.class);
            logger.error(e.getMessage(), e);
            List<Resort> resorts = findResorts();
            request.setAttribute(keyExMessage, e.getMessage());
            request.setAttribute(resortsKey, resorts);
            request.getRequestDispatcher(jspPath).forward(request, response);
            return;
        }
        response.sendRedirect(commandPath);
    }

    public List<Resort> findResorts() throws ServletException {
        LogicFactory factory = LogicFactory.getInstance();
        ResortLogic resortLogic = factory.getResortLogic();
        List<Resort> resorts;
        try {
            resorts = resortLogic.findResortsByParams(new HashMap<>());
        } catch (LogicException e) {
            Logger logger = LogManager.getLogger(AddHotel.class);
            logger.error(e.getMessage(), e);
            throw new ServletException(e.getMessage(), e);
        }
        return resorts;
    }
}
