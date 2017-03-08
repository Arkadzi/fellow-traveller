package us.fellowtraveller.presentation.presenter;

import us.fellowtraveller.domain.model.User;
import us.fellowtraveller.domain.subscribers.BaseProgressSubscriber;
import us.fellowtraveller.domain.usecase.SignUpUseCase;
import us.fellowtraveller.presentation.utils.Messages;
import us.fellowtraveller.presentation.view.SignUpView;

/**
 * Created by arkadii on 3/5/17.
 */

public class SignUpPresenterImpl extends ProgressPresenter<SignUpView>
        implements SignUpPresenter {

    private final SignUpUseCase signUpUseCase;

    public SignUpPresenterImpl(Messages messages, SignUpUseCase signUpUseCase) {
        super(messages);
        this.signUpUseCase = signUpUseCase;
    }

    @Override
    public void onSignUpButtonClick(User user) {
        signUpUseCase.setUser(user);
        signUpUseCase.execute(new BaseProgressSubscriber<User>(this) {
            @Override
            public void onNext(User response) {
                super.onNext(response);
                SignUpView view = getView();
                if (view != null) {
                    view.onSignUp(user);
                }
            }
        });
    }
}
