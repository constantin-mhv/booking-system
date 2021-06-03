package com.bookingsystem.models.announcement;

import java.util.Objects;

public class SelectCondition {
    private String title;
    private String country;
    private String city;
    private String sportType;
    private Float priceMin;
    private Float priceMax;
    private String sortCondition;
    private String sortTarget;

    public SelectCondition(String country, String city, String sportType, Float priceMin,
                           Float priceMax, String sortCondition, String sortTarget) {
        this.country = country;
        this.city = city;
        this.sportType = sportType;
        if(priceMin == null)
            this.priceMin = 0f;
        else this.priceMin = priceMin;
        if(priceMax == null)
            this.priceMax = 10000000f;
        else this.priceMax = priceMax;
        if (sortCondition == null) {
            this.sortCondition = "DESC";
        } else
            this.sortCondition = sortCondition;
        this.sortTarget = Objects.requireNonNullElse(sortTarget, "publication_date_time");
    }

    public String getTitle() {
        return title;
    }

    public String getCountry() {
        return country;
    }

    public String getSortTarget() {
        return sortTarget;
    }

    public void setSortCondition(String sortCondition) {
        this.sortCondition = sortCondition;
    }

    public String getSortCondition() {
        return sortCondition;
    }

    public void setSortTarget(String sortTarget) {
        this.sortTarget = sortTarget;
    }

    public String getCity() {
        return city;
    }

    public String getSportType() {
        return sportType;
    }

    public Float getPriceMin() {
        return priceMin;
    }

    public Float getPriceMax() {
        return priceMax;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setSportType(String sportType) {
        this.sportType = sportType;
    }

    public void setPriceMin(Float priceMin) {
        this.priceMin = priceMin;
    }

    public void setPriceMax(Float priceMax) {
        this.priceMax = priceMax;
    }

}
