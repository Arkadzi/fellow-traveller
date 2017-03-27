package us.fellowtraveller.presentation.presenter;

import us.fellowtraveller.domain.model.Car;
import us.fellowtraveller.presentation.view.AddCarView;

/**
 * Created by arkadii on 3/26/17.
 */
public interface AddCarPresenter extends Presenter<AddCarView>{
    void onCarCreated(Car car, String pictureFilePath);
}
