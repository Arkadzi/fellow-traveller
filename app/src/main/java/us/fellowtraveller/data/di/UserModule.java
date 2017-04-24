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
import us.fellowtraveller.domain.usecase.AddCarUseCase;
import us.fellowtraveller.domain.usecase.AddRouteUseCase;
import us.fellowtraveller.domain.usecase.DeleteCarUseCase;
import us.fellowtraveller.domain.usecase.DeleteRouteUseCase;
import us.fellowtraveller.domain.usecase.EditPhotoUseCase;
import us.fellowtraveller.domain.usecase.EditProfileUseCase;
import us.fellowtraveller.domain.usecase.BuildRouteUseCase;
import us.fellowtraveller.domain.usecase.FindRoutesUseCase;
import us.fellowtraveller.domain.usecase.GetAllOwnerRoutesUseCase;
import us.fellowtraveller.domain.usecase.GetAllSubscriberRoutesUseCase;
import us.fellowtraveller.domain.usecase.GetPointSubscribersUseCase;
import us.fellowtraveller.domain.usecase.SignInUseCase;
import us.fellowtraveller.domain.usecase.SignUpUseCase;
import us.fellowtraveller.domain.usecase.SubscribeUseCase;
import us.fellowtraveller.domain.usecase.UnsubscribeUseCase;
import us.fellowtraveller.domain.usecase.UserInfoUseCase;
import us.fellowtraveller.presentation.presenter.AddCarPresenter;
import us.fellowtraveller.presentation.presenter.AddCarPresenterImpl;
import us.fellowtraveller.presentation.presenter.CreateRoutePresenter;
import us.fellowtraveller.presentation.presenter.CreateRoutePresenterImpl;
import us.fellowtraveller.presentation.presenter.DriverPresenter;
import us.fellowtraveller.presentation.presenter.DriverPresenterImpl;
import us.fellowtraveller.presentation.presenter.EditProfilePresenter;
import us.fellowtraveller.presentation.presenter.EditProfilePresenterImpl;
import us.fellowtraveller.presentation.presenter.FindRoutePresenter;
import us.fellowtraveller.presentation.presenter.FindRoutePresenterImpl;
import us.fellowtraveller.presentation.presenter.ProfilePresenter;
import us.fellowtraveller.presentation.presenter.ProfilePresenterImpl;
import us.fellowtraveller.presentation.presenter.SignInPresenter;
import us.fellowtraveller.presentation.presenter.SignInPresenterImpl;
import us.fellowtraveller.presentation.presenter.SignUpPresenter;
import us.fellowtraveller.presentation.presenter.SignUpPresenterImpl;
import us.fellowtraveller.presentation.presenter.TravellerPresenter;
import us.fellowtraveller.presentation.presenter.TravellerPresenterImpl;
import us.fellowtraveller.presentation.presenter.ViewRoutePresenter;
import us.fellowtraveller.presentation.presenter.ViewRoutePresenterImpl;
import us.fellowtraveller.presentation.utils.Messages;

/**
 * Created by arkadii on 3/5/17.
 */

@Module
public class UserModule {
    @Provides
    @UserScope
    public RestApi provideRestApi(Retrofit retrofit, Context context, Account account) {
        return new RestApi(retrofit.create(RetrofitApi.class), context, account);
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
    ProfilePresenter provideProfilePresenter(Messages messages, UserInfoUseCase userInfoUseCase, DeleteCarUseCase deleteCarUseCase) {
        return new ProfilePresenterImpl(messages, userInfoUseCase, deleteCarUseCase);
    }

    @Provides
    @UserScope
    EditProfilePresenter provideEditProfilePresenter(Messages messages, EditProfileUseCase editProfileUseCase, EditPhotoUseCase editPhotoUseCase) {
        return new EditProfilePresenterImpl(messages, editProfileUseCase, editPhotoUseCase);
    }

    @Provides
    @UserScope
    AddCarPresenter provideAddCarPresenter(Messages messages, AddCarUseCase addCarUseCase) {
        return new AddCarPresenterImpl(messages, addCarUseCase);
    }

    @Provides
    @UserScope
    CreateRoutePresenter provideCreateRoutePresenter(Messages messages, AddRouteUseCase addRouteUseCase, BuildRouteUseCase buildRouteUseCase, Account account) {
        return new CreateRoutePresenterImpl(messages, addRouteUseCase, buildRouteUseCase, account);
    }

    @Provides
    @UserScope
    DriverPresenter provideDriverPresenter(Messages messages, GetAllOwnerRoutesUseCase getAllRoutesUseCase, DeleteRouteUseCase deleteRouteUseCase, Account account) {
        return new DriverPresenterImpl(messages, getAllRoutesUseCase, deleteRouteUseCase, account);
    }

    @Provides
    @UserScope
    TravellerPresenter provideTravellerPresenter(Messages messages, GetAllSubscriberRoutesUseCase getAllRoutesUseCase, Account account) {
        return new TravellerPresenterImpl(messages, getAllRoutesUseCase, account);
    }

    @Provides
    @UserScope
    FindRoutePresenter provideFindRoutePresenter(Messages messages, FindRoutesUseCase findRoutesUseCase) {
        return new FindRoutePresenterImpl(messages, findRoutesUseCase);
    }

    @Provides
    @UserScope
    ViewRoutePresenter provideViewRoutePresenter(Messages messages,
                                                 UserInfoUseCase userInfoUseCase,
                                                 SubscribeUseCase subscribeUseCase,
                                                 UnsubscribeUseCase unsubscribeUseCase, GetPointSubscribersUseCase getPointSubscribersUseCase) {
        return new ViewRoutePresenterImpl(messages, userInfoUseCase, subscribeUseCase, unsubscribeUseCase, getPointSubscribersUseCase);
    }
}
