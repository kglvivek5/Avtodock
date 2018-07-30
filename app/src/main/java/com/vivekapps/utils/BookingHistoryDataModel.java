package com.vivekapps.utils;

public class BookingHistoryDataModel {
    public BookingHistoryDataModel(String bookingDate,String bookingModel, String bookingPackage,
                                   String bookingPrice, String bookingVehicleType) {
        super();
        this.bookingDate = bookingDate;
        this.bookingModel = bookingModel;
        this.bookingPackage = bookingPackage;
        this.bookingPrice = bookingPrice;
        this.vehicleType = bookingVehicleType;
    }

    public String bookingModel,bookingDate,bookingPackage,bookingPrice,vehicleType;

    public String getBookingModel() {
        return bookingModel;
    }

    public void setBookingModel(String bookingModel) {
        this.bookingModel = bookingModel;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getBookingPackage() {
        return bookingPackage;
    }

    public void setBookingPackage(String bookingPackage) {
        this.bookingPackage = bookingPackage;
    }

    public String getBookingPrice() {
        return bookingPrice;
    }

    public void setBookingPrice(String bookingPrice) {
        this.bookingPrice = bookingPrice;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }
}
