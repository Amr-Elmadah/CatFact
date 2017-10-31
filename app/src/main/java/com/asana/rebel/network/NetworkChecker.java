package com.asana.rebel.network;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by Amr ElMadah on 10/31/17.
 */
public class NetworkChecker {

    public static boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

}
