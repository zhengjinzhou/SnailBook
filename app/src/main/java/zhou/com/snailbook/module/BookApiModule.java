package zhou.com.snailbook.module;

import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import zhou.com.snailbook.api.BookApi;
import zhou.com.snailbook.api.support.HeaderInterceptor;
import zhou.com.snailbook.api.support.Logger;
import zhou.com.snailbook.api.support.LoggingInterceptor;

/**
 * Created by zhou on 2018/1/25.
 */

@Module
public class BookApiModule {
    @Provides
    public OkHttpClient provideOkHttpClient() {

        LoggingInterceptor logging = new LoggingInterceptor(new Logger());
        logging.setLevel(LoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(20 * 1000, TimeUnit.MILLISECONDS)
                .readTimeout(20 * 1000, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true) // 失败重发
                .addInterceptor(new HeaderInterceptor())
                .addInterceptor(logging);
        return builder.build();
    }

    @Provides
    protected BookApi provideBookService(OkHttpClient okHttpClient) {
        return BookApi.getInstance(okHttpClient);
    }
}
