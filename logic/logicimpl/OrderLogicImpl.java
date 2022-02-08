package by.epamtc.stanislavmelnikov.logic.logicimpl;

import by.epamtc.stanislavmelnikov.controller.executor.Executor;
import by.epamtc.stanislavmelnikov.dao.daointerface.OrderDao;
import by.epamtc.stanislavmelnikov.dao.daointerface.TourDao;
import by.epamtc.stanislavmelnikov.dao.exception.DaoException;
import by.epamtc.stanislavmelnikov.dao.factory.DaoFactory;
import by.epamtc.stanislavmelnikov.entity.Order;
import by.epamtc.stanislavmelnikov.entity.OrderDiscount;
import by.epamtc.stanislavmelnikov.entity.Tour;
import by.epamtc.stanislavmelnikov.entity.User;
import by.epamtc.stanislavmelnikov.logic.exception.LogicException;
import by.epamtc.stanislavmelnikov.logic.logicinterface.OrderLogic;
import by.epamtc.stanislavmelnikov.logic.validation.Validation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class OrderLogicImpl implements OrderLogic {

    @Override
    public int addOrder(int userId, int tourId, String orderComment) throws LogicException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        OrderDao orderDao = daoFactory.getOrderDao();
        TourDao tourDao = daoFactory.getTourDao();
        Tour tour;
        orderComment = Validation.excludeInjections(orderComment);
        try {
            tour = tourDao.findTourById(tourId);
        } catch (DaoException e) {
            throw new LogicException(e.getMessage(), e);
        }
        Map<String, String> params = new HashMap<>();
        params.put("restrictions", "user_id");
        params.put("user_id", String.valueOf(userId));
        int orderId;
        int currentPrice = tour.getPrice();
        int amountOrders = countAmountOrders(params);
        int discountPercent = countDiscount(tour.getPrice(), amountOrders);
        int finalPrice = countFinalPrice(currentPrice, discountPercent);
        Order order = buildOrder(userId, tour, finalPrice, discountPercent, orderComment);
        try {
            orderId = orderDao.addOrder(order);
        } catch (DaoException e) {
            throw new LogicException(e.getMessage(), e);
        }
        return orderId;
    }


    @Override
    public int countDiscount(int currentPrice, int amountOrders) throws LogicException {
        int discountPercent;
        DaoFactory daoFactory = DaoFactory.getInstance();
        OrderDao orderDao = daoFactory.getOrderDao();
        try {
            discountPercent = orderDao.countDiscountPercent(currentPrice, amountOrders);
        } catch (DaoException e) {
            throw new LogicException(e.getMessage(), e);
        }
        return discountPercent;
    }

    @Override
    public int countFinalPrice(int currentPrice, int discountPercent) {
        double totalAmountPercentes = 100.0;
        double discount = currentPrice * (discountPercent / totalAmountPercentes);
        return (int) (currentPrice - discount);
    }

    @Override
    public int countAmountOrders(Map<String, String> params) throws LogicException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        OrderDao orderDao = daoFactory.getOrderDao();
        int amountOrders;
        try {
            amountOrders = orderDao.countOrdersByParams(params);
        } catch (DaoException e) {
            throw new LogicException();
        }
        return amountOrders;
    }

    @Override
    public int countOrdersByParams(Map<String, String> params) throws LogicException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        OrderDao orderDao = daoFactory.getOrderDao();
        Validation.excludeInjections(params);
        int amount;
        try {
            amount = orderDao.countOrdersByParams(params);
        } catch (DaoException e) {
            throw new LogicException(e.getMessage(), e);
        }
        return amount;
    }

    @Override
    public List<Order> findOrdersByParams(Map<String, String> params) throws LogicException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        OrderDao orderDao = daoFactory.getOrderDao();
        Validation.excludeInjections(params);
        List<Order> orders;
        try {
            orders = orderDao.findOrdersByParams(params);
        } catch (DaoException e) {
            throw new LogicException(e.getMessage(), e);
        }
        return orders;
    }

    @Override
    public void acceptOrder(int orderId) throws LogicException {
        String activeStatus = "Active";
        DaoFactory daoFactory = DaoFactory.getInstance();
        OrderDao orderDao = daoFactory.getOrderDao();
        try {
            orderDao.changeOrderStatus(orderId, activeStatus);
        } catch (DaoException e) {
            throw new LogicException(e.getMessage(), e);
        }
    }

    @Override
    public void denyOrder(int orderId) throws LogicException {
        String deniedStatus = "Denied";
        DaoFactory daoFactory = DaoFactory.getInstance();
        OrderDao orderDao = daoFactory.getOrderDao();
        try {
            orderDao.changeOrderStatus(orderId, deniedStatus);
        } catch (DaoException e) {
            throw new LogicException(e.getMessage(), e);
        }
    }

    @Override
    public void removeOrder(int orderId) throws LogicException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        OrderDao orderDao = daoFactory.getOrderDao();
        try {
            orderDao.removeOrder(orderId);
        } catch (DaoException e) {
            throw new LogicException(e.getMessage(), e);
        }
    }

    @Override
    public void runStatusUpdater() throws LogicException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        TourDao tourDao = daoFactory.getTourDao();
        OrderDao orderDao = daoFactory.getOrderDao();
        while (true) {
            LocalDate currentDate = LocalDate.now();
            List<Tour> tours;
            Map<String, String> params = new HashMap<>();
            params.put("restrictions", "comeback");
            params.put("comeback", currentDate.toString());
            try {
                tours = tourDao.findToursByParams(params);
                List<Integer> tourIds = tours.stream().map(Tour::getTourId).collect(Collectors.toList());
                for (Integer tourId : tourIds) {
                    orderDao.updateOrderStatus(tourId, "Non-active");
                }
            } catch (DaoException e) {
                throw new LogicException(e.getMessage(), e);
            }
            try {
                TimeUnit.HOURS.sleep(1);
            } catch (InterruptedException e) {
                throw new LogicException(e.getMessage(), e);
            }
        }
    }

    public Order buildOrder(int userId, Tour tour, int finalPrice, int discountPercent, String orderComment) {
        Order order = new Order();
        User user = new User();
        user.setUserID(userId);
        order.setUser(user);
        order.setTour(tour);
        order.setFinalPrice(finalPrice);
        OrderDiscount orderDiscount = new OrderDiscount();
        orderDiscount.setDiscountPercents(discountPercent);
        order.setDiscount(orderDiscount);
        order.setComment(orderComment);
        return order;
    }
}
