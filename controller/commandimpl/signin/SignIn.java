package by.epamtc.stanislavmelnikov.controller.commandimpl.signin;

import by.epamtc.stanislavmelnikov.controller.commandinterface.Command;
import by.epamtc.stanislavmelnikov.logic.exception.LogicException;
import by.epamtc.stanislavmelnikov.logic.factory.LogicFactory;
import by.epamtc.stanislavmelnikov.logic.logicinterface.UserLogic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

public class SignIn implements Command {
    private static final SignInSub1 sub1 = new SignInSub1();
    private static final String commandPath = "executor?command=sign_in&subcommand=3";
    private static final String jspPath = "WEB-INF/jsp/authorization.jsp";
    private static final String loginKey = "login";
    private static final String passKey = "password";
    private static final String signMesKey = "sign_in_mes";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subcommand = request.getParameter(subcommandParam);
        if (!subcommand.equals(mainSubcommand)) {
            sub1.execute(request, response);
            return;
        }
        String login = request.getParameter(loginKey);
        String password = request.getParameter(passKey);
        UserLogic userLogic = LogicFactory.getInstance().getUserLogic();
        Map<String, String> authorization;
        try {
            authorization = userLogic.signIn(login, password);
        } catch (LogicException e) {
            Logger logger = LogManager.getLogger(SignIn.class);
            logger.info(e.getMessage(), e);
            request.setAttribute(signMesKey, e.getMessage());
            request.getRequestDispatcher(jspPath).forward(request, response);
            return;
        }
        HttpSession session = request.getSession(true);
        if (session.getAttribute(authParam) != null) {
            session.removeAttribute(authParam);
        }
        session.setAttribute(authParam, authorization);
        response.sendRedirect(commandPath);
    }
}
