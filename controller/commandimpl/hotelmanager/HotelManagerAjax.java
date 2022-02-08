package by.epamtc.stanislavmelnikov.controller.commandimpl.hotelmanager;

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
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HotelManagerAjax implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subcommand = request.getParameter(subcommandParam);
        if (!subcommand.equals(ajaxKey)) {
            request.getRequestDispatcher(homePath).forward(request, response);
            return;
        }
        String query = request.getParameter("term");
        LogicFactory logicFactory = LogicFactory.getInstance();
        HotelLogic hotelLogic = logicFactory.getHotelLogic();
        Map<String, String> hotelParams = new HashMap<>();
        hotelParams.put(restrictionsKey, hotelNameKey);
        hotelParams.put(hotelNameKey, query);
        List<Hotel> hotels;
        try {
            hotels = hotelLogic.findHotelsByParams(hotelParams);
        } catch (LogicException e) {
            Logger logger = LogManager.getLogger(HotelManagerAjax.class);
            logger.error(e.getMessage(), e);
            throw new ServletException(e.getMessage(), e);
        }
        PrintWriter writer = response.getWriter();
        String json = generateJson(hotels);
        writer.println(json);
    }

    public String generateJson(List<Hotel> hotels) {
        StringBuilder json = new StringBuilder();
        json.append("[");
        for (int i = 0; i < hotels.size(); i++) {
            json.append("\"");
            json.append(hotels.get(i).getHotelName());
            json.append("\"");
            if (i != hotels.size() - 1) json.append(",");
        }
        json.append("]");
        return json.toString();
    }
}
