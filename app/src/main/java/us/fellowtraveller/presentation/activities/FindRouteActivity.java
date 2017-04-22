package us.fellowtraveller.presentation.activities;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import us.fellowtraveller.R;
import us.fellowtraveller.presentation.fragments.FindRouteFragment;

public class FindRouteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_route);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new FindRouteFragment())
                    .commit();
        }
    }


}
