package zhou.com.snailbook.manager;



import org.greenrobot.eventbus.EventBus;

import zhou.com.snailbook.bean.support.RefreshCollectionIconEvent;
import zhou.com.snailbook.bean.support.RefreshCollectionListEvent;
import zhou.com.snailbook.bean.support.SubEvent;

/**
 * @author yuyh.
 * @date 17/1/30.
 */

public class EventManager {

    public static void refreshCollectionList() {
        EventBus.getDefault().post(new RefreshCollectionListEvent());
    }

    public static void refreshCollectionIcon() {
        EventBus.getDefault().post(new RefreshCollectionIconEvent());
    }

    public static void refreshSubCategory(String minor, String type) {
        EventBus.getDefault().post(new SubEvent(minor, type));
    }

}
