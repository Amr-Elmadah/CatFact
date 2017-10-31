package com.asana.rebel.data;

import android.content.res.TypedArray;

import com.asana.rebel.data.callbacks.LoadCatFactsCallback;
import com.asana.rebel.data.models.Fact;
import com.asana.rebel.network.Callback;
import com.asana.rebel.network.ServiceGenerator;
import com.asana.rebel.network.interfaces.CatFactService;
import com.asana.rebel.utils.ActivityUtils;
import com.asana.rebel.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Amr ElMadah on 10/31/17.
 */

public class CatFactRepository implements CatFactDataSource {

    @Override
    public void loadCatFacts(int length, final LoadCatFactsCallback loadCatFactsCallback) {
        CatFactService catFactService = ServiceGenerator.createService(CatFactService.class);
        Call<JsonElement> call = catFactService.getFacts(length, 1000);
        call.enqueue(new Callback<JsonElement>(loadCatFactsCallback) {

            @Override
            public void onResponseSuccess(Call<JsonElement> call, Response<JsonElement> response) {
                List<Fact> facts = new ArrayList<>();
                if (response.body().getAsJsonObject().has("data")) {
                    JsonArray responseJsonArray = response.body().getAsJsonObject().get("data").getAsJsonArray();
                    facts = new Gson().fromJson(responseJsonArray, new TypeToken<List<Fact>>() {
                    }.getType());
                }
                loadCatFactsCallback.onFactsLoaded(facts);
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                super.onFailure(call, t);
            }
        });
    }

}