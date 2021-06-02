package cn.ymade.module_home.model;

/**
 * type 1:货品  2：单据 3：查询 4：条码
 * 5：汇总 6：出库 7：同步 8：人员
 */
public class HomeMenuBean {
    public String menuName;
    public int iconResId;
    public int type;

    public HomeMenuBean(String menuName, int iconResId, int type) {
        this.menuName = menuName;
        this.iconResId = iconResId;
        this.type = type;
    }

    public HomeMenuBean() {
    }
}
