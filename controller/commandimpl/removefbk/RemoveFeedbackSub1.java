package by.epamtc.stanislavmelnikov.controller.commandimpl.removefbk;

import by.epamtc.stanislavmelnikov.controller.commandinterface.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RemoveFeedbackSub1 implements Command {
    private static final String backMessage = "Назад";
    private static final String commandPath = "/executor?";
    private static final String toReplace = "::";
    private static final String replacement = "&";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subcommand = request.getParameter(subcommandParam);
        if (!subcommand.equals(subcommand1)) {
            request.getRequestDispatcher(homePath).forward(request, response);
            return;
        }
        String qStr = request.getParameter("qStr");
        qStr = qStr.replaceAll(toReplace, replacement);
        request.setAttribute(linkKey, commandPath + qStr);
        request.setAttribute(messageKey, backMessage);
        request.getRequestDispatcher(jspSuccessfulOperation).forward(request, response);
    }
}
