package com.barebone.app.network;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface NetworkCallInterface {


   //fake api call
    @GET("/forecast/{api_key}/{lat_long}")
    Observable<Object> getWeatherBase(@Path("api_key")String apikey, @Path("lat_long")String latLong);

}
