package us.fellowtraveller.presentation.dialogs;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import us.fellowtraveller.R;
import us.fellowtraveller.app.Application;
import us.fellowtraveller.domain.model.Car;
import us.fellowtraveller.domain.model.trip.Point;
import us.fellowtraveller.domain.model.trip.Route;
import us.fellowtraveller.presentation.adapters.viewholders.CarViewHolder2;
import us.fellowtraveller.presentation.app.view.SimpleItemSelectedListener;
import us.fellowtraveller.presentation.app.view.SimpleTextWatcher;
import us.fellowtraveller.presentation.fragments.RouteMapFragment;
import us.fellowtraveller.presentation.presenter.CreateRoutePresenter;
import us.fellowtraveller.presentation.utils.FieldUtils;
import us.fellowtraveller.presentation.utils.exceptions.BadFieldDataException;

/**
 * Created by arkadius on 4/15/17.
 */

public class CreateRouteDialog extends DialogFragment {

    public static final String TAG = "dialog_create_route";
    @Nullable
    private MapDialogListener mapDialogListener;
    private ArrayList<Point> points;
    @Bind(R.id.et_title)
    EditText etTitle;
    @Bind(R.id.et_seats_count)
    EditText etSeatsCount;
    @Bind(R.id.et_price)
    EditText etPrice;
    @Bind(R.id.car_spinner)
    Spinner spinner;
    @Bind(R.id.progress_bar)
    ProgressBar progressBar;
    @Bind(R.id.content_view)
    View contentView;
    @Bind(R.id.cb_per_km)
    CheckBox cbPerKm;

    @Inject
    CreateRoutePresenter presenter;

    private List<Car> cars;
    private SimpleItemSelectedListener spinnerListener = new SimpleItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Car car = (Car) spinner.getSelectedItem();
            try {
                if (Integer.parseInt(etSeatsCount.getText().toString()) > car.getCapacity()) {
                    etSeatsCount.setText(String.valueOf(car.getCapacity()));
                }
            } catch (RuntimeException e) {
            }
        }
    };
    private SimpleTextWatcher seatsTextChangeListener = new SimpleTextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (count > 0) {
                Car car = (Car) spinner.getSelectedItem();
                try {
                    int seats = Integer.parseInt(etSeatsCount.getText().toString());
                    if (car.getCapacity() < seats) {
                        etSeatsCount.setText(String.valueOf(car.getCapacity()));
                        etSeatsCount.setSelection(etSeatsCount.getText().length());
                    } else if (seats < 1) {
                        etSeatsCount.setText("");
                    }
                } catch (RuntimeException e) {
                    etSeatsCount.setText(String.valueOf(1));
                    etSeatsCount.setSelection(etSeatsCount.getText().length());
                }
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Application.getApp(getActivity()).getUserComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return getActivity().getLayoutInflater().inflate(R.layout.dialog_create_route, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        spinner.setAdapter(new CarArrayAdapter(cars));
        spinner.setOnItemSelectedListener(spinnerListener);
        etSeatsCount.addTextChangedListener(seatsTextChangeListener);
        if (getChildFragmentManager().findFragmentById(R.id.map_container) == null) {
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.map_container, RouteMapFragment.newInstance(points))
                    .commit();
        }
    }

    @OnClick(R.id.btn_ok)
    void onOkClick() {
        try {
            Route route = new Route();
            String title = FieldUtils.getNonEmptyText(etTitle);
            int seats = FieldUtils.getInt(etSeatsCount);
            float price = FieldUtils.getFloat(etPrice);
            route.setTitle(title);
            route.setSeats(seats);
            route.setPoints(points);
            route.setPrice(price, !cbPerKm.isChecked());
            route.setCar(((Car) spinner.getSelectedItem()).getId());
            presenter.onCreateRouteClick(route);
        } catch (BadFieldDataException e) {
        }
    }

    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        contentView.setVisibility(View.GONE);
    }

    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
        contentView.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.btn_cancel)
    void onCancelClick() {
        dismiss();
    }


    @Override
    public void onDestroyView() {
        ButterKnife.unbind(this);
        super.onDestroyView();
        if (mapDialogListener != null) {
            mapDialogListener.onDismiss();
        }
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public void setPoints(List<Point> points) {
        this.points = new ArrayList<>(points);
    }

    public static void show(FragmentManager fragmentManager,
                            MapDialogListener listener,
                            List<Point> points, List<Car> cars) {
        CreateRouteDialog createRouteDialog = new CreateRouteDialog();

        createRouteDialog.setCancelable(false);
        createRouteDialog.setMapDialogListener(listener);
        createRouteDialog.setCars(cars);
        createRouteDialog.setPoints(points);
        createRouteDialog.show(fragmentManager, TAG);
    }

    public void setMapDialogListener(@Nullable MapDialogListener mapDialogListener) {
        this.mapDialogListener = mapDialogListener;
    }

    public interface MapDialogListener {
        void onDismiss();
    }

    private class CarArrayAdapter extends ArrayAdapter<Car> {

        public CarArrayAdapter(List<Car> cars) {
            super(CreateRouteDialog.this.getActivity(), R.layout.item_car, cars);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            CarViewHolder2 carViewHolder = new CarViewHolder2(getActivity().getLayoutInflater(), parent);
            carViewHolder.bind(getItem(position));
            return carViewHolder.itemView;
        }

        @Override
        public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            return getView(position, convertView, parent);
        }
    }
}
