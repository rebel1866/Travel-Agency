package by.epamtc.stanislavmelnikov.dao.daoimpl;

import by.epamtc.stanislavmelnikov.dao.connection.ConnectionPool;
import by.epamtc.stanislavmelnikov.dao.daointerface.TourDao;
import by.epamtc.stanislavmelnikov.dao.exception.DaoException;
import by.epamtc.stanislavmelnikov.dao.sqlgenerator.SQLGenerator;
import by.epamtc.stanislavmelnikov.entity.*;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class SQLTourDao implements TourDao {
    private static final String sqlToursByParams = "select t.tour_id, amount_adults, amount_children, relevance, tour_name," +
            " departure, comeback, price, hotel_name, rating, h.hotel_id, location FROM tours t left join hotels h on h.hotel_id =" +
            " t.hotel_id and hotel_status = 'EXIST'  left join resorts r on r.resort_id = h.resort_id and resort_status =" +
            " 'EXIST' WHERE tour_status = 'EXIST'";
    private static final String sqlCountTours = "SELECT count(*) as amount FROM tours t left join hotels h on h.hotel_id = " +
            "t.hotel_id and hotel_status = 'EXIST'  left join resorts r on r.resort_id = h.resort_id and resort_status = " +
            "'EXIST' WHERE tour_status = 'EXIST'";
    private static final String fullTourSql = "select t.tour_id, tour_name, transport, departure, comeback, price, " +
            "feeding, amount_adults, amount_children, burning_status, relevance, hotel_room_type, amount_seats, " +
            "h.hotel_id, hotel_name, rating, hotel_description, amount_rooms, type_location, infrastructure, hotel_image_id, " +
            "image_path, feedback_id, fbk_date_time, fbk_rating, feedback_body, r.resort_id, resort_name, location, " +
            "population, resort_description, first_name, last_name, u.user_id from tours t left join hotels h on h.hotel_id " +
            "= t.hotel_id  and hotel_status = 'EXIST' left join hotel_images i on i.hotel_id = h.hotel_id and " +
            " hotel_image_status = 'EXIST' left join tour_feedbacks f on f.tour_id = t.tour_id and f.fbk_status = 'EXIST'" +
            " left join resorts r on r.resort_id = h.resort_id and resort_status = 'EXIST' left join users u on " +
            " u.user_id = f.user_id and u.user_status_id = 1 where tour_status = 'EXIST' and t.tour_id = ?";
    private static final String addTourSQL = "insert tours (tour_name,hotel_id,transport, departure, comeback, price," +
            " feeding, amount_adults, amount_children, amount_seats, hotel_room_type, burning_status, relevance, tour_status)" +
            " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String removeTourSql = "update tours t left join tour_feedbacks f on f.tour_id = " +
            "t.tour_id left join orders o on o.tour_id = t.tour_id set tour_status = 'DELETED', fbk_status= " +
            "'DELETED', order_status = 'DELETED' where t.tour_id = ?";
    private static final String updateTourSql = "update tours set tour_name = ?,hotel_id =?,transport=?, departure=?," +
            " comeback=?, price=?, feeding=?, amount_adults=?, amount_children=?, amount_seats=?, hotel_room_type=?, " +
            "burning_status=?, relevance=?, tour_status=? where tour_id = ?";

    @Override
    public List<Tour> findToursByParams(Map<String, String> params) throws DaoException {
        String sql = SQLGenerator.generateSQL(params, sqlToursByParams);
        List<Tour> tours = new ArrayList<>();
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int tourId = resultSet.getInt("tour_id");
                    String tourName = resultSet.getString("tour_name");
                    LocalDate departure = LocalDate.parse(resultSet.getString("departure"));
                    LocalDate comeback = LocalDate.parse(resultSet.getString("comeback"));
                    int price = resultSet.getInt("price");
                    String hotelName = resultSet.getString("hotel_name");
                    int hotelRating = resultSet.getInt("rating");
                    int amountAdults = resultSet.getInt("amount_adults");
                    int amountChildren = resultSet.getInt("amount_children");
                    Tour tour = buildTour(tourId, tourName, departure, comeback, price, hotelName, hotelRating, amountAdults, amountChildren);
                    tours.add(tour);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while trying to get tours from db", e);
        }
        connectionPool.releaseConnection(connection);
        return tours;
    }

    @Override
    public int countToursByParams(Map<String, String> params) throws DaoException {
        String sql = SQLGenerator.generateSQL(params, sqlCountTours);
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
            throw new DaoException("Exception while trying to count tours in db", e);
        }
        connectionPool.releaseConnection(connection);
        return amount;
    }

    @Override
    public Tour findTourById(int id) throws DaoException {
        Tour tour = new Tour();
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(fullTourSql,
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    buildFullTour(resultSet, tour);
                    resultSet.beforeFirst();
                    Set<HotelImage> images = new HashSet<>();
                    buildImages(images, tour, resultSet);
                    resultSet.beforeFirst();
                    Set<Feedback> feedbacks = new HashSet<>();
                    buildFeedbacks(resultSet, feedbacks, tour);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while trying to get full tour info from db", e);
        }
        connectionPool.releaseConnection(connection);
        return tour;
    }


    @Override
    public void addTour(Tour tour) throws DaoException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(addTourSQL)) {
            preparedStatement.setString(1, tour.getTourName());
            preparedStatement.setInt(2, tour.getHotel().getHotelId());
            preparedStatement.setString(3, tour.getTransport());
            preparedStatement.setString(4, tour.getDeparture().toString());
            preparedStatement.setString(5, tour.getComeback().toString());
            preparedStatement.setInt(6, tour.getPrice());
            preparedStatement.setString(7, tour.getFeeding());
            preparedStatement.setInt(8, tour.getAmountAdults());
            preparedStatement.setInt(9, tour.getAmountChildren());
            preparedStatement.setInt(10, tour.getAmountSeats());
            preparedStatement.setString(11, tour.getHotelRoomType());
            preparedStatement.setInt(12, tour.isBurningStatus() ? 1 : 0);
            preparedStatement.setInt(13, tour.getRelevance());
            preparedStatement.setString(14, tour.getTourStatus());
            int rows = preparedStatement.executeUpdate();
            if (rows == 0) throw new DaoException("tour adding operation is failed");
        } catch (SQLException e) {
            throw new DaoException("exception while trying to add new tour", e);
        }
        connectionPool.releaseConnection(connection);
    }


    @Override
    public void removeTourById(int id) throws DaoException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(removeTourSql)) {
            preparedStatement.setInt(1, id);
            int rows = preparedStatement.executeUpdate();
            if (rows == 0) throw new DaoException("Tour is not deleted");
        } catch (SQLException e) {
            throw new DaoException("exception while trying to remove tour", e);
        }
        connectionPool.releaseConnection(connection);
    }

    @Override
    public void updateTour(Tour tour) throws DaoException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateTourSql)) {
            preparedStatement.setString(1, tour.getTourName());
            preparedStatement.setInt(2, tour.getHotel().getHotelId());
            preparedStatement.setString(3, tour.getTransport());
            preparedStatement.setString(4, tour.getDeparture().toString());
            preparedStatement.setString(5, tour.getComeback().toString());
            preparedStatement.setInt(6, tour.getPrice());
            preparedStatement.setString(7, tour.getFeeding());
            preparedStatement.setInt(8, tour.getAmountAdults());
            preparedStatement.setInt(9, tour.getAmountChildren());
            preparedStatement.setInt(10, tour.getAmountSeats());
            preparedStatement.setString(11, tour.getHotelRoomType());
            preparedStatement.setInt(12, tour.isBurningStatus() ? 1 : 0);
            preparedStatement.setInt(13, tour.getRelevance());
            preparedStatement.setString(14, tour.getTourStatus());
            preparedStatement.setInt(15, tour.getTourId());
            int rows = preparedStatement.executeUpdate();
            if (rows == 0) throw new DaoException("Tour is not updated");
        } catch (SQLException e) {
            throw new DaoException("exception while trying to update tour", e);
        }
        connectionPool.releaseConnection(connection);
    }

    public Tour buildTour(int tourId, String tourName, LocalDate departure, LocalDate comeback, int price,
                          String hotelName, int hotelRating, int amountAdults, int amountChildren) {
        Tour tour = new Tour();
        Hotel hotel = new Hotel();
        hotel.setHotelName(hotelName);
        hotel.setRating(hotelRating);
        tour.setHotel(hotel);
        tour.setTourId(tourId);
        tour.setTourName(tourName);
        tour.setDeparture(departure);
        tour.setComeback(comeback);
        tour.setPrice(price);
        tour.setAmountAdults(amountAdults);
        tour.setAmountChildren(amountChildren);
        return tour;
    }

    public void buildFullTour(ResultSet resultSet, Tour tour) throws SQLException {
        tour.setTourId(resultSet.getInt("tour_id"));
        tour.setTourName(resultSet.getString("tour_name"));
        tour.setTransport(resultSet.getString("transport"));
        tour.setDeparture(LocalDate.parse(resultSet.getString("departure")));
        tour.setComeback(LocalDate.parse(resultSet.getString("comeback")));
        tour.setPrice(resultSet.getInt("price"));
        tour.setFeeding(resultSet.getString("feeding"));
        tour.setAmountAdults(resultSet.getInt("amount_adults"));
        tour.setAmountChildren(resultSet.getInt("amount_children"));
        tour.setBurningStatus(resultSet.getString("burning_status").equals("1"));
        tour.setRelevance(resultSet.getInt("relevance"));
        tour.setHotelRoomType(resultSet.getString("hotel_room_type"));
        tour.setAmountSeats(resultSet.getInt("amount_seats"));
        Hotel hotel = new Hotel();
        buildHotel(resultSet, hotel);
        tour.setHotel(hotel);
        Resort resort = new Resort();
        buildResort(resultSet, resort);
        tour.getHotel().setResort(resort);
    }

    public void buildResort(ResultSet resultSet, Resort resort) throws SQLException {
        resort.setResortId(resultSet.getInt("resort_id"));
        resort.setResortName(resultSet.getString("resort_name"));
        resort.setLocation(resultSet.getString("location"));
        resort.setPopulation(resultSet.getInt("population"));
        resort.setResortDescription(resultSet.getString("resort_description"));
    }

    public void buildHotel(ResultSet resultSet, Hotel hotel) throws SQLException {
        hotel.setHotelName(resultSet.getString("hotel_name"));
        hotel.setHotelId(resultSet.getInt("hotel_id"));
        hotel.setRating(resultSet.getInt("rating"));
        hotel.setHotelDescription(resultSet.getString("hotel_description"));
        hotel.setAmountRooms(resultSet.getInt("amount_rooms"));
        hotel.setTypeLocation(resultSet.getString("type_location"));
        hotel.setInfrastructure(resultSet.getString("infrastructure"));
    }

    public void buildFeedbacks(ResultSet resultSet, Set<Feedback> feedbacks, Tour tour) throws SQLException {
        while (resultSet.next()) {
            Feedback feedback = new Feedback();
            User user = new User();
            feedback.setFeedbackId(resultSet.getInt("feedback_id"));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String dateTime = resultSet.getString("fbk_date_time");
            if (dateTime != null) {
                LocalDateTime fbkDateTime = LocalDateTime.parse(dateTime, formatter);
                feedback.setFbkDateTime(fbkDateTime);
            }
            feedback.setFbkRating(resultSet.getInt("fbk_rating"));
            feedback.setFeedbackBody(resultSet.getString("feedback_body"));
            user.setUserID(resultSet.getInt("user_id"));
            user.setFirstName(resultSet.getString("first_name"));
            user.setLastName(resultSet.getString("last_name"));
            feedback.setUser(user);
            feedbacks.add(feedback);
        }
        List<Feedback> feedbackList = new ArrayList<>(feedbacks);
        tour.setFeedbacks(feedbackList);
    }

    public void buildImages(Set<HotelImage> images, Tour tour, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            HotelImage image = new HotelImage();
            image.setImageId(resultSet.getInt("hotel_image_id"));
            image.setImagePath(resultSet.getString("image_path"));
            images.add(image);
        }
        List<HotelImage> imagesList = new ArrayList<>(images);
        tour.getHotel().setHotelImages(imagesList);
    }
}
