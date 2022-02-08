package by.epamtc.stanislavmelnikov.controller.commandimpl.resetpassword;

import by.epamtc.stanislavmelnikov.controller.commandinterface.Command;
import by.epamtc.stanislavmelnikov.logic.exception.LogicException;
import by.epamtc.stanislavmelnikov.logic.factory.LogicFactory;
import by.epamtc.stanislavmelnikov.logic.logicinterface.UserLogic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResetPassword implements Command {
    private ResetPasswordSub1 sub1 = new ResetPasswordSub1();
    private static final String commandPath = "executor?command=reset_password&subcommand=3";
    private static final String jspPath = "WEB-INF/jsp/reset-password.jsp";
    private static final String loginKey = "login";
    private static final String emailParam = "email";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sub = request.getParameter(subcommandParam);
        if (!sub.equals(mainSubcommand)) {
            sub1.execute(request, response);
            return;
        }
        LogicFactory logicFactory = LogicFactory.getInstance();
        UserLogic userLogic = logicFactory.getUserLogic();
        String login = request.getParameter(loginKey);
        String email = request.getParameter(emailParam);
        try {
            userLogic.resetPassword(login, email);
        } catch (LogicException e) {
            Logger logger = LogManager.getLogger(ResetPassword.class);
            logger.error(e.getMessage(), e);
            request.setAttribute("e_message", e.getMessage());
            request.getRequestDispatcher(jspPath).forward(request, response);
            return;
        }
        response.sendRedirect(commandPath);
    }
}
