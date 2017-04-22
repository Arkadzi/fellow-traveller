package us.fellowtraveller.presentation.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import us.fellowtraveller.R;
import us.fellowtraveller.domain.model.trip.Route;
import us.fellowtraveller.presentation.adapters.RouteAdapter;
import us.fellowtraveller.presentation.utils.ScreenNavigator;

/**
 * Created by arkadius on 4/22/17.
 */

public class RouteListFragment extends Fragment {

    public static final String ARG_ROUTES = "arg_routes";

    public static RouteListFragment newInstance(List<Route> data) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_ROUTES, new ArrayList<>(data));
        RouteListFragment fragment = new RouteListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        RouteAdapter adapter = new RouteAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        adapter.setRouteClickListener(route -> ScreenNavigator.startRouteScreen(getActivity(), route));
        adapter.setData(((ArrayList<Route>) getArguments().getSerializable(ARG_ROUTES)));

        return view;
    }


}
