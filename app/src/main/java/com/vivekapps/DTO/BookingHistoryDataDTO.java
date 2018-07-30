package com.vivekapps.DTO;

public class BookingHistoryDataDTO {

    private String User_ID;

    private String location_longitude;

    private String vehicle_type;

    private String bike_detailing;

    private String car_detailing;

    private String Booking_ID;

    private String location_address;

    private String location_latitude;

    private String car_package;

    private String car_type;

    public String getUser_ID ()
    {
        return User_ID;
    }

    public void setUser_ID (String User_ID)
    {
        this.User_ID = User_ID;
    }

    public String getLocation_longitude ()
    {
        return location_longitude;
    }

    public void setLocation_longitude (String location_longitude)
    {
        this.location_longitude = location_longitude;
    }

    public String getVehicle_type ()
    {
        return vehicle_type;
    }

    public void setVehicle_type (String vehicle_type)
    {
        this.vehicle_type = vehicle_type;
    }

    public String getBike_detailing ()
    {
        return bike_detailing;
    }

    public void setBike_detailing (String bike_detailing)
    {
        this.bike_detailing = bike_detailing;
    }

    public String getCar_detailing ()
    {
        return car_detailing;
    }

    public void setCar_detailing (String car_detailing)
    {
        this.car_detailing = car_detailing;
    }

    public String getBooking_ID ()
    {
        return Booking_ID;
    }

    public void setBooking_ID (String Booking_ID)
    {
        this.Booking_ID = Booking_ID;
    }

    public String getLocation_address ()
    {
        return location_address;
    }

    public void setLocation_address (String location_address)
    {
        this.location_address = location_address;
    }

    public String getLocation_latitude ()
    {
        return location_latitude;
    }

    public void setLocation_latitude (String location_latitude)
    {
        this.location_latitude = location_latitude;
    }

    public String getCar_package ()
    {
        return car_package;
    }

    public void setCar_package (String car_package)
    {
        this.car_package = car_package;
    }

    public String getCar_type ()
    {
        return car_type;
    }

    public void setCar_type (String car_type)
    {
        this.car_type = car_type;
    }

    @Override
    public String toString()
    {
        return "BookingHistoryDataDTO [User_ID = "+User_ID+", location_longitude = "+location_longitude+", vehicle_type = "+vehicle_type+", bike_detailing = "+bike_detailing+", car_detailing = "+car_detailing+", Booking_ID = "+Booking_ID+", location_address = "+location_address+", location_latitude = "+location_latitude+", car_package = "+car_package+", car_type = "+car_type+"]";
    }

}
