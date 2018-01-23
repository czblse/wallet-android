package io.spaco.wallet.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;
import io.spaco.wallet.fragments.PinSetFragment;
import io.spaco.wallet.fragments.VerifyPinSetFragment;

/**
 * Pin界面的viewpager适配器
 * Created by kimi on 2018/1/23.
 */

public class PinSetAdapter extends FragmentPagerAdapter {

    SparseArray<Fragment> fragments = new SparseArray();

    public PinSetAdapter(FragmentManager fm) {
        super(fm);
        fragments.put(0, PinSetFragment.newInstance(null));
        fragments.put(1, VerifyPinSetFragment.newInstance(null));
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
