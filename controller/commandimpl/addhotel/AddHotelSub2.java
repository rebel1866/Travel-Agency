package by.epamtc.stanislavmelnikov.controller.commandimpl.addhotel;

import by.epamtc.stanislavmelnikov.controller.commandinterface.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddHotelSub2 implements Command {
    private static final AddHotelSub3 sub3 = new AddHotelSub3();
    private static final String commandPath = "/executor?command=hotels&page=1";
    private static final String backMessage = "Назад к просмотру отелей";
    private static final String jspPath = "WEB-INF/jsp/successful-operation.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sub = request.getParameter(subcommandParam);
        if (!sub.equals(subcommand2)) {
            sub3.execute(request, response);
            return;
        }
        request.setAttribute(linkKey, commandPath);
        request.setAttribute(messageKey, backMessage);
        request.getRequestDispatcher(jspPath).forward(request, response);
    }
}
