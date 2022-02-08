package by.epamtc.stanislavmelnikov.logic.logicimpl;

import by.epamtc.stanislavmelnikov.dao.daointerface.FeedbackDao;
import by.epamtc.stanislavmelnikov.dao.exception.DaoException;
import by.epamtc.stanislavmelnikov.dao.factory.DaoFactory;
import by.epamtc.stanislavmelnikov.entity.Feedback;
import by.epamtc.stanislavmelnikov.entity.Tour;
import by.epamtc.stanislavmelnikov.entity.User;
import by.epamtc.stanislavmelnikov.logic.exception.LogicException;
import by.epamtc.stanislavmelnikov.logic.logicinterface.FeedbackLogic;
import by.epamtc.stanislavmelnikov.logic.validation.Validation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class FeedbackLogicImpl implements FeedbackLogic {
    @Override
    public List<Feedback> findFeedbacksByParams(Map<String, String> params) throws LogicException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        FeedbackDao feedbackDao = daoFactory.getFeedbackDao();
        Validation.excludeInjections(params);
        List<Feedback> feedbacks;
        try {
            feedbacks = feedbackDao.getFeedbacksByParams(params);
        } catch (DaoException e) {
            throw new LogicException(e.getMessage(), e);
        }
        return feedbacks;
    }

    @Override
    public void addFeedback(int tourId, int userId, int rating, String fbkBody) throws LogicException {
        Feedback feedback = new Feedback();
        fbkBody = Validation.excludeInjections(fbkBody);
        User user = new User();
        Tour tour = new Tour();
        user.setUserID(userId);
        feedback.setUser(user);
        tour.setTourId(tourId);
        feedback.setTour(tour);
        feedback.setFbkRating(rating);
        feedback.setFeedbackBody(fbkBody);
        LocalDateTime currentDateTime = LocalDateTime.now();
        feedback.setFbkDateTime(currentDateTime);
        DaoFactory daoFactory = DaoFactory.getInstance();
        FeedbackDao feedbackDao = daoFactory.getFeedbackDao();
        try {
            feedbackDao.addFeedback(feedback);
        } catch (DaoException e) {
            throw new LogicException(e.getMessage(), e);
        }
    }

    @Override
    public void removeFeedback(int fbkId) throws LogicException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        FeedbackDao feedbackDao = daoFactory.getFeedbackDao();
        try {
            feedbackDao.removeFeedback(fbkId);
        } catch (DaoException e) {
            throw new LogicException(e.getMessage(), e);
        }
    }

    @Override
    public int countFeedbacksByParams(Map<String, String> params) throws LogicException {
        Validation.excludeInjections(params);
        DaoFactory daoFactory = DaoFactory.getInstance();
        FeedbackDao feedbackDao = daoFactory.getFeedbackDao();
        int amount;
        try {
            amount = feedbackDao.countFeedbacksByParams(params);
        } catch (DaoException e) {
            throw new LogicException(e.getMessage(), e);
        }
        return amount;
    }
}
