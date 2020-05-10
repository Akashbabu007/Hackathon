package com.example.hackathon;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.io.Console;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Appointment extends AppCompatActivity {

    String hash[];
    String hash1[];
    EditText customernameet;
    DatePicker date;
    Spinner timeslotspinner;
    TextView storenametv;
    private DatabaseReference firebaseDatabase;
    private FirebaseDatabase firebaseInstance;
    private String userId;
    PlaceDetails placeDetails;
    GooglePlaces googlePlaces;
    String storename;
    Button showDetails;
    public static String KEY_REFERENCE = "reference";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment1);
//        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
//        myToolbar.setTitle("Token Form");
//        myToolbar.setTitleTextColor(Color.WHITE);
        Intent intent = getIntent();
        final String reference = intent.getStringExtra(KEY_REFERENCE);
     storename=intent.getStringExtra("name"); //Getting the store name from the Groceries Activity
       hash=storename.split(",");
        hash1=hash[1].split("=");
        hash1[1]=hash1[1].substring(0,hash1[1].length()-1);
       Log.d("NAME OF STORE",hash1[1]);
//        storenametv=(TextView)findViewById(R.id.storenametv);
//        storenametv.setText(hash1[1]);
        showDetails = findViewById(R.id.show_details_button);
        Button submitbutton = (Button) findViewById(R.id.submit_button);
        customernameet=(EditText)findViewById(R.id.customeret);
        date=(DatePicker)findViewById(R.id.date);
        timeslotspinner = (Spinner) findViewById(R.id.timeslotspinner); //Spinner
        timeslotspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                      @Override
                                                      public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                                                          if (parent.getItemAtPosition(position).equals("Choose a Time Slot")) {
                                                          } else {
                                                              String item = parent.getItemAtPosition(position).toString();
                                                              Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_SHORT).show();
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
                Intent intent = new Intent(Appointment.this, SinglePlaceActivity.class);
                intent.putExtra(KEY_REFERENCE, reference);
                startActivity(intent);
            }
        });
        submitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseInstance = FirebaseDatabase.getInstance();
                firebaseDatabase = firebaseInstance.getReference();
                String store=hash[1];  String name;
                if (TextUtils.isEmpty(userId)) {
                    userId = firebaseDatabase.push().getKey();
                }
                 /* try {
                    placeDetails = googlePlaces.getPlaceDetails(reference);

                } catch (Exception e) {
                    e.printStackTrace();
                }

              if(placeDetails!=null) {
                    String status= placeDetails.status;
                    if(status.equals("Ok")) {
                        if(placeDetails.result!=null){
                            store=placeDetails.result.name;
                        }
                    }
                }*/

                name=customernameet.getText().toString();
                int day = date.getDayOfMonth();
                int month = date.getMonth() ;
                int year = date.getYear();
                year=year-1900; //Gregorrian Calender produces year added with 1900.


                SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
                Date d = new Date(year, month, day);
                String strDate = dateFormatter.format(d);
               // String strDate=date.getYear();
              String time= timeslotspinner.getSelectedItem().toString();
                Customer user = new Customer(name,strDate,time,store);

                firebaseDatabase.child(userId).setValue(user);

               addUserChangeListener();

               // MongoClientURI uri = new MongoClientURI("mongodb+srv://Aadhitya:Tn58ac8308%40@cluster0-dnezo.mongodb.net/SuperMarket?retryWrites=true&w=majority");
               // MongoClient client = new MongoClient(uri);
                //MongoDatabase db = client.getDatabase(uri.getDatabase());
                //MongoClientURI uri  = new MongoClientURI("mongodb+srv://Aadhitya:Tn58ac8308%40@cluster0-dnezo.mongodb.net/SuperMarket?retryWrites=true&w=majority");
                //MongoClient client = new MongoClient(uri);
                //MongoDatabase db = client.getDatabase(uri.getDatabase());
                // Log.d("Database Name",db.getName());
               // MongoLabSaveContact tsk = new MongoLabSaveContact();
                //tsk.execute();

                //FirebaseDatabase database = FirebaseDatabase.getInstance().getReference();
               // Log.d("Database Name",database.toString());
               // DatabaseReference myRef = database.getReference().child("Name");

                //myRef.setValue("Defne");
            }
        });

    }
    private void addUserChangeListener() {
        // User data change listener
        firebaseDatabase.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Customer user = dataSnapshot.getValue(Customer.class);

                // Check for null
                if (user == null) {
                    Log.e("Error", "User data is null!");
                    return;
                }

                Log.e("The Details are ",   user.getName() + ", " + user.getDate()+","+user.getTimeslot()+","+user.getStore());

                // Display newly updated name and email
               // txtDetails.setText(user.name + ", " + user.email);

                // clear edit text
                customernameet.setText("");
            


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e("error", "Failed to read user", error.toException());
            }
        });
    }


}


