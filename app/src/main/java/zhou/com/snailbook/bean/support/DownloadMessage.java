package zhou.com.snailbook.bean.support;

/**
 * Created by zhou on 2018/1/27.
 */

public class DownloadMessage {

    public String bookId;

    public String message;

    public boolean isComplete = false;

    public DownloadMessage(String bookId, String message, boolean isComplete) {
        this.bookId = bookId;
        this.message = message;
        this.isComplete = isComplete;
    }
}

