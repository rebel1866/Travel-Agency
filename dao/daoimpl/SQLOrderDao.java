package by.epamtc.stanislavmelnikov.dao.daoimpl;

import by.epamtc.stanislavmelnikov.dao.connection.ConnectionPool;
import by.epamtc.stanislavmelnikov.dao.daointerface.OrderDao;
import by.epamtc.stanislavmelnikov.dao.exception.DaoException;
import by.epamtc.stanislavmelnikov.dao.sqlgenerator.SQLGenerator;
import by.epamtc.stanislavmelnikov.entity.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SQLOrderDao implements OrderDao {
    private static final String sqlDiscount = "select discount_percentes from order_discounts where amount_orders=?" +
            " and ? between price_from and price_to";
    private static final String sqlAddOrder = "insert orders (user_id, tour_id,order_status,order_discount_id, " +
            "order_final_price, order_comment) values(?,?,'Awaiting', (select distinct order_discount_id from order_discounts where " +
            "discount_percentes = ?),?,?)";
    private static final String orderIdSQl = "select order_id from orders where user_id =? and tour_id=? order by order_id desc";
    private static final String sqlByParams = "select order_id,u.user_id,t.tour_id, order_status, discount_percentes, " +
            " order_final_price, order_comment, tour_name, departure, comeback, price, first_name, last_name, telephone, " +
            " email from orders o inner join users u on u.user_id=o.user_id inner join tours t on t.tour_id=o.tour_id inner join " +
            " order_discounts d on o.order_discount_id = d.order_discount_id where order_status !='Deleted'";
    private static final String sqlCountOrders = "select count(*) as amount from orders o inner join users u on" +
            " u.user_id=o.user_id inner join tours t on t.tour_id=o.tour_id inner join order_discounts d on o.order_discount_id" +
            " = d.order_discount_id where order_status !='Deleted'";
    private static final String changeStatusSQl = "update orders set order_status = ? where order_id=?";
    private static final String removeOrderSQL = "update orders set order_status = 'Deleted' where order_id=?";
    private static final String updateStatusSQL = "update orders set order_status = ? where tour_id=?";


    @Override
    public int countDiscountPercent(int currentPrice, int amountOrders) throws DaoException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        int discount = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlDiscount)) {
            preparedStatement.setInt(1, amountOrders);
            preparedStatement.setInt(2, currentPrice);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    discount = resultSet.getInt("discount_percentes");
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while trying to count discount", e);
        }
        connectionPool.releaseConnection(connection);
        return discount;
    }

    @Override
    public int addOrder(Order order) throws DaoException {
        long userId = order.getUser().getUserID();
        int tourId = order.getTour().getTourId();
        int discountPercent = order.getDiscount().getDiscountPercents();
        int finalPrice = order.getFinalPrice();
        int orderId;
        String comment = order.getComment();
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlAddOrder)) {
            preparedStatement.setInt(1, (int) userId);
            preparedStatement.setInt(2, tourId);
            preparedStatement.setInt(3, discountPercent);
            preparedStatement.setInt(4, finalPrice);
            preparedStatement.setString(5, comment);
            int row = preparedStatement.executeUpdate();
            if (row == 0) throw new DaoException("adding order has not been done");
            try (PreparedStatement preparedStatement1 = connection.prepareStatement(orderIdSQl)) {
                preparedStatement1.setInt(1, (int) userId);
                preparedStatement1.setInt(2, tourId);
                try (ResultSet resultSet = preparedStatement1.executeQuery()) {
                    resultSet.next();
                    orderId = resultSet.getInt("order_id");
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while trying to add new order", e);
        }
        connectionPool.releaseConnection(connection);
        return orderId;
    }

    @Override
    public List<Order> findOrdersByParams(Map<String, String> params) throws DaoException {
        String sql = SQLGenerator.generateSQL(params, sqlByParams);
        List<Order> orders = new ArrayList<>();
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int orderId = resultSet.getInt("order_id");
                    int userId = resultSet.getInt("user_id");
                    int tourId = resultSet.getInt("tour_id");
                    String orderStatus = resultSet.getString("order_status");
                    int discount = resultSet.getInt("discount_percentes");
                    int finalPrice = resultSet.getInt("order_final_price");
                    String orderComment = resultSet.getString("order_comment");
                    String tourName = resultSet.getString("tour_name");
                    String departure = resultSet.getString("departure");
                    String comeback = resultSet.getString("comeback");
                    int price = resultSet.getInt("price");
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");
                    String telephone = resultSet.getString("telephone");
                    String email = resultSet.getString("email");
                    Order order = buildOrders(orderId, userId, tourId, orderStatus, discount, finalPrice, orderComment,
                            tourName, departure, comeback, price, firstName, lastName, telephone, email);
                    orders.add(order);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while trying to get orders from db", e);
        }
        connectionPool.releaseConnection(connection);
        return orders;
    }


    @Override
    public int countOrdersByParams(Map<String, String> params) throws DaoException {
        String sql = SQLGenerator.generateSQL(params, sqlCountOrders);
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        int amount = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    amount = resultSet.getInt("amount");
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while trying to count orders in db", e);
        }
        connectionPool.releaseConnection(connection);
        return amount;
    }

    @Override
    public void changeOrderStatus(int orderId, String status) throws DaoException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(changeStatusSQl)) {
            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, orderId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Exception while trying to change order status", e);
        }
        connectionPool.releaseConnection(connection);
    }

    @Override
    public void removeOrder(int orderId) throws DaoException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(removeOrderSQL)) {
            preparedStatement.setInt(1, orderId);
            int row = preparedStatement.executeUpdate();
            if (row == 0) throw new DaoException("removing order has not been done");
        } catch (SQLException e) {
            throw new DaoException("Exception while trying to remove order", e);
        }
        connectionPool.releaseConnection(connection);
    }

    @Override
    public void updateOrderStatus(int tourId, String status) throws DaoException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateStatusSQL)) {
            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, tourId);
            int row = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Exception while trying to update order status", e);
        }
        connectionPool.releaseConnection(connection);
    }

    public Order buildOrders(int orderId, int userId, int tourId, String orderStatus, int discount, int finalPrice,
                             String orderComment, String tourName, String departure, String comeback, int price,
                             String firstName, String lastName, String telephone, String email) {
        Order order = new Order();
        User user = new User();
        Tour tour = new Tour();
        OrderDiscount orderDiscount = new OrderDiscount();
        order.setOrderId(orderId);
        user.setUserID(userId);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setTelephone(telephone);
        user.setEmail(email);
        order.setUser(user);
        tour.setTourId(tourId);
        tour.setTourName(tourName);
        tour.setDeparture(LocalDate.parse(departure));
        tour.setComeback(LocalDate.parse(comeback));
        tour.setPrice(price);
        order.setTour(tour);
        order.setOrderStatus(orderStatus);
        orderDiscount.setDiscountPercents(discount);
        order.setDiscount(orderDiscount);
        order.setFinalPrice(finalPrice);
        order.setComment(orderComment);
        return order;
    }
}
