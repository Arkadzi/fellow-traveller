package us.fellowtraveller.domain.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arkadii on 3/5/17.
 */


public class Account {
    public static final String PREF_ID = "pref_id";
    public static final String PREF_SSOID = "pref_ssoid";
    public static final String PREF_PASSWORD = "pref_password";
    public static final String PREF_FIRST_NAME = "pref_first_name";
    public static final String PREF_LAST_NAME = "pref_last_name";
    public static final String PREF_GENDER = "pref_gender";
    public static final String PREF_EMAIL = "pref_email";
    public static final String PREF_TOKEN = "pref_token";
    public static final String PREF_URL = "pref_url";
    public static final String PREF_CARS = "cars";
    Context context;
    private AccountUser user;

    public Account(Context context) {
        this.context = context;
    }

    public boolean isAuthorized() {
        return user().getToken() != null;
    }

    public void logout() {

    }

    public void editProfile(AccountUser user) {
        this.user = null;
        PreferenceManager.getDefaultSharedPreferences(context).edit()
                .putString(PREF_FIRST_NAME, user.getFirstName())
                .putString(PREF_LAST_NAME, user.getLastName())
                .putString(PREF_EMAIL, user.getEmail())
                .putString(PREF_GENDER, user.getGender())
                .putString(PREF_URL, user.getImageUrl())
                .commit();
    }

    public void save(AccountUser user) {
        this.user = null;
        PreferenceManager.getDefaultSharedPreferences(context).edit()
                .putString(PREF_ID, user.getId())
                .putString(PREF_SSOID, user.getSsoId())
                .putString(PREF_PASSWORD, user.getPassword())
                .putString(PREF_FIRST_NAME, user.getFirstName())
                .putString(PREF_LAST_NAME, user.getLastName())
                .putString(PREF_EMAIL, user.getEmail())
                .putString(PREF_GENDER, user.getGender())
                .putString(PREF_TOKEN, user.getToken())
                .putString(PREF_URL, user.getImageUrl())
                .putString(PREF_CARS, new Gson().toJson(user.getCars()))
                .commit();
    }

    public AccountUser user() {
        if (user == null) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            user = new AccountUser();
            user.setToken(prefs.getString(PREF_TOKEN, null));
            user.setId(prefs.getString(PREF_ID, null));
            user.setSsoId(prefs.getString(PREF_SSOID, null));
            user.setFirstName(prefs.getString(PREF_FIRST_NAME, null));
            user.setLastName(prefs.getString(PREF_LAST_NAME, null));
            user.setEmail(prefs.getString(PREF_EMAIL, null));
            user.setPassword(prefs.getString(PREF_PASSWORD, null));
            user.setGender(prefs.getString(PREF_GENDER, null));
            user.setImageUrl(prefs.getString(PREF_URL, null));
            user.setCars(new Gson().fromJson(prefs.getString(PREF_CARS, "[]"), new TypeToken<ArrayList<Car>>(){}.getType()));
        }
        return user;
    }


    public void saveCars(List<Car> cars) {
        PreferenceManager.getDefaultSharedPreferences(context).edit()
                .putString(PREF_CARS, new Gson().toJson(cars))
                .commit();
        user = null;
    }

    public void savePhoto(String url) {
        PreferenceManager.getDefaultSharedPreferences(context).edit()
                .putString(PREF_URL, url)
                .commit();
        user = null;
    }
}
