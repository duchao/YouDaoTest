package com.youdao.test.ui.activity;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.widget.Toolbar;
import android.widget.RadioGroup;

import com.youdao.test.R;
import com.youdao.test.base.BaseActivity;
import com.youdao.test.ui.fragemnt.FragmentController;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.rg_main_tab)
    RadioGroup mFooterTab;

    private FragmentController mFragmentController;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initEventAndaData() {
        mFragmentController = new FragmentController(this, R.id.fl_main_content);
        mFooterTab.setOnCheckedChangeListener(new MainCheckedChangeListener());
        mFragmentController.showFragment(FragmentController.MAIN_FRAGMENT_TAB);

    }

    private class MainCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkedId) {
            switch (checkedId) {
                case R.id.rb_main_first:
                    mFragmentController.showFragment(FragmentController.MAIN_FRAGMENT_TAB);
                    break;
                case R.id.rb_main_video:
                    mFragmentController.showFragment(FragmentController.VIDEO_FRAGMENT_TAB);
                    break;
                case R.id.rb_main_min_top:
                    mFragmentController.showFragment(FragmentController.MIN_TOP_FRAGMENT_TAB);
                    break;
                case R.id.rb_main_mine:
                    mFragmentController.showFragment(FragmentController.MINE_FRAGMENT_TAB);
                    break;
                default:
                    break;
            }
        }
    }
}
