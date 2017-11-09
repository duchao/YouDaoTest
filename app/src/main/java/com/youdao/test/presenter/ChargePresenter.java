package com.youdao.test.presenter;

import android.content.Context;
import android.os.Vibrator;
import com.youdao.test.R;
import com.youdao.test.base.RxPresenter;
import com.youdao.test.model.http.HttpManager;
import com.youdao.test.model.http.bean.ChargeBean;
import com.youdao.test.model.http.bean.ChargeInfoBean;
import com.youdao.test.model.http.bean.GunBean;
import com.youdao.test.model.http.request.ChargeBookRequest;
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

    public static final int GUN_STATE_BOOK = 1;

    public static final int GUN_STATE_FREE = 2;

    public static final int CHARGE_TYPE_AC = 2;

    public static final int CHARGE_TYPE_DC = 1;

    public static final int CHARGE_TYP_ALL = 0;

    public static final int CONFIG_AC_CHARGE = 0;

    public static final int CONFIG_DC_CHARGE = 1;

    public static final int CONFIG_ALL_CHARGE = 2;

    public static final int BOOK_MODE_MANAUL = 1;

    public static final int BOOK_MODE_AUTO = 2;

    private int mConfig = CONFIG_ALL_CHARGE;

    private int mTotalRefreshCount = 0;

    private Context mContext;

    private Disposable mStartRefreshSubscription;

    private Vibrator mVibrator;

    private int mBookMode = BOOK_MODE_MANAUL;

    private List<ChargeBean> mFreeChargeList;

    private boolean mBookSuccess = false;

    public ChargePresenter(Context context) {
        mContext = context;
    }

    @Override
    public void updateConfigInfo(int config) {
        mConfig = config;
    }

    @Override
    public void startRefresh() {
        mStartRefreshSubscription = Flowable.interval(1, TimeUnit.SECONDS).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                requestChargeInfo();
            }
        });
        addSubscribe(mStartRefreshSubscription);
    }

    @Override
    public void stopRefresh() {
        if (mStartRefreshSubscription != null) {
            mStartRefreshSubscription.dispose();
        }
        stopVibrator();
    }

    private void startVibrator() {
        if (mVibrator == null) {
            mVibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
        }
        if (mVibrator != null) {
            if (mVibrator.hasVibrator()) {
                mVibrator.cancel();
            }
            long[] pattern = {100, 400, 100, 400}; // 停止 开启 停止 开启
            mVibrator.vibrate(pattern, 2); //重复两次上面的pattern 如果只想震动一次，index设为-1
        }
    }

    private void stopVibrator() {
        if (mVibrator != null && mVibrator.hasVibrator()) {
            mVibrator.cancel();
            mVibrator = null;
        }
    }

    @Override
    public void udpateBookMode(int bookMode) {
        mBookMode = bookMode;
    }

    public void requestChargeInfo() {
        HttpManager.getInstance().json(new ChargeInfoRequest() {
            @Override
            protected void onStart() {
                super.onStart();
                mTotalRefreshCount++;
                mBookSuccess = false;
            }

            @Override
            public void onSucceed(ChargeInfoBean response) {
                String chargeInfo = handleChargeInfo(response);
                mView.updateChargeInfo(chargeInfo);
            }

            @Override
            public void onFailed(Throwable t) {
                LogUtils.e("ChargePresenter", t.toString());
            }

            @Override
            public void onComplete() {
                if (mFreeChargeList != null && mFreeChargeList.size() > 0) {
                    stopRefresh();
                    if (mBookMode == BOOK_MODE_MANAUL) {
                        // 开始震动
                        startVibrator();
                    } else if (mBookMode == BOOK_MODE_AUTO) {
                        // 开始预约
                        if (!mBookSuccess) {
                            bookeCharge(mFreeChargeList.get(0));
                        }
                    }
                } else {
                    stopVibrator();
                }
            }
        });
    }

    private String handleChargeInfo(ChargeInfoBean chargeInfo) {
        if (chargeInfo == null || chargeInfo.getChargeList() == null) {
            return "";
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
        return sb.toString();
    }

    private String handleCharge(List<ChargeBean> chargeList) {
        String chargeInfo = "";
        if (chargeList == null) {
            return chargeInfo;
        }
        int size = chargeList.size();
        if (mFreeChargeList != null) {
            mFreeChargeList.clear();
        }
        if (size == 0) {
            chargeInfo = mContext.getString(R.string.charge_no_free_charge);
        } else if (size > 0) {
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < size; i++) {
                ChargeBean chargeBean = chargeList.get(i);
                sb.append(chargeBean.getChargeName());
                if (i < size - 1) {
                    sb.append(",");
                }
                if (mFreeChargeList == null) {
                    mFreeChargeList = new ArrayList<ChargeBean>();
                }
                mFreeChargeList.add(chargeBean);
            }
            chargeInfo = mContext.getString(R.string.charge_free_charges, sb.toString());
        }
        return chargeInfo;
    }

    private void bookeCharge(final ChargeBean chargeBean) {
        if (chargeBean == null) {
            return;
        }
        HttpManager.getInstance().json(new ChargeBookRequest(chargeBean.getGunList().get(0).getGunNum(), chargeBean.getChargeId()) {

            @Override
            public void onSucceed(Object response) {
                mBookSuccess = true;
                mView.updateChargeInfo(chargeBean.getChargeName() + mContext.getString(R.string.charge_book_success));
                startVibrator();
            }

            @Override
            public void onFailed(Throwable t) {
                mView.updateChargeInfo(mContext.getString(R.string.charge_book_failed));
            }
        });
    }

}
