package us.fellowtraveller.data.di.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by sebastian on 13.08.16.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface UserScope {
}