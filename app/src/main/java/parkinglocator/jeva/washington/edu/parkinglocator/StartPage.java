package parkinglocator.jeva.washington.edu.parkinglocator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class StartPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);

        Button setLocation = (Button) findViewById(R.id.button);
        Button find = (Button) findViewById(R.id.button2);
        Button recent = (Button) findViewById(R.id.button3);

        setLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });

        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });

        recent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });


    }
}
