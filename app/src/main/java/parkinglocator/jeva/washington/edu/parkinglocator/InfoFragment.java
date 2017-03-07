package parkinglocator.jeva.washington.edu.parkinglocator;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class InfoFragment extends Fragment {
    private CarObject car;

    public InfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);


        Button submit = (Button) view.findViewById(R.id.button2);

        View.OnClickListener buttonListener = new View.OnClickListener() {
            public void onClick(View v) {
                EditText make = (EditText) v.getRootView().findViewById(R.id.editText1);
                EditText model = (EditText) v.getRootView().findViewById(R.id.editText2);
                EditText year = (EditText)v.getRootView().findViewById(R.id.editText4);
                EditText color = (EditText) v.getRootView().findViewById(R.id.editText);


//                DatabaseReference user = database.getReference("user");
//                user.setValue("User1!");
//                FirebaseDatabase userDatabase = user.getDatabase();
//
//                DatabaseReference userMake = userDatabase.getReference("make");
//                userMake.setValue( make.getText().toString());
//
//                DatabaseReference refModel = userDatabase.getReference("model");
//                refModel.setValue( model.getText().toString());
//
//                DatabaseReference refYear = userDatabase.getReference("year");
//                refYear.setValue( year.getText().toString());
//
//                DatabaseReference refColor = userDatabase.getReference("color");
//                refColor.setValue(color.getText().toString());
//                String id = android.telephony.TelephonyManager.getDeviceId();
                writeUserData(getDeviceId(v.getContext()),make.getText().toString(),model.getText().toString(),year.getText().toString(),color.getText().toString());


            }
        };


        view.findViewById(R.id.button2).setOnClickListener(buttonListener);
        // Inflate the layout for this fragment
        return view;
    }

    public static void writeUserData(String userId, String make, String model, String year, String color){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference user = database.getReference("user");

        user.child("users").child(userId).child("make").setValue(make);
        user.child("users").child(userId).child("model").setValue(model);
        user.child("users").child(userId).child("year").setValue(year);
        user.child("users").child(userId).child("color").setValue(color);
    }
    public String getDeviceId(Context context){
        TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }

}
