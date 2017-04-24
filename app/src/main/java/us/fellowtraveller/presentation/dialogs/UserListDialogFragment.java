package us.fellowtraveller.presentation.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import us.fellowtraveller.R;
import us.fellowtraveller.domain.model.User;
import us.fellowtraveller.presentation.adapters.UserAdapter;
import us.fellowtraveller.presentation.utils.ScreenNavigator;

/**
 * Created by arkadius on 4/24/17.
 */

public class UserListDialogFragment extends DialogFragment implements UserAdapter.OnClickListener {

    public static final String ARG_USERS = "arg_users";
    public static final String TAG = "user_list_dialog";
    private RecyclerView recyclerView;

    public static UserListDialogFragment newInstance(List<User> users) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_USERS, new ArrayList<>(users));
        UserListDialogFragment fragment = new UserListDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_list, null);
        ArrayList<User> users = (ArrayList<User>) getArguments().getSerializable(ARG_USERS);
        recyclerView = ((RecyclerView) view.findViewById(R.id.recycler_view));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        UserAdapter adapter = new UserAdapter(getActivity());
        adapter.setListener(this);
        adapter.setUsers(users);
        recyclerView.setAdapter(adapter);
        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle(R.string.title_subscribers)
                .setPositiveButton(R.string.action_close, null)
                .create();
    }

    @Override
    public void onClick(User user) {
        ScreenNavigator.startProfileScreen(getActivity(), user);
    }
}
