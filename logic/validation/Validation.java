package by.epamtc.stanislavmelnikov.logic.validation;

import by.epamtc.stanislavmelnikov.controller.controllerutils.PropertyLoader;
import by.epamtc.stanislavmelnikov.dao.daointerface.UserDao;
import by.epamtc.stanislavmelnikov.dao.exception.DaoException;
import by.epamtc.stanislavmelnikov.dao.factory.DaoFactory;
import by.epamtc.stanislavmelnikov.entity.User;
import by.epamtc.stanislavmelnikov.logic.exception.LogicException;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
    private static final String triangleBracketLeft = "<";
    private static final String triangleBracketRight = ">";
    private static final String regexNumbers = "[0-9].*[0-9].*";
    private static final String regexPunctuation = "[`~!#:;^/.,]";
    private static final String regexUpperCase = "[A-Z]";
    private static final String regexEmail = "[\\w\\d]{3,}@\\w{2,}\\.\\w{2,}";
    private static final String regexTel = "\\+[\\d]{6,}";
    private static final String regexDate = "\\d{4}-\\d{2}-\\d{2}";
    private static final String regexInteger = "[\\D]+";


    public static boolean isCorrectPassword(String password) {
        Pattern numbers = Pattern.compile(regexNumbers);
        Pattern punctuation = Pattern.compile(regexPunctuation);
        Pattern upperRegistr = Pattern.compile(regexUpperCase);
        Matcher matcher = numbers.matcher(password);
        if (!matcher.find()) return false;
        Matcher matcher1 = punctuation.matcher(password);
        if (matcher1.find()) return false;
        Matcher matcher2 = upperRegistr.matcher(password);
        if (!matcher2.find()) return false;
        if (password.length() < 6) return false;
        return true;
    }

    public static boolean isCorrectLogin(String login) throws DaoException {
        List<User> userList;
        DaoFactory daoFactory = DaoFactory.getInstance();
        UserDao userDao = daoFactory.getUserDao();
        Map<String, String> searchArgs = new HashMap<>();
        searchArgs.put("restrictions", "login");
        searchArgs.put("login", login);
        userList = userDao.findUsersByParams(searchArgs);
        if (userList.size() > 0) return false;
        return true;
    }

    public static boolean isCorrectEmail(String email) {
        Pattern emailPattern = Pattern.compile(regexEmail);
        Matcher matcher = emailPattern.matcher(email);
        if (!matcher.find()) return false;
        return true;
    }

    public static boolean isCorrectTelephone(String telephone) {
        Pattern telPattern = Pattern.compile(regexTel);
        Matcher matcher = telPattern.matcher(telephone);
        if (!matcher.find()) return false;
        return true;
    }

    public static boolean isCorrectDate(String birthDate) {
        Pattern birthDatePattern = Pattern.compile(regexDate);
        Matcher matcher = birthDatePattern.matcher(birthDate);
        if (!matcher.find()) return false;
        return true;
    }

    public static boolean isEmpty(String value) {
        if (value.equals("")) return true;
        return false;
    }

    public static boolean isCorrectLang(String language) {
        if (language.equals("en") || language.equals("ru")) {
            return true;
        }
        return false;
    }

    public static boolean isCorrectInteger(String value) {
        Pattern pattern = Pattern.compile(regexInteger);
        Matcher matcher = pattern.matcher(value);
        return !matcher.find();
    }

    public static boolean isCorrectBoolean(String bool) {
        return bool.equals("true") || bool.equals("false");
    }

    public static String excludeInjections(String input) {
        String outPut = excludeJsInjection(input);
        return outPut;
    }


    public static void excludeInjections(Map<String, String> input) {
        String restrictionsStr = input.get("restrictions");
        if (restrictionsStr == null) return;
        String[] restrictions = restrictionsStr.split(",");
        for (int i = 0; i < restrictions.length; i++) {
            String key = restrictions[i];
            String value = input.get(key);
            value = excludeInjections(value);
            input.replace(key, value);
        }
    }

    public static void excludeInjections(Map<String, String> input, String key) throws LogicException {
        String restrictionsStr;
        try {
            restrictionsStr = PropertyLoader.getProperty(key);
        } catch (IOException e) {
            throw new LogicException("cannot load property", e);
        }
        String delim = ", ";
        String[] restrictions = restrictionsStr.split(delim);
        for (int i = 0; i < restrictions.length; i++) {
            String keyEl = restrictions[i];
            String value = input.get(keyEl);
            value = excludeInjections(value);
            input.replace(keyEl, value);
        }
    }

    private static String excludeJsInjection(String input) {
        if (input == null) return "";
        StringBuilder builderInput = new StringBuilder(input);
        String specSymbol = ".*";
        Pattern pattern = Pattern.compile(triangleBracketLeft + specSymbol + triangleBracketRight);
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            builderInput.delete(matcher.start(), matcher.end());
        }
        return builderInput.toString();
    }
}
