package cn.ymade.module_home.db.beans;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.zcxie.zc.model_comm.util.CommUtil;

import java.io.Serializable;

/**
 * @author zc.xie
 * @date 2021/6/4 0004.
 * GitHub：
 * email：3104873490@qq.com
 * description：
 */
@Entity
public class SNBean implements Serializable {
    @NonNull
    @PrimaryKey
    public String SN;
    public int NO;
    public String Title;
    public String LotSN;
    public String ModifyTime= CommUtil.getCurrentTimeYMD();
    public int Status=1; //默认为1 ，0表示删除
    public int upload=0;   //默认为0  1 为未上传  2已上传
    public int out=0;   //默认为0  1 出库
}
