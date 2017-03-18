package us.fellowtraveller.presentation.utils;

import android.content.Context;
import android.content.Intent;

import us.fellowtraveller.domain.model.User;
import us.fellowtraveller.presentation.activities.AddCarActivity;
import us.fellowtraveller.presentation.activities.EditProfileActivity;
import us.fellowtraveller.presentation.activities.MainActivity;
import us.fellowtraveller.presentation.activities.ProfileActivity;
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

    public static void startAddCarScreen(Context context) {
        context.startActivity(new Intent(context, AddCarActivity.class));
    }

    public static void startEditProfileScreen(Context context) {
        context.startActivity(new Intent(context, EditProfileActivity.class));
    }
}
