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
    private ArrayList<CarObject> FINALCARLIST;
    private String lat = "";
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

        database.addListenerForSingleValueEvent(new ValueEventListener() {

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

                FINALCARLIST = new ArrayList<CarObject>();


                if (td.get(id) != null){
                    cars = td.get(id);
                }else{
                    lat = "000000";
                    lon = "000000";
                    details= "NO details to show";


                }
                //Go through each map in list.
                //get all the

                for (Map<String, String> map : cars) {
                    CarObject temp = new CarObject();
                    for (String key : map.keySet()) {
                        if(key.equals("make")) {
                            temp.setMake(map.get(key));
                        } else if (key.equals("color")) {
                            temp.setColor(map.get(key));
                        } else if (key.equals("model")){
                            temp.setModel(map.get(key));
                        } else if (key.equals("year")) {
                            temp.setYear(map.get(key));
                        } else if (key.equals("lat")) {
                            temp.setLat(map.get(key));
                        }else if (key.equals("lon")) {
                            temp.setLon(map.get(key));
                        }else if (key.equals("details")) {
                            temp.setDetails(map.get(key));
                        }
                    }
                    FINALCARLIST.add(temp);
                    Final.add("Make: " + temp.getMake() + " Model: " + temp.getModel() +
                            " Color: " + temp.getColor() + " Year: " + temp.getYear());
                }


                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, Final);

                ListView listView = (ListView) view.findViewById(R.id.list_view);

                listView.setAdapter(adapter);

                AdapterView.OnItemClickListener clickListener = new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                        Intent intent = new Intent(view.getContext(), FindActivity.class);
                        //String finalLat =
                        CarObject temp = FINALCARLIST.get(position);

                        intent.putExtra("lat", temp.getLat());
                        intent.putExtra("lon", temp.getLon());
                        intent.putExtra("details", temp.getDetails());
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
