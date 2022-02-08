package by.epamtc.stanislavmelnikov.logic.factory;


import by.epamtc.stanislavmelnikov.logic.logicimpl.*;
import by.epamtc.stanislavmelnikov.logic.logicinterface.*;

public class LogicFactory {
    private static final LogicFactory instance = new LogicFactory();
    private TourLogic tourLogic = new TourLogicImpl();
    private HotelLogic hotelLogic = new HotelLogicImpl();
    private FeedbackLogic feedbackLogic = new FeedbackLogicImpl();
    private Pagination pagination = new PaginationImpl();
    private UserLogic userLogic = new UserLogicImpl();
    private ResortLogic resortLogic = new ResortLogicImpl();
    private OrderLogic orderLogic = new OrderLogicImpl();

    private LogicFactory() {

    }

    public static LogicFactory getInstance() {
        return instance;
    }

    public TourLogic getTourLogic() {
        return tourLogic;
    }

    public void setTourLogic(TourLogic tourLogic) {
        this.tourLogic = tourLogic;
    }

    public HotelLogic getHotelLogic() {
        return hotelLogic;
    }

    public void setHotelLogic(HotelLogic hotelLogic) {
        this.hotelLogic = hotelLogic;
    }

    public FeedbackLogic getFeedbackLogic() {
        return feedbackLogic;
    }

    public void setFeedbackLogic(FeedbackLogic feedbackLogic) {
        this.feedbackLogic = feedbackLogic;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public UserLogic getUserLogic() {
        return userLogic;
    }

    public void setUserLogic(UserLogic userLogic) {
        this.userLogic = userLogic;
    }

    public ResortLogic getResortLogic() {
        return resortLogic;
    }

    public void setResortLogic(ResortLogic resortLogic) {
        this.resortLogic = resortLogic;
    }

    public OrderLogic getOrderLogic() {
        return orderLogic;
    }

    public void setOrderLogic(OrderLogic orderLogic) {
        this.orderLogic = orderLogic;
    }
}
