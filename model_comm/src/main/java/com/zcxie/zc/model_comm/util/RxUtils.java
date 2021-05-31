package com.zcxie.zc.model_comm.util;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class RxUtils {
    public void test(){
//        Observable.create(new ObservableOnSubscribe<List>() {
//            @Override
//            public void subscribe(ObservableEmitter<List> emitter) throws Exception {
//                List drawableRes = new ArrayList<>(); //.......从数据库中取出id资源数组操作
//                emitter.onNext(drawableRes);
//                emitter.onComplete();
//            }
//        }).flatMap(new Function<List, ObservableSource<Integer>>() {
//            @Override
//            public ObservableSource<Integer> apply(List list) throws Exception {
//                return Observable.fromIterable(list);
//            }
//        }).subscribeOn(Schedulers.io())//在IO线程执行数据库处理操作
//                .observeOn(AndroidSchedulers.mainThread())//在UI线程显示图片
//                .subscribe(new Observer<Integer>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        Log.d("----","onSubscribe");
//                    }
//
//                    @Override
//                    public void onNext(Integer integer) {
////                        imageView.setImageResource(integer);//拿到id,加载图片
//                        Log.d("----",integer+"");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.d("----",e.toString());
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.d("----","onComplete");
//                    }
//                });
    }
    public void simpleTest(){
//        Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
////              //   do working
//
////                emitter.onNext(showText);
//            }
//        }).subscribeOn(Schedulers.io())//在IO线程执行数据库处理操作
//                .observeOn(AndroidSchedulers.mainThread())//在UI线程显示图片
//                .subscribe(new Consumer<String>() {
//                    @Override
//                    public void accept(String str) throws Exception {
////                   // do working
//                    }
//                });
    }
}
