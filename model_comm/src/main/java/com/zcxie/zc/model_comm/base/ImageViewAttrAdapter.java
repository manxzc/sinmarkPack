package com.zcxie.zc.model_comm.base;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zcxie.zc.model_comm.R;
import com.zcxie.zc.model_comm.util.ImageUtils;


/**
 * Created by weiwei2 on 2019/10/18
 * modify by dan.y on 2020/03/16
 */
public class ImageViewAttrAdapter {
    /**
     * glide加载图片databing适配器 ---- xml中app:imageurl调用
     *
     * @param imageView
     * @param url
     * @param shapeType 加载出来的图形效果 <=0-无效果；1.圆形；2.圆角。
     *                  values/integers.xml中定义快捷引用
     * @param phResId   占位图, -1,无；可以不传，默认为0.
     *                  requireAll false 表示至少一个，可组合使用
     */
    @BindingAdapter(value = {"imageUrl", "shapeType", "phResId"}, requireAll = false)
    public static void loadImage(ImageView imageView, String url, int shapeType, int phResId) {
        GlideAdapterOption option = new GlideAdapterOption(shapeType, phResId).invoke();
        ImageUtils.glideLoadUrl(imageView.getContext(), imageView, url
                , option.isCircle(), option.isRounder(), option.getPhResId());
    }
    @BindingAdapter(value = {"imageUrl"}, requireAll = false)
    public static void loadImageRadious(ImageView imageView, String url) {
//        //设置图片圆角角度
//            RoundedCorners roundedCorners = new RoundedCorners(10);
//            //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
//            // RequestOptions options = RequestOptions.bitmapTransform(roundedCorners).override(300, 300);
//            RequestOptions options = RequestOptions.bitmapTransform(roundedCorners);
//            Glide.with(imageView.getContext()).load(url).apply(options).into(imageView);

        Glide.with(imageView.getContext()).load(url).
                apply(new RequestOptions().placeholder(R.mipmap.img_defaut)//占位图
                        .error(R.mipmap.img_defaut)
                        .centerCrop())
                .into(imageView);

    }
    //
    @BindingAdapter(value = {"resIdImg", "imgRadious"}, requireAll = false)
    public static void LoadResBg(ImageView imageView, int resIdImg, float imgRadious) {
        if (resIdImg != -1) {
//            CornerTransform cornerTransform = new CornerTransform(imageView.getContext(), CommUtil.dipTopx(imageView.getContext(), 10));
//            cornerTransform.setExceptCorner(false, false, false, false);
//            Glide.with(imageView.getContext()).
//                    asBitmap().skipMemoryCache(true)
//                    .load(resIdImg)
//                    .apply(RequestOptions.bitmapTransform(cornerTransform))
//                    .into(imageView);

            Glide.with(imageView.getContext()).load(resIdImg).
                    apply(new RequestOptions().placeholder(R.mipmap.img_defaut)//占位图
                            .error(R.mipmap.img_defaut)
                            .centerCrop())
                    .into(imageView);

        }

    }

    @BindingAdapter(value = {"imgResId"}, requireAll = false)
    public static void LoadImgResBg(ImageView imageView, int imgResId) {
        if (imgResId != -1)
            imageView.setImageResource(imgResId);
    }

    private static class GlideAdapterOption {
        private int shapeType;
        private int phResId;
        private boolean isCircle;
        private boolean isRounder;

        public GlideAdapterOption(int shapeType, int phResId) {
            this.shapeType = shapeType;
            this.phResId = phResId;
        }

        public int getPhResId() {
            return phResId;
        }

        public boolean isCircle() {
            return isCircle;
        }

        public boolean isRounder() {
            return isRounder;
        }

        public GlideAdapterOption invoke() {
            isCircle = false;
            isRounder = false;
            if (shapeType == 1) {
                isCircle = true;
                isRounder = false;
            } else if (shapeType == 2) {
                isCircle = false;
                isRounder = true;
            } else {
            }
            phResId = (phResId == 0 || phResId == -1) ? -1 : phResId;
            return this;
        }
    }
}
