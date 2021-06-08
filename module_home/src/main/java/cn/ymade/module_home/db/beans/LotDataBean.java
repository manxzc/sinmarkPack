package cn.ymade.module_home.db.beans;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

/**
 * @author zc.xie
 * @date 2021/6/6 0006.
 * GitHub：
 * email：3104873490@qq.com
 * description：
 */
@Entity
public class LotDataBean implements Serializable {
    @NonNull
    @PrimaryKey
    public String LotSN;
    public String LotNo;
    public String LotName;
    public long Stamp=System.currentTimeMillis();
    public int items= 0;
    public int Status=1; //默认为1 ，0表示删除
    public int upload=0;   //默认为0  1 已上传
    public String staff;
}
