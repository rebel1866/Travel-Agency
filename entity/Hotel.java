package by.epamtc.stanislavmelnikov.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Hotel implements Serializable {
    private static final long SerialVersionUID = 2456416541564156140L;
    private int hotelId;
    private String hotelName;
    private int rating;
    private String hotelDescription;
    private int amountRooms;
    private String typeLocation;
    private String infrastructure;
    private Resort resort;
    private List<HotelImage> hotelImages;

    public Hotel(){

    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public String getHotelName() {
        return hotelName;
    }

    public String getInfrastructure() {
        return infrastructure;
    }

    public void setInfrastructure(String infrastructure) {
        this.infrastructure = infrastructure;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getHotelDescription() {
        return hotelDescription;
    }

    public void setHotelDescription(String hotelDescription) {
        this.hotelDescription = hotelDescription;
    }

    public int getAmountRooms() {
        return amountRooms;
    }

    public void setAmountRooms(int amountRooms) {
        this.amountRooms = amountRooms;
    }

    public String getTypeLocation() {
        return typeLocation;
    }

    public void setTypeLocation(String typeLocation) {
        this.typeLocation = typeLocation;
    }

    public Resort getResort() {
        return resort;
    }

    public void setResort(Resort resort) {
        this.resort = resort;
    }

    public List<HotelImage> getHotelImages() {
        return hotelImages;
    }

    public void setHotelImages(List<HotelImage> hotelImages) {
        this.hotelImages = hotelImages;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "hotelId=" + hotelId +
                ", hotelName='" + hotelName + '\'' +
                ", rating=" + rating +
                ", hotelDescription='" + hotelDescription + '\'' +
                ", amountRooms=" + amountRooms +
                ", typeLocation=" + typeLocation +
                ", resort=" + resort +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hotel hotel = (Hotel) o;
        return hotelId == hotel.hotelId && rating == hotel.rating && amountRooms == hotel.amountRooms && Objects.
                equals(hotelName, hotel.hotelName) && Objects.equals(hotelDescription,
                hotel.hotelDescription) && Objects.equals(typeLocation, hotel.typeLocation) && Objects.equals(infrastructure,
                hotel.infrastructure) && Objects.equals(resort, hotel.resort) && Objects.equals(hotelImages, hotel.hotelImages);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hotelId, hotelName, rating, hotelDescription, amountRooms, typeLocation, infrastructure, resort, hotelImages);
    }
}
