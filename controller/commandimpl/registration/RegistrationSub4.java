package by.epamtc.stanislavmelnikov.controller.commandimpl.registration;

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
import java.util.HashMap;
import java.util.Map;

public class RegistrationSub4 implements Command {
    private static final String emailKey = "email";
    private static final String firstNameKey = "first_name";
    private static final String lastNameKey = "last_name";
    private static final String loginKey = "login";
    private static final String passwordKey = "password";
    private static final String rPasswordKey = "r_password";
    private static final String birthDateKey = "birth_date";
    private static final String telephoneKey = "telephone";
    private static final String genderKey = "gender";
    private static final String commandPath = "executor?command=registration&subcommand=5";
    private static final RegistrationSub5 sub5 = new RegistrationSub5();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subCommand = request.getParameter(subcommandParam);
        if (!subCommand.equals(subcommand4)) {
            sub5.execute(request, response);
            return;
        }
        LogicFactory factory = LogicFactory.getInstance();
        UserLogic userLogic = factory.getUserLogic();
        String email = request.getParameter(emailKey);
        String firstName = request.getParameter(firstNameKey);
        String lastName = request.getParameter(lastNameKey);
        Map<String, String> params = new HashMap<>();
        params.put(loginKey, request.getParameter(loginKey));
        params.put(passwordKey, request.getParameter(passwordKey));
        params.put(rPasswordKey, request.getParameter(rPasswordKey));
        params.put(firstNameKey, firstName);
        params.put(lastNameKey, lastName);
        params.put(birthDateKey, request.getParameter(birthDateKey));
        params.put(emailKey, email);
        params.put(telephoneKey, request.getParameter(telephoneKey));
        params.put(genderKey, request.getParameter(genderKey));
        HttpSession session = request.getSession();
        session.setAttribute("reg-args", params);
        String key;
        try {
            key = userLogic.confirmRegistration(email, firstName, lastName);
        } catch (LogicException e) {
            Logger logger = LogManager.getLogger(RegistrationSub4.class);
            logger.error(e.getMessage(), e);
            throw new ServletException(e.getMessage(), e);
        }
        session.setAttribute("reg-key", key);
        response.sendRedirect(commandPath);
    }
}
