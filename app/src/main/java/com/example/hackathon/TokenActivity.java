package com.example.hackathon;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationCompat.InboxStyle;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Locale;

import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


public class TokenActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {
    NotificationManager notificationManager;
    NotificationCompat.Builder builder;
    Notification notification;
    private TextToSpeech mytexttospeech = null;
    public static final int ID = 45612;
    String speech;
    private int MY_DATA_CHECK_CODE = 0;
    TextView name,date,time,phone,storename;
    TextView buttonsms,buttonemail,buttonscreenshot;
    private static final int PERMISSION_REQUEST_CODE_IMAGE = 200;
    private static final int PERMISSION_REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_token);
        Toolbar toolbar=findViewById(R.id.toolbars1);
        toolbar.setTitle("Token Summary");
        toolbar.setTitleTextColor(Color.WHITE);

        Intent intent=getIntent();
        String Name="",Date="",Time="",Phone="",StoreName="";
        Name=intent.getStringExtra("Name");
        Log.i("Name in token",Name);
        Date=intent.getStringExtra("Date");
        Time=intent.getStringExtra("Time");
        Phone=intent.getStringExtra("Phone");
        StoreName=intent.getStringExtra("StoreName");
        name=(TextView)findViewById(R.id.customer_name);
        date=(TextView)findViewById(R.id.date);
        time=(TextView)findViewById(R.id.timeslot);
        phone=(TextView)findViewById(R.id.phones);
        storename=(TextView)findViewById(R.id.shop);
        name.setText(Name);
        date.setText(Date);
        time.setText(Time);
        phone.setText(Phone);
        storename.setText(StoreName);
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        builder = new NotificationCompat.Builder(TokenActivity.this);
        Intent checkttsIntent = new Intent();
        checkttsIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkttsIntent, MY_DATA_CHECK_CODE);
        buttonemail=(TextView)findViewById(R.id.buttonemail);
        buttonsms=(TextView)findViewById(R.id.buttonsms);
        buttonscreenshot=(TextView)findViewById(R.id.buttonscreenshot);
        buttonsms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (checkSelfPermission(Manifest.permission.SEND_SMS)
                            == PackageManager.PERMISSION_DENIED) {

                        Log.d("permission", "permission denied to SEND_SMS - requesting it");
                        String[] permissions = {Manifest.permission.SEND_SMS};

                        requestPermissions(permissions, PERMISSION_REQUEST_CODE);

                    }
                }

                sendSMS(phone.getText().toString(), "Name: " +name.getText().toString()+"\n" + " Store Name: " +storename.getText().toString() +"\n"
                        +"Date: " +date.getText().toString() + "\n" +"Time Slot: " + time.getText().toString() );

            }
        });
        buttonemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater inflater = LayoutInflater.from(TokenActivity.this);
                final View inflator = inflater.inflate(R.layout.dialog_box, null);

               // LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                 //       LinearLayout.LayoutParams.MATCH_PARENT,
                   //     LinearLayout.LayoutParams.MATCH_PARENT);
               // emailEditText.setLayoutParams(lp);
                AlertDialog dialog = new AlertDialog.Builder(TokenActivity.this,R.style.MyDialogTheme)
                        .setTitle("Email ID")
                        .setMessage("")
                        .setView(inflater.inflate(R.layout.dialog_box, null))
                        .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Dialog dialogObj =Dialog.class.cast(dialog);
                                EditText emailEditText = (EditText)dialogObj.findViewById(R.id.emailtext);
                                Log.d("Alert Dialog", "Email: " + emailEditText.getText().toString());

                                try {
                                   String message=name.getText().toString()+"\n"+date.getText().toString()+"\n"+time.getText().toString()+"\n"+storename.getText().toString()+"\n"+phone.getText().toString();
                                    //sendEmail(emailEditText.getText().toString(),"Slot Allotment",message);
                                    new SendEmailAsyncTask().execute(emailEditText.getText().toString(), name.getText().toString(), date.getText().toString(), time.getText().toString(), storename.getText().toString(), phone.getText().toString());
                                    Toast.makeText(getApplicationContext(), "Email Sent Succesfully",
                                            Toast.LENGTH_SHORT).show();
                                } catch (Exception e) {
                                    Log.e("SendMail", e.getMessage(), e);
                                    Toast.makeText(getApplicationContext(), "Error in Sending Email",
                                            Toast.LENGTH_SHORT).show();
                                }

                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();
            }
        });
        buttonscreenshot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkPermission()) {
                    takeScreenshot(view);
                    Toast.makeText(getApplicationContext(), "Screen shot taken",
                            Toast.LENGTH_SHORT).show();
                } else {
                    if (checkPermission()) {
                        requestPermissionAndContinue();
                        Toast.makeText(getApplicationContext(), "Screen shot taken",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        takeScreenshot(view);
                        Toast.makeText(getApplicationContext(), "Screen shot taken",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
    //Notification tab to print the notification
    public void notification(String message)
    {

        Log.d("Notification started", " ");


        speech = "Customer Name:" +name.getText().toString()+ " Date : " +date.getText().toString() + " Time alloted " + time.getText().toString() + " Name of the Store: " +storename.getText().toString();

        notification = builder.setSmallIcon(R.mipmap.fixanappointment)
                .setTicker("Notification")
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentTitle("Slot Booked " ).setContentText("Expand to get details")
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE).build();
        InboxStyle inboxstyle = new InboxStyle();
        inboxstyle.setBigContentTitle("Token Confirmation");
        inboxstyle.addLine(message);
        builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setStyle(new InboxStyle()
                .addLine("Customer Name: "+name.getText().toString())
                .addLine("Date: "+date.getText().toString())
                .addLine("Time Alloted: "+time.getText().toString())
                .addLine("Store Name: "+storename.getText().toString()));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId("com.example.hackathon");
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    "com.example.hackathon",
                    "Find Mart",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
        notificationManager.notify(ID, builder.build());

        speakWords(speech);

    }
    private void speakWords(String speech) {
        //speak straight away
        Log.d("Speak Words Called"," ");
        mytexttospeech.setPitch(1.1f);
        mytexttospeech.setSpeechRate(0.8f);
        mytexttospeech.speak(speech, TextToSpeech.QUEUE_FLUSH,null,null);

    }
    @SuppressLint("MissingSuperCall")
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_DATA_CHECK_CODE) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {


                mytexttospeech = new TextToSpeech(getApplicationContext(), this);
                Log.d("Initialised TTS", " ");

            } else {

                Intent ttsintent = new Intent();
                ttsintent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(ttsintent);
            }
        }
    }

    @Override
    public void onInit(int initStatus) {
        if (initStatus == TextToSpeech.SUCCESS) {
            if(mytexttospeech.isLanguageAvailable(Locale.ENGLISH)==TextToSpeech.LANG_AVAILABLE)
                mytexttospeech.setLanguage(Locale.ENGLISH);
            Log.d("Success" ," ");
            notification(speech);
        }
        else if (initStatus == TextToSpeech.ERROR) {
            Toast.makeText(getApplicationContext(), "Sorry! Text To Speech failed...", Toast.LENGTH_LONG).show();
            Log.d("Failed" , " ");
        }
    }
    private void sendSMS( String phoneNumber, String message)
    {
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        PendingIntent sentpendingintent = PendingIntent.getBroadcast(this, 0,
                new Intent(SENT), 0);

        PendingIntent deliveredpendingintent = PendingIntent.getBroadcast(this, 0,
                new Intent(DELIVERED), 0);

        //---when the SMS has been sent---
        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS sent",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(getBaseContext(), "Generic failure",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(getBaseContext(), "No service",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(getBaseContext(), "Null PDU",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(getBaseContext(), "Radio off",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(SENT));

        //---when the SMS has been delivered---
        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(getBaseContext(), "SMS not delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(DELIVERED));

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, "null", message.trim(), sentpendingintent, deliveredpendingintent);
    }
    class SendEmailAsyncTask extends AsyncTask<String, Void, Boolean> {


        GMailSender m = new GMailSender("aadhitya98@gmail.com", "tn58ac8308@");
        public SendEmailAsyncTask() {

        }

        @Override
        protected Boolean doInBackground(String... params) {
            if (BuildConfig.DEBUG) Log.v(SendEmailAsyncTask.class.getName(), "doInBackground()");
            try {

                m.sendMail("Slot Booking for SuperMarket",
                        "Customer Name: "+params[1]+" Date: "+params[2]+" Time: "+params[3]+" Store Name: "+params[4]+" Phone Number:" +params[5],
                        "aadhitya98@gmail.com",
                        params[0]);
                return true;
            } catch (AuthenticationFailedException e) {
                Log.e(SendEmailAsyncTask.class.getName(), "Bad account details");
                e.printStackTrace();
                return false;
            } catch (MessagingException e) {
                e.printStackTrace();
                return false;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }

    }
    }
    /*private void showAddItemDialog(Context c) {
        final EditText emailEditText = new EditText(c);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Email ID");
        alert.setMessage("Enter your E-mail ID");

// Set an EditText view to get user input
        //final EditText input = new EditText(this);
        alert.setView(emailEditText);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //String value = input.getText();
                // Do something with value!
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });

        alert.show();
    }*/
    private void takeScreenshot(View view) {
        View v = view.getRootView();
        v.setDrawingCacheEnabled(true);
        Bitmap b = v.getDrawingCache();
        String extr = Environment.getExternalStorageDirectory().toString();
        File myPath = new File(extr, "Image" + ".jpg");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(myPath);
            b.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            MediaStore.Images.Media.insertImage(getContentResolver(), b, "Screen", "screen");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void openScreenshot(File imageFile) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(imageFile);
        intent.setDataAndType(uri, "image/*");
        startActivity(intent);
    }
    private boolean checkPermission() {

        return ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED;
    }
    private void requestPermissionAndContinue() {
        if (ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, WRITE_EXTERNAL_STORAGE)
                    && ActivityCompat.shouldShowRequestPermissionRationale(this, READ_EXTERNAL_STORAGE)) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
                alertBuilder.setCancelable(true);
                alertBuilder.setTitle("Permission");
                alertBuilder.setMessage("Permission is necessary to take screenshot");
                alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(TokenActivity.this, new String[]{WRITE_EXTERNAL_STORAGE
                                , READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
                    }
                });
                AlertDialog alert = alertBuilder.create();
                alert.show();
                Log.e("", "permission denied, show dialog");
            } else {
                ActivityCompat.requestPermissions(TokenActivity.this, new String[]{WRITE_EXTERNAL_STORAGE,
                        READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
            }
        } else {
            getApplicationContext();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (permissions.length > 0 && grantResults.length > 0) {

                boolean flag = true;
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        flag = false;
                    }
                }
                if (flag) {
                    takeScreenshot(getCurrentFocus());
                } else {
                    finish();
                }

            } else {
                finish();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


//  public void onBackPressed() {
//        super.onBackPressed();
//        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
//        startActivity(intent);
//        finish();
//
//
//    }
}
