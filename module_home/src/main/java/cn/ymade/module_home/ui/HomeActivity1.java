package cn.ymade.module_home.ui;


import androidx.recyclerview.widget.GridLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zcxie.zc.model_comm.base.BaseActivity;
import com.zcxie.zc.model_comm.callbacks.CallBack;
import com.zcxie.zc.model_comm.util.AppConfig;
import com.zcxie.zc.model_comm.util.CommUtil;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import cn.ymade.module_home.R;
import cn.ymade.module_home.adapter.HomeMenuAdapter;
import cn.ymade.module_home.common.Constant;
import cn.ymade.module_home.databinding.ActivityHomeBinding;
import cn.ymade.module_home.db.beans.CheckBean;
import cn.ymade.module_home.model.HomeMenuBean;
import cn.ymade.module_home.vm.VMHome;


/**
 * @author zc.xie
 * @date 2021/4/19 0019.
 * GitHub：
 * email：3104873490@qq.com
 * description：
 */
@Route(path = "/home/homeActivity1")
public class HomeActivity1 extends BaseActivity {
    ActivityHomeBinding binding;
    private List<HomeMenuBean> homeMenuBeanList = new ArrayList<>();
    HomeMenuAdapter homeMenuAdapter = null;


    public void initData() {
        initMenuData();
        //loadData();
        binding.rvAssetm.setLayoutManager(new GridLayoutManager(this, 3));
        homeMenuAdapter = new HomeMenuAdapter(homeMenuBeanList, new CallBack<HomeMenuBean>() {
            @Override
            public void callBack(HomeMenuBean obj) {
                switch (obj.type) {
                    case 1:
                        //startIntentActivity(CommScanActivity.class,false,Constant.FROM_ACTION,CommScanActivity.FROM_SCAN);
                        CommUtil.ToastU.showToast("货品");
                        break;
                    case 2:
                        //startIntentActivity(CommScanActivity.class,false,Constant.FROM_ACTION,CommScanActivity.FROM_RFID);
                        CommUtil.ToastU.showToast("单据");
                        break;
                    case 3:
//                       startActivity(CheckListActivity.class);
                        CommUtil.ToastU.showToast("查询");
//                        Intent intent = new Intent(HomeActivity.this, CheckActivity.class);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        startActivity(intent);
                        break;
                    case 4:
//                      startActivity( AssetRequisitionlistActivity.class);
                        CommUtil.ToastU.showToast("条码");
                        break;
                    case 5:
//                        startActivity( ProcessComActivity.class,Constant.EXTRA_CATE,"退还");
                        CommUtil.ToastU.showToast("汇总");
                        break;
                    case 6:
                        CommUtil.ToastU.showToast("出库");
                        //startActivity(SyncActivity.class);
                        break;
                    case 7:
//                        startActivity( ProcessComActivity.class,Constant.EXTRA_CATE,"退还");
                        CommUtil.ToastU.showToast("同步");
                        break;
                    case 8:
                        //startActivity(SyncActivity.class);
                        CommUtil.ToastU.showToast("人员");
                        break;

                }

            }
        });
        binding.rvAssetm.setAdapter(homeMenuAdapter);
    }
    private void initMenuData() {
        for (int i = 0; i < Constant.menuName.length; i++) {
            homeMenuBeanList.add(new HomeMenuBean(Constant.menuName[i], Constant.menuIconResId[i], i + 1));
        }

    }
//    @Override
//    public void initEvent() {
//        super.initEvent();
//        binding.imgSetting.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(SettingActivity.class);
//            }
//        });
//        binding.llChange.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(ChangeCheckActivity.class);
//            }
//        });
//        LiveDataBus.get().with("ChangeCheck",Integer.class).observe(this, new Observer<Integer>() {
//            @Override
//            public void onChanged(Integer integer) {
//                loadData();
//            }
//        });
//    }

//    //加载数据
//    private void loadData() {
//        Observable.create(new ObservableOnSubscribe<CheckBean>() {
//            @Override
//            public void subscribe(ObservableEmitter<CheckBean> emitter) throws Exception {
//                CheckBean checkBean= XinMaDatabase.getInstance().checkBeanDao().getSingle();
//                Log.i("TAG", "loadData: ChangeCheck "+(checkBean==null));
//                if (checkBean!=null)
//                    emitter.onNext(checkBean);
//            }
//        })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<CheckBean>() {
//                    @Override
//                    public void accept(CheckBean data) throws Exception {
//
//                        binding.checkNo.setText(data.getCheckNo());
//                        binding.checkExt.setText(data.getRemark());
//                        binding.tvWaitCheck.setText(data.getWaitItems()+"");
//                        binding.tvComCheck.setText(data.getFactItems()+"");
//                        binding.tvDiffCheck.setText(data.getDiffItems()+"");
//                    }
//                });
//
//    }

    @Override
    protected void onResume() {
        super.onResume();
//        binding.username.setText("hi,"+ AppConfig.CompanyName.get());
//        binding.unCode.setText(AppConfig.DeviceNo.get());
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    public void processLogic() {
        initData();
    }

    @NotNull
    @Override
    public Class findViewModelClass() {
        return VMHome.class;
    }
}
