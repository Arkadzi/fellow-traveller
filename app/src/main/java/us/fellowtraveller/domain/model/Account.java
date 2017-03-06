package us.fellowtraveller.domain.model;

import android.content.Context;

/**
 * Created by arkadii on 3/5/17.
 */

public class Account {
    Context context;

    public Account(Context context) {
        this.context = context;
    }

    public boolean isAuthorized() {
        return false;
    }

    public void logout() {

    }
}
