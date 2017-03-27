package us.fellowtraveller.presentation.view;

import us.fellowtraveller.domain.model.User;

/**
 * Created by arkadii on 3/5/17.
 */

public interface SignInView extends ProgressView {

    void onSignIn(User user);
}
