package us.fellowtraveller.data.di;

import dagger.Subcomponent;
import us.fellowtraveller.data.di.scope.UserScope;
import us.fellowtraveller.presentation.MainActivity;
import us.fellowtraveller.presentation.SignInActivity;

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
}
