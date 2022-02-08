package by.epamtc.stanislavmelnikov.controller.commandimpl.addrole;

import by.epamtc.stanislavmelnikov.controller.commandinterface.Command;
import by.epamtc.stanislavmelnikov.logic.exception.LogicException;
import by.epamtc.stanislavmelnikov.logic.factory.LogicFactory;
import by.epamtc.stanislavmelnikov.logic.logicinterface.UserLogic;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AddUserRole implements Command {
    private static final AddRoleSub1 sub1 = new AddRoleSub1();
    private static final int amountCommands = 31;
    private static final String commandPath = "executor?command=add_new_role&subcommand=2";
    private static final String roleNameParam = "role_name";
    private static final String adminKey = "Admin";
    private static final String rightsListKey = "rights_list";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subcommand = request.getParameter(subcommandParam);
        if (!subcommand.equals(mainSubcommand)) {
            sub1.execute(request, response);
            return;
        }
        String roleName = request.getParameter(roleNameParam);
        StringBuilder rightsList = getRights(request);
        Map<String, String> args = new HashMap<>();
        args.put(roleNameParam, adminKey + roleName);
        args.put(rightsListKey, rightsList.toString());
        LogicFactory factory = LogicFactory.getInstance();
        UserLogic userLogic = factory.getUserLogic();
        try {
            userLogic.addNewRole(args);
        } catch (LogicException e) {
            throw new ServletException(e.getMessage(), e);
        }
        response.sendRedirect(commandPath);
    }

    public StringBuilder getRights(HttpServletRequest request) {
        StringBuilder rightsList = new StringBuilder();
        for (int i = 1; i <= amountCommands; i++) {
            String element = request.getParameter("input" + i);
            if (element != null) {
                rightsList.append(element);
                if (i != amountCommands) rightsList.append(", ");
            }
        }
        return rightsList;
    }
}
