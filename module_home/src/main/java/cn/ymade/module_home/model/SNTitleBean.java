package cn.ymade.module_home.model;

import java.io.Serializable;
import java.util.List;

import cn.ymade.module_home.db.beans.SNBean;

public class SNTitleBean implements Serializable {
    public String title;
    public int count;
    public List<SNBean> snBeans;
}
