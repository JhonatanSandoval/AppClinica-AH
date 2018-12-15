package net.touchsf.appclinica.model;

import java.io.Serializable;

public class DetailDoctor implements Serializable {

    private String doctor;
    private String speciality;
    private String image;

    public DetailDoctor() {
    }

    public DetailDoctor(String doctor, String speciality, String image) {
        this.doctor = doctor;
        this.speciality = speciality;
        this.image = image;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
