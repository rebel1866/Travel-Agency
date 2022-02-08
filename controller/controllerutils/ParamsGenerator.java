package by.epamtc.stanislavmelnikov.controller.controllerutils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class ParamsGenerator {
    private static final String restrictionsKey = "restrictions";
    private static final String limitKey = "limit";
    private static final String sortKey = "sorting";
    private static final String specSymbol = ", ";
    private static final String empty = "";
    private static final String sortOrderKey = "sortingOrder";
    private static final String startPointKey = "startPoint=";
    private static final String amountPageKey = ",amountOnPage=";
    private static final String noFields = "no.fields";
    private static final String pageKey = "page";

    public static Map<String, String> generateParams(HttpServletRequest request, int amountOnPage, boolean isLimit,
                                                     String key) throws IOException {
        Map<String, String> params = new HashMap<>();
        String forms = PropertyLoader.getProperty(key);
        List<String> formNames = List.of(forms.split(specSymbol));
        List<String> restrictions = generateRestrictions(formNames, params, request);
        String restrictionsStr = generateRestrictionsString(restrictions);
        if (!restrictionsStr.equals(empty)) {
            params.put(restrictionsKey, restrictionsStr);
        }
        if (isLimit) {
            String limit = calculateLimit(amountOnPage, request);
            params.put(limitKey, limit);
        }
        if (isSortingExists(request)) {
            String sorting = calculateSorting(request);
            params.put(sortKey, sorting);
        }
        return params;
    }

    public static Map<String, String> generateParams(HttpServletRequest request, int amountOnPage, boolean isLimit) throws IOException {
        return generateParams(request, amountOnPage, isLimit, noFields);
    }

    public static Map<String, String> getFormKeyValue(HttpServletRequest request, String key) throws IOException {
        String fieldsStr = PropertyLoader.getProperty(key);
        String[] fields = fieldsStr.split(specSymbol);
        Map<String, String> params = new HashMap<>();
        for (String keyField : fields) {
            String value = request.getParameter(keyField);
            params.put(keyField, value);
        }
        return params;
    }

    public static List<String> generateRestrictions(List<String> formNames, Map<String, String> params, HttpServletRequest request) {
        List<String> restrictions = new ArrayList<>();
        for (String formName : formNames) {
            String param = request.getParameter(formName);
            if (param != null && !param.equals(empty)) {
                params.put(formName, param);
                restrictions.add(formName);
            }
        }
        return restrictions;
    }

    public static boolean isSortingExists(HttpServletRequest request) {
        String sorting = request.getParameter(sortKey);
        return sorting != null && !sorting.equals(empty);
    }

    public static String calculateLimit(int amountOnPage, HttpServletRequest request) {
        String pageStr = request.getParameter(pageKey);
        int page = 0;
        try {
            if (pageStr != null) {
                page = Integer.parseInt(pageStr);
            }
        } catch (NumberFormatException e) {
          page = 1;
        }
        int startPoint = page * amountOnPage - amountOnPage;
        String limit = startPointKey + startPoint + amountPageKey + amountOnPage;
        return limit;
    }

    public static String calculateSorting(HttpServletRequest request) {
        String sorting = request.getParameter(sortKey);
        String sortingOrder = request.getParameter(sortOrderKey);
        String result = sorting + " " + sortingOrder;
        return result;
    }

    public static String generateRestrictionsString(List<String> restrictions) {
        String restricts = restrictions.stream().collect(Collectors.joining(","));
        return restricts;
    }
}
