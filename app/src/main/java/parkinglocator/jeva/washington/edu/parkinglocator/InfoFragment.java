package parkinglocator.jeva.washington.edu.parkinglocator;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
    private ArrayList<Map<String, String>> cars;
    private String lan = "";
    private String lon = "";
    private String details = "";

    public InfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_info, container, false);

        final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        cars = new ArrayList<Map<String, String>>();

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
                Map<String, ArrayList<Map<String, String>>> td = (HashMap<String,ArrayList<Map<String, String>>>) dataSnapshot.getValue();

                ArrayList<String> Final = new ArrayList<String>();

                cars = new ArrayList<Map<String, String>>();



                if (td.get(id) != null){
                    cars = td.get(id);
                }else{
                    lan = "000000";
                    lon = "000000";
                    details= "NO details to show";


                }
                //Go through each map in list.
                //get all the

                for (Map<String, String> map : cars) {
                    String Value = "";
                    for (String key : map.keySet()) {
                        if(key.equals("make")) {
                            Value += " Make: " + map.get(key);
                        } else if (key.equals("color")) {
                            Value += " Color: " + map.get(key);
                        } else if (key.equals("model")){
                            Value += " Model: " + map.get(key);
                        } else if (key.equals("year")) {
                            Value += " Year: " + map.get(key);
                        }else if (key.equals("lan")) {
                            lan = key;
                        }else if (key.equals("lon")) {
                           lon = key;
                        }else if (key.equals("details")) {
                            details = key;
                        }
                    }
                    Final.add(Value);
                }


                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, Final);

                ListView listView = (ListView) view.findViewById(R.id.list_view);

                listView.setAdapter(adapter);

                AdapterView.OnItemClickListener clickListener = new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                        Intent intent = new Intent(view.getContext(), FindActivity.class);
                        intent.putExtra("lan", lan);
                        intent.putExtra("lon", lon);
                        intent.putExtra("details", details);
                        startActivity(intent);
                    }
                };
                listView.setOnItemClickListener(clickListener);
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
