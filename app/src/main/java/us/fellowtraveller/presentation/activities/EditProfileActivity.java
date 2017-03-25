package us.fellowtraveller.presentation.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import us.fellowtraveller.R;
import us.fellowtraveller.app.Application;
import us.fellowtraveller.data.di.UserComponent;
import us.fellowtraveller.domain.model.Account;
import us.fellowtraveller.domain.model.AccountUser;
import us.fellowtraveller.domain.model.User;
import us.fellowtraveller.presentation.app.view.DateTextView;
import us.fellowtraveller.presentation.dialogs.DatePickDialogFragment;
import us.fellowtraveller.presentation.presenter.EditProfilePresenter;
import us.fellowtraveller.presentation.utils.ActivityUtils;
import us.fellowtraveller.presentation.utils.CircleTransform;
import us.fellowtraveller.presentation.utils.FieldUtils;
import us.fellowtraveller.presentation.utils.ImageUtils;
import us.fellowtraveller.presentation.utils.exceptions.BadFieldDataException;
import us.fellowtraveller.presentation.view.EditProfileView;

public class EditProfileActivity extends ProgressActivity implements EditProfileView, DatePickDialogFragment.DatePickerListener {
    public static final String ARG_PHOTO = "photo";
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
    @Bind(R.id.tv_birthday)
    DateTextView tvBirthday;
    @Inject
    Account user;
    @Inject
    EditProfilePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        getSupportActionBar().setTitle(R.string.hint_edit_profile);
        UserComponent userComponent = Application.getApp(this).getUserComponent();
        user = userComponent.account();
        userComponent.inject(this);
        initViews(savedInstanceState);
        presenter.onCreate(this);
    }

    @Override
    protected void onDestroy() {
        presenter.onRelease();
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
        AccountUser user = this.user.user();
        ivProfilePhoto.setOnClickListener(view -> ImageUtils.requestImage(this));
        tvBirthday.setOnClickListener(view -> DatePickDialogFragment.newInstance(tvBirthday.getDate())
                .show(getSupportFragmentManager(), DatePickDialogFragment.TAG));
        displayPicture();

        if (savedInstanceState == null) {
            etAbout.setText(user.getAbout());
            etEmail.setText(user.getEmail());
            etFirstName.setText(user.getFirstName());
            etLastName.setText(user.getLastName());
            rgGender.check(User.GENDER_FEMALE.equals(user.getGender()) ? R.id.rb_female : R.id.rb_male);
            Long birthday = user.getBirthday();
            tvBirthday.setDate(birthday != null ? birthday : DateTextView.DATE_UNSPECIFIED);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        ImageUtils.onPermissionRequested(this, requestCode);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (ImageUtils.onActivityResult(requestCode)) {
            String pictureUrl = ImageUtils.fetchImagePath(this, data, resultCode);
            if (pictureUrl != null) {
                presenter.onImageEdited(pictureUrl);
            } else if (resultCode == RESULT_OK) {
                showMessage(getString(R.string.error_unknown));
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDatePicked(long date) {
        tvBirthday.setDate(date);
    }

    @Override
    public void onProfileEdited() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onPhotoEdited() {
        setResult(RESULT_OK);
        showMessage(getString(R.string.hint_photo_edited));
        displayPicture();
    }

    @OnClick(R.id.fab_action_save)
    void onSaveButtonClick() {
        try {
            User user = new User();
            AccountUser accountUser = this.user.user();

            String lastName = FieldUtils.getNonEmptyText(etLastName);
            String firstName = FieldUtils.getNonEmptyText(etFirstName);
            String email = FieldUtils.getNonEmptyText(etEmail);
            String gender = rgGender.getCheckedRadioButtonId() == R.id.rb_male ? User.GENDER_MALE : User.GENDER_FEMALE;

            user.setSsoId(accountUser.getSsoId());

            if (!firstName.equals(accountUser.getFirstName())) {
                user.setFirstName(firstName);
            }
            if (!lastName.equals(accountUser.getLastName())) {
                user.setLastName(lastName);
            }
            if (!email.equals(accountUser.getEmail())) {
                user.setEmail(email);
            }
            if (!gender.equals(accountUser.getGender())){
                user.setGender(gender);
            }
            presenter.onProfileEdited(user);
        } catch (BadFieldDataException e) {}

//        if (birthday != DateTextView.DATE_UNSPECIFIED && (accountUser.)) {
//            user.setBirthday(birthday);
//        }
//        if (user.getBirthday())
    }

    private void displayPicture() {
        String imageUrl = user.user().getImageUrl();
        Picasso.with(this)
                .load(imageUrl)
                .transform(new CircleTransform())
                .into(ivProfilePhoto);
    }
}
