package parkinglocator.jeva.washington.edu.parkinglocator;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class ParkActivity extends AppCompatActivity {
    private Location mLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_park);

        mLocation = getIntent().getExtras().getParcelable(MainActivity.EXTRA_LOCATION);

        Toolbar toolbar = (Toolbar) findViewById(R.id.parkToolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_action_cancel);
        ab.setDisplayHomeAsUpEnabled(true);

        Button btn = (Button) findViewById(R.id.btn_park);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = getIntent();
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
    }

}
