package by.epamtc.stanislavmelnikov.controller.commandimpl.removeorder;

import by.epamtc.stanislavmelnikov.controller.commandinterface.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class RemoveOrderSub1 implements Command {
    private static final RemoveOrderSub2 sub2 = new RemoveOrderSub2();
    private static final String jspPath = "WEB-INF/jsp/warning-order-remove.jsp";
    private static final String backMessage = "Назад к просмотру регистраций";
    private static final String commandPath = "/executor?command=orders&page=1";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subcommand = request.getParameter(subcommandParam);
        if (!subcommand.equals(subcommand1)) {
            sub2.execute(request, response);
            return;
        }
        HttpSession session = request.getSession();
        if (session.getAttribute(accessAllowedKey) != null) {
            String orderId = request.getParameter(orderIdParam);
            request.setAttribute(orderIdParam, orderId);
            request.getRequestDispatcher(jspPath).forward(request, response);
            session.removeAttribute(accessAllowedKey);
        } else {
            request.setAttribute(linkKey, commandPath);
            request.setAttribute(messageKey, backMessage);
            request.getRequestDispatcher(jspNotAvailable).forward(request, response);
        }
    }
}
