package us.fellowtraveller.presentation.presenter;

import us.fellowtraveller.presentation.view.ProfileView;

/**
 * Created by arkadii on 3/18/17.
 */
public interface ProfilePresenter extends Presenter<ProfileView> {
    void onUserRequested(String userId);

}
