package by.epamtc.stanislavmelnikov.controller.commandimpl.removehotel;

import by.epamtc.stanislavmelnikov.controller.commandimpl.removetour.RemoveTour;
import by.epamtc.stanislavmelnikov.controller.commandinterface.Command;
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

public class RemoveHotelSub2 implements Command {
    private static final RemoveHotelSub3 subCommand3 = new RemoveHotelSub3();
    private static final String commandPath = "executor?command=remove_hotel&subcommand=3&hotel_id=";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subCommand = request.getParameter(subcommandParam);
        if (!subCommand.equals(subcommand2)) {
            subCommand3.execute(request, response);
            return;
        }
        String idStr = request.getParameter(hotelIdParam);
        LogicFactory logicFactory = LogicFactory.getInstance();
        HotelLogic hotelLogic = logicFactory.getHotelLogic();
        int id = Integer.parseInt(idStr);
        try {
            hotelLogic.removeHotel(id);
        } catch (LogicException e) {
            Logger logger = LogManager.getLogger(RemoveHotelSub2.class);
            logger.error(e.getMessage(), e);
            throw new ServletException(e.getMessage(), e);
        }
        response.sendRedirect(commandPath + id);
    }
}
