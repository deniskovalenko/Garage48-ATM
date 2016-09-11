package com.atm.atm;

import com.atm.atm.model.EventFromBackend;
import com.atm.atm.model.EventWithImageUrl;
import com.atm.atm.model.JoinEventBody;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by denis on 11.09.16.
 */
public interface RestService {
    @GET("feed")
    Call<ResponseBody> queryEvents(@Query("lat") double lat, @Query("lon") double lon);

    @GET("feed")
    Call<ResponseBody> queryEvents();

    @POST("feed/{id}/participants")
    Call<JoinEventBody> joinEvent(@Path("id") String event_id, @Body JoinEventBody body);

    @POST("feed")
    Call<EventWithImageUrl> downloadFile(@Body EventWithImageUrl event);
}