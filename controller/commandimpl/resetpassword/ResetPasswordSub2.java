package by.epamtc.stanislavmelnikov.controller.commandimpl.resetpassword;

import by.epamtc.stanislavmelnikov.controller.commandinterface.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResetPasswordSub2 implements Command {
    private ResetPasswordSub3 sub3 = new ResetPasswordSub3();
    private static final String jspPath = "WEB-INF/jsp/reset-password.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sub = request.getParameter(subcommandParam);
        if (!sub.equals(subcommand2)) {
            sub3.execute(request, response);
            return;
        }
        request.getRequestDispatcher(jspPath).forward(request, response);
    }
}
