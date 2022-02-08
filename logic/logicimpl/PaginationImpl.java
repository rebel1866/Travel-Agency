package by.epamtc.stanislavmelnikov.logic.logicimpl;

import by.epamtc.stanislavmelnikov.logic.logicinterface.Pagination;

import java.util.HashMap;
import java.util.Map;

public class PaginationImpl implements Pagination {
    private final static int STEP = 4;

    @Override
    public Map<String, Integer> paginate(int currentPage, int amountItems, int amountOnPage) {
        Map<String, Integer> paginationParams = new HashMap<>();
        int totalPages = countTotalPages(amountItems, amountOnPage);
        int startPointToPrint = countStartPointToPrint(currentPage);
        int endPointToPrint = countEndPointToPrint(currentPage, totalPages);
        paginationParams.put("currentPage", currentPage);
        paginationParams.put("totalPages", totalPages);
        paginationParams.put("startPoint", startPointToPrint);
        paginationParams.put("endPoint", endPointToPrint);
        return paginationParams;
    }

    public int countTotalPages(int amountItems, int amountOnPage) {
        int remains = amountItems % amountOnPage;
        if (amountItems < amountOnPage && amountItems > 0) {
            return 1;
        }
        if (remains == 0) {
            return amountItems / amountOnPage;
        }
        return (amountItems / amountOnPage) + 1;
    }


    public int countStartPointToPrint(int currentPage) {
        if (currentPage <= STEP + 1) return 1;
        return currentPage - STEP;
    }

    public int countEndPointToPrint(int currentPage, int totalPages) {
        if (currentPage >= totalPages - STEP) return totalPages;
        return currentPage + STEP;
    }
}
