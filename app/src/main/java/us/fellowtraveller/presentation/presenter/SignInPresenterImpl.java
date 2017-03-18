package us.fellowtraveller.presentation.presenter;

import javax.inject.Inject;

import us.fellowtraveller.data.di.scope.UserScope;
import us.fellowtraveller.domain.Repository;
import us.fellowtraveller.domain.model.AccountUser;
import us.fellowtraveller.domain.model.User;
import us.fellowtraveller.domain.subscribers.BaseProgressSubscriber;
import us.fellowtraveller.domain.usecase.SignInUseCase;
import us.fellowtraveller.presentation.utils.Messages;
import us.fellowtraveller.presentation.view.SignInView;

/**
 * Created by arkadii on 3/5/17.
 */

public class SignInPresenterImpl extends ProgressPresenter<SignInView>
        implements SignInPresenter {

    private final SignInUseCase signInUseCase;

    public SignInPresenterImpl(Messages messages, SignInUseCase signInUseCase) {
        super(messages);
        this.signInUseCase = signInUseCase;
    }

    @Override
    public void onSignInButtonClick(AccountUser user) {
        signInUseCase.setUser(user);
        signInUseCase.execute(new BaseProgressSubscriber<AccountUser>(this) {
            @Override
            public void onNext(AccountUser response) {
                super.onNext(response);
                SignInView view = getView();
                if (view != null) {
                    view.onSignIn(user);
                }
            }
        });
    }
}
