package com.vivekapps.utils;

import com.vivekapps.DTO.BookingDTO;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface BookingServices {


    @FormUrlEncoded
    @POST("Bookings")
    Call<BookingDTO> insertBooking(@Field("request") String request, @Field("User_ID") String userid,
                                   @Field("vehicle_type") String vehicle_type, @Field("car_type") String car_type,
                                   @Field("car_package") String car_package, @Field("car_detailing") String car_detailing,
                                   @Field("location_address") String location_address, @Field("location_latitude") String location_latitude,
                                   @Field("location_longitude") String location_longitude, @Field("bike_detailing") String bike_detailing);

}
