package by.epamtc.stanislavmelnikov.controller.commandimpl;

import by.epamtc.stanislavmelnikov.controller.commandinterface.Command;
import by.epamtc.stanislavmelnikov.controller.controllerutils.ParamsGenerator;
import by.epamtc.stanislavmelnikov.controller.controllerutils.PropertyLoader;
import by.epamtc.stanislavmelnikov.entity.Hotel;
import by.epamtc.stanislavmelnikov.entity.Order;
import by.epamtc.stanislavmelnikov.logic.exception.LogicException;
import by.epamtc.stanislavmelnikov.logic.factory.LogicFactory;
import by.epamtc.stanislavmelnikov.logic.logicinterface.HotelLogic;
import by.epamtc.stanislavmelnikov.logic.logicinterface.OrderLogic;
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

public class OrderManager implements Command {
    private static final String keyFields = "fields.orders";
    private static final String amountOnPageKey = "amount.on.page";
    private static final String jspPath = "WEB-INF/jsp/orders.jsp";
    private static final String commandPath = "executor?command=orders&page=1";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LogicFactory logicFactory = LogicFactory.getInstance();
        OrderLogic orderLogic = logicFactory.getOrderLogic();
        String amountStr = PropertyLoader.getProperty(amountOnPageKey);
        int amountOnPage = Integer.parseInt(amountStr);
        Map<String, String> paramsCount = ParamsGenerator.generateParams(request, amountOnPage, false, keyFields);
        Map<String, String> ordersParams = ParamsGenerator.generateParams(request, amountOnPage, true, keyFields);
        List<Order> orders;
        int amountItems;
        try {
            amountItems = orderLogic.countOrdersByParams(paramsCount);
            orders = orderLogic.findOrdersByParams(ordersParams);
        } catch (LogicException e) {
            Logger logger = LogManager.getLogger(OrderManager.class);
            logger.error(e.getMessage(), e);
            throw new ServletException(e.getMessage(), e);
        }
        String pageStr = request.getParameter(pageKey);
        int page;
        if (!Validation.isCorrectInteger(pageStr)) {
            page = 1;
        } else page = Integer.parseInt(pageStr);
        goToPage(request, response, page, amountItems, orders, amountOnPage);
    }

    private void goToPage(HttpServletRequest request, HttpServletResponse response, int page,
                          int amountItems, List<Order> orders, int amountOnPage) throws ServletException, IOException {
        Pagination pagination = LogicFactory.getInstance().getPagination();
        Map<String, Integer> paginationParams = pagination.paginate(page, amountItems, amountOnPage);
        int totalPages = paginationParams.get("totalPages");
        if (page > totalPages && totalPages != 0) {
            request.getRequestDispatcher(commandPath).forward(request, response);
        } else {
            request.setAttribute("orders", orders);
            request.setAttribute(paginationKey, paginationParams);
            request.getRequestDispatcher(jspPath).forward(request, response);
        }
    }
}
