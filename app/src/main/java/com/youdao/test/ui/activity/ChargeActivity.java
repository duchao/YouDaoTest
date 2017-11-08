package com.youdao.test.ui.activity;

import android.support.annotation.IdRes;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.youdao.test.R;
import com.youdao.test.base.BaseMvpActivity;
import com.youdao.test.presenter.ChargePresenter;
import com.youdao.test.presenter.contract.ChargeContract;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by duchao on 2017/11/7.
 */

public class ChargeActivity extends BaseMvpActivity<ChargePresenter> implements ChargeContract.View {

    @BindView(R.id.tv_charge_info)
    TextView mChargeInfo;

    @BindView(R.id.view_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.btn_start_refresh)
    Button mStartRefresh;

    @BindView(R.id.rg_charge_select)
    RadioGroup mChargeSelect;

    @BindView(R.id.btn_stop_refresh)
    Button mStopRefresh;

    @BindView(R.id.rg_charge_book)
    RadioGroup mChargeBookMode;

    @Override
    protected ChargePresenter initPresenter() {
        return new ChargePresenter(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_charge;
    }

    @Override
    protected void initEventAndaData() {
        setToolbar(mToolbar, R.string.charge_netease_charge);
        mChargeSelect.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkId) {
                switch (checkId) {
                    case R.id.rb_charge_ac:
                        mPresenter.updateConfigInfo(ChargePresenter.CONFIG_AC_CHARGE);
                        break;
                    case R.id.rb_charge_dc:
                        mPresenter.updateConfigInfo(ChargePresenter.CONFIG_DC_CHARGE);
                        break;
                    case R.id.rb_charge_all:
                        mPresenter.updateConfigInfo(ChargePresenter.CONFIG_ALL_CHARGE);
                        break;
                    default:
                        break;
                }

            }
        });
        mChargeBookMode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkId) {
                switch (checkId) {
                    case R.id.rb_charge_manual_book:
                        mPresenter.udpateBookMode(ChargePresenter.BOOK_MODE_MANAUL);
                        break;
                    case R.id.rb_charge_auto_book:
                        mPresenter.udpateBookMode(ChargePresenter.BOOK_MODE_AUTO);
                        break;
                    default:
                        break;
                }
            }
        });
        mStartRefresh.setEnabled(true);
        mStopRefresh.setEnabled(false);
    }

    @Override
    public void updateChargeInfo(String info) {
        mChargeInfo.setText(info);
    }

    @OnClick(R.id.btn_start_refresh)
    protected void startRefresh() {
        mPresenter.startRefresh();
        mStartRefresh.setEnabled(false);
        mStopRefresh.setEnabled(true);
    }

    @OnClick(R.id.btn_stop_refresh)
    protected void stopRefresh() {
        mPresenter.stopRefresh();
        mStopRefresh.setEnabled(false);
        mStartRefresh.setEnabled(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
