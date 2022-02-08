package by.epamtc.stanislavmelnikov.controller.commandimpl;

import by.epamtc.stanislavmelnikov.controller.commandinterface.Command;
import by.epamtc.stanislavmelnikov.controller.controllerutils.ParamsGenerator;
import by.epamtc.stanislavmelnikov.controller.controllerutils.PropertyLoader;
import by.epamtc.stanislavmelnikov.entity.Feedback;
import by.epamtc.stanislavmelnikov.logic.exception.LogicException;
import by.epamtc.stanislavmelnikov.logic.factory.LogicFactory;
import by.epamtc.stanislavmelnikov.logic.logicinterface.FeedbackLogic;
import by.epamtc.stanislavmelnikov.logic.logicinterface.Pagination;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class FeedbackManager implements Command {
    private static final String amountOnPageKey = "amount.on.page.feedbacks";
    private static final String commandPath = "executor?command=feedbacks&page=1";
    private static final String jspPath = "WEB-INF/jsp/feedbacks.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LogicFactory logicFactory = LogicFactory.getInstance();
        FeedbackLogic feedbackLogic = logicFactory.getFeedbackLogic();
        String amountStr = PropertyLoader.getProperty(amountOnPageKey);
        int amountOnPage = Integer.parseInt(amountStr);
        List<Feedback> feedbacks;
        int amountItems;
        Map<String, String> paramsCount = ParamsGenerator.generateParams(request, amountOnPage, false);
        Map<String, String> fbkParams = ParamsGenerator.generateParams(request, amountOnPage, true);
        try {
            amountItems = feedbackLogic.countFeedbacksByParams(paramsCount);
            feedbacks = feedbackLogic.findFeedbacksByParams(fbkParams);
        } catch (LogicException e) {
            Logger logger = LogManager.getLogger(FeedbackManager.class);
            logger.error(e.getMessage(), e);
            throw new ServletException(e.getMessage(), e);
        }
        int page = Integer.parseInt(request.getParameter(pageKey));
        Pagination pagination = logicFactory.getPagination();
        Map<String, Integer> paginationParams = pagination.paginate(page, amountItems, amountOnPage);
        int totalPages = paginationParams.get("totalPages");
        goToPage(totalPages, request, response, feedbacks, page, paginationParams);
    }

    private void goToPage(int totalPages, HttpServletRequest request, HttpServletResponse response,
                          List<Feedback> feedbacks, int page, Map<String, Integer> paginationParams) throws ServletException, IOException {
        if (page > totalPages && totalPages != 0) {
            request.getRequestDispatcher(commandPath).forward(request, response);
        } else {
            request.setAttribute("feedbacks", feedbacks);
            request.setAttribute(paginationKey, paginationParams);
            request.getRequestDispatcher(jspPath).forward(request, response);
        }
    }
}
