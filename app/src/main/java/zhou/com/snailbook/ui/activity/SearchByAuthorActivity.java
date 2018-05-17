package zhou.com.snailbook.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import zhou.com.snailbook.R;

public class SearchByAuthorActivity extends AppCompatActivity {

    public static final String INTENT_AUTHOR = "author";

    public static void startActivity(Context context, String author) {
        context.startActivity(new Intent(context, SearchByAuthorActivity.class)
                .putExtra(INTENT_AUTHOR, author));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_author);
    }
}
