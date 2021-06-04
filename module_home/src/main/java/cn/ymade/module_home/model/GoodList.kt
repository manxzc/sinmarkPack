package cn.ymade.module_home.model

import cn.ymade.module_home.db.beans.SNBean

/**
 * @author zc.xie
 * @date 2021/6/4 0004.
 * GitHub：
 * email：3104873490@qq.com
 * description：
 */
data class GoodList(
    val code: Int,
    val NextSN: Int,
    val Goods:List<SNBean>
)
