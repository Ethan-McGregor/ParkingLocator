package parkinglocator.jeva.washington.edu.parkinglocator;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

public class FindActivity extends AppCompatActivity {
    private int lan;
    private int lon;
    private String details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);

        Intent intent = getIntent();
        lan = intent.getIntExtra("lan", 0);
        lon = intent.getIntExtra("lon", 0);
        details = intent.getStringExtra("details");
        Log.i(MainActivity.TAG, "" + lan + " " + lon + " " + details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.findToolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setTitle(getString(R.string.title_activity_find));
        ab.setHomeAsUpIndicator(R.drawable.ic_action_cancel);
        ab.setDisplayHomeAsUpEnabled(true);

        MapFragment mf = new MapFragment();
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.fragment_placeholder, mf);
        tx.commit();
    }
}
