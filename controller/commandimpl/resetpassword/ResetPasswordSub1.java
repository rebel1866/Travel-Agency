package by.epamtc.stanislavmelnikov.controller.commandimpl.resetpassword;

import by.epamtc.stanislavmelnikov.controller.commandinterface.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResetPasswordSub1 implements Command {
    private ResetPasswordSub2 sub2 = new ResetPasswordSub2();
    private static final String commandPath = "/executor?command=reset_password&subcommand=main&login=";
    private static final String emailKey = "&email=";
    private static final String acceptKey = "Подтвердить";
    private static final String loginKey = "login";
    private static final String emailParam = "email";
    private static final String actionKey = "action";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sub = request.getParameter(subcommandParam);
        if (!sub.equals(subcommand1)) {
            sub2.execute(request, response);
            return;
        }
        String action = request.getParameter(actionKey);
        String login = request.getParameter(loginKey);
        String email = request.getParameter(emailParam);
        if (action.equals(acceptKey)) {
            request.getRequestDispatcher(commandPath + login + emailKey + email).forward(request, response);
        } else {
            request.getRequestDispatcher(homePath).forward(request, response);
        }
    }
}
