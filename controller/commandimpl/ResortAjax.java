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

public class ResortAjax implements Command {
    private static final String resortNameKey = "resort_name";
    private static final String termKey = "term";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter(termKey);
        LogicFactory logicFactory = LogicFactory.getInstance();
        ResortLogic resortLogic = logicFactory.getResortLogic();
        Map<String, String> resortParams = new HashMap<>();
        resortParams.put(restrictionsKey, resortNameKey);
        resortParams.put(resortNameKey, query);
        List<Resort> resorts;
        try {
            resorts = resortLogic.findResortsByParams(resortParams);
        } catch (LogicException e) {
            Logger logger = LogManager.getLogger(ResortAjax.class);
            logger.error(e.getMessage(), e);
            throw new ServletException(e.getMessage(), e);
        }
        PrintWriter writer = response.getWriter();
        String json = generateJson(resorts);
        writer.println(json);
    }

    public String generateJson(List<Resort> resorts) {
        StringBuilder json = new StringBuilder();
        json.append("[");
        for (int i = 0; i < resorts.size(); i++) {
            json.append("\"");
            json.append(resorts.get(i).getResortName());
            json.append("\"");
            if (i != resorts.size() - 1) json.append(",");
        }
        json.append("]");
        return json.toString();
    }
}
