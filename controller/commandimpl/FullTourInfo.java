package by.epamtc.stanislavmelnikov.controller.commandimpl;

import by.epamtc.stanislavmelnikov.controller.commandinterface.Command;
import by.epamtc.stanislavmelnikov.entity.Tour;
import by.epamtc.stanislavmelnikov.logic.exception.LogicException;
import by.epamtc.stanislavmelnikov.logic.factory.LogicFactory;
import by.epamtc.stanislavmelnikov.logic.logicinterface.TourLogic;
import by.epamtc.stanislavmelnikov.logic.validation.Validation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FullTourInfo implements Command {
    private static final String backMessage = "Назад к просмотру туров";
    private static final String commandPath = "/executor?command=tours&page=1";
    private static final String jspPath = "WEB-INF/jsp/full-tour-info.jsp";
    private static final String exMessage = "Incorrect id param";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("id");
        if (!Validation.isCorrectInteger(idStr)) throw new ServletException(exMessage);
        int id = Integer.parseInt(idStr);
        Tour tour;
        LogicFactory logicFactory = LogicFactory.getInstance();
        TourLogic tourLogic = logicFactory.getTourLogic();
        try {
            tour = tourLogic.findTourById(id);
        } catch (LogicException e) {
            Logger logger = LogManager.getLogger(FullTourInfo.class);
            logger.error(e.getMessage(), e);
            request.setAttribute(linkKey, commandPath);
            request.setAttribute(messageKey, backMessage);
            request.getRequestDispatcher(jspNotAvailable).forward(request, response);
            return;
        }
        Cookie cookie = new Cookie(tourIdParam + idStr, idStr);
        response.addCookie(cookie);
        request.setAttribute("tour", tour);
        request.getRequestDispatcher(jspPath).forward(request, response);
    }
}
