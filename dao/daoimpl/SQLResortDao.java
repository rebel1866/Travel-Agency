package by.epamtc.stanislavmelnikov.dao.daoimpl;

import by.epamtc.stanislavmelnikov.dao.connection.ConnectionPool;
import by.epamtc.stanislavmelnikov.dao.daointerface.ResortDao;
import by.epamtc.stanislavmelnikov.dao.exception.DaoException;
import by.epamtc.stanislavmelnikov.dao.sqlgenerator.SQLGenerator;
import by.epamtc.stanislavmelnikov.entity.Resort;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SQLResortDao implements ResortDao {
    private final String sourceSQl = "select resort_id, resort_name, location, population, resort_description from" +
            " resorts where resort_status = 'EXIST'";

    @Override
    public List<Resort> findResortsByParams(Map<String, String> params) throws DaoException {
        String sql = SQLGenerator.generateSQL(params, sourceSQl);
        List<Resort> resorts = new ArrayList<>();
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Resort resort = new Resort();
                    resort.setResortId(resultSet.getInt("resort_id"));
                    resort.setResortName(resultSet.getString("resort_name"));
                    resort.setPopulation(resultSet.getInt("population"));
                    resort.setLocation(resultSet.getString("location"));
                    resort.setResortDescription(resultSet.getString("resort_description"));
                    resorts.add(resort);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while trying to get resorts from db", e);
        }
        connectionPool.releaseConnection(connection);
        return resorts;
    }
}
