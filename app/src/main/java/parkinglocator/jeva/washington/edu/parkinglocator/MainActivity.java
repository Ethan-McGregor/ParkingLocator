package parkinglocator.jeva.washington.edu.parkinglocator;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TabLayout tabLayout;
    private ViewPager mPager;
    private PagerAdapter mAdapter;
    private StorageReference mStorageRef;
    private int mCurrentTab;
    public static final String TAG = "MainActivity";
    public static final int LOCATION_REQUEST = 1;
    private int carCount = 0;

    private ArrayList<CarObject> FINALCARLIST;

    private String lat = "";
    private String lon = "";
    private String details = "";


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
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.car_info)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final FloatingActionButton fabPark = (FloatingActionButton) findViewById(R.id.myLocationButton);
        fabPark.setOnClickListener(this);

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
                if (mCurrentTab == 0)
                    fabPark.setVisibility(View.VISIBLE);
                else
                    fabPark.setVisibility(View.GONE);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        final DatabaseReference database = FirebaseDatabase.getInstance().getReference();

        database.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String id;
                try {
                    id = getDeviceId(getApplicationContext());
                } catch (java.lang.SecurityException e) {
                    id = "Emulator";
                }
                if (dataSnapshot != null && dataSnapshot.getValue() != null) {
                    Map<String, ArrayList<Map<String, String>>> td = (HashMap<String, ArrayList<Map<String, String>>>) dataSnapshot.getValue();

                    ArrayList<String> Final = new ArrayList<String>();

                    ArrayList<Map<String, String>> cars = new ArrayList<Map<String, String>>();

                    FINALCARLIST = new ArrayList<CarObject>();


                    if (td.get(id) != null) {
                        cars = td.get(id);
                    } else {
                        lat = "000000";
                        lon = "000000";
                        details = "NO details to show";


                    }
                    //Go through each map in list.
                    //get all the

                    for (Map<String, String> map : cars) {
                        CarObject temp = new CarObject();
                        for (String key : map.keySet()) {
                            if (key.equals("make")) {
                                temp.setMake(map.get(key));
                            } else if (key.equals("color")) {
                                temp.setColor(map.get(key));
                            } else if (key.equals("model")) {
                                temp.setModel(map.get(key));
                            } else if (key.equals("year")) {
                                temp.setYear(map.get(key));
                            } else if (key.equals("lat")) {
                                temp.setLat(map.get(key));
                            } else if (key.equals("lon")) {
                                temp.setLon(map.get(key));
                            } else if (key.equals("details")) {
                                temp.setDetails(map.get(key));
                            }
                        }
                        FINALCARLIST.add(temp);

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_help:
                // open help screen
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onClick(View v) {
        switch (mCurrentTab) {
            case 0:
                MapFragment mFragment = (MapFragment) mAdapter.getRegisteredFragment(0);
                startActivityForResult(new Intent()
                    .setClass(getApplicationContext(), ParkActivity.class)
                    .putExtra("location", mFragment.getCurrentLocation())
                    .putExtra("count", carCount++)
                    .putParcelableArrayListExtra("carList", FINALCARLIST),
                    MainActivity.LOCATION_REQUEST
                );
                break;
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
                double lat;
                double lon;
                try{
                    lat = location.getLatitude();
                    lon = location.getLongitude();
                }
                catch(java.lang.NullPointerException e){
                    lat = 0.0;
                    lon = 0.0;
                }
                fragment.markCurrentLocation(this,
                        new LatLng(lat,lon));
            }
        }
    }

    public String getDeviceId(Context context){
        TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        String id;
        try {
            id = telephonyManager.getDeviceId();
        }
        catch(java.lang.SecurityException e){
            id = "Emulator";
        }
        return id;
    }
}
