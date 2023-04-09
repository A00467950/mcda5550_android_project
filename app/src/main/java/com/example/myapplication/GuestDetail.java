package com.example.myapplication;

public class GuestDetail {
    private String guestName;
    private String gender;

    public GuestDetail(){}

    public GuestDetail(String guestName, String gender){
        this.guestName = guestName;
        this.gender = gender;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getGender() {
        return gender;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
