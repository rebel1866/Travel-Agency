package by.epamtc.stanislavmelnikov.controller.commandimpl.updateuser;

import by.epamtc.stanislavmelnikov.controller.commandinterface.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UpdateUserSub2 implements Command {
    private static final UpdateUserSub3 sub3 = new UpdateUserSub3();
    private static final String commandPath = "/executor?command=full_user_info&user_id=";
    private static final String backMessage = "Назад к просмотру информации о пользователе";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subcommand = request.getParameter(subcommandParam);
        if (!subcommand.equals(subcommand2)) {
            sub3.execute(request, response);
            return;
        }
        request.setAttribute(linkKey, commandPath + request.getParameter(userIdParam));
        request.setAttribute(messageKey, backMessage);
        request.getRequestDispatcher(jspSuccessfulOperation).forward(request, response);
    }
}
