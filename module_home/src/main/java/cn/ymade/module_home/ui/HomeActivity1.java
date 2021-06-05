package cn.ymade.module_home.ui;


import android.content.Intent;
import android.view.View;

import androidx.databinding.DataBinderMapper;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zcxie.zc.model_comm.base.BaseActivity;
import com.zcxie.zc.model_comm.callbacks.CallBack;
import com.zcxie.zc.model_comm.util.AppConfig;
import com.zcxie.zc.model_comm.util.CommUtil;
import com.zcxie.zc.model_comm.util.LiveDataBus;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import cn.ymade.module_home.R;
import cn.ymade.module_home.adapter.HomeMenuAdapter;
import cn.ymade.module_home.adapter.HomeMenuAdapterKt;
import cn.ymade.module_home.common.Constant;
import cn.ymade.module_home.databinding.ActivityHomeBinding;
import cn.ymade.module_home.db.beans.CheckBean;
import cn.ymade.module_home.db.beans.DevInfoBean;
import cn.ymade.module_home.db.database.DataBaseManager;
import cn.ymade.module_home.model.HomeMenuBean;
import cn.ymade.module_home.model.HomeTitleData;
import cn.ymade.module_home.vm.VMHome;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;


/**
 * @author zc.xie
 * @date 2021/4/19 0019.
 * GitHub：
 * email：3104873490@qq.com
 * description：
 */
@Route(path = "/home/homeActivity1")
public class HomeActivity1 extends BaseActivity<VMHome,ActivityHomeBinding> {

    private List<HomeMenuBean> homeMenuBeanList = new ArrayList<>();
//    HomeMenuAdapter homeMenuAdapter = null;
    HomeMenuAdapterKt homeMenuAdapter = null;


    public void initData() {

        getMBinding().setVm(getMViewModel());
        initMenuData();
        loadData();
        getMBinding().rvAssetm.setLayoutManager(new GridLayoutManager(this, 3));
        homeMenuAdapter = new HomeMenuAdapterKt(homeMenuBeanList, new CallBack<HomeMenuBean>() {
            @Override
            public void callBack(HomeMenuBean obj) {
                switch (obj.type) {
                    case 1:
                        startActivity(new Intent(HomeActivity1.this,SNListActivity.class));
                        break;
                    case 2:
                        //startIntentActivity(CommScanActivity.class,false,Constant.FROM_ACTION,CommScanActivity.FROM_RFID);
                        CommUtil.ToastU.showToast("单据");
                        break;
                    case 3:
                        startActivity(new Intent(HomeActivity1.this,SNSearchActivity.class));
                        break;
                    case 4:
                        startActivity(new Intent(HomeActivity1.this,SimpSNActivity.class));
                        break;
                    case 5:
//                        startActivity( ProcessComActivity.class,Constant.EXTRA_CATE,"退还");
                        CommUtil.ToastU.showToast("汇总");
                        break;
                    case 6:
                        startActivity(new Intent(HomeActivity1.this,CreateOutLotActivity.class));
                        break;
                    case 7:
                        startActivity(new Intent(HomeActivity1.this,SyncActvity.class));
                        break;
                    case 8:
                        startActivity(new Intent(HomeActivity1.this,StaffActivity.class));
                        break;

                }

            }
        });

        getMBinding().rvAssetm.setAdapter(homeMenuAdapter);
    }
    private void initMenuData() {
        for (int i = 0; i < Constant.menuName.length; i++) {
            homeMenuBeanList.add(new HomeMenuBean(Constant.menuName[i], Constant.menuIconResId[i], i + 1));
        }

    }

    //加载数据
    private void loadData() {

        getMViewModel().getHomeTitleData(new CallBack<HomeTitleData>() {
            @Override
            public void callBack(HomeTitleData data) {
                getMBinding().inNum.setText(""+data.getInSN());
                getMBinding().todayOut.setText(""+data.getTodaySN());
                getMBinding().waitNum.setText(""+data.getWaitUp());
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        getMBinding().unCode.setText(AppConfig.staff.get());
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    public void processLogic() {
        initData();
        initEvent();
    }

    private void initEvent() {
        getMBinding().imgSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity1.this,SettingsActivity.class));
            }
        });
        LiveDataBus.get().with(Constant.LD_UP_HOME_TITLE,Integer.class).observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                loadData();
            }
        });
    }

    @NotNull
    @Override
    public Class findViewModelClass() {
        return VMHome.class;
    }


}
