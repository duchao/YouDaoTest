package com.youdao.test.model.http;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.subscribers.DisposableSubscriber;
import okhttp3.ResponseBody;

/**
 * Created by duchao on 2017/10/23.
 */

public abstract class HttpRequest<T> extends DisposableSubscriber<ResponseBody> {

    private String mUrl;

    private Map<String, String> mParams;

    private HttpRequest(Map<String, String> params) {
        mUrl = initUrl();
        mParams = initParams(params);
    }

    public HttpRequest() {
        this(new HashMap<String, String>());
    }

    public String getUrl() {
        return mUrl;
    }

    public Map<String, String> getParams() {
        return mParams;
    }

    protected abstract Map initParams(Map<String, String> map);

    protected abstract String initUrl();

    public abstract Class<?> getBeanClass();

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onNext(ResponseBody t) {
        try {
            byte[] bytes = t.bytes();
            String jsStr = new String(bytes);
            int code = 1;
            String msg = "";
            String dataStr = "";
            T dataResponse = null;
            BaseResponse<T> baseResponse = null;
            JSONObject jsonObject = new JSONObject(jsStr.trim());
            code = jsonObject.optInt("code");
            msg = jsonObject.optString("msg");
            baseResponse = new BaseResponse<>();
            baseResponse.setCode(code);
            baseResponse.setMessage(msg);
            dataStr = jsonObject.opt("data").toString();
            if (dataStr.isEmpty()) {
                dataStr = jsonObject.optString("result");
            }
            if (dataStr.isEmpty()) {
                baseResponse.setData(null);
            }
            dataStr = jsonObject.optJSONObject("data").toString();
            Gson gson = new Gson();
            dataResponse = (T) gson.fromJson(dataStr, getBeanClass());
            if (dataResponse != null) {
                baseResponse.setData(dataResponse);
            }
            onSucceed(dataResponse);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void onSucceed(T response) {
    }

    @Override
    public void onError(Throwable t) {
        onFailed(t);
    }

    public void onFailed(Throwable t) {

    }

    @Override
    public void onComplete() {

    }

}
