package us.fellowtraveller.presentation.view;

import us.fellowtraveller.domain.model.Car;
import us.fellowtraveller.domain.model.User;

/**
 * Created by arkadii on 3/18/17.
 */
public interface ProfileView extends ProgressView {
    void renderUser(User user);
    void renderError();

    void onCarDeleted(Car response);
}
