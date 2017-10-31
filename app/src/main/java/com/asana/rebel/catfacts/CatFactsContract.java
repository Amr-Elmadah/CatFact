package com.asana.rebel.catfacts;

import com.asana.rebel.BasePresenter;
import com.asana.rebel.BaseView;
import com.asana.rebel.NetworkView;
import com.asana.rebel.data.models.Fact;

import java.util.List;

/**
 * Created by Amr ElMadah on 10/31/17.
 */
public interface CatFactsContract {

    interface View extends BaseView<Presenter>, NetworkView {

        void setLoadingIndicator(boolean active);

        boolean isActive();

        void showCatFacts(List<Fact> catFacts);

        void showClickedCatFact(Fact fact);
    }

    interface Presenter extends BasePresenter {

        void loadCatFacts(int length);

        void onCatFactClicked(Fact fact);
    }
}
