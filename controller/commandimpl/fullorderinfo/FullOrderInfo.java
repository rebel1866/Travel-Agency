package by.epamtc.stanislavmelnikov.controller.commandimpl.fullorderinfo;

import by.epamtc.stanislavmelnikov.controller.commandinterface.Command;
import by.epamtc.stanislavmelnikov.entity.Order;
import by.epamtc.stanislavmelnikov.logic.exception.LogicException;
import by.epamtc.stanislavmelnikov.logic.factory.LogicFactory;
import by.epamtc.stanislavmelnikov.logic.logicinterface.OrderLogic;
import by.epamtc.stanislavmelnikov.logic.validation.Validation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FullOrderInfo implements Command {
    private static final FullOrderSub1 sub1 = new FullOrderSub1();
    private static final String exMessage = "Incorrect order id param";
    private static final String commandPath = "/executor?command=orders&page=1";
    private static final String backMessage = "Назад к просмотру регистраций";
    private static final String jspPath = "WEB-INF/jsp/full-order-info.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subcommand = request.getParameter(subcommandParam);
        if (!subcommand.equals(mainSubcommand)) {
            sub1.execute(request, response);
            return;
        }
        String orderId = request.getParameter(orderIdParam);
        if (!Validation.isCorrectInteger(orderId)) throw new ServletException(exMessage);
        LogicFactory logicFactory = LogicFactory.getInstance();
        OrderLogic orderLogic = logicFactory.getOrderLogic();
        Map<String, String> params = new HashMap<>();
        params.put(restrictionsKey, orderIdParam);
        params.put(orderIdParam, orderId);
        List<Order> orders;
        try {
            orders = orderLogic.findOrdersByParams(params);
        } catch (LogicException e) {
            Logger logger = LogManager.getLogger(FullOrderInfo.class);
            logger.error(e.getMessage(), e);
            throw new ServletException(e.getMessage(), e);
        }
        goToPage(request, response, orders);
    }

    public void goToPage(HttpServletRequest request, HttpServletResponse response, List<Order> orders)
            throws ServletException, IOException {
        Order order;
        if (orders.size() == 0) {
            request.setAttribute(linkKey, commandPath);
            request.setAttribute(messageKey, backMessage);
            request.getRequestDispatcher(jspNotAvailable).forward(request, response);
        } else {
            order = orders.get(0);
            request.setAttribute("order", order);
            request.getRequestDispatcher(jspPath).forward(request, response);
        }
    }
}
