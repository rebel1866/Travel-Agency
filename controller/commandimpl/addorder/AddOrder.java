package by.epamtc.stanislavmelnikov.controller.commandimpl.addorder;

import by.epamtc.stanislavmelnikov.controller.commandinterface.Command;
import by.epamtc.stanislavmelnikov.logic.exception.LogicException;
import by.epamtc.stanislavmelnikov.logic.factory.LogicFactory;
import by.epamtc.stanislavmelnikov.logic.logicinterface.OrderLogic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

public class AddOrder implements Command {
    private static final AddOrderSub1 sub1 = new AddOrderSub1();
    private static final String commentParam = "comment";
    private static final String commandPath = "executor?command=add_order&subcommand=2&tour_id=";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subCommand = request.getParameter(subcommandParam);
        if (!subCommand.equals(mainSubcommand)) {
            sub1.execute(request, response);
            return;
        }
        LogicFactory factory = LogicFactory.getInstance();
        OrderLogic orderLogic = factory.getOrderLogic();
        int tourId = Integer.parseInt(request.getParameter(tourIdParam));
        HttpSession session = request.getSession(true);
        Map<String, String> authorization = (Map<String, String>) session.getAttribute(authParam);
        int userId = Integer.parseInt(authorization.get(userIdParam));
        String comment = request.getParameter(commentParam);
        int orderId;
        try {
            orderId = orderLogic.addOrder(userId, tourId, comment);
        } catch (LogicException e) {
            Logger logger = LogManager.getLogger(AddOrder.class);
            logger.error(e.getMessage(), e);
            throw new ServletException(e.getMessage(), e);
        }
        session.setAttribute(orderIdParam, orderId);
        response.sendRedirect(commandPath + request.getParameter(tourIdParam));
    }
}
