package by.epamtc.stanislavmelnikov.logic.logicinterface;

import by.epamtc.stanislavmelnikov.entity.Order;
import by.epamtc.stanislavmelnikov.logic.exception.LogicException;
import com.mysql.cj.log.Log;

import java.util.List;
import java.util.Map;

public interface OrderLogic {
    int addOrder(int userId, int tourId, String orderComment) throws LogicException;
    int countDiscount(int currentPrice, int amountOrders) throws LogicException;
    int countFinalPrice(int currentPrice, int discountPercent);
    int countAmountOrders(Map<String, String> params) throws LogicException;
    int countOrdersByParams(Map<String,String> params) throws LogicException;
    List<Order> findOrdersByParams(Map<String,String> params) throws LogicException;
    void acceptOrder(int orderId) throws LogicException;

    void denyOrder(int orderId) throws LogicException;

    void removeOrder(int orderId) throws LogicException;

    void runStatusUpdater() throws LogicException;
}
