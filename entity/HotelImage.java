package by.epamtc.stanislavmelnikov.entity;


import java.io.Serializable;
import java.util.Objects;

public class HotelImage implements Serializable {
    private static final long SerialVersionUID = 4756416541564156141L;
    private int imageId;
    private String imagePath;

    public HotelImage() {

    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public String toString() {
        return "HotelImage{" +
                "imageId=" + imageId +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (getClass() != o.getClass()) return false;
        HotelImage image = (HotelImage) o;
        if (imageId != image.imageId) {
            return false;
        }
        if (null == imagePath) {
            return imagePath == image.imagePath;
        } else {
            if (!imagePath.equals(image.imagePath)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(imageId, imagePath);
    }
}
