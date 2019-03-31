package com.example.asus.election;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.graphics.Color.BLUE;
import static android.graphics.Color.GRAY;

public class Turnout extends AppCompatActivity {
    TextView datetime4,turnout_error;
    Spinner sp,sp1;
    EditText male,female,transgender;
    Button submit;
    Calendar calendar;
    SimpleDateFormat simpledateformat1;
    String Date;
    int date;
    String selectedtime,selectedtime1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turnout);
        datetime4 = findViewById(R.id.datetime4);
        turnout_error = findViewById(R.id.turnout_error);
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        transgender = findViewById(R.id.transgender);
        submit = findViewById(R.id.submit);
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
                                datetime4.setText(dateString);
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        t.start();
        calendar = Calendar.getInstance();
        simpledateformat1 = new SimpleDateFormat("HH");
        Date = simpledateformat1.format(calendar.getTime());
        date=Integer.parseInt(Date);
        String[] time = getResources().getStringArray(R.array.time);
        final List<String> list = new ArrayList<>();
        if(date>=9&&date<11){
            selectedtime=time[1];
            list.add(selectedtime);
        }
        else if(date>=11&&date<13){
            selectedtime=time[2];
            list.add(selectedtime);
        }
        else if(date>=13&&date<15){
            selectedtime=time[3];
            list.add(selectedtime);
        }
        else if(date>=15&&date<17){
            selectedtime=time[4];
            list.add(selectedtime);
        }
        else if(date>=17){
            selectedtime=time[5];
            selectedtime1=time[6];
            list.add(selectedtime);
            list.add(selectedtime1);
        }
        else{
            selectedtime="not selected";
            list.add(selectedtime);
        }
        Toast.makeText(getApplicationContext(),selectedtime, Toast.LENGTH_SHORT).show();

        //final String[] str = {selectedtime};
        sp = findViewById(R.id.turnout_booth);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                /*switch (position){
                    case 1:
                        layout();
                        break;
                    case 2:
                        layout();
                        break;
                    case 3:
                        layout();
                        break;

                }*/
                turnout_layout(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sp1 = findViewById(R.id.time);
        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, list);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp1.setAdapter(adp1);
        /*sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                /*switch (position){
                    case 1:
                        layout();
                        break;
                    case 2:
                        layout();
                        break;
                    case 3:
                        layout();
                        break;

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

    }
    public void turnout_layout(int position) {
        if (position == 0) {
            turnout_error.setText("*Select Any Booth");
            turnout_error.setError("");
        } else {
            turnout_error.setText("");
            turnout_error.setError(null);
            male.setEnabled(true);
            female.setEnabled(true);
            transgender.setEnabled(true);
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String male_count = male.getText().toString();
                    String female_count = female.getText().toString();
                    String transgender_count = transgender.getText().toString();
                    Pattern q = Pattern.compile("^[1-9][0-9]*$");
                    Matcher m = q.matcher(male_count);
                    boolean count1 = m.matches();
                    Matcher f = q.matcher(female_count);
                    boolean count2 = f.matches();
                    Matcher t = q.matcher(transgender_count);
                    boolean count3 = t.matches();
                    if (!count1 || !count2 || !count3) {
                        if (!count1)
                            male.setError("Invalid Entry");
                        if (!count2)
                            female.setError("Invalid Entry");

                        if (!count3)
                            transgender.setError("Invalid Entry");
                    } else {
                        Toast.makeText(getApplicationContext(),"Success", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
