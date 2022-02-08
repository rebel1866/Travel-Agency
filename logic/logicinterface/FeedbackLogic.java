package by.epamtc.stanislavmelnikov.logic.logicinterface;

import by.epamtc.stanislavmelnikov.entity.Feedback;
import by.epamtc.stanislavmelnikov.logic.exception.LogicException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface FeedbackLogic {
    List<Feedback> findFeedbacksByParams(Map<String,String> params) throws LogicException;

    void addFeedback(int tourId, int userId, int rating, String fbkBody) throws LogicException;

    int countFeedbacksByParams(Map<String,String> params) throws LogicException;

    void removeFeedback(int fbkId) throws LogicException;
}
