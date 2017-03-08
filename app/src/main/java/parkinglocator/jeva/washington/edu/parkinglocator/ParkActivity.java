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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

        databasePull.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, ArrayList<Map<String, String>>> td = (HashMap<String,ArrayList<Map<String, String>>>) dataSnapshot.getValue();
                ArrayList<Map<String, String>> cars = new ArrayList<Map<String, String>>();
                cars = td.get(userId);

                for (Map<String, String> map : cars) {
                   count++;
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }

            });

        user.child("" +count).child("make").setValue(make);
        user.child("" +count).child("model").setValue(model);
        user.child("" +count).child("year").setValue(year);
        user.child("" +count).child("color").setValue(color);
        user.child("" +count).child("details").setValue(details);
        user.child("" +count).child("lat").setValue("" + lat);
        user.child("" +count).child("lon").setValue("" + lon);

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
