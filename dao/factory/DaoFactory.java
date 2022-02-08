package by.epamtc.stanislavmelnikov.dao.factory;

import by.epamtc.stanislavmelnikov.dao.daoimpl.*;
import by.epamtc.stanislavmelnikov.dao.daointerface.*;

public class DaoFactory {
    private static final DaoFactory instance = new DaoFactory();
    private UserDao userDao = new SQLUserDao();
    private TourDao tourDao = new SQLTourDao();
    private HotelDao hotelDao = new SQLHotelDao();
    private FeedbackDao feedbackDao = new SQLFeedbackDao();
    private ResortDao resortDao = new SQLResortDao();
    private OrderDao orderDao = new SQLOrderDao();

    private DaoFactory() {

    }

    public static DaoFactory getInstance() {
        return instance;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public TourDao getTourDao() {
        return tourDao;
    }

    public void setTourDao(TourDao tourDao) {
        this.tourDao = tourDao;
    }

    public HotelDao getHotelDao() {
        return hotelDao;
    }

    public void setHotelDao(HotelDao hotelDao) {
        this.hotelDao = hotelDao;
    }

    public FeedbackDao getFeedbackDao() {
        return feedbackDao;
    }

    public void setFeedbackDao(FeedbackDao feedbackDao) {
        this.feedbackDao = feedbackDao;
    }

    public ResortDao getResortDao() {
        return resortDao;
    }

    public void setResortDao(ResortDao resortDao) {
        this.resortDao = resortDao;
    }

    public OrderDao getOrderDao() {
        return orderDao;
    }

    public void setOrderDao(OrderDao orderDao) {
        this.orderDao = orderDao;
    }
}
