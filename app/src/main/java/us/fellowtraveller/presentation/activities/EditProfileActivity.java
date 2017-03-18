package us.fellowtraveller.presentation.activities;

import android.os.Bundle;

import us.fellowtraveller.R;

public class EditProfileActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
    }

    @Override
    protected boolean hasBackButton() {
        return true;
    }
}
