package zhou.com.snailbook.component;

import android.content.Context;

import dagger.Component;
import zhou.com.snailbook.api.BookApi;
import zhou.com.snailbook.module.AppModule;
import zhou.com.snailbook.module.BookApiModule;

/**
 * Created by zhou on 2018/1/25.
 */

@Component(modules = {AppModule.class, BookApiModule.class})
public interface AppComponent {
    Context getContext();

    BookApi getReaderApi();
}
