package com.asana.rebel.catfacts;

import com.asana.rebel.Action;
import com.asana.rebel.data.CatFactRepository;
import com.asana.rebel.data.callbacks.LoadCatFactsCallback;
import com.asana.rebel.data.models.Fact;

import java.util.List;

import static com.asana.rebel.utils.Utils.checkNotNull;

/**
 * Created by Amr ElMadah on 10/31/17.
 */
public class CatFactsPresenter implements CatFactsContract.Presenter {


    private CatFactRepository mFactsRepository;
    private CatFactsContract.View mView;
    private int mLength = 0;

    public CatFactsPresenter(int length, CatFactRepository catFactsRepository, CatFactsContract.View view) {
        mFactsRepository = checkNotNull(catFactsRepository);
        mView = checkNotNull(view);
        mLength = checkNotNull(length);
        mView.setPresenter(this);
    }

    @Override
    public void loadCatFacts(final int length) {
        mView.setLoadingIndicator(true);
        mFactsRepository.loadCatFacts(length
                , new LoadCatFactsCallback() {
                    @Override
                    public void onFactsLoaded(List<Fact> facts) {
                        if (!mView.isActive()) {
                            return;
                        }
                        mView.setLoadingIndicator(false);
                        mView.showCatFacts(facts);
                    }

                    @Override
                    public void onResponseError(int responseCode) {
                        if (!mView.isActive()) {
                            return;
                        }
                        mView.setLoadingIndicator(false);
                        mView.showServerError();
                    }

                    @Override
                    public void onNoConnection() {
                        if (!mView.isActive()) {
                            return;
                        }

                        mView.setLoadingIndicator(false);
                        mView.showNoConnection(new Action() {
                            @Override
                            public void perform() {
                                loadCatFacts(length);
                            }
                        });
                    }

                    @Override
                    public void onTimeOut() {
                        if (!mView.isActive()) {
                            return;
                        }

                        mView.setLoadingIndicator(false);
                        mView.showTimeOut();
                    }
                });
    }

    @Override
    public void onCatFactClicked(Fact fact) {
        mView.showClickedCatFact(fact);
    }

    @Override
    public void start() {
        loadCatFacts(mLength);
    }
}
