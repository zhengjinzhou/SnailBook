package zhou.com.snailbook.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by zhou on 2018/1/25.
 */

@Module
public class AppModule {
    private Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Provides
    public Context provideContext() {
        return context;
    }
}
