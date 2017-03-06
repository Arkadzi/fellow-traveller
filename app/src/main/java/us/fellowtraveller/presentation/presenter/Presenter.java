package us.fellowtraveller.presentation.presenter;


import us.fellowtraveller.presentation.view.View;

/**
 * Created by sebastian on 07.07.16.
 */
public interface Presenter<V extends View> {
    void onCreate(V view);
    void onRelease();
}
