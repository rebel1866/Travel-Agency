package by.epamtc.stanislavmelnikov.controller.commandimpl.fullorderinfo;

import by.epamtc.stanislavmelnikov.controller.commandinterface.Command;
import by.epamtc.stanislavmelnikov.logic.exception.LogicException;
import by.epamtc.stanislavmelnikov.logic.factory.LogicFactory;
import by.epamtc.stanislavmelnikov.logic.logicinterface.OrderLogic;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FullOrderSub1 implements Command {
    private static final FullOrderSub2 sub2 = new FullOrderSub2();
    private static final String commandPath = "/executor?command=full_order_info&subcommand=main&order_id=";
    private static final String backMessage = "Назад";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subcommand = request.getParameter(subcommandParam);
        if (!subcommand.equals(subcommand1)) {
            sub2.execute(request, response);
            return;
        }
        int orderId = Integer.parseInt(request.getParameter(orderIdParam));
        LogicFactory factory = LogicFactory.getInstance();
        OrderLogic logic = factory.getOrderLogic();
        try {
            logic.acceptOrder(orderId);
        } catch (LogicException e) {
            throw new ServletException(e.getMessage(), e);
        }
        request.setAttribute(linkKey, commandPath + orderId);
        request.setAttribute(messageKey, backMessage);
        request.getRequestDispatcher(jspSuccessfulOperation).forward(request, response);
    }
}
