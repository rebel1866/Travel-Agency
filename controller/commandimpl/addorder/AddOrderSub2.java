package by.epamtc.stanislavmelnikov.controller.commandimpl.addorder;

import by.epamtc.stanislavmelnikov.controller.commandinterface.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddOrderSub2 implements Command {
    private static final AddOrderSub3 sub3 = new AddOrderSub3();
    private static final String messagePersonalData = "Перейти в личный кабинет";
    private static final String commandPath = "/executor?command=personal_data";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subcommand = request.getParameter(subcommandParam);
        if (!subcommand.equals(subcommand2)) {
            sub3.execute(request, response);
            return;
        }
        request.setAttribute(linkKey, commandPath);
        request.setAttribute(messageKey, messagePersonalData);
        request.getRequestDispatcher(jspSuccessfulOperation).forward(request, response);
    }
}
