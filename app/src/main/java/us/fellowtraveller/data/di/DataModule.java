package us.fellowtraveller.data.di;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import us.fellowtraveller.data.Constants;
import us.fellowtraveller.domain.model.Account;

@Module
public class DataModule {


    @Provides
    @Singleton
    public Account provideAccount(Context context) {
        return new Account(context);
    }

    @Provides
    @Singleton
    public OkHttpClient provideOkClient(Context context, Account account) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(chain -> {
                    Request.Builder builder = chain.request().newBuilder();
                    String token = account.user().getToken();
                    if (token != null) {
                        builder.addHeader("Authorization", token);
                    }
                    return chain.proceed(builder.build());
                })
                .addInterceptor(interceptor)
                .build();
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit(OkHttpClient okClient) {
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(okClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
