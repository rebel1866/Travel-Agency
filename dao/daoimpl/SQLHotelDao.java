package by.epamtc.stanislavmelnikov.dao.daoimpl;

import by.epamtc.stanislavmelnikov.dao.connection.ConnectionPool;
import by.epamtc.stanislavmelnikov.dao.daointerface.HotelDao;
import by.epamtc.stanislavmelnikov.dao.exception.DaoException;
import by.epamtc.stanislavmelnikov.dao.sqlgenerator.SQLGenerator;
import by.epamtc.stanislavmelnikov.entity.*;

import java.sql.*;
import java.util.*;

public class SQLHotelDao implements HotelDao {
    private static final String sqlByParams = "SELECT hotel_name, rating, resort_name, hotel_id, hotel_description FROM " +
            "hotels h left join resorts r on r.resort_id = h.resort_id  and resort_status = 'EXIST' WHERE hotel_status =" +
            " 'EXIST' ";
    private static final String sqlCountHotels = "select count(*) as amount from hotels h left join resorts r " +
            "on r.resort_id = h.resort_id and resort_status= 'EXIST' where hotel_status = 'EXIST'";
    private static final String addHotelSql = "insert hotels (hotel_name, resort_id, rating , hotel_description," +
            " amount_rooms, type_location, infrastructure, hotel_status) values(?,?,?,?,?,?,?, 'EXIST')";
    private static final String lastIdSql = "select max(hotel_id) as max from hotels";
    private static final String addImagesSql = "insert hotel_images (hotel_id, image_path, hotel_image_status) values(?,?,'EXIST')";
    private static final String fullInfoSql = "select h.hotel_id,hotel_name, rating, hotel_description, amount_rooms," +
            " type_location, infrastructure, image_path, hotel_image_id, resort_name, r.resort_id from hotels h left join hotel_images i on " +
            "h.hotel_id=i.hotel_id and hotel_image_status = 'EXIST' left join resorts r on r.resort_id = h.resort_id " +
            "and resort_status = 'EXIST' where hotel_status = 'EXIST' and h.hotel_id=?";
    private static final String removeHotelSql = "update hotels h left join hotel_images i using(hotel_id) " +
            "set hotel_status ='DELETED', hotel_image_status = 'DELETED' where hotel_id=?";
    private static final String updateSQl = "update hotels set hotel_name = ?, resort_id =?, rating=?, " +
            "hotel_description = ?, amount_rooms= ?, type_location= ?, infrastructure = ? where hotel_id=?";
    private static final String removeImageSQL = "update hotel_images set hotel_image_status= 'DELETED' where hotel_image_id=?";

