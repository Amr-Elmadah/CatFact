package com.asana.rebel.network.interfaces;

import com.asana.rebel.data.models.Fact;
import com.google.gson.JsonElement;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Amr ElMadah on 10/31/17.
 */
public interface CatFactService {

    @GET("facts")
    Call<JsonElement> getFacts(@Query("max_length") int length, @Query("limit") int limit);
}
