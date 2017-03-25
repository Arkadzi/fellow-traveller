package us.fellowtraveller.data.di;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import us.fellowtraveller.data.RepositoryImpl;
import us.fellowtraveller.data.di.scope.UserScope;
import us.fellowtraveller.data.rest.RestApi;
import us.fellowtraveller.data.rest.RetrofitApi;
import us.fellowtraveller.domain.Repository;
import us.fellowtraveller.domain.model.Account;
import us.fellowtraveller.domain.usecase.EditPhotoUseCase;
import us.fellowtraveller.domain.usecase.EditProfileUseCase;
import us.fellowtraveller.domain.usecase.SignInUseCase;
import us.fellowtraveller.domain.usecase.SignUpUseCase;
import us.fellowtraveller.domain.usecase.UserInfoUseCase;
import us.fellowtraveller.presentation.presenter.EditProfilePresenter;
import us.fellowtraveller.presentation.presenter.EditProfilePresenterImpl;
import us.fellowtraveller.presentation.presenter.ProfilePresenter;
import us.fellowtraveller.presentation.presenter.ProfilePresenterImpl;
import us.fellowtraveller.presentation.presenter.SignInPresenter;
import us.fellowtraveller.presentation.presenter.SignInPresenterImpl;
import us.fellowtraveller.presentation.presenter.SignUpPresenter;
import us.fellowtraveller.presentation.presenter.SignUpPresenterImpl;
import us.fellowtraveller.presentation.utils.Messages;

/**
 * Created by arkadii on 3/5/17.
 */

@Module
public class UserModule {
    @Provides
    @UserScope
    public RestApi provideRestApi(Retrofit retrofit, Context context) {
        return new RestApi(retrofit.create(RetrofitApi.class), context);
    }

    @Provides
    @UserScope
    public Repository provideRepository(RestApi restApi, Account account) {
        return new RepositoryImpl(restApi, account);
    }

    @Provides
    @UserScope
    SignInPresenter provideSignInPresenter(Messages messages, SignInUseCase signInUseCase) {
        return new SignInPresenterImpl(messages, signInUseCase);
    }

    @Provides
    @UserScope
    SignUpPresenter provideSignUpPresenter(Messages messages, SignUpUseCase signUpUseCase) {
        return new SignUpPresenterImpl(messages, signUpUseCase);
    }

    @Provides
    @UserScope
    ProfilePresenter provideProfilePresenter(Messages messages, UserInfoUseCase userInfoUseCase) {
        return new ProfilePresenterImpl(messages, userInfoUseCase);
    }

    @Provides
    @UserScope
    EditProfilePresenter provideEditProfilePresenter(Messages messages, EditProfileUseCase editProfileUseCase, EditPhotoUseCase editPhotoUseCase) {
        return new EditProfilePresenterImpl(messages, editProfileUseCase, editPhotoUseCase);
    }
}
