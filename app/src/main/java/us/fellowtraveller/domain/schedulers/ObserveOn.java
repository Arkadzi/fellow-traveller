package us.fellowtraveller.domain.schedulers;

import rx.Scheduler;

public interface ObserveOn {
    Scheduler getScheduler();
}
