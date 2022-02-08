package by.epamtc.stanislavmelnikov.controller.commandimpl;

import by.epamtc.stanislavmelnikov.controller.commandinterface.Command;
import by.epamtc.stanislavmelnikov.entity.Feedback;
import by.epamtc.stanislavmelnikov.entity.Hotel;
import by.epamtc.stanislavmelnikov.entity.Tour;
import by.epamtc.stanislavmelnikov.logic.exception.LogicException;
import by.epamtc.stanislavmelnikov.logic.factory.LogicFactory;
import by.epamtc.stanislavmelnikov.logic.logicinterface.FeedbackLogic;
import by.epamtc.stanislavmelnikov.logic.logicinterface.HotelLogic;
import by.epamtc.stanislavmelnikov.logic.logicinterface.TourLogic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeManager implements Command {
    private static final String homeJsp = "WEB-INF/jsp/home.jsp";
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LogicFactory factory = LogicFactory.getInstance();
        TourLogic logic = factory.getTourLogic();
        HotelLogic hotelLogic = factory.getHotelLogic();
        FeedbackLogic feedbackLogic = factory.getFeedbackLogic();
        Logger logger = LogManager.getLogger(HomeManager.class);
        Map<String, String> paramsTour = generateParamsTour();
        Map<String, String> paramsHotel = generateParamsHotel();
        Map<String, String> paramsFeed = generateParamsFbk();
        List<Tour> tours;
        List<Feedback> feedbacks;
        List<Hotel> hotels;
        List<Tour> recommendedTours;
        Cookie[] cookies = request.getCookies();
        try {
            tours = logic.findToursByParams(paramsTour);
            hotels = hotelLogic.findHotelsByParams(paramsHotel);
            feedbacks = feedbackLogic.findFeedbacksByParams(paramsFeed);
            recommendedTours = logic.findRecommendedTours(cookies);
        } catch (LogicException e) {
            logger.error(e.getMessage(), e);
            throw new ServletException(e.getMessage(), e);
        }
        request.setAttribute("feedbacks", feedbacks);
        request.setAttribute("hotels", hotels);
        request.setAttribute("tours", tours);
        request.setAttribute("rec_tours", recommendedTours);
        request.getRequestDispatcher(homeJsp).forward(request, response);
    }


    public Map<String, String> generateParamsFbk() {
        Map<String, String> paramsFeed = new HashMap<>();
        paramsFeed.put("limit", "startPoint=0,amountOnPage=2");
        paramsFeed.put("sorting", "fbk_date_time desc");
        return paramsFeed;
    }

    public Map<String, String> generateParamsHotel() {
        Map<String, String> paramsHotel = new HashMap<>();
        paramsHotel.put("limit", "startPoint=0,amountOnPage=3");
        paramsHotel.put("sorting", "rating desc");
        return paramsHotel;
    }

    public Map<String, String> generateParamsTour() {
        Map<String, String> paramsTour = new HashMap<>();
        paramsTour.put("restrictions", "burning_status");
        paramsTour.put("limit", "startPoint=0,amountOnPage=3");
        paramsTour.put("sorting", "relevance desc");
        paramsTour.put("burning_status", "1");
        return paramsTour;
    }
}
