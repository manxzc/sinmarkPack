package cn.ymade.module_home.common;


import cn.ymade.module_home.R;

/**
 * Created by admin on 2016/6/13.
 */
public class Constant {

    public static String[] menuName = {"查询", "单据", "出库", "货品","条码",  "同步", "汇总",  "人员"};
    public static int[] menuIconResId = { R.drawable.ic_menu_scan,R.drawable.ic_menu_lot,  R.drawable.ic_menu_cart,
            R.drawable.ic_menu_goods, R.drawable.ic_menu_asset,   R.drawable.ic_menu_sync,
            R.drawable.ic_menu_summary, R.drawable.ic_menu_staff
    };

    public static final int SERVER_INPUT_TYPE_ADDRESS=1;
    public static final int SERVER_INPUT_TYPE_PORT=2;
    public static final String SP_SERVER_ADDRESS="serverAddress";
    public static final String SP_SERVER_PORT="serverPort";

    public static final String CONSTANT_HTTP="http://";

    public static final String  CHECK_STATUS_WAIT ="0";
    public static final String  CHECK_STATUS_CHECKING="1";
    public static final String  CHECK_STATUS_DIF="2";

    public static final String FROM_ACTION="from_action";

    public static final String LD_UP_HOME_TITLE= "updateHomeTitle";
}
