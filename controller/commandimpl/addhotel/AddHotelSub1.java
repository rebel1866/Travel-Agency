package by.epamtc.stanislavmelnikov.controller.commandimpl.addhotel;

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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class AddHotelSub1 implements Command {
    private static final AddHotelSub2 sub2 = new AddHotelSub2();
    private static final String resortsParam = "resorts";
    private static final String jspHotelPath = "WEB-INF/jsp/add-hotel.jsp";
    private static final String commandPath = "/executor?command=hotels&page=1";
    private static final String backMessage = "Назад к просмотру отелей";
    private static final String jspAvailablePath = "WEB-INF/jsp/not-available.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subName = request.getParameter(subcommandParam);
        if (!subName.equals(subcommand1)) {
            sub2.execute(request, response);
            return;
        }
        LogicFactory factory = LogicFactory.getInstance();
        ResortLogic resortLogic = factory.getResortLogic();
        List<Resort> resorts;
        try {
            resorts = resortLogic.findResortsByParams(new HashMap<>());
        } catch (LogicException e) {
            Logger logger = LogManager.getLogger(AddHotelSub1.class);
            logger.error(e.getMessage(), e);
            throw new ServletException(e.getMessage(), e);
        }
        goToPage(request, response, resorts);
    }

    private void goToPage(HttpServletRequest request, HttpServletResponse response, List<Resort> resorts)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute(accessAllowedKey) != null) {
            request.setAttribute(resortsParam, resorts);
            request.getRequestDispatcher(jspHotelPath).forward(request, response);
            session.removeAttribute(accessAllowedKey);
        } else {
            request.setAttribute(linkKey, commandPath);
            request.setAttribute(messageKey, backMessage);
            request.getRequestDispatcher(jspAvailablePath).forward(request, response);
        }
    }
}
