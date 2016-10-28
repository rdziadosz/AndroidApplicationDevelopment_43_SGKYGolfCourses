package eu.dziadosz.sgkygolfcourses;

import java.io.Serializable;

/**
 * Created by Radosław on 27.10.2016.
 */

public class Course implements Serializable {

    String name;
    String position;
    String email;
    String phone;
    String photo;
    Double lat;
    Double lng;
    String url;
    String description;

    public Course(String name, String position, String email, String phone, String photo, Double lat, Double lng, String url, String description) {
        this.name = name;
        this.position = position;
        this.email = email;
        this.phone = phone;
        this.photo = photo;
        this.lat = lat;
        this.lng = lng;
        this.url = url;
        this.description = description;
    }
}