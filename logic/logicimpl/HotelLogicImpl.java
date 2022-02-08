package by.epamtc.stanislavmelnikov.logic.logicimpl;

import by.epamtc.stanislavmelnikov.dao.daointerface.HotelDao;
import by.epamtc.stanislavmelnikov.dao.exception.DaoException;
import by.epamtc.stanislavmelnikov.dao.factory.DaoFactory;
import by.epamtc.stanislavmelnikov.entity.Hotel;
import by.epamtc.stanislavmelnikov.entity.HotelImage;
import by.epamtc.stanislavmelnikov.entity.Resort;
import by.epamtc.stanislavmelnikov.logic.exception.LogicException;
import by.epamtc.stanislavmelnikov.logic.logicinterface.HotelLogic;
import by.epamtc.stanislavmelnikov.logic.validation.Validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HotelLogicImpl implements HotelLogic {
    @Override
    public List<Hotel> findHotelsByParams(Map<String, String> params) throws LogicException {
        Validation.excludeInjections(params);
        DaoFactory daoFactory = DaoFactory.getInstance();
        HotelDao hotelDao = daoFactory.getHotelDao();
        List<Hotel> hotels;
        try {
            hotels = hotelDao.findHotelsByParams(params);
        } catch (DaoException e) {
            throw new LogicException(e.getMessage(), e);
        }
        return hotels;
    }

    @Override
    public int countHotelsByParams(Map<String, String> params) throws LogicException {
        Validation.excludeInjections(params);
        int amountItems;
        DaoFactory daoFactory = DaoFactory.getInstance();
        HotelDao hotelDao = daoFactory.getHotelDao();
        try {
            amountItems = hotelDao.countHotelsByParams(params);
        } catch (DaoException e) {
            throw new LogicException(e.getMessage(), e);
        }
        return amountItems;
    }

    @Override
    public Hotel findHotelById(int id) throws LogicException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        HotelDao hotelDao = daoFactory.getHotelDao();
        Hotel hotel;
        try {
            hotel = hotelDao.findHotelById(id);
        } catch (DaoException e) {
            throw new LogicException(e.getMessage(), e);
        }
        return hotel;
    }

    @Override
    public void addHotel(Map<String, String> params) throws LogicException {
        String hotelName = Validation.excludeInjections(params.get("hotel_name"));
        String resortId = Validation.excludeInjections(params.get("resort_id"));
        String rating = Validation.excludeInjections(params.get("rating"));
        String hotelDes = Validation.excludeInjections(params.get("hotel_description"));
        String amountRooms = Validation.excludeInjections(params.get("amount_rooms"));
        String typeLocation = Validation.excludeInjections(params.get("type_location"));
        String infrastructure = Validation.excludeInjections(params.get("infrastructure"));
        List<HotelImage> images = getImages(params);
        validateFields(hotelName, rating, hotelDes, amountRooms, typeLocation, infrastructure, resortId);
        Hotel hotel = buildHotel(hotelName, resortId, rating, hotelDes, amountRooms, typeLocation, infrastructure, images);
        DaoFactory daoFactory = DaoFactory.getInstance();
        HotelDao hotelDao = daoFactory.getHotelDao();
        try {
            hotelDao.addHotel(hotel);
        } catch (DaoException e) {
            throw new LogicException(e.getMessage(), e);
        }
    }

    @Override
    public void removeHotel(int id) throws LogicException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        HotelDao hotelDao = daoFactory.getHotelDao();
        try {
            hotelDao.removeHotel(id);
        } catch (DaoException e) {
            throw new LogicException(e.getMessage(), e);
        }
    }

    @Override
    public void updateHotel(Map<String, String> params) throws LogicException {
        String hotelId = Validation.excludeInjections(params.get("hotel_id"));
        String hotelName = Validation.excludeInjections(params.get("hotel_name"));
        String resortId = Validation.excludeInjections(params.get("resort_id"));
        String rating = Validation.excludeInjections(params.get("rating"));
        String hotelDes = Validation.excludeInjections(params.get("hotel_description"));
        String amountRooms = Validation.excludeInjections(params.get("amount_rooms"));
        String typeLocation = Validation.excludeInjections(params.get("type_location"));
        String infrastructure = Validation.excludeInjections(params.get("infrastructure"));
        List<HotelImage> newImages = getImages(params);
        List<HotelImage> imagesToRemove = getImagesToRemove(params);
        newImages.addAll(imagesToRemove);
        validateFields(hotelName, rating, hotelDes, amountRooms, typeLocation, infrastructure, resortId);
        Hotel hotel = buildHotel(hotelName, resortId, rating, hotelDes, amountRooms, typeLocation, infrastructure, newImages);
        hotel.setHotelId(Integer.parseInt(hotelId));
        DaoFactory daoFactory = DaoFactory.getInstance();
        HotelDao hotelDao = daoFactory.getHotelDao();
        try {
            hotelDao.updateHotel(hotel);
        } catch (DaoException e) {
            throw new LogicException(e.getMessage(), e);
        }
    }

    public List<HotelImage> getImagesToRemove(Map<String, String> params) {
        List<HotelImage> hotelImages = new ArrayList<>();
        for (int i = 1; i < 20; i++) {
            String key = "remove-image-" + i;
            String value = params.get(key);
            if (value == null) continue;
            HotelImage hotelImage = new HotelImage();
            hotelImage.setImageId(Integer.parseInt(value));
            hotelImages.add(hotelImage);
        }
        return hotelImages;
    }

    public Hotel buildHotel(String hotelName, String resortId, String rating, String hotelDes, String amountRooms,
                            String typeLocation, String infrastructure, List<HotelImage> images) {
        Hotel hotel = new Hotel();
        hotel.setHotelName(hotelName);
        Resort resort = new Resort();
        resort.setResortId(Integer.parseInt(resortId));
        hotel.setResort(resort);
        hotel.setRating(Integer.parseInt(rating));
        hotel.setHotelDescription(hotelDes);
        hotel.setAmountRooms(Integer.parseInt(amountRooms));
        hotel.setTypeLocation(typeLocation);
        hotel.setInfrastructure(infrastructure);
        hotel.setHotelImages(images);
        return hotel;
    }

    public void validateFields(String hotelName, String rating, String hotelDes, String amountRooms,
                               String typeLocation, String infrastructure, String resortId) throws LogicException {
        if (Validation.isEmpty(hotelName)) throw new LogicException("Incorrect value - hotel name is empty");
        if (Validation.isEmpty(hotelDes)) throw new LogicException("Incorrect value - hotel description is empty");
        if (Validation.isEmpty(typeLocation))
            throw new LogicException("Incorrect value - hotel type location is empty");
        if (Validation.isEmpty(infrastructure))
            throw new LogicException("Incorrect value - hotel infrastructure is empty");
        if (!Validation.isCorrectInteger(rating)) throw new LogicException("Incorrect rating");
        if (!Validation.isCorrectInteger(amountRooms)) throw new LogicException("Incorrect amount rooms");
        if (!Validation.isCorrectInteger(resortId)) throw new LogicException("Incorrect resort id");
    }

    public List<HotelImage> getImages(Map<String, String> params) {
        List<HotelImage> images = new ArrayList<>();
        for (int i = 1; ; i++) {
            String key = "image" + i;
            String value = params.get(key);
            if (value == null) break;
            HotelImage hotelImage = new HotelImage();
            hotelImage.setImagePath(value);
            images.add(hotelImage);
        }
        return images;
    }
}
