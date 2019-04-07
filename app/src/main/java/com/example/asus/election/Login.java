package com.example.asus.election;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.EachExceptionsHandler;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class Login extends AppCompatActivity {
    Button login;
    TextView deviceinfo,datetime;
    Calendar calendar;
    SimpleDateFormat simpledateformat;
    String Date;
    EditText user,pass;
    String USER, PASS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = findViewById(R.id.login_button);
        deviceinfo = findViewById(R.id.deviceinfo);
        datetime = findViewById(R.id.datetime);
        user = findViewById(R.id.login_username);
        pass = findViewById(R.id.login_pass);
        //Thread to get date and time continuously
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                long date = System.currentTimeMillis();
                                SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy\nhh-mm-ss a");
                                String dateString = sdf.format(date);
                                datetime.setText(dateString);
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        t.start();
        //IMEI and IMSI of mobile phone
        TelephonyManager m_telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String IMEI,IMSI;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        IMEI = m_telephonyManager.getDeviceId();
        IMSI = m_telephonyManager.getSubscriberId();
        deviceinfo.setText("IMEI - "+IMEI);
        deviceinfo.append("\nIMSI - "+IMSI);
        calendar = Calendar.getInstance();
        simpledateformat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date = simpledateformat.format(calendar.getTime());
        //deviceinfo.append("\n"+Date);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                func();
                //Toast.makeText(getApplicationContext(), "Something went wrong! Try later!", Toast.LENGTH_SHORT).show();

            }
        });


    }

    public void func(){
        //Toast.makeText(getApplicationContext(),"hello",Toast.LENGTH_SHORT).show();
        HashMap<String, String> getData = new HashMap<String, String>();
        USER = user.getText().toString();
        PASS = pass.getText().toString();
        getData.put("user", USER);
        getData.put("pass", PASS);

        PostResponseAsyncTask task2 = new PostResponseAsyncTask(Login.this, getData, new AsyncResponse() {
            @Override
            public void processFinish(String s) {
                if (!(s.isEmpty())) {
                   Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login.this,Menu.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(getApplicationContext(), "Something went wrong! Try later!", Toast.LENGTH_SHORT).show();


                }

            }
        });
        task2.execute("http://192.168.1.101/election/mssql.php");

        task2.setEachExceptionsHandler(new EachExceptionsHandler() {
            @Override
            public void handleIOException(IOException e) {
                Toast.makeText(getApplicationContext(), "Cannot connect to server!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void handleMalformedURLException(MalformedURLException e) {
                Toast.makeText(getApplicationContext(), "URL Error!", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void handleProtocolException(ProtocolException e) {
                Toast.makeText(getApplicationContext(), "Protocol Error!", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void handleUnsupportedEncodingException(UnsupportedEncodingException e) {
                Toast.makeText(getApplicationContext(), "Encoding Error!", Toast.LENGTH_SHORT).show();

            }
        });

    }
}
