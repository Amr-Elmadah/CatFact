package com.asana.rebel.data;

import com.asana.rebel.data.callbacks.LoadCatFactsCallback;

/**
 * Created by Amr ElMadah on 10/31/17.
 */

public interface CatFactDataSource {
    void loadCatFacts(int length, LoadCatFactsCallback loadCatFactsCallback);
}
