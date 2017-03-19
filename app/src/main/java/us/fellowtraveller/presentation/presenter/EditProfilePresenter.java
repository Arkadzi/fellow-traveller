package us.fellowtraveller.presentation.presenter;

import us.fellowtraveller.domain.model.User;
import us.fellowtraveller.presentation.view.EditProfileView;

/**
 * Created by arkadii on 3/19/17.
 */

public interface EditProfilePresenter extends Presenter<EditProfileView> {
    void onProfileEdited(User user);

    void onImageEdited(String pictureUrl);
}
