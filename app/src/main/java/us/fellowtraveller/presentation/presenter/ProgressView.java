package us.fellowtraveller.presentation.presenter;

import us.fellowtraveller.presentation.view.View;

/**
 * Created by sebastian on 10.06.16.
 */
public interface ProgressView extends View {
    void showProgress();
    void hideProgress();
}
