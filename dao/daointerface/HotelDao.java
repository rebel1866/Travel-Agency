package by.epamtc.stanislavmelnikov.dao.daointerface;

import by.epamtc.stanislavmelnikov.dao.exception.DaoException;
import by.epamtc.stanislavmelnikov.entity.Hotel;

import java.util.List;
import java.util.Map;

public interface HotelDao {
    List<Hotel> findHotelsByParams(Map<String,String> params) throws DaoException;
    int countHotelsByParams(Map<String,String> params) throws DaoException;
    void addHotel(Hotel hotel) throws DaoException;
    Hotel findHotelById(int id) throws DaoException;
    void removeHotel(int id) throws DaoException;
    void updateHotel(Hotel hotel) throws DaoException;
}
