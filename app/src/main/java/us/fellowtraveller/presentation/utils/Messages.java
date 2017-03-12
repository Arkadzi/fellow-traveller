package us.fellowtraveller.presentation.utils;

import android.content.Context;
import android.support.annotation.StringRes;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.adapter.rxjava.HttpException;
import us.fellowtraveller.R;


/**
 * Created by sebastian on 21.06.16.
 */
@Singleton
public class Messages {

    private Context c;

    @Inject
    public Messages(Context c) {
        this.c = c;
    }

    public String getError(Throwable e) {
        if (e instanceof SocketTimeoutException || e instanceof ConnectException) {
            return c.getString(R.string.error_check_network_connection);
        }
        HttpException httpException = null;
        if (e instanceof HttpException) {
            httpException = (HttpException) e;
        } else if (e.getCause() != null && e.getCause() instanceof HttpException) {
            httpException = (HttpException) e.getCause();
        }
        if (httpException != null)
            switch (httpException.code()) {
                case 401:
                    return convertError(R.string.error_user_not_authorized);
                case 409:
                    return convertError(R.string.error_such_user_exists);
            }
    return e.getMessage();
}


    public String convertError(@StringRes int messageId) {
        return c.getString(messageId);
    }
}
