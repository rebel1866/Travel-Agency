package by.epamtc.stanislavmelnikov.dao.sqlgenerator;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SQLGenerator {
    private static Map<String, Action> actions = new HashMap<>();

    private static final Action concatEquals = (sql, key, value) -> sql.append(key + "=" + value);
    private static final Action concatMoreThan = (sql, key, value) -> sql.append(key + ">=" + value);
    private static final Action concatLessThan = (sql, key, value) -> sql.append(key + "<=" + value);
    private static final Action concatPriceFrom = (sql, key, value) -> sql.append("price" + ">=" + value);
    private static final Action concatPriceTo = (sql, key, value) -> sql.append("price" + "<=" + value);
    private static final Action concatLike = (sql, key, value) -> {
        StringBuilder newValue = new StringBuilder(value);
        newValue.insert(value.length() - 1, "%");
        newValue.insert(1, "%");
        sql.append("upper(" + key + ") like upper(" + newValue + ")");
    };
    private static final Action concatNightsFrom = ((sql, key, value) -> {
        sql.append("(select DATEDIFF(comeback,departure))>" + value);
    });
    private static final Action concatNightsTo = ((sql, key, value) -> {
        sql.append("(select DATEDIFF(comeback,departure))<" + value);
    });
    private static final Action concatHotelId = ((sql, key, value) -> {
        sql.append("h.hotel_id=" + value);
    });
    private static final Action concatUserId = ((sql, key, value) -> {
        sql.append("u.user_id=" + value);
    });
    private static final Action concatTourId = ((sql, key, value) -> {
        sql.append("t.tour_id=" + value);
    });

    static {
        actions.put("price_from", concatPriceFrom);
        actions.put("price_to", concatPriceTo);
        actions.put("location", concatLike);
        actions.put("hotel_name", concatLike);
        actions.put("resort_name", concatLike);
        actions.put("departure", concatMoreThan);
        actions.put("comeback", concatLessThan);
        actions.put("amount_nights_from", concatNightsFrom);
        actions.put("amount_nights_to", concatNightsTo);
        actions.put("hotel_id",concatHotelId);
        actions.put("user_id", concatUserId);
        actions.put("tour_id",concatTourId);
        actions.put("login",concatLike);
    }

    public static void setActions(String key, Action value) {
        actions.put(key, value);
    }

    public static String generateSQL(Map<String, String> params, String sourceSql) {
        StringBuilder sql = new StringBuilder(sourceSql);
        String restrictions = params.get("restrictions");
        String sorting = params.get("sorting");
        String limit = params.get("limit");
        if (restrictions != null) {
            String[] restrictionsAr = restrictions.split(",");
            generateWhereBlock(sql, restrictionsAr, params);
        }
        if (sorting != null) {
            generateOrderBlock(sql, sorting);
        }
        if (limit != null) {
            generateLimitBlock(sql, limit);
        }
        return sql.toString();
    }


    public static String findPattern(String regex, String source) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(source);
        String emptyString = "";
        if (matcher.find()) {
            return matcher.group(1);
        }
        return emptyString;
    }


    public static void generateWhereBlock(StringBuilder sql, String[] restrictions, Map<String, String> params) {
        if (isWhereExists(sql)) {
            sql.append(" and ");
        } else {
            sql.append(" where ");
        }
        for (int i = 0; i < restrictions.length; i++) {
            String key = restrictions[i];
            String value = params.get(key);
            boolean isNumber = isNumber(value);
            if (!isNumber) {
                value = wrapApostrophe(value);
            }
            executeAction(sql, key, value);
            if (i != restrictions.length - 1) sql.append(" and ");
        }
    }

    public static void executeAction(StringBuilder sql, String key, String value) {
        Action action = actions.get(key);
        if (action == null) {
            concatEquals.doAction(sql, key, value);
        } else {
            action.doAction(sql, key, value);
        }
    }

    public static void generateLimitBlock(StringBuilder sql, String limit) {
        String startPoint = getStartPoint(limit);
        String amountOnPage = getAmountOnPage(limit);
        sql.append(" LIMIT " + startPoint + ", " + amountOnPage);
    }

    public static void generateOrderBlock(StringBuilder sql, String sorting) {
        String criteria = getSortCriteria(sorting);
        String order = getSortOrder(sorting);
        sql.append(" ORDER BY " + criteria + " " + order);
    }


    public static boolean isNumber(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static String wrapApostrophe(String value) {
        return "'" + value + "'";
    }

    public static String getStartPoint(String limitStr) {
        String amount = findPattern("startPoint=(\\d+),", limitStr);
        return amount;
    }

    public static String getAmountOnPage(String limitStr) {
        String amount = findPattern("amountOnPage=(\\d+)", limitStr);
        return amount;
    }

    public static String getSortCriteria(String limitStr) {
        String criteria = limitStr.split(" ")[0];
        return criteria;
    }

    public static String getSortOrder(String limitStr) {
        String order = limitStr.split(" ")[1];
        return order;
    }

    public static boolean isWhereExists(StringBuilder sql) {
        Pattern pattern = Pattern.compile("WHERE");
        Matcher matcher = pattern.matcher(sql.toString().toUpperCase());
        return matcher.find();
    }
}
