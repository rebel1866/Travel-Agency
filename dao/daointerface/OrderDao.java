package by.epamtc.stanislavmelnikov.dao.daointerface;

import by.epamtc.stanislavmelnikov.dao.exception.DaoException;
import by.epamtc.stanislavmelnikov.entity.Order;

import java.util.List;
import java.util.Map;

public interface OrderDao {

    int countDiscountPercent(int currentPrice, int amountOrders) throws DaoException;

    int addOrder(Order order) throws DaoException;

    List<Order> findOrdersByParams(Map<String, String> params) throws DaoException;

    int countOrdersByParams(Map<String, String> params) throws DaoException;
    void changeOrderStatus(int orderId, String status) throws DaoException;

    void removeOrder(int orderId) throws DaoException;

    void updateOrderStatus(int id, String status) throws DaoException;
}
