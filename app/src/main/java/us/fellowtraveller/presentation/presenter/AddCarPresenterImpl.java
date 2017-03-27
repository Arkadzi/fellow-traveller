package us.fellowtraveller.presentation.presenter;

import us.fellowtraveller.domain.model.Car;
import us.fellowtraveller.domain.subscribers.BaseProgressSubscriber;
import us.fellowtraveller.domain.usecase.AddCarUseCase;
import us.fellowtraveller.presentation.utils.Messages;
import us.fellowtraveller.presentation.view.AddCarView;

/**
 * Created by arkadii on 3/26/17.
 */
public class AddCarPresenterImpl extends ProgressPresenter<AddCarView> implements AddCarPresenter {
    private final AddCarUseCase addCarUseCase;

    public AddCarPresenterImpl(Messages messages, AddCarUseCase addCarUseCase) {
        super(messages);
        this.addCarUseCase = addCarUseCase;
    }

    @Override
    public void onCarCreated(Car car, String pictureFilePath) {
        addCarUseCase.setData(car, pictureFilePath);
        addCarUseCase.execute(new BaseProgressSubscriber<Car>(this) {
            @Override
            public void onNext(Car response) {
                super.onNext(response);
                AddCarView view = getView();
                if (view != null) {
                    view.onCarAdded(response);
                }
            }
        });

    }
}
