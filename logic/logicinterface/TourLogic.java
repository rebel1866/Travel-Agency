package by.epamtc.stanislavmelnikov.logic.logicinterface;

import by.epamtc.stanislavmelnikov.entity.Tour;
import by.epamtc.stanislavmelnikov.logic.exception.LogicException;

import javax.servlet.http.Cookie;
import java.util.List;
import java.util.Map;

public interface TourLogic {
    List<Tour> findToursByParams(Map<String,String> params) throws LogicException;
    int countToursByParams(Map<String,String> params) throws LogicException;
    Tour findTourById(int id) throws LogicException;
    void addTour(Map<String,String> params) throws LogicException;
    void removeTourById(int id) throws LogicException;
    void updateTour(Map<String,String> params) throws LogicException;
    List<Tour> findRecommendedTours(Cookie[] cookies) throws LogicException;
    void runCurrencyUpdater() throws LogicException;
}
