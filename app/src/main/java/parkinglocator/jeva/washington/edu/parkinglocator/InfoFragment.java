package parkinglocator.jeva.washington.edu.parkinglocator;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class InfoFragment extends Fragment {
    private  ArrayList<String> cars;


    public InfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_info, container, false);

        final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        cars = new ArrayList<String>();

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String id;
                try {
                    id = getDeviceId(view.getContext());
                }
                catch(java.lang.SecurityException e){
                    id = "Emulator";

                }
                Map<String, ArrayList<String>> td = (HashMap<String,ArrayList<String>>) dataSnapshot.getValue();

                cars = new ArrayList<String>();
                cars = td.get(id);
                //List<String> values = td.values();
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, cars);

                ListView listView = (ListView) view.findViewById(R.id.list_view);

                listView.setAdapter(adapter);

        }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }


        });
        return view;
    }

    public String getDeviceId(Context context){
        TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }



}
