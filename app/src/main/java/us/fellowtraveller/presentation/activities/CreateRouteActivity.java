package us.fellowtraveller.presentation.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import us.fellowtraveller.R;
import us.fellowtraveller.presentation.adapters.TripPointAdapter;
import us.fellowtraveller.presentation.adapters.view_handlers.SimpleItemTouchHelperCallback;
import us.fellowtraveller.presentation.utils.LocationUtils;

public class CreateRouteActivity extends AppCompatActivity {
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.item_from)
    TextView itemFrom;
    @Bind(R.id.item_to)
    TextView itemTo;
    private TripPointAdapter adapter;
    private int pressedPointViewId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_route);
        initViews();
    }

    private void initViews() {
        ButterKnife.bind(this);
        adapter = new TripPointAdapter(getLayoutInflater());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (LocationUtils.onActivityResult(requestCode)) {
            Place place = LocationUtils.fetchPlace(this, resultCode, data);
            if (place != null) {
                updatePlace(place);
            } else {
                Toast.makeText(this, R.string.error_unknown, Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        LocationUtils.onPermissionRequested(this, requestCode);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void updatePlace(Place place) {
        switch (pressedPointViewId) {
            case R.id.item_from:
                itemFrom.setText(place.getAddress());
                break;
            case R.id.item_to:
                itemTo.setText(place.getAddress());
                break;
            case R.id.btn_add_point:
                break;
        }
    }

    @OnClick({R.id.item_from, R.id.item_to, R.id.btn_add_point})
    void onClick(View view) {
        pressedPointViewId = view.getId();
        LocationUtils.requestLocation(this);
    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }
}
