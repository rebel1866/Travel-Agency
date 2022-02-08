package by.epamtc.stanislavmelnikov.controller.commandimpl.registration;

import by.epamtc.stanislavmelnikov.controller.commandinterface.Command;
import by.epamtc.stanislavmelnikov.entity.User;
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
import java.util.HashMap;
import java.util.Map;

public class Registration implements Command {
    private static final RegistrationSub1 subCommand1 = new RegistrationSub1();
    private static final String regKey = "reg-key";
    private static final String regArgs = "reg-args";
    private static final String jspRegistrationPath = "WEB-INF/jsp/registration.jsp";
    private static final String jspFailPath = "WEB-INF/jsp/registration-fail.jsp";
    private static final String commandPath = "executor?command=registration&subcommand=3";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subCommand = request.getParameter(subcommandParam);
        if (!subCommand.equals(mainSubcommand)) {
            subCommand1.execute(request, response);
            return;
        }
        HttpSession session = request.getSession(true);
        String keyFromSession = (String) session.getAttribute(regKey);
        String keyParam = request.getParameter("key");
        if (!keyFromSession.equals(keyParam)) {
            session.removeAttribute(regKey);
            request.getRequestDispatcher(jspFailPath).forward(request, response);
            return;
        }
        session.removeAttribute(regKey);
        Map<String, String> params = (Map<String, String>) session.getAttribute(regArgs);
        session.removeAttribute(regArgs);
        User user;
        UserLogic userLogic = LogicFactory.getInstance().getUserLogic();
        try {
            user = userLogic.addUser(params);
        } catch (LogicException e) {
            Logger logger = LogManager.getLogger(Registration.class);
            logger.info(e.getMessage(), e);
            request.setAttribute("e_message", e.getMessage());
            request.getRequestDispatcher(jspRegistrationPath).forward(request, response);
            return;
        }
        session.setAttribute("user_name", user.getFirstName());
        session.setAttribute("user_last_name", user.getLastName());
        response.sendRedirect(commandPath);
    }
}
