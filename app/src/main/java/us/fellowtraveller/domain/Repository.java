package us.fellowtraveller.domain;

import rx.Observable;
import us.fellowtraveller.domain.model.User;

/**
 * Created by arkadii on 3/5/17.
 */

public interface Repository {

    Observable<User> signIn(User user);
    Observable<User> signUp(User user);
}
