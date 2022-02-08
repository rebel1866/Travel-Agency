package by.epamtc.stanislavmelnikov.controller.commandimpl.hotelmanager;

import by.epamtc.stanislavmelnikov.controller.commandinterface.Command;
import by.epamtc.stanislavmelnikov.controller.controllerutils.ParamsGenerator;
import by.epamtc.stanislavmelnikov.controller.controllerutils.PropertyLoader;
import by.epamtc.stanislavmelnikov.entity.Hotel;
import by.epamtc.stanislavmelnikov.logic.exception.LogicException;
import by.epamtc.stanislavmelnikov.logic.factory.LogicFactory;
import by.epamtc.stanislavmelnikov.logic.logicinterface.HotelLogic;
import by.epamtc.stanislavmelnikov.logic.logicinterface.Pagination;
import by.epamtc.stanislavmelnikov.logic.validation.Validation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class HotelManager implements Command {
    private static final String keyFields = "fields.hotels";
    private static final String amountOnPageKey = "amount.on.page";
    private static final String jspPath = "WEB-INF/jsp/hotels.jsp";
    private static final String commandPath = "executor?command=hotels&page=1";
    private static final HotelManagerAjax sub1 = new HotelManagerAjax();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subcommand = request.getParameter(subcommandParam);
        if (subcommand != null) {
            sub1.execute(request, response);
            return;
        }
        LogicFactory logicFactory = LogicFactory.getInstance();
        HotelLogic hotelLogic = logicFactory.getHotelLogic();
        int amountOnPage = Integer.parseInt(PropertyLoader.getProperty(amountOnPageKey));
        Map<String, String> paramsCount = ParamsGenerator.generateParams(request, amountOnPage, false, keyFields);
        Map<String, String> hotelParams = ParamsGenerator.generateParams(request, amountOnPage, true, keyFields);
        List<Hotel> hotels;
        int amountItems;
        try {
            amountItems = hotelLogic.countHotelsByParams(paramsCount);
            hotels = hotelLogic.findHotelsByParams(hotelParams);
        } catch (LogicException e) {
            Logger logger = LogManager.getLogger(HotelManager.class);
            logger.error(e.getMessage(), e);
            throw new ServletException(e.getMessage(), e);
        }
        int page = getPage(request.getParameter(pageKey));
        Pagination pagination = logicFactory.getPagination();
        goToPage(page, amountItems, hotels, request, response, amountOnPage, pagination);
    }

    private int getPage(String pageStr) {
        int page;
        if (!Validation.isCorrectInteger(pageStr)) {
            page = 1;
        } else {
            page = Integer.parseInt(pageStr);
        }
        return page;
    }

    private void goToPage(int page, int amountItems, List<Hotel> hotels, HttpServletRequest request,
                          HttpServletResponse response, int amountOnPage, Pagination pagination) throws ServletException, IOException {
        Map<String, Integer> paginationParams = pagination.paginate(page, amountItems, amountOnPage);
        int totalPages = paginationParams.get("totalPages");
        if (page > totalPages && totalPages != 0) {
            request.getRequestDispatcher(commandPath).forward(request, response);
        } else {
            request.setAttribute("hotels", hotels);
            request.setAttribute(paginationKey, paginationParams);
            request.getRequestDispatcher(jspPath).forward(request, response);
        }
    }
}
