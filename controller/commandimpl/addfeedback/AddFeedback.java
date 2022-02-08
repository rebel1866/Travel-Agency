package by.epamtc.stanislavmelnikov.controller.commandimpl.addfeedback;

import by.epamtc.stanislavmelnikov.controller.commandinterface.Command;
import by.epamtc.stanislavmelnikov.logic.exception.LogicException;
import by.epamtc.stanislavmelnikov.logic.factory.LogicFactory;
import by.epamtc.stanislavmelnikov.logic.logicinterface.FeedbackLogic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddFeedback implements Command {
    private static final AddFeedbackSub1 sub1 = new AddFeedbackSub1();
    private static final String commandPath = "executor?command=add_feedback&subcommand=1&tour_id=";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subcommand = request.getParameter(subcommandParam);
        if (!subcommand.equals(mainSubcommand)) {
            sub1.execute(request, response);
            return;
        }
        int userId = Integer.parseInt(request.getParameter(userIdParam));
        int tourId = Integer.parseInt(request.getParameter(tourIdParam));
        int rating = Integer.parseInt(request.getParameter(ratingParam));
        String fbkBody = request.getParameter(fbkBodyParam);
        LogicFactory logicFactory = LogicFactory.getInstance();
        FeedbackLogic feedbackLogic = logicFactory.getFeedbackLogic();
        try {
            feedbackLogic.addFeedback(tourId, userId, rating, fbkBody);
        } catch (LogicException e) {
            Logger logger = LogManager.getLogger(AddFeedback.class);
            logger.error(e.getMessage(), e);
            throw new ServletException(e.getMessage(), e);
        }
        response.sendRedirect(commandPath + tourId);
    }
}
