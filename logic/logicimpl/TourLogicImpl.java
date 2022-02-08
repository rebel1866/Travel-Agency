package by.epamtc.stanislavmelnikov.logic.logicimpl;

import by.epamtc.stanislavmelnikov.dao.daointerface.TourDao;
import by.epamtc.stanislavmelnikov.dao.exception.DaoException;
import by.epamtc.stanislavmelnikov.dao.factory.DaoFactory;
import by.epamtc.stanislavmelnikov.entity.Hotel;
import by.epamtc.stanislavmelnikov.entity.Tour;
import by.epamtc.stanislavmelnikov.entity.currencyholder.CurrencyHolder;
import by.epamtc.stanislavmelnikov.logic.exception.LogicException;
import by.epamtc.stanislavmelnikov.logic.logicinterface.TourLogic;
import by.epamtc.stanislavmelnikov.logic.validation.Validation;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.servlet.http.Cookie;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TourLogicImpl implements TourLogic {
    private final static String key = "fields.add.tour.form";
    private static final String currencyRegex = "\"Cur_OfficialRate\":([.\\d]+)}";
    private static final String currencyNameRegex = "\"Cur_Abbreviation\":\"([A-Z]+)\",";

    @Override
    public List<Tour> findToursByParams(Map<String, String> params) throws LogicException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        TourDao tourDao = daoFactory.getTourDao();
        List<Tour> tours;
        Validation.excludeInjections(params);
        try {
            tours = tourDao.findToursByParams(params);
        } catch (DaoException e) {
            throw new LogicException(e.getMessage(), e);
        }
        return tours;
    }

    @Override
    public int countToursByParams(Map<String, String> params) throws LogicException {
        int amountItems;
        DaoFactory daoFactory = DaoFactory.getInstance();
        TourDao tourDao = daoFactory.getTourDao();
        Validation.excludeInjections(params);
        try {
            amountItems = tourDao.countToursByParams(params);
        } catch (DaoException e) {
            throw new LogicException(e.getMessage(), e);
        }
        return amountItems;
    }

    @Override
    public Tour findTourById(int id) throws LogicException {
        Tour tour;
        DaoFactory daoFactory = DaoFactory.getInstance();
        TourDao tourDao = daoFactory.getTourDao();
        try {
            tour = tourDao.findTourById(id);
        } catch (DaoException e) {
            throw new LogicException(e.getMessage(), e);
        }
        return tour;
    }

    @Override
    public void addTour(Map<String, String> params) throws LogicException {
        Validation.excludeInjections(params, key);
        String tourName = params.get("tour_name");
        int hotelId = Integer.parseInt(params.get("hotel_id"));
        String transport = params.get("transport");
        String departureStr = params.get("departure");
        String comebackStr = params.get("comeback");
        String feeding = params.get("feeding");
        String priceStr = params.get("price");
        String amountAdultsStr = params.get("amount_adults");
        String amountChildrenStr = params.get("amount_children");
        String hotelRoomType = params.get("hotel_room_type");
        String amountSeatsStr = params.get("amount_seats");
        String burningStatusStr = params.get("burning_status");
        String relevanceStr = params.get("relevance");
        String tourStatus = params.get("tour_status");
        validateFields(departureStr, comebackStr, priceStr, amountAdultsStr, amountChildrenStr, amountSeatsStr, relevanceStr
                , tourName, transport, feeding, hotelRoomType, burningStatusStr, tourStatus);
        Tour tour = buildTour(tourName, hotelId, transport, departureStr, comebackStr, feeding, priceStr, amountAdultsStr,
                amountChildrenStr, hotelRoomType, amountSeatsStr, burningStatusStr, relevanceStr, tourStatus);
        DaoFactory daoFactory = DaoFactory.getInstance();
        TourDao tourDao = daoFactory.getTourDao();
        try {
            tourDao.addTour(tour);
        } catch (DaoException e) {
            throw new LogicException(e.getMessage(), e);
        }
    }

    @Override
    public void removeTourById(int id) throws LogicException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        TourDao tourDao = daoFactory.getTourDao();
        try {
            tourDao.removeTourById(id);
        } catch (DaoException e) {
            throw new LogicException(e.getMessage(), e);
        }
    }

    @Override
    public void updateTour(Map<String, String> params) throws LogicException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        TourDao tourDao = daoFactory.getTourDao();
        Validation.excludeInjections(params, key);
        String tourName = params.get("tour_name");
        int hotelId = Integer.parseInt(params.get("hotel_id"));
        String transport = params.get("transport");
        String departureStr = params.get("departure");
        String comebackStr = params.get("comeback");
        String feeding = params.get("feeding");
        String priceStr = params.get("price");
        String amountAdultsStr = params.get("amount_adults");
        String amountChildrenStr = params.get("amount_children");
        String hotelRoomType = params.get("hotel_room_type");
        String amountSeatsStr = params.get("amount_seats");
        String burningStatusStr = params.get("burning_status");
        String relevanceStr = params.get("relevance");
        String tourStatus = params.get("tour_status");
        String tourIdStr = params.get("tour_id");
        if (!Validation.isCorrectInteger(tourIdStr)) throw new LogicException("Incorrect tour id");
        int tourId = Integer.parseInt(params.get("tour_id"));
        validateFields(departureStr, comebackStr, priceStr, amountAdultsStr, amountChildrenStr, amountSeatsStr, relevanceStr
                , tourName, transport, feeding, hotelRoomType, burningStatusStr, tourStatus);
        Tour tour = buildTour(tourName, hotelId, transport, departureStr, comebackStr, feeding, priceStr, amountAdultsStr,
                amountChildrenStr, hotelRoomType, amountSeatsStr, burningStatusStr, relevanceStr, tourStatus);
        tour.setTourId(tourId);
        try {
            tourDao.updateTour(tour);
        } catch (DaoException e) {
            throw new LogicException(e.getMessage(), e);
        }
    }

    @Override
    public List<Tour> findRecommendedTours(Cookie[] cookies) throws LogicException {
        List<Integer> IDs = new ArrayList<>();
        if (cookies == null) return new ArrayList<>();
        for (Cookie cookie : cookies) {
            if (cookie.getName().startsWith("tour_id")) {
                IDs.add(Integer.parseInt(cookie.getValue()));
            }
        }
        List<Tour> toursWatched = new ArrayList<>();
        for (Integer id : IDs) {
            Tour tour = findTourById(id);
            toursWatched.add(tour);
        }
        Map<String, String> params = new HashMap<>();
        toursWatched.stream().filter((l) -> l.getHotel() != null).map((l) -> l.getHotel().getResort().getLocation()).
                filter(Objects::nonNull).
                collect(Collectors.groupingBy(Function.identity(), Collectors.counting())).entrySet().stream()
                .max(Map.Entry.comparingByValue()).ifPresent((l) -> {
                    params.put("restrictions", "location");
                    params.put("location", l.getKey());
                    params.put("limit", "startPoint=0,amountOnPage=3");
                    params.put("sorting", "rating desc");
                });
        if (params.size() == 0) return new ArrayList<>();
        return findToursByParams(params);
    }

    @Override
    public void runCurrencyUpdater() throws LogicException {
        final String nbrbUrl = "https://www.nbrb.by/api/exrates/rates/431";
        while (true) {
            try {
                Document doc = Jsoup.connect(nbrbUrl).ignoreContentType(true).get();
                String json = doc.body().text();
                Pattern ratePattern = Pattern.compile(currencyRegex);
                Pattern rateNamePattern = Pattern.compile(currencyNameRegex);
                Matcher matcherRate = ratePattern.matcher(json);
                Matcher matcherRateName = rateNamePattern.matcher(json);
                String currencyName = "";
                String currencyRate = "";
                if (matcherRate.find()) currencyRate = matcherRate.group(1);
                if (matcherRateName.find()) currencyName = matcherRateName.group(1);
                boolean isEmpty = CurrencyHolder.getCurrencyRates().size() == 0;
                if (isEmpty) {
                    CurrencyHolder.getCurrencyRates().put(currencyName, Double.parseDouble(currencyRate));
                } else {
                    CurrencyHolder.getCurrencyRates().replace(currencyName, Double.parseDouble(currencyRate));
                }
                try {
                    TimeUnit.HOURS.sleep(1);
                } catch (InterruptedException e) {
                    throw new LogicException(e.getMessage(), e);
                }
            } catch (IOException e) {
                throw new LogicException("cannot connect to nbrb api", e);
            }
        }
    }
       public static class CurrencyHolder {
        private static Map<String, Double> currencyRates = new ConcurrentHashMap<>();

        public static Map<String, Double> getCurrencyRates() {
            return currencyRates;
        }

        public static void setCurrencyRates(Map<String, Double> currencyRates) {
            CurrencyHolder.currencyRates = currencyRates;
        }
    }

    public void validateFields(String departureStr, String comebackStr, String priceStr, String amountAdultsStr,
                               String amountChildrenStr, String amountSeatsStr, String relevanceStr, String tourName,
                               String transport, String feeding, String roomType, String burningStatus,
                               String tourStatus) throws LogicException {
        if (Validation.isEmpty(tourName)) throw new LogicException("Empty tour name");
        if (Validation.isEmpty(transport)) throw new LogicException("Empty transport field");
        if (!Validation.isCorrectDate(comebackStr)) throw new LogicException("Incorrect comeback value");
        if (!Validation.isCorrectDate(departureStr)) throw new LogicException("Incorrect departure value");
        if (!Validation.isCorrectBoolean(burningStatus)) throw new LogicException("Wrong burning status value");
        if (!Validation.isCorrectInteger(relevanceStr)) throw new LogicException("Incorrect input relevance");
        if (!Validation.isCorrectInteger(amountSeatsStr)) throw new LogicException("Incorrect input amount seats");
        if (Validation.isEmpty(roomType)) throw new LogicException("Empty room type field");
        if (!Validation.isCorrectInteger(priceStr)) throw new LogicException("Incorrect input price");
        if (Validation.isEmpty(feeding)) throw new LogicException("Empty feeding field");
        if (!Validation.isCorrectInteger(amountAdultsStr)) throw new LogicException("Incorrect input amount adults");
        if (!Validation.isCorrectInteger(amountChildrenStr))
            throw new LogicException("Incorrect input amount children");
        if (Validation.isEmpty(tourStatus)) throw new LogicException("Empty tour status field");
    }

    public Tour buildTour(String tourName, int hotelId, String transport, String departureStr, String comebackStr, String feeding,
                          String priceStr, String amountAdultsStr, String amountChildrenStr, String hotelRoomType,
                          String amountSeatsStr, String burningStatus, String relevanceStr, String tourStatus) {
        Tour tour = new Tour();
        tour.setTourName(tourName);
        Hotel hotel = new Hotel();
        hotel.setHotelId(hotelId);
        tour.setHotel(hotel);
        tour.setTransport(transport);
        tour.setDeparture(LocalDate.parse(departureStr));
        tour.setComeback(LocalDate.parse(comebackStr));
        tour.setFeeding(feeding);
        tour.setPrice(Integer.parseInt(priceStr));
        tour.setAmountAdults(Integer.parseInt(amountAdultsStr));
        tour.setAmountChildren(Integer.parseInt(amountChildrenStr));
        tour.setHotelRoomType(hotelRoomType);
        tour.setAmountSeats(Integer.parseInt(amountSeatsStr));
        tour.setBurningStatus(Boolean.parseBoolean(burningStatus));
        tour.setRelevance(Integer.parseInt(relevanceStr));
        tour.setTourStatus(tourStatus);
        return tour;
    }
}
