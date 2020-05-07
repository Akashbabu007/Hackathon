package com.example.hackathon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class Appointment extends AppCompatActivity {
    String name;
    String hash[];
    String hash1[];
    EditText customernameet;
    DatePicker date;
    Spinner timeslotspinner;
    TextView storenametv;

    Button showDetails;
    public static String KEY_REFERENCE = "reference";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment1);
//        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
//        myToolbar.setTitle("Token Form");
//        myToolbar.setTitleTextColor(Color.WHITE);
        Intent intent= getIntent();
        final String reference = intent.getStringExtra(KEY_REFERENCE);
//        name=intent.getStringExtra("name"); //Getting the store name from the Groceries Activity
//        hash=name.split(",");
//        hash1=hash[1].split("=");
//        hash1[1]=hash1[1].substring(0,hash1[1].length()-1);
//        Log.d("NAME OF STORE",hash1[1]);
//        storenametv=(TextView)findViewById(R.id.storenametv);
//        storenametv.setText(hash1[1]);
        showDetails = findViewById(R.id.show_details_button);
        timeslotspinner=(Spinner)findViewById(R.id.timeslotspinner); //Spinner
        timeslotspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?>parent, View view, int position, long l) {
                if (parent.getItemAtPosition(position).equals("Choose a Time Slot")){
                }else {
                    String item = parent.getItemAtPosition(position).toString();
                    Toast.makeText(parent.getContext(),"Selected: " +item, Toast.LENGTH_SHORT).show();
                }
            }
             @Override
             public void onNothingSelected(AdapterView<?> adapterView) {
            }
        }
        );
        showDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Appointment.this,SinglePlaceActivity.class);
                intent.putExtra(KEY_REFERENCE, reference);
                startActivity(intent);
            }
        });
    }
}
