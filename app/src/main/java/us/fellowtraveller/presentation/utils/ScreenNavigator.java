package us.fellowtraveller.presentation.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import us.fellowtraveller.domain.model.User;
import us.fellowtraveller.presentation.activities.AddCarActivity;
import us.fellowtraveller.presentation.activities.CreateRouteActivity;
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
//        user = new User();
//        user.setLastName("Умник");
//        user.setEmail("clever.boy@ukr.net");
//        user.setFirstName("Малолетний");
//        user.setSsoId("228");
//        user.setId("asdasd");
//        user.setImageUrl("https://s-media-cache-ak0.pinimg.com/736x/07/fa/03/07fa03be6676ca10e088449075d460f6.jpg");
//        ArrayList<Car> cars = new ArrayList<>();
//        cars.add(new Car("1", "Nissan Skyline", 3, 2000, 4, "https://s-media-cache-ak0.pinimg.com/originals/a5/40/c3/a540c37dab57c8a77b2caaf323493684.jpg"));
//        cars.add(new Car("2", "Четкая жига", 3, 1976, 2, "http://censoru.net/uploads/posts/2015-03/1426786529_000_14.jpg"));
//        user.setCars(cars);
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
}
