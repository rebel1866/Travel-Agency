package by.epamtc.stanislavmelnikov.controller.commandimpl.giveadminrights;

import by.epamtc.stanislavmelnikov.controller.commandinterface.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class GiveAdminSub2 implements Command {
    private static final GiveAdminSub3 sub3 = new GiveAdminSub3();
    private static final String commandPath = "/executor?command=full_user_info&user_id=";
    private static final String backMessage ="Назад к просмотру пользователей";
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subcommand= request.getParameter(subcommandParam);
        if(!subcommand.equals(subcommand2)){
            sub3.execute(request, response);
            return;
        }
        HttpSession session = request.getSession();
        int userId = Integer.parseInt((String) session.getAttribute(giveAdminRights));
        session.removeAttribute(giveAdminRights);
        request.setAttribute(linkKey, commandPath + userId);
        request.setAttribute(messageKey, backMessage);
        request.getRequestDispatcher(jspSuccessfulOperation).forward(request, response);
    }
}
