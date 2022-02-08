package by.epamtc.stanislavmelnikov.dao.connection;

import by.epamtc.stanislavmelnikov.dao.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool {
    private BlockingQueue<Connection> connectionPool;
    private BlockingQueue<Connection> usedConnections;

    private ConnectionPool() {
    }


    private static class PoolHolder {
        private final static ConnectionPool instance = new ConnectionPool();
    }

    public static ConnectionPool getInstance() {
        return PoolHolder.instance;
    }

    public void create(String url, String user, String password, String driver, int poolSize) throws DaoException {
        connectionPool = new ArrayBlockingQueue<>(poolSize);
        usedConnections = new ArrayBlockingQueue<>(poolSize);
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new DaoException("cannot load driver", e);
        }
        for (int i = 0; i < poolSize; i++) {
            try {
                connectionPool.add(createConnection(url, user, password));
            } catch (SQLException e) {
                throw new DaoException("cannot add connection in pool", e);
            }
        }
    }

    public void close() throws DaoException {
        for (int i = 0; i < connectionPool.size(); i++) {
            Connection connection = connectionPool.remove();
            try {
                connection.close();
            } catch (SQLException e) {
                throw new DaoException("cannot close connection", e);
            }
        }
    }


    public Connection getConnection() throws DaoException {
        Connection connection;
        try {
            connection = connectionPool.take();
        } catch (InterruptedException e) {
            Logger logger = LogManager.getLogger(ConnectionPool.class);
            logger.error(e.getMessage(), e);
            throw new DaoException("cannot get connection", e);
        }
        usedConnections.add(connection);
        return connection;
    }


    public void releaseConnection(Connection connection) {
        connectionPool.add(connection);
        usedConnections.remove(connection);
    }

    public Connection createConnection(String url, String user, String password) throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}