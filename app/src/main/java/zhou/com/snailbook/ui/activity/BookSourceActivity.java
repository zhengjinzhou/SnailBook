package zhou.com.snailbook.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import zhou.com.snailbook.R;

public class BookSourceActivity extends AppCompatActivity {

    public static final String INTENT_BOOK_ID = "bookId";

    public static void start(Activity activity, String bookId, int reqId) {
        activity.startActivityForResult(new Intent(activity, BookSourceActivity.class)
                .putExtra(INTENT_BOOK_ID, bookId), reqId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_source);
    }
}
