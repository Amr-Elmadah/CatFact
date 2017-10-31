package com.asana.rebel.network;

import com.asana.rebel.catfact.BuildConfig;

/**
 * Created by Amr ElMadah on 10/31/17.
 */

public class NetworkConstants {

    public static final boolean QA = BuildConfig.QA;

    /**
     * The base url of the web service.
     */

    public static final String BASE_URL = "https://catfact.ninja";

    //We use that for QA server for debugging - also we make favor for production and staging (check gradle)
    public static final String BASE_URL_QA = "https://catfact.ninja";

    public static String BASE_URL_API = getBaseUrl() + "/";

    public static String getBaseUrl() {
        if (QA) {
            return BASE_URL_QA;
        } else {
            return BASE_URL;
        }
    }
}
