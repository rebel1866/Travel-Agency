package by.epamtc.stanislavmelnikov.dao.daoimpl;

import by.epamtc.stanislavmelnikov.dao.connection.ConnectionPool;
import by.epamtc.stanislavmelnikov.dao.daointerface.FeedbackDao;
import by.epamtc.stanislavmelnikov.dao.exception.DaoException;
import by.epamtc.stanislavmelnikov.dao.sqlgenerator.SQLGenerator;
import by.epamtc.stanislavmelnikov.entity.Feedback;
import by.epamtc.stanislavmelnikov.entity.Hotel;
import by.epamtc.stanislavmelnikov.entity.Tour;
import by.epamtc.stanislavmelnikov.entity.User;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SQLFeedbackDao implements FeedbackDao {
    private static final String sqlByParams = "SELECT feedback_id, fbk_rating, feedback_body, fbk_date_time, user_id, " +
            "tour_name, first_name, last_name, hotel_id, hotel_name FROM tour_feedbacks INNER JOIN users USING (user_id) " +
            "inner join tours using (tour_id) inner join hotels using(hotel_id) WHERE fbk_status = 'EXIST' and " +
            "user_status_id = 1 and tour_status='EXIST'";
    private static final String addFbkSQL = "insert tour_feedbacks (user_id, tour_id, feedback_body, fbk_date_time, " +
            "fbk_status, fbk_rating) values (?,?,?,?,'EXIST',?)";
    private static final String sqlCountFeedbacks = "SELECT count(*) as amount FROM tour_feedbacks INNER JOIN users USING (user_id) " +
            "inner join tours using (tour_id) inner join hotels using(hotel_id) WHERE fbk_status = 'EXIST' and " +
            "user_status_id = 1 and tour_status='EXIST'";
    private static final String removeSQL = "update tour_feedbacks set fbk_status = 'DELETED' where feedback_id = ?";

    @Override
    public List<Feedback> getFeedbacksByParams(Map<String, String> params) throws DaoException {
        String sql = SQLGenerator.generateSQL(params, sqlByParams);
        List<Feedback> feedbacks = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int feedbackId = resultSet.getInt("feedback_id");
                    String feedbackBody = resultSet.getString("feedback_body");
                    LocalDateTime fbkDateTime = LocalDateTime.parse(resultSet.getString("fbk_date_time"), formatter);
                    int userId = resultSet.getInt("user_id");
                    int rating = resultSet.getInt("fbk_rating");
                    String hotelName = resultSet.getString("hotel_name");
                    int hotelId = resultSet.getInt("hotel_id");
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");
                    Feedback feedback = buildFeedback(feedbackId, feedbackBody, fbkDateTime, userId, firstName, lastName, rating, hotelName, hotelId);
                    feedbacks.add(feedback);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while trying to get feedbacks from db", e);
        }
        connectionPool.releaseConnection(connection);
        return feedbacks;
    }

    @Override
    public void removeFeedback(int fbkId) throws DaoException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(removeSQL)) {
            preparedStatement.setInt(1, fbkId);
            int row = preparedStatement.executeUpdate();
            if (row == 0) throw new DaoException("deleting feedback has not been done");
        } catch (SQLException e) {
            throw new DaoException("Exception while trying to remove feedback", e);
        }
        connectionPool.releaseConnection(connection);
    }

    @Override
    public void addFeedback(Feedback feedback) throws DaoException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(addFbkSQL)) {
            preparedStatement.setInt(1, (int) feedback.getUser().getUserID());
            preparedStatement.setInt(2, feedback.getTour().getTourId());
            preparedStatement.setString(3, feedback.getFeedbackBody());
            preparedStatement.setString(4, feedback.getFbkDateTime().toString());
            preparedStatement.setInt(5, feedback.getFbkRating());
            int row = preparedStatement.executeUpdate();
            if (row == 0) throw new DaoException("adding feedback has not been done");
        } catch (SQLException e) {
            throw new DaoException("Exception while trying to add new feedback", e);
        }
        connectionPool.releaseConnection(connection);
    }

    @Override
    public int countFeedbacksByParams(Map<String, String> params) throws DaoException {
        String sql = SQLGenerator.generateSQL(params, sqlCountFeedbacks);
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
            throw new DaoException("Exception while trying to count feedbacks in db", e);
        }
        connectionPool.releaseConnection(connection);
        return amount;
    }

    public Feedback buildFeedback(int feedbackId, String feedbackBody, LocalDateTime fbkDateTime, int userId,
                                  String firstName, String lastName, int rating, String hotelName, int hotelId) {
        Feedback feedback = new Feedback();
        User user = new User();
        user.setUserID(userId);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        feedback.setUser(user);
        feedback.setFeedbackId(feedbackId);
        feedback.setFeedbackBody(feedbackBody);
        feedback.setFbkDateTime(fbkDateTime);
        feedback.setFbkRating(rating);
        Tour tour = new Tour();
        Hotel hotel = new Hotel();
        hotel.setHotelName(hotelName);
        hotel.setHotelId(hotelId);
        tour.setHotel(hotel);
        feedback.setTour(tour);
        return feedback;
    }
}
