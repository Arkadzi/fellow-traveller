package us.fellowtraveller.data.di;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import us.fellowtraveller.data.RepositoryImpl;
import us.fellowtraveller.data.di.scope.UserScope;
import us.fellowtraveller.data.rest.RestApi;
import us.fellowtraveller.data.rest.RetrofitApi;
import us.fellowtraveller.domain.Repository;
import us.fellowtraveller.domain.usecase.SignInUseCase;
import us.fellowtraveller.presentation.presenter.SignInPresenter;
import us.fellowtraveller.presentation.presenter.SignInPresenterImpl;
import us.fellowtraveller.presentation.utils.Messages;

/**
 * Created by arkadii on 3/5/17.
 */

@Module
public class UserModule {
    @Provides
    @UserScope
    public RestApi provideRestApi(Retrofit retrofit) {
        return new RestApi(retrofit.create(RetrofitApi.class));
    }

    @Provides
    @UserScope
    public Repository provideRepository(RestApi restApi) {
        return new RepositoryImpl(restApi);
    }

    @Provides
    @UserScope
    SignInPresenter provideSignInPresenter(Messages messages, SignInUseCase signInUseCase) {
        return new SignInPresenterImpl(messages, signInUseCase);
    }
}