    @Override
    public List<Hotel> findHotelsByParams(Map<String, String> params) throws DaoException {
        String sql = SQLGenerator.generateSQL(params, sqlByParams);
        List<Hotel> hotels = new ArrayList<>();
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int hotelId = resultSet.getInt("hotel_id");
                    String hotelName = resultSet.getString("hotel_name");
                    int hotelRating = resultSet.getInt("rating");
                    String resortName = resultSet.getString("resort_name");
                    String hotelDescription = resultSet.getString("hotel_description");
                    Hotel hotel = buildHotel(hotelName, hotelRating, resortName, hotelId, hotelDescription);
                    hotels.add(hotel);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while trying to get hotels from db", e);
        }
        connectionPool.releaseConnection(connection);
        return hotels;
    }


    @Override
    public int countHotelsByParams(Map<String, String> params) throws DaoException {
        String sql = SQLGenerator.generateSQL(params, sqlCountHotels);
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
            throw new DaoException("Exception while trying to count hotels in db", e);
        }
        connectionPool.releaseConnection(connection);
        return amount;
    }

    @Override
    public Hotel findHotelById(int id) throws DaoException {
        Hotel hotel = new Hotel();
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(fullInfoSql,
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                buildFullHotel(resultSet, hotel);
                resultSet.beforeFirst();
                buildHotelImages(hotel, resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while trying to get full hotel info from db", e);
        }
        connectionPool.releaseConnection(connection);
        return hotel;
    }


    @Override
    public void addHotel(Hotel hotel) throws DaoException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        int hotelId;
        try (PreparedStatement preparedStatement = connection.prepareStatement(addHotelSql)) {
            connection.setAutoCommit(false);
            preparedStatement.setString(1, hotel.getHotelName());
            preparedStatement.setInt(2, hotel.getResort().getResortId());
            preparedStatement.setInt(3, hotel.getRating());
            preparedStatement.setString(4, hotel.getHotelDescription());
            preparedStatement.setInt(5, hotel.getAmountRooms());
            preparedStatement.setString(6, hotel.getTypeLocation());
            preparedStatement.setString(7, hotel.getInfrastructure());
            int rows = preparedStatement.executeUpdate();
            if (rows == 0) throw new SQLException("Adding hotel has not been done");
            hotelId = findLastId(connection);
            List<HotelImage> images = hotel.getHotelImages();
            buildImages(images, connection, hotelId);
            connection.commit();
            connection.setAutoCommit(true);
            connectionPool.releaseConnection(connection);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new DaoException("Exception while trying to rollback operation - adding hotel in DB", ex);
            }
            throw new DaoException("Exception while adding new hotel in DB", e);
        }
    }

    @Override
    public void removeHotel(int id) throws DaoException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(removeHotelSql)) {
            preparedStatement.setInt(1, id);
            int rows = preparedStatement.executeUpdate();
            if (rows == 0) throw new DaoException("Hotel is not deleted");
        } catch (SQLException e) {
            throw new DaoException("exception while trying to remove hotel", e);
        }
        connectionPool.releaseConnection(connection);
    }

    @Override
    public void updateHotel(Hotel hotel) throws DaoException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateSQl)) {
            connection.setAutoCommit(false);
            preparedStatement.setString(1, hotel.getHotelName());
            preparedStatement.setInt(2, hotel.getResort().getResortId());
            preparedStatement.setInt(3, hotel.getRating());
            preparedStatement.setString(4, hotel.getHotelDescription());
            preparedStatement.setInt(5, hotel.getAmountRooms());
            preparedStatement.setString(6, hotel.getTypeLocation());
            preparedStatement.setString(7, hotel.getInfrastructure());
            preparedStatement.setInt(8, hotel.getHotelId());
            int rows = preparedStatement.executeUpdate();
            if (rows == 0) throw new SQLException("Updating hotel has not been done");
            List<HotelImage> hotelImages = hotel.getHotelImages();
            updateImages(connection, hotelImages, hotel.getHotelId());
            connection.commit();
            connection.setAutoCommit(true);
            connectionPool.releaseConnection(connection);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new DaoException("Exception while trying to rollback operation - updating hotel", ex);
            }
            throw new DaoException("Exception while updating hotel", e);
        }
    }

    public void updateImages(Connection connection, List<HotelImage> hotelImages, int hotelId) throws SQLException {
        for (HotelImage image : hotelImages) {
            if (image.getImageId() != 0) {
                PreparedStatement preparedStatement = connection.prepareStatement(removeImageSQL);
                preparedStatement.setInt(1, image.getImageId());
                int rows = preparedStatement.executeUpdate();
                if (rows == 0) throw new SQLException("Image has not been deleted");
            } else {
                PreparedStatement preparedStatement = connection.prepareStatement(addImagesSql);
                preparedStatement.setInt(1, hotelId);
                preparedStatement.setString(2, image.getImagePath());
                int rows = preparedStatement.executeUpdate();
                if (rows == 0) throw new SQLException("Image has not been added");
            }
        }
    }

    public int findLastId(Connection connection) throws SQLException {
        int hotelId;
        try (PreparedStatement preparedStatement1 = connection.prepareStatement(lastIdSql)) {
            try (ResultSet resultSet = preparedStatement1.executeQuery()) {
                resultSet.next();
                hotelId = resultSet.getInt("max");
            }
        }
        return hotelId;
    }

    public void buildImages(List<HotelImage> images, Connection connection, int hotelId) throws SQLException {
        for (HotelImage image : images) {
            try (PreparedStatement preparedStatementImages = connection.prepareStatement(addImagesSql)) {
                preparedStatementImages.setInt(1, hotelId);
                preparedStatementImages.setString(2, image.getImagePath());
                int rowsImages = preparedStatementImages.executeUpdate();
                if (rowsImages == 0) throw new SQLException("Adding images has not been done");
            }
        }
    }

    public Hotel buildHotel(String hotelName, int hotelRating, String resortName, int hotelId, String hotelDes) {
        Hotel hotel = new Hotel();
        Resort resort = new Resort();
        resort.setResortName(resortName);
        hotel.setResort(resort);
        hotel.setHotelId(hotelId);
        hotel.setHotelName(hotelName);
        hotel.setRating(hotelRating);
        hotel.setHotelDescription(hotelDes);
        return hotel;
    }

    public void buildFullHotel(ResultSet resultSet, Hotel hotel) throws SQLException {
        hotel.setHotelId(resultSet.getInt("hotel_id"));
        hotel.setHotelName(resultSet.getString("hotel_name"));
        hotel.setRating(resultSet.getInt("rating"));
        hotel.setHotelDescription(resultSet.getString("hotel_description"));
        hotel.setAmountRooms(resultSet.getInt("amount_rooms"));
        hotel.setTypeLocation(resultSet.getString("type_location"));
        hotel.setInfrastructure(resultSet.getString("infrastructure"));
        Resort resort = new Resort();
        resort.setResortId(resultSet.getInt("resort_id"));
        resort.setResortName(resultSet.getString("resort_name"));
        hotel.setResort(resort);
    }

    public void buildHotelImages(Hotel hotel, ResultSet resultSet) throws SQLException {
        List<HotelImage> images = new ArrayList<>();
        while (resultSet.next()) {
            HotelImage image = new HotelImage();
            image.setImagePath(resultSet.getString("image_path"));
            image.setImageId(resultSet.getInt("hotel_image_id"));
            images.add(image);
        }
        hotel.setHotelImages(images);
    }
}
