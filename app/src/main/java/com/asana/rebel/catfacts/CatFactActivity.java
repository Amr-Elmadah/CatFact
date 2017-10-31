package com.asana.rebel.catfacts;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.asana.rebel.catfact.R;
import com.asana.rebel.utils.ActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CatFactActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private CatFactsFragment notificationsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_facts);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        notificationsFragment = (CatFactsFragment) getSupportFragmentManager().findFragmentById(R.id.container);
        if (notificationsFragment == null) {
            notificationsFragment = new CatFactsFragment();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), notificationsFragment, R.id.container);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
