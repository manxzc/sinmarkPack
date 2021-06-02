package cn.ymade.module_home.adapter;

import android.view.View;

import androidx.annotation.NonNull;

import com.zcxie.zc.model_comm.base.BindBaseAdapter;
import com.zcxie.zc.model_comm.base.BindBaseViewHolder;
import com.zcxie.zc.model_comm.callbacks.CallBack;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import cn.ymade.module_home.R;
import cn.ymade.module_home.databinding.ItemHomeMenuLayoutBinding;
import cn.ymade.module_home.model.HomeMenuBean;


public class HomeMenuAdapter extends BindBaseAdapter<List<HomeMenuBean>> {
    CallBack<HomeMenuBean> callBack = null;

    public HomeMenuAdapter(List<HomeMenuBean> list, CallBack<HomeMenuBean> callBack) {
        super((List<? extends List<HomeMenuBean>>) list);
        this.callBack = callBack;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_home_menu_layout;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull BindBaseViewHolder holder, int position) {
        ((ItemHomeMenuLayoutBinding) holder.getBinding()).setBean((HomeMenuBean) getDatas().get(position));
        holder.getBinding().executePendingBindings();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null!=callBack)
                    callBack.callBack((HomeMenuBean) getDatas().get(position));
            }
        });
    }
}
