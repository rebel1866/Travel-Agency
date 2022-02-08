package by.epamtc.stanislavmelnikov.controller.commandimpl.resetpassword;

import by.epamtc.stanislavmelnikov.controller.commandinterface.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResetPasswordSub3 implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sub = request.getParameter(subcommandParam);
        if (!sub.equals(subcommand3)) {
            request.getRequestDispatcher(homePath).forward(request, response);
            return;
        }
        request.getRequestDispatcher(jspSuccessfulOperation).forward(request, response);
    }
}
