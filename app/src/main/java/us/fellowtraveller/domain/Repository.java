package us.fellowtraveller.domain;

import rx.Observable;
import us.fellowtraveller.domain.model.AccountUser;
import us.fellowtraveller.domain.model.User;

/**
 * Created by arkadii on 3/5/17.
 */

public interface Repository {

    Observable<AccountUser> signIn(AccountUser user);

    Observable<AccountUser> signUp(AccountUser user);

    Observable<User> getUserInfo(String userId);
}
