package us.fellowtraveller.presentation.presenter;

import us.fellowtraveller.domain.model.AccountUser;
import us.fellowtraveller.presentation.view.SignInView;

/**
 * Created by arkadii on 3/5/17.
 */

public interface SignInPresenter extends Presenter<SignInView> {
    void onSignInButtonClick(AccountUser user);
}
