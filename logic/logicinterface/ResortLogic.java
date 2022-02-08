package by.epamtc.stanislavmelnikov.logic.logicinterface;


import by.epamtc.stanislavmelnikov.entity.Resort;
import by.epamtc.stanislavmelnikov.logic.exception.LogicException;

import java.util.List;
import java.util.Map;

public interface ResortLogic {
    List<Resort> findResortsByParams(Map<String, String> params) throws LogicException;
}
