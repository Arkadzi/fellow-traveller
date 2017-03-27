package us.fellowtraveller.presentation.presenter;

import us.fellowtraveller.domain.subscribers.BaseProgressSubscriber;
import us.fellowtraveller.presentation.utils.Messages;
import us.fellowtraveller.presentation.view.ProgressView;


/**
 * Created by sebastian on 08.07.16.
 */
public class ProgressPresenter<V extends ProgressView> extends BasePresenter<V> implements
        BaseProgressSubscriber.ProgressSubscriberListener {


    public ProgressPresenter(Messages messages) {
        super(messages);
    }

    @Override
    public void onError(Throwable t) {
        V view = getView();
        if (view != null) {
            view.hideProgress();
            String error = getMessages().getError(t);
            view.showMessage(error);
        }
    }

    @Override
    public void onCompleted() {
        V view = getView();
        if (view != null) {
            view.hideProgress();
        }
    }

    @Override
    public void onStartLoading() {
        V view = getView();
        if (view != null) {
            view.showProgress();
        }
    }
}
