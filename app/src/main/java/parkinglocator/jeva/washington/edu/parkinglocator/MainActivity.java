package parkinglocator.jeva.washington.edu.parkinglocator;

import android.app.Fragment;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TabLayout tabLayout;
    private ViewPager mPager;
    private PagerAdapter mAdapter;
    private StorageReference mStorageRef;
    private int mCurrentTab;
    public static final String TAG = "MainActivity";
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
                int i = tab.getPosition();
                mPager.setCurrentItem(i);
                mCurrentTab = i;
                Log.i(TAG, "current tab: " + mCurrentTab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        FloatingActionButton fabPark = (FloatingActionButton) findViewById(R.id.myLocationButton);
        fabPark.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (mCurrentTab == 0) {
            MapFragment mFragment = (MapFragment) mAdapter.getRegisteredFragment(0);
            startActivityForResult(new Intent()
                            .setClass(getApplicationContext(), ParkActivity.class)
                            .putExtra("location", mFragment.getCurrentLocation()),
                    MainActivity.LOCATION_REQUEST
            );
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // check which request we're responding to
        if (requestCode == LOCATION_REQUEST) {
            // make sure the request was successful
            if (resultCode == RESULT_OK) {
                MapFragment fragment = (MapFragment) mAdapter.getRegisteredFragment(0);
                Location location = data.getExtras().getParcelable("location");
                fragment.markCurrentLocation(this,
                        new LatLng(location.getLatitude(),location.getLongitude()));
            }
        }
    }
}
