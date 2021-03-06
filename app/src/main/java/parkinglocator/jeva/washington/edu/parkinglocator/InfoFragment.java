package parkinglocator.jeva.washington.edu.parkinglocator;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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
    private String id;
    private String lat = "";
    private String lon = "";
    private String details = "";
    private LinearLayoutManager mLayoutManager;

    public InfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_info, container, false);

        Button btn = (Button) view.findViewById(R.id.button4);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                refresh();
            }
        });
        refresh();
        return view;
    }

    private class CarViewHolder
            extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private CarViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), FindActivity.class);
            CarObject temp = FINALCARLIST.get(getAdapterPosition());

            intent.putExtra("lat", temp.getLat());
            intent.putExtra("lon", temp.getLon());
            intent.putExtra("details", temp.getDetails());
            startActivity(intent);
        }
    }
    public  void refresh(){
        final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        database.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                SharedPreferences sPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                id = sPrefs.getString("key_uuid", null);

                if (dataSnapshot != null && dataSnapshot.getValue() != null) {
                    Log.d(MainActivity.TAG, "" + dataSnapshot.getValue());
                    Map<String, ArrayList<Map<String, String>>> td = (HashMap<String, ArrayList<Map<String, String>>>) dataSnapshot.getValue();

                    ArrayList<String> Final = new ArrayList<String>();
                    cars = new ArrayList<Map<String, String>>();
                    FINALCARLIST = new ArrayList<CarObject>();

                    if (td.get(id) != null) {
                        cars = td.get(id);
                    } else {
                        lat = "000000";
                        lon = "000000";
                        details = "NO details to show";
                    }

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
                        Final.add("Make: " + temp.getMake() + " Model: " + temp.getModel() +
                                " Color: " + temp.getColor() + " Year: " + temp.getYear());
                    }

                    RecyclerView mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_view);
                    RecyclerView.Adapter<CarViewHolder> mAdapter;
                    if (mRecyclerView.getLayoutManager() == null) {
                        mLayoutManager = new LinearLayoutManager(getActivity());
                        mRecyclerView.setLayoutManager(mLayoutManager);
                        mAdapter = new RecyclerView.Adapter<CarViewHolder>() {

                            @Override
                            public CarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                                View v = LayoutInflater.from(parent.getContext()).inflate(
                                        R.layout.car_row,
                                        parent,
                                        false);
                                return new CarViewHolder(v);
                            }

                            @Override
                            public void onBindViewHolder(CarViewHolder vh, int position) {
                                TextView tv = (TextView) vh.itemView.findViewById(R.id.text1);
                                CarObject car = FINALCARLIST.get(position);
                                tv.setText(car.getColor() + " " + car.getYear() + " " +
                                        car.getMake() + " " + car.getModel());
                                tv = (TextView) vh.itemView.findViewById(R.id.text2);
                                tv.setText(car.getDetails());
                            }

                            @Override
                            public int getItemCount() {
                                return FINALCARLIST.size();
                            }
                        };
                        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                                mLayoutManager.getOrientation());
                        mRecyclerView.addItemDecoration(dividerItemDecoration);
                        mRecyclerView.setAdapter(mAdapter);
                    } else {
                        mAdapter = mRecyclerView.getAdapter();
                        mAdapter.notifyDataSetChanged();
                        ((MainActivity) getActivity()).updateCarList(FINALCARLIST);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
    }

}
