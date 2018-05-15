package com.vivekapps.utils;

/**
 * Created by narasimma on 28/04/18.
 */

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginServices {

    /**
     * Login user
     * @param name
     * @param password
     */
    //TODO: Implement username in the request, as of now only name is used and Address has to be removed

    @FormUrlEncoded
    @POST("Login")
    Call <Void> userLogin(@Field("request") String request, @Field("name") String name, @Field("password") String password);

    @FormUrlEncoded
    @POST("Login")
    Call <Void> userRegister(@Field("request") String request, @Field("name") String name, @Field("Email") String email,
                @Field("Phone") String phone,@Field("password") String password,@Field("Address") String address);

}
