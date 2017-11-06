package com.youdao.test.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.youdao.test.model.http.BaseResponse;
import com.youdao.test.model.http.ExceptionHandle;

import org.reactivestreams.Publisher;

import java.lang.reflect.Type;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * Created by duchao on 2017/10/25.
 */

public class RxUtils {

    public static <T> Flowable<T> createData(final T t) {
        return Flowable.create(new FlowableOnSubscribe<T>() {
            @Override
            public void subscribe(FlowableEmitter<T> emitter) throws Exception {
                try {
                    emitter.onNext(t);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        }, BackpressureStrategy.BUFFER);
    }

    public static FlowableTransformer rxScheduler() {
        return new FlowableTransformer() {
            @Override
            public Publisher apply(@NonNull Flowable upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

//
//    public static <T> FlowableTransformer<T, T> scheduler() {
//        return new FlowableTransformer<T, T>() {
//            @Override
//            public Publisher<T> apply(@NonNull Flowable<T> upstream) {
//                return upstream.subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread());
//            }
//        };
//    }

//    public static  FlowableTransformer<ResponseBody, BaseResponse> transformer() {
//        return  new FlowableTransformer<ResponseBody, BaseResponse>() {
//            @Override
//            public Publisher<BaseResponse> apply(@NonNull Flowable<ResponseBody> upstream) {
//                return upstream.map(new Function<ResponseBody, BaseResponse>() {
//                    @Override
//                    public BaseResponse apply(@NonNull ResponseBody responseBodyResponse) throws Exception {
//                        final BaseResponse bean = gson.fromJson(str.toString(),objectType);
//                        return bean;
//                    }
//                });
//            }
//        };
//    }

    // 统一处理返回结果
    public static <T> FlowableTransformer<T, T> handleResult() {
        return new FlowableTransformer<T, T>() {
            @Override
            public Flowable<T> apply(@NonNull Flowable<T> upstream) {
                return upstream.onErrorResumeNext(new HandleErrorFunction<T>());
            }
        };
    }

//    private static class HandleResultFunction<T> implements Function<BaseResponse<T>, T> {
//
//        @Override
//        public T apply(@NonNull BaseResponse<T> response) throws Exception {
//            if (!response.isOk())
//                throw new RuntimeException((response.getCode() + "" + response.getMessage()).equals("") ? "" : response.getMessage());
//            return response.getData();
//        }
//    }

    private static class HandleErrorFunction<T> implements Function<Throwable, Flowable<T>> {
        @Override
        public Flowable<T> apply(@NonNull Throwable throwable) throws Exception {
            return Flowable.error(ExceptionHandle.handleException(throwable));
        }
    }
}

