package by.epamtc.stanislavmelnikov.controller.commandimpl.removeuser;

import by.epamtc.stanislavmelnikov.controller.commandinterface.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RemoveUserSub2 implements Command {
    private static final RemoveUserSub3 sub3 = new RemoveUserSub3();
    private static final String acceptPath = "/executor?command=remove_user&subcommand=main&user_id=";
    private static final String commandPath = "/executor?command=full_user_info&user_id=";
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
        String userId = request.getParameter(userIdParam);
        if (action.equals(acceptKey)) {
            request.getRequestDispatcher(acceptPath + userId).forward(request, response);
            return;
        }
        request.getRequestDispatcher(commandPath + userId).forward(request, response);
    }
}
