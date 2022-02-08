package by.epamtc.stanislavmelnikov.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Tour implements Serializable {
    private static final long SerialVersionUID = 756416541564104545L;
    private int tourId;
    private String tourName;
    private String transport;
    private LocalDate departure;
    private LocalDate comeback;
    private int price;
    private String feeding;
    private int amountAdults;
    private boolean burningStatus;
    private int relevance;
    private int amountChildren;
    private String hotelRoomType;
    private int amountSeats;
    private String tourStatus;
    private Hotel hotel;
    private List<Feedback> feedbacks;

    public Tour() {

    }

    public int getTourId() {
        return tourId;
    }

    public void setTourId(int tourId) {
        this.tourId = tourId;
    }

    public String getTourName() {
        return tourName;
    }

    public void setTourName(String tourName) {
        this.tourName = tourName;
    }

    public String getTransport() {
        return transport;
    }

    public int getAmountSeats() {
        return amountSeats;
    }

    public void setAmountSeats(int amountSeats) {
        this.amountSeats = amountSeats;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }

    public int getAmountChildren() {
        return amountChildren;
    }

    public void setAmountChildren(int amountChildren) {
        this.amountChildren = amountChildren;
    }

    public LocalDate getDeparture() {
        return departure;
    }

    public void setDeparture(LocalDate departure) {
        this.departure = departure;
    }

    public LocalDate getComeback() {
        return comeback;
    }

    public void setComeback(LocalDate comeback) {
        this.comeback = comeback;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getFeeding() {
        return feeding;
    }

    public String getHotelRoomType() {
        return hotelRoomType;
    }

    public void setHotelRoomType(String hotelRoomType) {
        this.hotelRoomType = hotelRoomType;
    }

    public void setFeeding(String feeding) {
        this.feeding = feeding;
    }

    public int getAmountAdults() {
        return amountAdults;
    }

    public void setAmountAdults(int amountAdults) {
        this.amountAdults = amountAdults;
    }

    public boolean isBurningStatus() {
        return burningStatus;
    }

    public void setBurningStatus(boolean burningStatus) {
        this.burningStatus = burningStatus;
    }

    public int getRelevance() {
        return relevance;
    }

    public void setRelevance(int relevance) {
        this.relevance = relevance;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public List<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }

    public String getTourStatus() {
        return tourStatus;
    }

    public void setTourStatus(String tourStatus) {
        this.tourStatus = tourStatus;
    }

    @Override
    public String toString() {
        return "Tour{" +
                "tourId=" + tourId +
                ", tourName='" + tourName + '\'' +
                ", transport='" + transport + '\'' +
                ", departure=" + departure +
                ", comeback=" + comeback +
                ", price=" + price +
                ", feeding='" + feeding + '\'' +
                ", amountPersons=" + amountAdults +
                ", burningStatus=" + burningStatus +
                ", relevance=" + relevance +
                ", hotel=" + hotel +
                ", feedbacks=" + feedbacks +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tour tour = (Tour) o;
        return tourId == tour.tourId && price == tour.price && amountAdults == tour.amountAdults && burningStatus ==
                tour.burningStatus && relevance == tour.relevance && amountChildren == tour.amountChildren &&
                amountSeats == tour.amountSeats && tourName.equals(tour.tourName) && transport.equals(tour.transport)
                && departure.equals(tour.departure) && comeback.equals(tour.comeback) && Objects.equals(feeding,
                tour.feeding) && Objects.equals(hotelRoomType, tour.hotelRoomType) && Objects.equals(tourStatus,
                tour.tourStatus) && Objects.equals(hotel, tour.hotel) && Objects.equals(feedbacks, tour.feedbacks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tourId, tourName, transport, departure, comeback, price, feeding, amountAdults, burningStatus,
                relevance, amountChildren, hotelRoomType, amountSeats, tourStatus, hotel, feedbacks);
    }
}
