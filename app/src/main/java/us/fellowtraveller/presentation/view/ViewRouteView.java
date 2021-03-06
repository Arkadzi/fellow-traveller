package us.fellowtraveller.presentation.view;

import java.util.List;

import us.fellowtraveller.domain.model.User;

/**
 * Created by arkadius on 4/22/17.
 */

public interface ViewRouteView extends ProgressView {
    void renderUser(User response);

    void onSubscribed();

    void onUnsubscribed();

    void showSubscribers(List<User> response);
}
