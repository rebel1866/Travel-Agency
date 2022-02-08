package by.epamtc.stanislavmelnikov.controller.commandimpl.fullorderinfo;

import by.epamtc.stanislavmelnikov.controller.commandinterface.Command;
import by.epamtc.stanislavmelnikov.logic.exception.LogicException;
import by.epamtc.stanislavmelnikov.logic.factory.LogicFactory;
import by.epamtc.stanislavmelnikov.logic.logicinterface.OrderLogic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FullOrderSub2 implements Command {
    private static final String commandPath = "/executor?command=full_order_info&subcommand=main&order_id=";
    private static final String backMessage = "Назад";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subcommand = request.getParameter(subcommandParam);
        if (!subcommand.equals(subcommand2)) {
            request.getRequestDispatcher(homePath).forward(request, response);
            return;
        }
        int orderId = Integer.parseInt(request.getParameter(orderIdParam));
        LogicFactory factory = LogicFactory.getInstance();
        OrderLogic logic = factory.getOrderLogic();
        try {
            logic.denyOrder(orderId);
        } catch (LogicException e) {
            Logger logger = LogManager.getLogger(FullOrderSub2.class);
            logger.error(e.getMessage(), e);
            throw new ServletException(e.getMessage(), e);
        }
        request.setAttribute(linkKey, commandPath + orderId);
        request.setAttribute(messageKey, backMessage);
        request.getRequestDispatcher(jspSuccessfulOperation).forward(request, response);
    }
}
