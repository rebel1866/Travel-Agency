package by.epamtc.stanislavmelnikov.dao.daointerface;

import by.epamtc.stanislavmelnikov.dao.exception.DaoException;
import by.epamtc.stanislavmelnikov.entity.Resort;

import java.util.List;
import java.util.Map;

public interface ResortDao {
    List<Resort> findResortsByParams(Map<String,String> params) throws DaoException;
}
