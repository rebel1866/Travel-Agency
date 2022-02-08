package by.epamtc.stanislavmelnikov.dao.daointerface;

import by.epamtc.stanislavmelnikov.dao.exception.DaoException;
import by.epamtc.stanislavmelnikov.entity.Feedback;

import java.util.List;
import java.util.Map;

 public interface FeedbackDao {
    List<Feedback> getFeedbacksByParams(Map<String,String> params) throws DaoException;

     void addFeedback(Feedback feedback) throws DaoException;

     int countFeedbacksByParams(Map<String, String> params) throws DaoException;

     void removeFeedback(int fbkId) throws DaoException;
 }
