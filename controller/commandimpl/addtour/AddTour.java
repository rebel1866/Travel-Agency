package by.epamtc.stanislavmelnikov.controller.commandimpl.addtour;

import by.epamtc.stanislavmelnikov.controller.commandinterface.Command;
import by.epamtc.stanislavmelnikov.controller.controllerutils.ParamsGenerator;
import by.epamtc.stanislavmelnikov.entity.Hotel;
import by.epamtc.stanislavmelnikov.logic.exception.LogicException;
import by.epamtc.stanislavmelnikov.logic.factory.LogicFactory;
import by.epamtc.stanislavmelnikov.logic.logicinterface.HotelLogic;
import by.epamtc.stanislavmelnikov.logic.logicinterface.TourLogic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddTour implements Command {
    private final AddTourSub1 subCommand1 = new AddTourSub1();
    private final static String key = "fields.add.tour.form";
    private static final String jspPath = "WEB-INF/jsp/add-tour.jsp";
    private static final String commandPath = "executor?command=add_tour&subcommand=2";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subCommand = request.getParameter(subcommandParam);
        if (!subCommand.equals(mainSubcommand)) {
            subCommand1.execute(request, response);
            return;
        }
        Map<String, String> params = ParamsGenerator.getFormKeyValue(request, key);
        LogicFactory factory = LogicFactory.getInstance();
        TourLogic tourLogic = factory.getTourLogic();
        Logger logger = LogManager.getLogger(AddTour.class);
        try {
            tourLogic.addTour(params);
        } catch (LogicException e) {
            logger.info(e.getMessage(), e);
            request.setAttribute("e_message", e.getMessage());
            request.setAttribute("hotels", findHotels());
            request.getRequestDispatcher(jspPath).forward(request, response);
            return;
        }
        response.sendRedirect(commandPath);
    }

    public List<Hotel> findHotels() throws ServletException {
        LogicFactory factory = LogicFactory.getInstance();
        HotelLogic hotelLogic = factory.getHotelLogic();
        List<Hotel> hotels;
        try {
            hotels = hotelLogic.findHotelsByParams(new HashMap<>());
        } catch (LogicException e) {
            Logger logger = LogManager.getLogger(AddTour.class);
            logger.error(e.getMessage(), e);
            throw new ServletException(e.getMessage(), e);
        }
        return hotels;
    }
}
