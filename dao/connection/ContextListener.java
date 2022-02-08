package by.epamtc.stanislavmelnikov.dao.connection;

import by.epamtc.stanislavmelnikov.dao.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextListener implements ServletContextListener {
    private static final String urlKey = "db-url";
    private static final String userkey = "db-user";
    private static final String passwordKey = "db-password";
    private static final String driverKey = "db-driver";
    private static final String poolSizeKey = "pool-size";

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        String url = servletContext.getInitParameter(urlKey);
        String user = servletContext.getInitParameter(userkey);
        String password = servletContext.getInitParameter(passwordKey);
        String driverName = servletContext.getInitParameter(driverKey);
        int poolSize = Integer.parseInt(servletContext.getInitParameter(poolSizeKey));
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try {
            connectionPool.create(url, user, password, driverName, poolSize);
        } catch (DaoException e) {
            Logger logger = LogManager.getLogger(ContextListener.class);
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try {
            connectionPool.close();
        } catch (DaoException e) {
            Logger logger = LogManager.getLogger(ContextListener.class);
            logger.error(e.getMessage(), e);
        }
    }
}
