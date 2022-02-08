package by.epamtc.stanislavmelnikov.controller.commandimpl.addorder;


import by.epamtc.stanislavmelnikov.controller.commandinterface.Command;
import by.epamtc.stanislavmelnikov.entity.Order;
import by.epamtc.stanislavmelnikov.entity.OrderDiscount;
import by.epamtc.stanislavmelnikov.entity.Tour;
import by.epamtc.stanislavmelnikov.entity.User;
import by.epamtc.stanislavmelnikov.logic.exception.LogicException;
import by.epamtc.stanislavmelnikov.logic.factory.LogicFactory;
import by.epamtc.stanislavmelnikov.logic.logicinterface.OrderLogic;
import by.epamtc.stanislavmelnikov.logic.logicinterface.TourLogic;
import by.epamtc.stanislavmelnikov.logic.logicinterface.UserLogic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AddOrderSub1 implements Command {
    private static final AddOrderSub2 sub2 = new AddOrderSub2();
    private static final String backMessage = "Назад к туру";
    private static final String jspOrderPath = "WEB-INF/jsp/add-new-order.jsp";
    private static final String commandPath = "/executor?command=full_tour_info&id=";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subCommand = request.getParameter(subcommandParam);
        if (!subCommand.equals(subcommand1)) {
            sub2.execute(request, response);
            return;
        }
        HttpSession session = request.getSession(true);
        Map<String, String> authorization = (Map<String, String>) session.getAttribute("authorization");
        int userId = Integer.parseInt(authorization.get(userIdParam));
        int tourId = Integer.parseInt(request.getParameter(tourIdParam));
        LogicFactory logicFactory = LogicFactory.getInstance();
        OrderLogic orderLogic = logicFactory.getOrderLogic();
        User user = findUser(userId);
        Tour tour = findTour(tourId);
        int finalPrice;
        int discount;
        int currentPrice = tour.getPrice();
        Map<String, String> params = new HashMap<>();
        params.put(restrictionsKey, userIdParam);
        params.put(userIdParam, String.valueOf(userId));
        try {
            int amountOrders = orderLogic.countAmountOrders(params);
            discount = orderLogic.countDiscount(currentPrice, amountOrders);
            finalPrice = orderLogic.countFinalPrice(currentPrice, discount);
        } catch (LogicException e) {
            Logger logger = LogManager.getLogger(AddOrderSub1.class);
            logger.error(e.getMessage(), e);
            throw new ServletException(e.getMessage(), e);
        }
        Order order = buildOrder(user, tour, finalPrice, discount);
        goToPage(request, response, order, session, tourId);
    }

    public Tour findTour(int tourId) throws ServletException {
        Tour tour;
        LogicFactory logicFactory = LogicFactory.getInstance();
        TourLogic tourLogic = logicFactory.getTourLogic();
        try {
            tour = tourLogic.findTourById(tourId);
        } catch (LogicException e) {
            throw new ServletException(e.getMessage(), e);
        }
        return tour;
    }

    public User findUser(int userId) throws ServletException {
        User user;
        LogicFactory logicFactory = LogicFactory.getInstance();
        UserLogic userLogic = logicFactory.getUserLogic();
        Map<String, String> params = new HashMap<>();
        params.put(restrictionsKey, userIdParam);
        params.put(userIdParam, String.valueOf(userId));
        try {
            user = userLogic.findUsersByParams(params).get(0);
        } catch (LogicException e) {
            throw new ServletException(e.getMessage(), e);
        }
        return user;
    }

    public void goToPage(HttpServletRequest request, HttpServletResponse response, Order order, HttpSession session, int tourId)
            throws ServletException, IOException {
        if (session.getAttribute(accessAllowedKey) != null) {
            request.setAttribute("order", order);
            request.getRequestDispatcher(jspOrderPath).forward(request, response);
            session.removeAttribute(accessAllowedKey);
        } else {
            request.setAttribute(linkKey, commandPath + tourId);
            request.setAttribute(messageKey, backMessage);
            request.getRequestDispatcher(jspNotAvailable).forward(request, response);
        }
    }

    public Order buildOrder(User user, Tour tour, int finalPrice, int discount) {
        Order order = new Order();
        order.setUser(user);
        order.setTour(tour);
        order.setFinalPrice(finalPrice);
        OrderDiscount orderDiscount = new OrderDiscount();
        orderDiscount.setDiscountPercents(discount);
        order.setDiscount(orderDiscount);
        return order;
    }
}
