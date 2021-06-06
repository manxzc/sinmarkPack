package cn.ymade.module_home.db.beans;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.zcxie.zc.model_comm.util.CommUtil;

import java.io.Serializable;

/**
 * @author zc.xie
 * @date 2021/6/6 0006.
 * GitHub：
 * email：3104873490@qq.com
 * description：
 */
@Entity
public class SNDeleteByLotBean implements Serializable {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String SN;
    public String LotSN;
    public int Status=0; //默认为1 ，0表示删除
    public String ModifyTime= CommUtil.getCurrentTimeYMD();

}
