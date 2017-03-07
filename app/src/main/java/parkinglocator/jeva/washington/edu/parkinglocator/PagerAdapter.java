package parkinglocator.jeva.washington.edu.parkinglocator;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

public class PagerAdapter extends FragmentStatePagerAdapter {
    SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();
    int tabCount;

    public PagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                return new MapFragment();
            case 1:
                return new SavedLocationsFragment();
            case 2:
                return new InfoFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    public Fragment getRegisteredFragment(int position) {
        return registeredFragments.get(position);
    }
}
