package by.epamtc.stanislavmelnikov.dao.daointerface;

import by.epamtc.stanislavmelnikov.dao.exception.DaoException;
import by.epamtc.stanislavmelnikov.entity.Tour;

import java.util.List;
import java.util.Map;

public interface TourDao {
    List<Tour> findToursByParams(Map<String,String> params) throws DaoException;
    int countToursByParams(Map<String,String> params) throws DaoException;
    Tour findTourById(int id) throws DaoException;
    void addTour(Tour tour) throws DaoException;
    void removeTourById(int id) throws DaoException;
    void updateTour(Tour tour) throws DaoException;
}
