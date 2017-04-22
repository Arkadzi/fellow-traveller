package us.fellowtraveller.presentation.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import us.fellowtraveller.data.Constants;
import us.fellowtraveller.domain.model.User;
import us.fellowtraveller.domain.model.trip.Route;
import us.fellowtraveller.presentation.activities.AddCarActivity;
import us.fellowtraveller.presentation.activities.CreateRouteActivity;
import us.fellowtraveller.presentation.activities.EditProfileActivity;
import us.fellowtraveller.presentation.activities.FindRouteActivity;
import us.fellowtraveller.presentation.activities.MainActivity;
import us.fellowtraveller.presentation.activities.ProfileActivity;
import us.fellowtraveller.presentation.activities.RouteActivity;
import us.fellowtraveller.presentation.activities.SignInActivity;
import us.fellowtraveller.presentation.activities.SignUpActivity;

/**
 * Created by arkadii on 3/18/17.
 */

public class ScreenNavigator {
    public static void startSignUpScreen(Context context) {
        context.startActivity(new Intent(context, SignUpActivity.class));
    }

    public static void startSignInScreen(Context context) {
        context.startActivity(new Intent(context, SignInActivity.class));
    }

    public static void startMainScreen(Context context, boolean newTask) {
        Intent intent = new Intent(context, MainActivity.class);
        if (newTask) {
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }


    public static void startProfileScreen(Context context, User user) {
        Intent intent = new Intent(context, ProfileActivity.class);
        intent.putExtra(ProfileActivity.ARG_USER, user);
        context.startActivity(intent);
    }

    public static void startAddCarScreen(Activity activity, int requestCode) {
        activity.startActivityForResult(new Intent(activity, AddCarActivity.class), requestCode);
    }

    public static void startEditProfileScreen(Activity activity, int requestCode) {
        activity.startActivityForResult(new Intent(activity, EditProfileActivity.class), requestCode);
    }

    public static void startCreateRouteScreen(Fragment fragment, Activity activity, int requestCode) {
        fragment.startActivityForResult(new Intent(activity, CreateRouteActivity.class), requestCode);
    }

    public static void startSearchRouteScreen(Fragment fragment, Activity activity, int requestCode) {
        fragment.startActivityForResult(new Intent(activity, FindRouteActivity.class), requestCode);
    }

    public static void startRouteScreen(Activity activity, Route route) {
        Intent intent = new Intent(activity, RouteActivity.class);
        intent.putExtra(Constants.Intents.EXTRA_ROUTE, route);
        activity.startActivity(intent);
    }
}
