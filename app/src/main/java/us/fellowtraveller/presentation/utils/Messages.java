package us.fellowtraveller.presentation.utils;

import android.content.Context;

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
        if (e instanceof SocketTimeoutException || e instanceof HttpException) {
            return c.getString(R.string.error_check_network_connection);
        }
        return e.getMessage();
    }


    public String convertError(int messageId) {
        return c.getString(messageId);
    }
}
