package by.epamtc.stanislavmelnikov.logic.logicimpl;

import by.epamtc.stanislavmelnikov.dao.daointerface.ResortDao;
import by.epamtc.stanislavmelnikov.dao.exception.DaoException;
import by.epamtc.stanislavmelnikov.dao.factory.DaoFactory;
import by.epamtc.stanislavmelnikov.entity.Resort;
import by.epamtc.stanislavmelnikov.logic.exception.LogicException;
import by.epamtc.stanislavmelnikov.logic.logicinterface.ResortLogic;
import by.epamtc.stanislavmelnikov.logic.validation.Validation;

import java.util.List;
import java.util.Map;

public class ResortLogicImpl implements ResortLogic {
    @Override
    public List<Resort> findResortsByParams(Map<String, String> params) throws LogicException {
        Validation.excludeInjections(params);
        DaoFactory daoFactory = DaoFactory.getInstance();
        ResortDao resortDao = daoFactory.getResortDao();
        List<Resort> resorts;
        try {
            resorts = resortDao.findResortsByParams(params);
        } catch (DaoException e) {
            throw new LogicException(e.getMessage(), e);
        }
        return resorts;
    }
}

