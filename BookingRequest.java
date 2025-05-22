package com.example.demo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class BookingRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String carPark;
    private String parkingName;
    private String parkingNumber;

    // Constructors
    public BookingRequest() {}

    public BookingRequest(String carPark, String parkingName, String parkingNumber) {
        this.carPark = carPark;
        this.parkingName = parkingName;
        this.parkingNumber = parkingNumber;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCarPark() {
        return carPark;
    }

    public void setCarPark(String carPark) {
        this.carPark = carPark;
    }

    public String getParkingName() {
        return parkingName;
    }

    public void setParkingName(String parkingName) {
        this.parkingName = parkingName;
    }

    public String getParkingNumber() {
        return parkingNumber;
    }

    public void setParkingNumber(String parkingNumber) {
        this.parkingNumber = parkingNumber;
    }
}