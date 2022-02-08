package by.epamtc.stanislavmelnikov.entity;

import java.io.Serializable;
import java.util.Objects;

public class Resort implements Serializable {
    private int resortId;
    private String resortName;
    private String location;
    private int population;
    private String resortDescription;

    public Resort() {

    }

    public int getResortId() {
        return resortId;
    }

    public void setResortId(int resortId) {
        this.resortId = resortId;
    }

    public String getResortName() {
        return resortName;
    }

    public void setResortName(String resortName) {
        this.resortName = resortName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public String getResortDescription() {
        return resortDescription;
    }

    public void setResortDescription(String resortDescription) {
        this.resortDescription = resortDescription;
    }

    @Override
    public String toString() {
        return "Resort{" +
                "resortId=" + resortId +
                ", resortName='" + resortName + '\'' +
                ", location='" + location + '\'' +
                ", population=" + population +
                ", resortDescription='" + resortDescription + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resort resort = (Resort) o;
        return resortId == resort.resortId && population == resort.population && Objects.equals(resortName,
                resort.resortName) && Objects.equals(location, resort.location) && Objects.equals(resortDescription,
                resort.resortDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resortId, resortName, location, population, resortDescription);
    }
}
