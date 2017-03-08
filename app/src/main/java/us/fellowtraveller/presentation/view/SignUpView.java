package us.fellowtraveller.presentation.view;

import us.fellowtraveller.domain.model.User;
import us.fellowtraveller.presentation.presenter.ProgressView;

/**
 * Created by arkadii on 3/5/17.
 */

public interface SignUpView extends ProgressView {

    void onSignUp(User user);
}
