package by.epamtc.stanislavmelnikov.controller.commandimpl.changetour;

import by.epamtc.stanislavmelnikov.controller.commandinterface.Command;
import by.epamtc.stanislavmelnikov.controller.controllerutils.ParamsGenerator;
import by.epamtc.stanislavmelnikov.logic.exception.LogicException;
import by.epamtc.stanislavmelnikov.logic.factory.LogicFactory;
import by.epamtc.stanislavmelnikov.logic.logicinterface.TourLogic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class ChangeTour implements Command {
    private final static ChangeTourSub1 sub1 = new ChangeTourSub1();
    private final static String key = "fields.add.tour.form";
    private final static String commandPath1 = "executor?command=change_tour&subcommand=1&tour_id=";
    private final static String commandPath2 = "executor?command=change_tour&subcommand=2&tour_id=";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subCommand = request.getParameter(subcommandParam);
        if (!subCommand.equals(mainSubcommand)) {
            sub1.execute(request, response);
            return;
        }
        LogicFactory factory = LogicFactory.getInstance();
        TourLogic tourLogic = factory.getTourLogic();
        Map<String, String> params = ParamsGenerator.getFormKeyValue(request, key);
        String tourId = request.getParameter(tourIdParam);
        params.put(tourIdParam, tourId);
        try {
            tourLogic.updateTour(params);
        } catch (LogicException e) {
            Logger logger = LogManager.getLogger(ChangeTour.class);
            logger.error(e.getMessage(), e);
            request.setAttribute("e_message", e.getMessage());
            request.getRequestDispatcher(commandPath1 + tourId).forward(request, response);
            return;
        }
        response.sendRedirect(commandPath2 + tourId);
    }
}
