package us.fellowtraveller.presentation.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.Calendar;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import us.fellowtraveller.R;
import us.fellowtraveller.app.Application;
import us.fellowtraveller.domain.model.Car;
import us.fellowtraveller.presentation.app.view.NumberTextView;
import us.fellowtraveller.presentation.dialogs.ChooseImageDialogFragment;
import us.fellowtraveller.presentation.dialogs.NumberPickerDialogFragment;
import us.fellowtraveller.presentation.presenter.AddCarPresenter;
import us.fellowtraveller.presentation.utils.ActivityUtils;
import us.fellowtraveller.presentation.utils.CircleTransform;
import us.fellowtraveller.presentation.utils.FieldUtils;
import us.fellowtraveller.presentation.utils.ImageUtils;
import us.fellowtraveller.presentation.utils.exceptions.BadFieldDataException;
import us.fellowtraveller.presentation.view.AddCarView;

public class AddCarActivity extends ProgressActivity implements NumberPickerDialogFragment.NumberPickerListener, AddCarView {
    public static final String ARG_IMAGE_URL = "image_url";
    public static final String EXTRA_CAR = "extra_car";
    @Bind(R.id.iv_car_photo)
    ImageView ivCarPhoto;
    @Bind(R.id.et_title)
    EditText etTitle;
    @Bind(R.id.et_capacity)
    EditText etCapacity;
    @Bind(R.id.tv_year_manufacture)
    NumberTextView tvYearManufacture;
    @Bind(R.id.rb_condition)
    RatingBar rbCondition;
    @Inject
    AddCarPresenter presenter;
    private String pictureFilePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);
        getSupportActionBar().setTitle(R.string.hint_new_car);
        Application.getApp(this).getUserComponent().inject(this);
        pictureFilePath = ActivityUtils.restore(savedInstanceState, ARG_IMAGE_URL);
        initViews(savedInstanceState);
        presenter.onCreate(this);
    }

    private void initViews(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        rbCondition.setMax(Car.MAX_RATING);
        rbCondition.setNumStars(Car.MAX_RATING);
        ivCarPhoto.setOnClickListener(view -> {
            ChooseImageDialogFragment.newInstance().show(getSupportFragmentManager(), ChooseImageDialogFragment.TAG);
        });
        tvYearManufacture.setOnClickListener(view -> NumberPickerDialogFragment.newInstance(1960, Calendar.getInstance().get(Calendar.YEAR))
                .show(getSupportFragmentManager(), NumberPickerDialogFragment.TAG));
        if (savedInstanceState == null) {
            rbCondition.setRating(3);
        }
        updatePicture();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        ImageUtils.onPermissionRequested(this, requestCode);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (ImageUtils.onActivityResult(requestCode)) {
            String pictureUrl = ImageUtils.fetchImagePath(this, data, resultCode, requestCode);
            if (pictureUrl != null) {
                this.pictureFilePath = pictureUrl;
            } else if (resultCode == RESULT_OK) {
                showMessage(getString(R.string.error_unknown));
                this.pictureFilePath = null;
            }
            updatePicture();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void updatePicture() {
        Uri uri = null;
        if (pictureFilePath != null) {
            uri = Uri.fromFile(new File(pictureFilePath));
        }
        if (uri != null) {
            Picasso.with(this).load(uri).transform(new CircleTransform()).into(ivCarPhoto);
        } else {
            ivCarPhoto.setImageBitmap(null);
        }
    }

    @OnClick(R.id.fab_action_save)
    void onSaveButtonClick() {
        try {

            String title = FieldUtils.getNonEmptyText(etTitle);
            int capacity = FieldUtils.getInt(etCapacity);
            int year = tvYearManufacture.getNumber();
            int condition = (int) rbCondition.getRating();
            if (year == NumberTextView.NUMBER_UNSPECIFIED) {
                tvYearManufacture.setError(getString(R.string.error_empty_field));
            } else {
                Car car = new Car(title, capacity, year, condition);
                presenter.onCarCreated(car, pictureFilePath);
            }
        } catch (BadFieldDataException e) {}
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(ARG_IMAGE_URL, pictureFilePath);
        super.onSaveInstanceState(outState);
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

    @Override
    public void onNumberPicked(int number) {
        tvYearManufacture.setNumber(number);
        tvYearManufacture.setError(null);
    }

    @Override
    public void onCarAdded(Car car) {
        Toast.makeText(this, R.string.toast_car_added, Toast.LENGTH_SHORT).show();
        Intent data = new Intent();
        data.putExtra(EXTRA_CAR, car);
        setResult(RESULT_OK, data);
        finish();
    }
}
