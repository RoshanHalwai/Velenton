package com.example.rhalwai.velenton.home;

import java.io.Serializable;

/**
 * Created by rhalwai on 12/27/2017.
 */

public class ProductDetails implements Serializable{
    private String Availability;
    private String Fabric;
    private String Length;
    private String Occasion;
    private String Size;
    private String Sleeves;
    private String Type;
    private String WashCare;
    private String Work;

    public ProductDetails() {
    }

    public ProductDetails(String availability, String fabric, String length, String occasion, String size, String sleeves, String type, String washCare, String work) {
        Availability = availability;
        Fabric = fabric;
        Length = length;
        Occasion = occasion;
        Size = size;
        Sleeves = sleeves;
        Type = type;
        WashCare = washCare;
        Work = work;
    }

    public String getAvailability() {
        return Availability;
    }

    public String getFabric() {
        return Fabric;
    }

    public String getLength() {
        return Length;
    }

    public String getOccasion() {
        return Occasion;
    }

    public String getSize() {
        return Size;
    }

    public String getSleeves() {
        return Sleeves;
    }

    public String getType() {
        return Type;
    }

    public String getWashCare() {
        return WashCare;
    }

    public String getWork() {
        return Work;
    }
}
