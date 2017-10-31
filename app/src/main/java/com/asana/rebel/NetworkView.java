package com.asana.rebel;

/**
 * Created by Amr ElMadah on 10/31/17.
 */
public interface NetworkView {

    void showNoConnection(Action retryAction);

    void hideNoConnection();

    void showServerError();

    void showTimeOut();
}
