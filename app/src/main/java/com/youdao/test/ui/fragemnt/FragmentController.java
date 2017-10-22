package com.youdao.test.ui.fragemnt;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import com.youdao.test.R;

import java.util.ArrayList;

/**
 * Created by duchao on 2017/10/22.
 */

public class FragmentController {

    public static final int MAIN_FRAGMENT_TAB = 0;

    public static final int VIDEO_FRAGMENT_TAB = 1;

    public static final int MIN_TOP_FRAGMENT_TAB = 2;

    public static final int MINE_FRAGMENT_TAB = 3;

    private int mContainerId;

    private FragmentManager mFragmentManger;

    private ArrayList<Fragment> mFragments;

    private Fragment mCurrentFragment;

    public FragmentController(Activity activity, int containerId) {
        mContainerId = containerId;
        mFragmentManger = activity.getFragmentManager();
        initFragment();
    }

    private void initFragment() {
        mFragments = new ArrayList<>();
        mFragments.add(new MainFragment());
        mFragments.add(new VideoFragment());
        mFragments.add(new MinTopFragment());
        mFragments.add(new MineFragment());
    }

    public void showFragment(int index) {
        FragmentTransaction ft = mFragmentManger.beginTransaction();
        if (mCurrentFragment != null) {
            ft.hide(mCurrentFragment);
        }
        Fragment fragment = mFragmentManger.findFragmentByTag(mFragments.get(index).getClass().getName());
        if (null == fragment) {
            fragment = mFragments.get(index);
        }
        mCurrentFragment = fragment;
        if (!fragment.isAdded()) {
            ft.add(mContainerId, fragment, fragment.getClass().getName());
        } else {
            ft.show(fragment);
        }
        ft.commit();
    }

    public Fragment getFragment(int position) {
        return mFragments.get(position);
    }

}
