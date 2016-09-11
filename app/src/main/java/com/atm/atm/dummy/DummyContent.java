package com.atm.atm.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample title for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<ATMEventViewModel> ITEMS = new ArrayList<>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, ATMEventViewModel> ITEM_MAP = new HashMap<String, ATMEventViewModel>();

    private static final int COUNT = 25;

    static {
        ITEMS.add(new ATMEventViewModel(
                "12345678", "Jogging in the park",
                "Join us for a small run", "300m", 6, "Derek J", "16 min",
                "https://graph.facebook.com/1018860008231616/picture?width=128&height=128",
                "http://www.footballtube.com/img/video/nice-c7-free-kick-goal.jpg"));
        ITEMS.add(new ATMEventViewModel(
                "87654321", "Open dance class",
                "Salsa is a hit this summer. Feel free to join to learn some!", "500 m", 18, "Anny M", "44 min",
                "https://graph.facebook.com/1018860008231616/picture?width=128&height=128",
                "http://www.easyfreepatterns.com/patterns/19/circus-carnival-tent-stock-illustration-image-41630589-19875.jpg"));
        ITEMS.add(new ATMEventViewModel(
                "87654321", "Open dance class",
                "Salsa is a hit this summer. Feel free to join to learn some!", "500 m", 18, "Anny M", "44 min",
                "https://graph.facebook.com/1018860008231616/picture?width=128&height=128",
                "https://thumbs.dreamstime.com/x/meditation-dawn-23878596.jpg"));
        ITEMS.add(new ATMEventViewModel(
                "12345678", "Jogging in the park",
                "Join us for a small run", "300m", 6, "Derek J", "16 min",
                "https://graph.facebook.com/1018860008231616/picture?width=128&height=128",
                "https://thumbs.dreamstime.com/x/astronaut-floating-black-background-space-looking-earth-elements-image-furnished-nasa-30144346.jpg"));
    }

    /**
     * A dummy item representing a piece of title.
     */
    public static class ATMEventViewModel {
        public final String id; //event_id
        public final String title; //
        public final String description;
        public final String distance;
        public final int numberOfAttendants;
        public final String hostName;
        public final String timeLeft;
        public final String hosterPhotoUrl;
        public final String eventPhotoUrl;

        public ATMEventViewModel(String id, String title, String description, String distance,
                                 int numberOfAttendants, String hostName, String timeLeft, String hosterPhotoUrl, String eventPhotoUrl) {
            this.id = id;
            this.title = title;
            this.description = description;
            this.distance = distance;
            this.numberOfAttendants = numberOfAttendants;
            this.hostName = hostName;
            this.timeLeft = timeLeft;
            this.hosterPhotoUrl = hosterPhotoUrl;
            this.eventPhotoUrl = eventPhotoUrl;

        }

        @Override
        public String toString() {
            return title;
        }
    }
}
