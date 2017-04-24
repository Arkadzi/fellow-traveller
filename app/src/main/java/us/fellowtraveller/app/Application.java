package us.fellowtraveller.app;

import android.content.Context;

import us.fellowtraveller.data.di.UserComponent;
import us.fellowtraveller.data.di.UserModule;
import us.fellowtraveller.presentation.utils.ScreenNavigator;

/**
 * Created by arkadii on 3/5/17.
 */

public class Application extends android.app.Application {

    private ApplicationComponent component;
    private UserComponent userComponent;

    public static Application getApp(Context context) {
        return (Application) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        buildAppComponent();
        buildUserComponent();
    }


    public void logout() {
        userComponent.account().logout();
        buildUserComponent();
        ScreenNavigator.startFirstScreen(this);
    }

    private void buildAppComponent() {
        component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    private void buildUserComponent() {
        userComponent = component.plus(new UserModule());
    }

    public ApplicationComponent getAppComponent() {
        return component;
    }

    public UserComponent getUserComponent() {
        return userComponent;
    }

}
