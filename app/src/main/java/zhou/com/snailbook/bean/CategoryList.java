package zhou.com.snailbook.bean;

import java.util.List;

import zhou.com.snailbook.base.Base;

/**
 * Created by zhou on 2018/1/26.
 */

public class CategoryList extends Base {


    /**
     * male : [{"name":"玄幻","bookCount":188244},{"name":"奇幻","bookCount":24183}]
     * ok : true
     */

    public List<MaleBean> male;
    /**
     * name : 古代言情
     * bookCount : 125103
     */

    public List<MaleBean> female;

    public static class MaleBean {
        public String name;
        public int bookCount;
    }
}
