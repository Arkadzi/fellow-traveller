package us.fellowtraveller.data.di;

import dagger.Subcomponent;
import us.fellowtraveller.data.di.scope.UserScope;
import us.fellowtraveller.domain.model.Account;
import us.fellowtraveller.presentation.activities.AddCarActivity;
import us.fellowtraveller.presentation.activities.CreateRouteActivity;
import us.fellowtraveller.presentation.activities.EditProfileActivity;
import us.fellowtraveller.presentation.activities.FindRouteActivity;
import us.fellowtraveller.presentation.activities.MainActivity;
import us.fellowtraveller.presentation.activities.ProfileActivity;
import us.fellowtraveller.presentation.activities.RouteActivity;
import us.fellowtraveller.presentation.activities.SignInActivity;
import us.fellowtraveller.presentation.activities.SignUpActivity;
import us.fellowtraveller.presentation.activities.SplashActivity;
import us.fellowtraveller.presentation.dialogs.CreateRouteDialog;
import us.fellowtraveller.presentation.fragments.DriverFragment;
import us.fellowtraveller.presentation.fragments.FindRouteFragment;
import us.fellowtraveller.presentation.fragments.TravellerFragment;

/**
 * Created by arkadii on 3/5/17.
 */
@UserScope
@Subcomponent(
        modules = {
                UserModule.class
        }
)
public interface UserComponent {
    void inject(MainActivity mainActivity);

    void inject(SignInActivity signInActivity);

    void inject(SignUpActivity signUpActivity);

    void inject(SplashActivity splashActivity);

    void inject(ProfileActivity profileActivity);

    void inject(EditProfileActivity editProfileActivity);

    void inject(AddCarActivity addCarActivity);

    void inject(CreateRouteActivity createRouteActivity);

    void inject(CreateRouteDialog createRouteDialog);

    void inject(DriverFragment driverFragment);

    void inject(TravellerFragment travellerFragment);

    void inject(FindRouteActivity findRouteActivity);

    void inject(FindRouteFragment findRouteFragment);

    void inject(RouteActivity routeActivity);

    Account account();
}
