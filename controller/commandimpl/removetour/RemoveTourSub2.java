package by.epamtc.stanislavmelnikov.controller.commandimpl.removetour;

import by.epamtc.stanislavmelnikov.controller.commandinterface.Command;
import by.epamtc.stanislavmelnikov.logic.exception.LogicException;
import by.epamtc.stanislavmelnikov.logic.factory.LogicFactory;
import by.epamtc.stanislavmelnikov.logic.logicinterface.TourLogic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RemoveTourSub2 implements Command {
    private static final RemoveTourSub3 subCommand3 = new RemoveTourSub3();
    private static final String commandPath = "executor?command=remove_tour&subcommand=3";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subCommand = request.getParameter(subcommandParam);
        if (!subCommand.equals(subcommand2)) {
            subCommand3.execute(request, response);
            return;
        }
        String idStr = request.getParameter(tourIdParam);
        LogicFactory logicFactory = LogicFactory.getInstance();
        TourLogic tourLogic = logicFactory.getTourLogic();
        int id = Integer.parseInt(idStr);
        try {
            tourLogic.removeTourById(id);
        } catch (LogicException e) {
            Logger logger = LogManager.getLogger(RemoveTourSub2.class);
            logger.error(e.getMessage(), e);
            throw new ServletException(e.getMessage(), e);
        }
        response.sendRedirect(commandPath);
    }
}
