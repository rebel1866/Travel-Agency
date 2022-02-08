package by.epamtc.stanislavmelnikov.controller.commandimpl.removetour;

import by.epamtc.stanislavmelnikov.controller.commandinterface.Command;
import by.epamtc.stanislavmelnikov.logic.exception.LogicException;
import by.epamtc.stanislavmelnikov.logic.factory.LogicFactory;
import by.epamtc.stanislavmelnikov.logic.logicinterface.TourLogic;
import by.epamtc.stanislavmelnikov.logic.validation.Validation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class RemoveTour implements Command {
    private static final RemoveTourSub1 subCommand1 = new RemoveTourSub1();
    private static final String jspPath = "WEB-INF/jsp/warning-tour-remove.jsp";
    private static final String commandPath = "/executor?command=tours&page=1";
    private static final String backMessage = "Назад к просмотру туров";
    private static final String tourNameKey = "tour_name";
    private static final String exMessage = "Incorrect tour id";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subCommand = request.getParameter(subcommandParam);
        if (!subCommand.equals(mainSubcommand)) {
            subCommand1.execute(request, response);
            return;
        }
        String tourName = request.getParameter(tourNameKey);
        String idStr = request.getParameter(tourIdParam);
        if (!Validation.isCorrectInteger(idStr)) throw new ServletException(exMessage);
        HttpSession session = request.getSession();
        if (session.getAttribute(accessAllowedKey) != null) {
            request.setAttribute(tourNameKey, tourName);
            request.setAttribute(tourIdParam, idStr);
            request.getRequestDispatcher(jspPath).forward(request, response);
            session.removeAttribute(accessAllowedKey);
        } else {
            request.setAttribute(linkKey, commandPath);
            request.setAttribute(messageKey, backMessage);
            request.getRequestDispatcher(jspNotAvailable).forward(request, response);
        }
    }
}
