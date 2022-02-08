package by.epamtc.stanislavmelnikov.controller.commandimpl.addfeedback;

import by.epamtc.stanislavmelnikov.controller.commandinterface.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddFeedbackSub1 implements Command {
    private static final String jspPath = "WEB-INF/jsp/thank-for-feedback.jsp";
    private static final String commandPath = "/executor?command=full_tour_info&id=";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subcommand = request.getParameter(subcommandParam);
        if (!subcommand.equals(subcommand1)) {
            request.getRequestDispatcher(homePath).forward(request, response);
            return;
        }
        request.setAttribute(linkKey, commandPath + request.getParameter(tourIdParam));
        request.getRequestDispatcher(jspPath).forward(request, response);
    }
}
