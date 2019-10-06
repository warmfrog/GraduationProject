package cn.booksp.sharebook.dummy;

import cn.booksp.sharebook.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyBookInfo {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<DummyItem> ITEMS = new ArrayList<DummyItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

    private static final int COUNT = 15;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    private static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.book_name, item);
    }

    private static DummyItem createDummyItem(int position) {
        return new DummyItem(R.drawable.software, "Software Engineer" + position + 1, "Roger Pressman", "$15", "面交，快递", "距离3公里以内");
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem {

        public final int book_image;
        public final String book_name;
        public final String book_author;
        public final String book_price;
        public final String deliver_method;
        public final String book_distance;

        public DummyItem(int book_image, String book_name, String book_author, String book_price, String deliver_method, String book_distance) {
            this.book_image = book_image;
            this.book_name = book_name;
            this.book_author = book_author;
            this.book_price = book_price;
            this.deliver_method = deliver_method;
            this.book_distance = book_distance;
        }
    }
}
