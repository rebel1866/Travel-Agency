package by.epamtc.stanislavmelnikov.logic.logicinterface;

import by.epamtc.stanislavmelnikov.entity.Hotel;
import by.epamtc.stanislavmelnikov.logic.exception.LogicException;

import java.util.List;
import java.util.Map;

public interface HotelLogic {
    List<Hotel> findHotelsByParams(Map<String, String> params) throws LogicException;

    int countHotelsByParams(Map<String, String> params) throws LogicException;

    void addHotel(Map<String, String> params) throws LogicException;

    Hotel findHotelById(int id) throws LogicException;

    void removeHotel(int id) throws LogicException;

    void updateHotel(Map<String,String> params) throws LogicException;
}
