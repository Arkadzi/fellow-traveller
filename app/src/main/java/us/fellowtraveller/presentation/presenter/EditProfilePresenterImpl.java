package us.fellowtraveller.presentation.presenter;

import us.fellowtraveller.domain.model.AccountUser;
import us.fellowtraveller.domain.model.Photo;
import us.fellowtraveller.domain.model.User;
import us.fellowtraveller.domain.subscribers.BaseProgressSubscriber;
import us.fellowtraveller.domain.usecase.EditPhotoUseCase;
import us.fellowtraveller.domain.usecase.EditProfileUseCase;
import us.fellowtraveller.presentation.utils.Messages;
import us.fellowtraveller.presentation.view.EditProfileView;

/**
 * Created by arkadii on 3/19/17.
 */
public class EditProfilePresenterImpl extends ProgressPresenter<EditProfileView> implements EditProfilePresenter {
    private final EditProfileUseCase editProfileUseCase;
    private final EditPhotoUseCase editPhotoUseCase;

    public EditProfilePresenterImpl(Messages messages, EditProfileUseCase editProfileUseCase, EditPhotoUseCase editPhotoUseCase) {
        super(messages);
        this.editProfileUseCase = editProfileUseCase;
        this.editPhotoUseCase = editPhotoUseCase;
    }

    @Override
    public void onProfileEdited(User user) {
        editProfileUseCase.setUser(user);
        editProfileUseCase.execute(new BaseProgressSubscriber<AccountUser>(this) {
            @Override
            public void onNext(AccountUser response) {
                super.onNext(response);
                EditProfileView view = getView();
                if (view != null) {
                    view.onProfileEdited();
                }
            }
        });
    }

    @Override
    public void onImageEdited(String filePath) {
        editPhotoUseCase.setFilePath(filePath);
        editPhotoUseCase.execute(new BaseProgressSubscriber<Photo>(this) {
            @Override
            public void onNext(Photo response) {
                super.onNext(response);
                EditProfileView view = getView();
                if (view != null) {
                    view.onPhotoEdited();
                }
            }
        });
    }

}
