package us.fellowtraveller.data;

import rx.Observable;
import us.fellowtraveller.data.rest.RestApi;
import us.fellowtraveller.domain.Repository;
import us.fellowtraveller.domain.model.User;

/**
 * Created by arkadii on 3/5/17.
 */

public class RepositoryImpl implements Repository {
    private RestApi restApi;


    public RepositoryImpl(RestApi restApi) {
        this.restApi = restApi;
    }

    @Override
    public Observable<User> signIn(User user) {
        return restApi.signIn(user);
    }
}
