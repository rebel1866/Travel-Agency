package by.epamtc.stanislavmelnikov.controller.commandimpl;

import by.epamtc.stanislavmelnikov.controller.commandinterface.Command;
import by.epamtc.stanislavmelnikov.controller.controllerutils.ParamsGenerator;
import by.epamtc.stanislavmelnikov.controller.controllerutils.PropertyLoader;
import by.epamtc.stanislavmelnikov.entity.User;
import by.epamtc.stanislavmelnikov.logic.exception.LogicException;
import by.epamtc.stanislavmelnikov.logic.factory.LogicFactory;
import by.epamtc.stanislavmelnikov.logic.logicinterface.Pagination;
import by.epamtc.stanislavmelnikov.logic.logicinterface.UserLogic;
import by.epamtc.stanislavmelnikov.logic.validation.Validation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class UserManager implements Command {
    private static final String keyFields = "fields.user";
    private static final String amountOnPageKey = "amount.on.page";
    private static final String jspPath = "WEB-INF/jsp/users.jsp";
    private static final String commandPath = "executor?command=users&page=1";
    private static final String totalPagesKey = "totalPages";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LogicFactory logicFactory = LogicFactory.getInstance();
        UserLogic userLogic = logicFactory.getUserLogic();
        int amountOnPage = Integer.parseInt(PropertyLoader.getProperty(amountOnPageKey));
        Map<String, String> paramsCount = ParamsGenerator.generateParams(request, amountOnPage, false, keyFields);
        Map<String, String> userParams = ParamsGenerator.generateParams(request, amountOnPage, true, keyFields);
        List<User> users;
        int amountItems;
        try {
            amountItems = userLogic.countUsersByParams(paramsCount);
            users = userLogic.findUsersByParams(userParams);
        } catch (LogicException e) {
            Logger logger = LogManager.getLogger(UserManager.class);
            logger.error(e.getMessage(), e);
            throw new ServletException(e.getMessage(), e);
        }
        String pageStr = request.getParameter(pageKey);
        int page;
        if (!Validation.isCorrectInteger(pageStr)) {
            page = 1;
        } else page = Integer.parseInt(pageStr);
        goToPage(request, response, page, amountItems, logicFactory, users, amountOnPage);
    }

    private void goToPage(HttpServletRequest request, HttpServletResponse response, int page, int amountItems,
                          LogicFactory logicFactory, List<User> users, int amountOnPage) throws ServletException, IOException {
        Pagination pagination = logicFactory.getPagination();
        Map<String, Integer> paginationParams = pagination.paginate(page, amountItems, amountOnPage);
        int totalPages = paginationParams.get(totalPagesKey);
        if (page > totalPages && totalPages != 0) {
            request.getRequestDispatcher(commandPath).forward(request, response);
        } else {
            request.setAttribute("users", users);
            request.setAttribute(paginationKey, paginationParams);
            request.getRequestDispatcher(jspPath).forward(request, response);
        }
    }
}
