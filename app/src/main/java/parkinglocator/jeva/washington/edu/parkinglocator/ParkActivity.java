package parkinglocator.jeva.washington.edu.parkinglocator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ParkActivity extends AppCompatActivity {
    private Location mLocation;
    private int carCount;
    private String id;
    private  int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_park);

        carCount = getIntent().getExtras().getInt("count");
        mLocation = getIntent().getExtras().getParcelable("location");

        Toolbar toolbar = (Toolbar) findViewById(R.id.parkToolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_action_cancel);
        ab.setDisplayHomeAsUpEnabled(true);

        Button btn = (Button) findViewById(R.id.btn_park);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText make = (EditText) v.getRootView().findViewById(R.id.input_manufacturer);
                EditText model = (EditText) v.getRootView().findViewById(R.id.input_model);
                EditText year = (EditText)v.getRootView().findViewById(R.id.input_year);
                EditText color = (EditText) v.getRootView().findViewById(R.id.input_color);
                EditText details = (EditText) v.getRootView().findViewById(R.id.input_details);
                double lat;
                double lon;
                try{
                    lat = mLocation.getLatitude();
                    lon = mLocation.getLongitude();
                }
                catch(java.lang.NullPointerException e){
                    lat = 0.0;
                    lon = 0.0;
                }
                
                String id;

                try {
                    id = getDeviceId(v.getContext());
                }
                catch(java.lang.SecurityException e){
                    id = "Emulator";

                }
                writeUserData(getDeviceId(v.getContext()), carCount, make.getText().toString(),
                        model.getText().toString(),year.getText().toString(),color.getText().toString(),
                        details.getText().toString(), lat, lon);

                Toast.makeText(v.getContext(),"Car information submitted and saved!", Toast.LENGTH_SHORT).show();

                Intent returnIntent = getIntent();
                returnIntent.putExtra("location", mLocation);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
    }

    public void writeUserData(final String userId, int car, String make, String model, String year,
                                     String color, String details, double lat, double lon){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference databasePull = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference user = database.getReference(userId);


        user.child("" + carCount).child("make").setValue(make);
        user.child("" + carCount).child("model").setValue(model);
        user.child("" + carCount).child("year").setValue(year);
        user.child("" + carCount).child("color").setValue(color);
        user.child("" + carCount).child("details").setValue(details);
        user.child("" + carCount).child("lat").setValue("" + lat);
        user.child("" + carCount).child("lon").setValue("" + lon);

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
