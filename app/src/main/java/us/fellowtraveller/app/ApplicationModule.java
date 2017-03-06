package us.fellowtraveller.app;

import android.content.Context;
import android.util.Log;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import us.fellowtraveller.data.di.DataModule;
import us.fellowtraveller.domain.schedulers.ObserveOn;
import us.fellowtraveller.domain.schedulers.SubscribeOn;

@Module(includes = DataModule.class)
public class ApplicationModule {
    private final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return application;
    }

    @Provides
    @Singleton
    SubscribeOn provideSubscribeOn() {
        return Schedulers::newThread;
    }

    @Singleton
    @Provides
    ObserveOn provideObserveOn() {
        return AndroidSchedulers::mainThread;
    }
}
