package by.epamtc.stanislavmelnikov.controller.commandimpl.removeorder;

import by.epamtc.stanislavmelnikov.controller.commandinterface.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RemoveOrderSub2 implements Command {
    private static final RemoveOrderSub3 sub3 = new RemoveOrderSub3();
    private static final String acceptPath = "/executor?command=remove_order&subcommand=main&order_id=";
    private static final String denyPath = "/executor?command=full_order_info&subcommand=main&order_id=";
    private static final String acceptKey = "Подтвердить";
    private static final String actionKey = "action";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subcommand = request.getParameter(subcommandParam);
        if (!subcommand.equals(subcommand2)) {
            sub3.execute(request, response);
            return;
        }
        String action = request.getParameter(actionKey);
        String orderId = request.getParameter(orderIdParam);
        if (action.equals(acceptKey)) {
            request.getRequestDispatcher(acceptPath + orderId).forward(request, response);
        } else {
            request.getRequestDispatcher(denyPath + orderId).forward(request, response);
        }
    }
}
