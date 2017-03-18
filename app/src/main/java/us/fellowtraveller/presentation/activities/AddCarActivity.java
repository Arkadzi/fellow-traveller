package us.fellowtraveller.presentation.activities;

import android.os.Bundle;

import us.fellowtraveller.R;

public class AddCarActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);
    }

    @Override
    protected boolean hasBackButton() {
        return true;
    }
}
