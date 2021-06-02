package cn.ymade.module_home.db.beans;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

/**
 * @author zc.xie
 * @date 2021/4/19 0019.
 * GitHub：
 * email：3104873490@qq.com
 * description：
 */
@Entity
public class CheckBean implements Serializable {
    /**
     * CheckNo : 2021051005
     * CheckDate : 2021-05
     * Items : 7
     * FactItems : 1
     * DiffItems : 6
     * WaitItems : 0
     * Remark : 测试0002
     * UploadTime : 2021/4/19 18:57:53
     */
    @NonNull
    @PrimaryKey
    private String CheckNo;
    private String CheckDate;
    private int Items;
    private int FactItems;
    private int DiffItems;
    private int WaitItems;
    private String Remark;
    private String UploadTime;

    public String getCheckNo() {
        return CheckNo;
    }

    public void setCheckNo(String CheckNo) {
        this.CheckNo = CheckNo;
    }

    public String getCheckDate() {
        return CheckDate;
    }

    public void setCheckDate(String CheckDate) {
        this.CheckDate = CheckDate;
    }

    public int getItems() {
        return Items;
    }

    public void setItems(int Items) {
        this.Items = Items;
    }

    public int getFactItems() {
        return FactItems;
    }

    public void setFactItems(int FactItems) {
        this.FactItems = FactItems;
    }

    public int getDiffItems() {
        return DiffItems;
    }

    public void setDiffItems(int DiffItems) {
        this.DiffItems = DiffItems;
    }

    public int getWaitItems() {
        return WaitItems;
    }

    public void setWaitItems(int WaitItems) {
        this.WaitItems = WaitItems;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String Remark) {
        this.Remark = Remark;
    }

    public String getUploadTime() {
        return UploadTime;
    }

    public void setUploadTime(String UploadTime) {
        this.UploadTime = UploadTime;
    }
}
