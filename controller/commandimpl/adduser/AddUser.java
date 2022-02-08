package by.epamtc.stanislavmelnikov.controller.commandimpl.adduser;

import by.epamtc.stanislavmelnikov.controller.commandinterface.Command;
import by.epamtc.stanislavmelnikov.controller.controllerutils.ParamsGenerator;
import by.epamtc.stanislavmelnikov.logic.exception.LogicException;
import by.epamtc.stanislavmelnikov.logic.factory.LogicFactory;
import by.epamtc.stanislavmelnikov.logic.logicinterface.UserLogic;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

public class AddUser implements Command {
    private static final AddUserSub1 sub1 = new AddUserSub1();
    private static final String key = "fields.add.user";
    private static final String commandPath1 = "/executor?command=add_user&subcommand=1";
    private static final String commandPath2 = "executor?command=add_user&subcommand=2";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subcommand = request.getParameter(subcommandParam);
        if (!subcommand.equals(mainSubcommand)) {
            sub1.execute(request, response);
            return;
        }
        LogicFactory factory = LogicFactory.getInstance();
        UserLogic userLogic = factory.getUserLogic();
        Map<String, String> params = ParamsGenerator.getFormKeyValue(request, key);
        HttpSession session = request.getSession();
        try {
            userLogic.addUser(params);
        } catch (LogicException e) {
            request.setAttribute("e_message", e.getMessage());
            session.setAttribute(accessAllowedKey, allowedValue);
            request.getRequestDispatcher(commandPath1).forward(request, response);
            return;
        }
        response.sendRedirect(commandPath2);
    }
}
