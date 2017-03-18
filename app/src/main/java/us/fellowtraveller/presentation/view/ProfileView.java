package us.fellowtraveller.presentation.view;

import us.fellowtraveller.domain.model.User;
import us.fellowtraveller.presentation.presenter.ProgressView;

/**
 * Created by arkadii on 3/18/17.
 */
public interface ProfileView extends ProgressView {
    void renderUser(User user);
    void renderError();
}
