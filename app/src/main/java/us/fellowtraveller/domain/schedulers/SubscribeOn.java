package us.fellowtraveller.domain.schedulers;

import rx.Scheduler;

public interface SubscribeOn {
    Scheduler getScheduler();
}
