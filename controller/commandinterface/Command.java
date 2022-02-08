package by.epamtc.stanislavmelnikov.controller.commandinterface;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public interface Command {

    String subcommandParam = "subcommand";
    String mainSubcommand = "main";
    String subcommand1 = "1";
    String subcommand2 = "2";
    String subcommand3 = "3";
    String subcommand4 = "4";
    String exceptionMessage = "No such subcommand";
    String userIdParam = "user_id";
    String tourIdParam = "tour_id";
    String hotelIdParam = "hotel_id";
    String orderIdParam = "order_id";
    String giveAdminRights = "give_admin_id";
    String hotelNameKey = "hotel_name";
    String ratingParam = "rating";
    String fbkBodyParam = "fbk_body";
    String accessAllowedKey = "access-allowed";
    String jspNotAvailable = "WEB-INF/jsp/not-available.jsp";
    String jspSuccessfulOperation = "WEB-INF/jsp/successful-operation.jsp";
    String ajaxKey = "ajax";
    String pageKey = "page";
    String linkKey = "link";
    String homePath = "/executor?command=home";
    String allowedValue = "true";
    String messageKey = "message";
    String restrictionsKey = "restrictions";
    String authParam = "authorization";
    String paginationKey = "pagination";

    void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
