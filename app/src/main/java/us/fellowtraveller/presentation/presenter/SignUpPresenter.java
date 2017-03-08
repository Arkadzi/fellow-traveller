package us.fellowtraveller.presentation.presenter;

import us.fellowtraveller.domain.model.User;
import us.fellowtraveller.presentation.view.SignUpView;

/**
 * Created by arkadii on 3/5/17.
 */

public interface SignUpPresenter extends Presenter<SignUpView> {
    void onSignUpButtonClick(User user);
}
