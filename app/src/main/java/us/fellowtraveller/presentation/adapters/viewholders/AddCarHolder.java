package us.fellowtraveller.presentation.adapters.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import us.fellowtraveller.R;

/**
 * Created by arkadii on 3/19/17.
 */
public class AddCarHolder extends RecyclerView.ViewHolder {
    TextView tvHintNoCars;
    Button btnAddCar;

    public AddCarHolder(LayoutInflater layoutInflater, ViewGroup parent) {
        super(layoutInflater.inflate(R.layout.item_add_car, parent, false));
        btnAddCar = (Button) itemView.findViewById(R.id.btn_add_car);
        tvHintNoCars = (TextView) itemView.findViewById(R.id.tv_hint_no_cars);
    }

    public void bind(View.OnClickListener onClickListener, boolean hasCars, boolean canAddCars) {
        tvHintNoCars.setVisibility(canAddCars || hasCars ? View.GONE : View.VISIBLE);
        btnAddCar.setVisibility(canAddCars ? View.VISIBLE : View.GONE);
        btnAddCar.setOnClickListener(onClickListener);
    }

    public void unbind() {
        btnAddCar.setOnClickListener(null);
    }
}
