package zhou.com.snailbook.api.support;

import zhou.com.snailbook.utils.LogUtils;

/**
 * Created by zhou on 2018/1/25.
 */

public class Logger implements LoggingInterceptor.Logger {

    @Override
    public void log(String message) {
        LogUtils.i("http : " + message);
    }
}
