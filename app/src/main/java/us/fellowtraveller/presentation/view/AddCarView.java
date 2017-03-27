package us.fellowtraveller.presentation.view;

import us.fellowtraveller.domain.model.Car;
import us.fellowtraveller.presentation.presenter.ProgressView;

/**
 * Created by arkadii on 3/26/17.
 */
public interface AddCarView extends ProgressView {
    void onCarAdded(Car car);
}
