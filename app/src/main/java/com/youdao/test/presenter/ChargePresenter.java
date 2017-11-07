package com.youdao.test.presenter;

import android.content.Context;
import android.os.Vibrator;

import com.youdao.test.R;
import com.youdao.test.base.RxPresenter;
import com.youdao.test.model.http.HttpManager;
import com.youdao.test.model.http.bean.ChargeBean;
import com.youdao.test.model.http.bean.ChargeInfoBean;
import com.youdao.test.model.http.bean.GunBean;
import com.youdao.test.model.http.request.ChargeInfoRequest;
import com.youdao.test.presenter.contract.ChargeContract;
import com.youdao.test.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by duchao on 2017/11/7.
 */

// AC 为交流充电桩，慢充桩, DC为直流充电桩,快充桩
public class ChargePresenter extends RxPresenter<ChargeContract.View> implements ChargeContract.Presenter {

    public static final int GUN_STATE_CHARGED = 0;

    public static final int GUN_STATE_SERVED = 1;

    public static final int GUN_STATE_FREE = 2;

    public static final int CHARGE_TYPE_AC = 2;

    public static final int CHARGE_TYPE_DC = 1;

    public static final int CHARGE_TYP_ALL = 0;

    public static final int CONFIG_AC_CHARGE = 0;

    public static final int CONFIG_DC_CHARGE = 1;

    public static final int CONFIG_ALL_CHARGE = 2;

    private int mConfig = CONFIG_ALL_CHARGE;

    private int mTotalRefreshCount = 0;

    private Context mContext;

    private Disposable mStartRefreshSubscription;

    private Vibrator mVibrator;

    public ChargePresenter(Context context) {
        mContext = context;
    }

    @Override
    public void updateConfigInfo(int config) {
        mConfig = config;
    }

    @Override
    public void startRefresh() {
        if (mStartRefreshSubscription == null) {
            mStartRefreshSubscription = Flowable.interval(1, TimeUnit.SECONDS).subscribe(new Consumer<Long>() {
                @Override
                public void accept(Long aLong) throws Exception {
                    requestChargeInfo();
                }
            });
            addSubscribe(mStartRefreshSubscription);
        }
    }

    @Override
    public void stopRefresh() {
        mStartRefreshSubscription.dispose();
        mStartRefreshSubscription = null;
        mVibrator.cancel();
        mVibrator = null;
    }

    public void requestChargeInfo() {
        HttpManager.getInstance().json(new ChargeInfoRequest() {
            @Override
            protected void onStart() {
                super.onStart();
                mTotalRefreshCount++;
                mVibrator = (Vibrator)mContext.getSystemService(Context.VIBRATOR_SERVICE);
            }

            @Override
            public void onSucceed(ChargeInfoBean response) {
                handleChargeInfo(response);
            }

            @Override
            public void onFailed(Throwable t) {
                LogUtils.e("ChargePresenter", t.toString());
            }
        });
    }

    private void handleChargeInfo(ChargeInfoBean chargeInfo) {
        if (chargeInfo == null || chargeInfo.getChargeList() == null) {
            return;
        }
        List<ChargeBean> freeAcCharge = new ArrayList<ChargeBean>();
        List<ChargeBean> freeDcCharge = new ArrayList<ChargeBean>();
        List<ChargeBean> freeCharge = new ArrayList<ChargeBean>();
        for (ChargeBean charge : chargeInfo.getChargeList()) {
            List<GunBean> gunList = charge.getGunList();
            boolean isFreeCharge = false;
            if (gunList != null && gunList.size() > 0) {
                GunBean gunBean = gunList.get(0);
                if (gunBean.getGunState() == GUN_STATE_FREE) {
                    isFreeCharge = true;
                }
            }
            if (isFreeCharge) {
                if (charge.getChargeType() == CHARGE_TYPE_AC) {
                    freeAcCharge.add(charge);
                } else if (charge.getChargeType() == CHARGE_TYPE_DC) {
                    freeDcCharge.add(charge);
                }
                freeCharge.add(charge);
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append(mContext.getString(R.string.charge_refreshing, mTotalRefreshCount));
        sb.append("\n");
        sb.append("\n");
        if (mConfig == CONFIG_ALL_CHARGE) {
            sb.append(handleCharge(freeCharge));
        } else if (mConfig == CONFIG_AC_CHARGE) {
            sb.append(handleCharge(freeAcCharge));
        } else if (mConfig == CONFIG_DC_CHARGE) {
            sb.append(handleCharge(freeDcCharge));
        }
        mView.updateChargeInfo(sb.toString());
    }

    private String handleCharge(List<ChargeBean> chargeList) {
        String chargeInfo = "";
        if (chargeList == null) {
            return chargeInfo;
        }
        int size = chargeList.size();
        if (size == 0) {
            chargeInfo = mContext.getString(R.string.charge_no_free_charge);
        } else if (size > 0) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < size; i++) {
                sb.append(chargeList.get(i).getChargeName());
                if ( i < size - 1) {
                    sb.append(",");
                }
            }
            chargeInfo = mContext.getString(R.string.charge_free_charges, sb.toString());
            if (mVibrator != null) {
                long [] pattern = {100,400,100,400}; // 停止 开启 停止 开启
                mVibrator.vibrate(pattern,2); //重复两次上面的pattern 如果只想震动一次，index设为-1
            }
        }
        return chargeInfo;
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mVibrator != null && mVibrator.hasVibrator()) {
            mVibrator.cancel();
            mVibrator = null;
        }
    }
}
