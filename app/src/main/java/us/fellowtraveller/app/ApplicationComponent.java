package us.fellowtraveller.app;

import javax.inject.Singleton;

import dagger.Component;
import us.fellowtraveller.data.di.UserComponent;
import us.fellowtraveller.data.di.UserModule;

/**
 * Created by arkadii on 3/5/17.
 */

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    UserComponent plus(UserModule userModule);

}
