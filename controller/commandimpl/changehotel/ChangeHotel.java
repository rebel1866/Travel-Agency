package by.epamtc.stanislavmelnikov.controller.commandimpl.changehotel;

import by.epamtc.stanislavmelnikov.controller.commandinterface.Command;
import by.epamtc.stanislavmelnikov.logic.exception.LogicException;
import by.epamtc.stanislavmelnikov.logic.factory.LogicFactory;
import by.epamtc.stanislavmelnikov.logic.logicinterface.HotelLogic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class ChangeHotel implements Command {
    private static final ChangeHotelSub1 subCommand1 = new ChangeHotelSub1();
    private static final String commandPath1 = "executor?command=change_hotel&subcommand=1&hotel_id=";
    private static final String commandPath2 = "executor?command=change_hotel&subcommand=2&hotel_id=";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> params = (Map<String, String>) request.getAttribute("params");
        if (params == null) {
            subCommand1.execute(request, response);
            return;
        }
        LogicFactory factory = LogicFactory.getInstance();
        HotelLogic hotelLogic = factory.getHotelLogic();
        String hotelId = params.get(hotelIdParam);
        try {
            hotelLogic.updateHotel(params);
        } catch (LogicException e) {
            Logger logger = LogManager.getLogger(ChangeHotel.class);
            logger.error(e.getMessage(), e);
            request.setAttribute("e_message", e.getMessage());
            request.getRequestDispatcher(commandPath1 + hotelId).forward(request, response);
            return;
        }
        response.sendRedirect(commandPath2 + hotelId);
    }
}
