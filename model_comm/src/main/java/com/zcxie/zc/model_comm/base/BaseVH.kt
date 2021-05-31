package com.zcxie.zc.model_comm.base

import android.content.Context
import android.graphics.Bitmap
import android.text.Spanned
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class BaseVH(val mConvertView: View):RecyclerView.ViewHolder(mConvertView) {

    var mViews: SparseArray<View> = SparseArray<View>()

    companion object{
        fun getVH(parent: ViewGroup, layoutId: Int):BaseVH{
         return BaseVH(LayoutInflater.from(parent.context).inflate(layoutId, parent, false))
        }
    }
    fun <T : View>getView(id: Int):View{
      var v:T= mViews.get(id) as T
        if (v==null){
            v=mConvertView.findViewById(id)
            mViews.put(id, v)
        }
        return v
    }
    fun setText(id: Int, text: String){
        val tv:TextView= getView<TextView>(id) as TextView
        tv?.let { it.text = text }
    }
    fun setTextColor(id: Int, color: Int){
        val tv:TextView= getView<TextView>(id) as TextView
        tv?.let { it.setTextColor(color)}
    }
    fun setTextSpanned(context: Context?, id: Int, value: Spanned?) {
        val view: TextView = getView<TextView>(id) as TextView
        view.text = value
    }
    fun setImage(id: Int, value: Int) {
        val view: ImageView = getView<ImageView>(id) as ImageView
        view.setImageResource(value)
    }

    fun setBmp(id: Int, bitmap: Bitmap?) {
        val view: ImageView = getView<ImageView>(id) as ImageView
        view.setImageBitmap(bitmap)
    }
    fun setLocalImg(id: Int, imgUrl: Int) {
        val view: ImageView = getView<ImageView>(id) as ImageView
        Glide.with(view.context).load(imgUrl).apply(
            RequestOptions() //占位图
                .centerCrop()
        )
            .into(view)
    }
    fun setNetImageRadi(context: Context?, id: Int, value: String?) {
        val view: ImageView = getView<ImageView>(id) as ImageView
        Glide.with(context!!).load(value).apply(
            RequestOptions() //占位图
                .centerCrop()
        )
            .into(view)
    }

    fun getConvertView(): View? {
        return mConvertView
    }

    fun getChildView(viewId: Int): View? {
        return getView<View>(viewId)
    }
}