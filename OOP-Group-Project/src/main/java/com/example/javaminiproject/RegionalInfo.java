package com.example.javaminiproject;

public class RegionalInfo {
    private int id;
    private String name;
    private String small_description;
    private String description;
    private String image_url;
    private String district;
    private String location;
    private String type;
    private float rating;


    public RegionalInfo(int id, String name, String small_description, String description, String image_url, String district, String location, String type, float rating) {
        this.id = id;
        this.name = name;
        this.small_description = small_description;
        this.description = description;
        this.image_url = image_url;
        this.district = district;
        this.location = location;
        this.type = type;
        this.rating = rating;
    }

    public int getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public  String getSmall_description() {
        return small_description;
    }

    public String getDescription() {
        return description;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getDistrict() {
        return district;
    }

    public String getLocation() {
        return location;
    }

    public String getType() {
        return type;
    }

    public float getRating() {
        return rating;
    }

    @Override
    public String toString() {
        return  "id=" + id +
                "\nname='" + name + '\'' +
                "\nsmall_description='" + small_description + '\'' +
                "\ndescription='" + description + '\'' +
                "\nimage_url='" + image_url + '\'' +
                "\ndistrict='" + district + '\'' +
                "\nlocation='" + location + '\'' +
                "\ntype='" + type + '\'' +
                "\nrating=" + rating;
    }
}