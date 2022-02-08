package by.epamtc.stanislavmelnikov.controller.commandimpl;

import by.epamtc.stanislavmelnikov.controller.commandinterface.Command;
import by.epamtc.stanislavmelnikov.entity.Resort;
import by.epamtc.stanislavmelnikov.logic.exception.LogicException;
import by.epamtc.stanislavmelnikov.logic.factory.LogicFactory;
import by.epamtc.stanislavmelnikov.logic.logicinterface.ResortLogic;
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
import java.util.stream.Collectors;

public class LocationAjax implements Command {
    private static final String locationKey = "location";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("term");
        LogicFactory logicFactory = LogicFactory.getInstance();
        ResortLogic resortLogic = logicFactory.getResortLogic();
        Map<String, String> resortParams = new HashMap<>();
        resortParams.put(restrictionsKey, locationKey);
        resortParams.put(locationKey, query);
        List<Resort> resorts;
        try {
            resorts = resortLogic.findResortsByParams(resortParams);
        } catch (LogicException e) {
            Logger logger = LogManager.getLogger(LocationAjax.class);
            logger.error(e.getMessage(), e);
            throw new ServletException(e.getMessage(), e);
        }
        PrintWriter writer = response.getWriter();
        String json = generateJson(resorts);
        writer.println(json);
    }

    public String generateJson(List<Resort> resorts) {
        List<String> list = resorts.stream().map(Resort::getLocation).distinct().collect(Collectors.toList());
        StringBuilder json = new StringBuilder();
        json.append("[");
        for (int i = 0; i < list.size(); i++) {
            json.append("\"");
            json.append(list.get(i));
            json.append("\"");
            if (i != list.size() - 1) json.append(",");
        }
        json.append("]");
        return json.toString();
    }
}
