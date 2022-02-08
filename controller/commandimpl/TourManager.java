package by.epamtc.stanislavmelnikov.controller.commandimpl;

import by.epamtc.stanislavmelnikov.controller.commandinterface.Command;
import by.epamtc.stanislavmelnikov.controller.controllerutils.PropertyLoader;
import by.epamtc.stanislavmelnikov.entity.Tour;
import by.epamtc.stanislavmelnikov.logic.exception.LogicException;
import by.epamtc.stanislavmelnikov.logic.factory.LogicFactory;
import by.epamtc.stanislavmelnikov.logic.logicinterface.Pagination;
import by.epamtc.stanislavmelnikov.logic.logicinterface.TourLogic;
import by.epamtc.stanislavmelnikov.controller.controllerutils.ParamsGenerator;
import by.epamtc.stanislavmelnikov.logic.validation.Validation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;


public class TourManager implements Command {
    private static final String amountOnPageKey = "amount.on.page";
    private static final String keyFields = "fields.tour";
    private static final String commandPath ="/executor?command=tours&page=1";
    private static final String jspPath = "WEB-INF/jsp/tours.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LogicFactory logicFactory = LogicFactory.getInstance();
        TourLogic tourLogic = logicFactory.getTourLogic();
        int amountItems;
        String amountStr = PropertyLoader.getProperty(amountOnPageKey);
        int amountOnPage = Integer.parseInt(amountStr);
        String pageStr = request.getParameter(pageKey);
        int page;
        if (!Validation.isCorrectInteger(pageStr)) {
            page = 1;
        }
        else page = Integer.parseInt(pageStr);
        Map<String, String> paramsCount = ParamsGenerator.generateParams(request, amountOnPage, false, keyFields);
        Map<String, String> tourParams = ParamsGenerator.generateParams(request, amountOnPage, true, keyFields);
        List<Tour> tours;
        try {
            amountItems = tourLogic.countToursByParams(paramsCount);
            tours = tourLogic.findToursByParams(tourParams);
        } catch (LogicException e) {
            Logger logger = LogManager.getLogger(TourManager.class);
            logger.error(e.getMessage(), e);
            throw new ServletException(e.getMessage(), e);
        }
        goToPage(request, response, amountItems, page, tours, amountOnPage);
    }

    private void goToPage(HttpServletRequest request, HttpServletResponse response, int amountItems,
                          int page, List<Tour> tours, int amountOnPage) throws ServletException, IOException {
        Pagination pagination = LogicFactory.getInstance().getPagination();
        Map<String, Integer> paginationParams = pagination.paginate(page, amountItems, amountOnPage);
        int totalPages = paginationParams.get("totalPages");
        if (page > totalPages && totalPages != 0) {
            request.getRequestDispatcher(commandPath).forward(request, response);
        } else {
            request.setAttribute("tours", tours);
            request.setAttribute(paginationKey, paginationParams);
            request.getRequestDispatcher(jspPath).forward(request, response);
        }
    }
}
