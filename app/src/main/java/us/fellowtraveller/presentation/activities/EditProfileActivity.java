package us.fellowtraveller.presentation.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import us.fellowtraveller.R;
import us.fellowtraveller.app.Application;
import us.fellowtraveller.domain.model.AccountUser;
import us.fellowtraveller.domain.model.User;
import us.fellowtraveller.presentation.app.view.DateTextView;
import us.fellowtraveller.presentation.dialogs.DatePickDialogFragment;
import us.fellowtraveller.presentation.utils.ActivityUtils;
import us.fellowtraveller.presentation.utils.CircleTransform;

public class EditProfileActivity extends BaseActivity implements DatePickDialogFragment.DatePickerListener {
    public static final String ARG_USER = "user";
    public static final String ARG_PICTURE_URL = "picture_url";
    @Bind(R.id.iv_profile_photo)
    ImageView ivProfilePhoto;
    @Bind(R.id.et_first_name)
    EditText etFirstName;
    @Bind(R.id.et_last_name)
    EditText etLastName;
    @Bind(R.id.et_email)
    EditText etEmail;
    @Bind(R.id.et_about)
    EditText etAbout;
    @Bind(R.id.rg_gender)
    RadioGroup rgGender;
    @Bind(R.id.fab_action_save)
    FloatingActionButton fabSave;
    @Bind(R.id.tv_birthday)
    DateTextView tvBirthday;
    private AccountUser user;
    private String pictureUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        user = Application.getApp(this).getUserComponent().account().user();
        initViews(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    @Override
    protected boolean hasBackButton() {
        return true;
    }

    private void initViews(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        etAbout.setMinLines(3);
        tvBirthday.setOnClickListener(view -> DatePickDialogFragment.newInstance(tvBirthday.getDate())
                .show(getSupportFragmentManager(), DatePickDialogFragment.TAG));
        pictureUrl = ActivityUtils.restore(savedInstanceState, ARG_PICTURE_URL, user.getImageUrl());
        Picasso.with(this)
                .load(pictureUrl)
                .transform(new CircleTransform())
                .into(ivProfilePhoto);

        if (savedInstanceState == null) {
            etAbout.setText(user.getAbout());
            etEmail.setText(user.getEmail());
            etFirstName.setText(user.getFirstName());
            etLastName.setText(user.getLastName());
            rgGender.check(User.GENDER_FEMALE.equals(user.getGender()) ? R.id.rb_female : R.id.rb_male);
            if (user.getBirthday() != 0) {
                tvBirthday.setDate(user.getBirthday());
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(ARG_PICTURE_URL, pictureUrl);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDatePicked(long date) {
        tvBirthday.setDate(date);
    }
}
