package eu.vk.trackerapp;

import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.Subject;

public class ListUpdateTracker {
    private static final Subject<Boolean> UPDATE_TRACKER = PublishSubject.create();

    private ListUpdateTracker() {
    }

    public static ListUpdateTracker getInstance() {
        return new ListUpdateTracker();
    }

    public Subject<Boolean> getUpdateTracker() {
        return UPDATE_TRACKER;
    }
}
