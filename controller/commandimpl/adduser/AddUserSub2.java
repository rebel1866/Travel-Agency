package by.epamtc.stanislavmelnikov.controller.commandimpl.adduser;

import by.epamtc.stanislavmelnikov.controller.commandinterface.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddUserSub2 implements Command {
    private AddUserSub3 sub3 = new AddUserSub3();
    private static final String backMessage = "Назад к просмотру пользователей";
    private static final String commandPath = "/executor?command=users&page=1";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subcommand = request.getParameter(subcommandParam);
        if (!subcommand.equals(subcommand2)) {
            sub3.execute(request, response);
            return;
        }
        request.setAttribute(linkKey, commandPath);
        request.setAttribute(messageKey, backMessage);
        request.getRequestDispatcher(jspSuccessfulOperation).forward(request, response);
    }
}
