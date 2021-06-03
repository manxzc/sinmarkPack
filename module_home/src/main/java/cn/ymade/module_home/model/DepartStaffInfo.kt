package cn.ymade.module_home.model

import cn.ymade.module_home.db.beans.DepartBean
import cn.ymade.module_home.db.beans.StaffBean

/**
 * @author zc.xie
 * @date 2021/6/3 0003.
 * GitHub：
 * email：3104873490@qq.com
 * description：
 */
data class DepartStaffInfo(
    val Depart: List<DepartBean>,
    val Staff: List<StaffBean>,
    val code: Int
)