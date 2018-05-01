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
    @FormUrlEncoded
    @POST("Login")
    Call <Void> userLogin(@Field("request") String request, @Field("name") String name, @Field("password") String password);

    /**
     * Logout
     */
    @POST("logout")
    Call<Void> logout();

}
