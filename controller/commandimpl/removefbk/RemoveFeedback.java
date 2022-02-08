package by.epamtc.stanislavmelnikov.controller.commandimpl.removefbk;

import by.epamtc.stanislavmelnikov.controller.commandinterface.Command;
import by.epamtc.stanislavmelnikov.logic.exception.LogicException;
import by.epamtc.stanislavmelnikov.logic.factory.LogicFactory;
import by.epamtc.stanislavmelnikov.logic.logicinterface.FeedbackLogic;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RemoveFeedback implements Command {
    private static final RemoveFeedbackSub1 sub1 = new RemoveFeedbackSub1();
    private static final String commandPath = "executor?command=remove_feedback&subcommand=1&qStr=";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subcommand = request.getParameter(subcommandParam);
        if (!subcommand.equals(mainSubcommand)) {
            sub1.execute(request, response);
            return;
        }
        int fbkId = Integer.parseInt(request.getParameter("fbk_id"));
        LogicFactory logicFactory = LogicFactory.getInstance();
        FeedbackLogic feedbackLogic = logicFactory.getFeedbackLogic();
        try {
            feedbackLogic.removeFeedback(fbkId);
        } catch (LogicException e) {
            throw new ServletException(e.getMessage(), e);
        }
        String qStr = request.getParameter("qStr");
        response.sendRedirect(commandPath + qStr);
    }
}
