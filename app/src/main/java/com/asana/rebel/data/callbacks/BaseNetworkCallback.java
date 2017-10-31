package com.asana.rebel.data.callbacks;

/**
 * Created by Amr ElMadah on 10/31/17.
 */

public interface BaseNetworkCallback {

    void onResponseError(int responseCode);

    void onNoConnection();

    void onTimeOut();
}
