package zhou.com.snailbook.bean.support;

/**
 * Created by zhou on 2018/1/26.
 */

public class FindBean {
    private String title;
    private int icon;

    public FindBean(String title, int icon) {
        this.title = title;
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
