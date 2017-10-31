package com.asana.rebel.data.callbacks;

import com.asana.rebel.data.models.Fact;

import java.util.List;

public interface LoadCatFactsCallback extends BaseNetworkCallback {
    void onFactsLoaded(List<Fact> facts);
}
