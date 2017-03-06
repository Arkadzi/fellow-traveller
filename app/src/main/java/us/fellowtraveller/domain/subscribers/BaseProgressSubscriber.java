package us.fellowtraveller.domain.subscribers;


import android.util.Log;

import retrofit2.adapter.rxjava.HttpException;

public class BaseProgressSubscriber<T> extends BaseUseCaseSubscriber<T> {
    private ProgressSubscriberListener listener;

    public BaseProgressSubscriber(ProgressSubscriberListener listener) {
        this.listener = listener;
    }

    @Override
    public void onStart() {
        if (listener != null)
            listener.onStartLoading();
    }

    @Override
    public void onCompleted() {
        Log.e("useCase", "onComplete");
        if (listener != null)
            listener.onCompleted();
        listener = null;
    }

    @Override
    public void onError(Throwable e) {
        Log.e("useCase", "onError " + e);
        int code = 0;
        if (e instanceof HttpException) {
            code = ((HttpException) e).code();
        }
        if (!(e instanceof HttpException && (code == 401 || code == 403))) {
            if (listener != null)
                listener.onError(e);
        }

        listener = null;
    }

    @Override
    public void onNext(T response) {
        Log.e("useCase", "onNext " + response);
    }

    public interface ProgressSubscriberListener {
        void onError(Throwable t);

        void onCompleted();

        void onStartLoading();
    }
}