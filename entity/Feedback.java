package by.epamtc.stanislavmelnikov.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class Feedback implements Serializable {
    private static final long SerialVersionUID = 156416541564156145L;
    private int feedbackId;
    private String feedbackBody;
    private LocalDateTime fbkDateTime;
    private int fbkRating;
    private String tourName;
    private String fbkStatus;
    private Tour tour;
    private User user;

    public Feedback() {

    }

    public int getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(int feedbackId) {
        this.feedbackId = feedbackId;
    }

    public int getFbkRating() {
        return fbkRating;
    }

    public void setFbkRating(int fbkRating) {
        this.fbkRating = fbkRating;
    }

    public String getFeedbackBody() {
        return feedbackBody;
    }

    public void setFeedbackBody(String feedbackBody) {
        this.feedbackBody = feedbackBody;
    }

    public LocalDateTime getFbkDateTime() {
        return fbkDateTime;
    }

    public void setFbkDateTime(LocalDateTime fbkDateTime) {
        this.fbkDateTime = fbkDateTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTourName() {
        return tourName;
    }

    public void setTourName(String tourName) {
        this.tourName = tourName;
    }

    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    public String getFbkStatus() {
        return fbkStatus;
    }

    public void setFbkStatus(String fbkStatus) {
        this.fbkStatus = fbkStatus;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "feedbackId=" + feedbackId +
                ", feedbackBody='" + feedbackBody + '\'' +
                ", fbkDateTime=" + fbkDateTime +
                ", fbkRating=" + fbkRating +
                ", tourName='" + tourName + '\'' +
                ", fbkStatus='" + fbkStatus + '\'' +
                ", tour=" + tour +
                ", user=" + user +
                '}' + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Feedback feedback = (Feedback) o;
        return feedbackId == feedback.feedbackId && fbkRating == feedback.fbkRating && Objects.equals(feedbackBody,
                feedback.feedbackBody) && Objects.equals(fbkDateTime, feedback.fbkDateTime) && Objects.equals(tourName,
                feedback.tourName) && Objects.equals(fbkStatus, feedback.fbkStatus) && Objects.equals(tour, feedback.tour)
                && Objects.equals(user, feedback.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackId, feedbackBody, fbkDateTime, fbkRating, tourName, fbkStatus, tour, user);
    }
}
