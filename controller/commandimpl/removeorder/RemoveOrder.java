package by.epamtc.stanislavmelnikov.controller.commandimpl.removeorder;

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

public class RemoveOrder implements Command {
    private static final RemoveOrderSub1 sub1 = new RemoveOrderSub1();
    private static final String commandPath = "executor?command=remove_order&subcommand=3";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subcommand = request.getParameter(subcommandParam);
        if (!subcommand.equals(mainSubcommand)) {
            sub1.execute(request, response);
            return;
        }
        LogicFactory logicFactory = LogicFactory.getInstance();
        OrderLogic orderLogic = logicFactory.getOrderLogic();
        int orderId = Integer.parseInt(request.getParameter(orderIdParam));
        try {
            orderLogic.removeOrder(orderId);
        } catch (LogicException e) {
            Logger logger = LogManager.getLogger(RemoveOrder.class);
            logger.error(e.getMessage(), e);
            throw new ServletException(e.getMessage(), e);
        }
        response.sendRedirect(commandPath);
    }
}
