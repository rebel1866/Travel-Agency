package by.epamtc.stanislavmelnikov.logic.logicinterface;

import java.util.Map;

public interface Pagination {
    Map<String, Integer> paginate(int currentPage, int amountItems, int amountOnPage);
}
