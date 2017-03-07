package parkinglocator.jeva.washington.edu.parkinglocator;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MainActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager mPager;
    private PagerAdapter mAdapter;
    private StorageReference mStorageRef;
    public static final String TAG = "MainActivity";
    public static final String EXTRA_LOCATION = "edu.washington.gjdevera.quizdroid.LOCATION";
    public static final int LOCATION_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mStorageRef = FirebaseStorage.getInstance().getReference();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // initializing the tablayout
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_park)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_saved)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.car_info)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        mPager = (ViewPager) findViewById(R.id.pager);
        mAdapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        mPager.setAdapter(mAdapter);
        mPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // check which request we're responding to
        if (requestCode == LOCATION_REQUEST) {
            // make sure the request was successful
            if (resultCode == RESULT_OK) {
                MapFragment fragment = (MapFragment) getSupportFragmentManager().findFragmentByTag(
                        "android:switcher:"+R.id.pager+":0");
                fragment.markCurrentLocation(this, fragment.getCurrentLocation());
            }
        }
    }
}
