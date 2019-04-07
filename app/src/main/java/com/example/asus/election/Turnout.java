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

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.EachExceptionsHandler;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
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
    int date,length;
    String selectedtime,selectedtime1,temp,currenttimeselected;
    String[] booths;
    String[] status;
    final List<String> listbooth = new ArrayList<>();
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
        HashMap<String, String> getData = new HashMap<String, String>();

        PostResponseAsyncTask task2 = new PostResponseAsyncTask(Turnout.this, getData, new AsyncResponse() {
            @Override
            public void processFinish(String s) {
                if (!(s.isEmpty())) {
                    //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getApplicationContext(), "Something went wrong! Try later!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        try {
            temp=task2.execute("http://192.168.1.101/election/booths.php").get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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
        //Toast.makeText(getApplicationContext(),selectedtime, Toast.LENGTH_SHORT).show();

        //final String[] str = {selectedtime};
        sp = findViewById(R.id.turnout_booth);
        booths = temp.split(",");
        length = booths.length;
        listbooth.add("Select Booth");
        listbooth.addAll(Arrays.asList(booths).subList(1, length));
        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,listbooth);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adp1.notifyDataSetChanged();
        sp.setAdapter(adp1);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    turnout_error.setText("*Select Any Booth");
                    turnout_error.setError("");
                    male.setText("");
                    female.setText("");
                    transgender.setText("");
                    male.setEnabled(false);
                    female.setEnabled(false);
                    transgender.setEnabled(false);
                    submit.setText("Submit");
                } else {
                    turnout_error.setText("");
                    turnout_error.setError(null);
                    male.setEnabled(true);
                    female.setEnabled(true);
                    transgender.setEnabled(true);
                    male.setText("");
                    female.setText("");
                    transgender.setText("");
                    turnout_layout(position);

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sp1 = findViewById(R.id.time);
        ArrayAdapter<String> adp2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, list);
        adp2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp1.setAdapter(adp2);
        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currenttimeselected = sp1.getSelectedItem().toString();
                Toast.makeText(getApplicationContext(),currenttimeselected, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    public void turnout_layout(int position) {
        final String booth = sp.getItemAtPosition(position).toString();
        HashMap<String, String> getData = new HashMap<String, String>();
        getData.put("booth",booth);
        PostResponseAsyncTask task2 = new PostResponseAsyncTask(Turnout.this, getData, new AsyncResponse() {
            @Override
            public void processFinish(String s) {
                if (!(s.isEmpty())) {
                    //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                    status = s.split(",");
                    if(status[0].equals("1")) {
                        male.setText(status[1]);
                        female.setText(status[2]);
                        transgender.setText(status[3]);
                        male.setEnabled(false);
                        female.setEnabled(false);
                        transgender.setEnabled(false);
                        submit.setEnabled(false);
                    }
                    if(status[0].equals("0")) {
                        male.setText(status[1]);
                        female.setText(status[2]);
                        transgender.setText(status[3]);
                        submit.setText("Update");
                        submit.setEnabled(true);
                    }
                    if(status[0].equals("-1")){
                        submit.setEnabled(true);
                        submit.setText("Submit");
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Something went wrong! Try later!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        task2.execute("http://192.168.1.101/election/turnoutstatus.php");

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

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String male_count = male.getText().toString();
                    final String female_count = female.getText().toString();
                    final String transgender_count = transgender.getText().toString();
                    Pattern q = Pattern.compile("^[0-9][0-9]*$");
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
                        HashMap<String, String> getData = new HashMap<String, String>();
                        getData.put("booth",booth);
                        PostResponseAsyncTask task2 = new PostResponseAsyncTask(Turnout.this, getData, new AsyncResponse() {
                            @Override
                            public void processFinish(String s) {
                                if (!(s.isEmpty())) {
                                    //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                                    status = s.split(",");
                                    if(status[2].equals("Y")){
                                        int submit=Integer.parseInt(status[0]);
                                        submit = submit + 1;
                                        String update = String.valueOf(submit);
                                        Toast.makeText(getApplicationContext(),"Success", Toast.LENGTH_SHORT).show();
                                        HashMap<String, String> getData = new HashMap<String, String>();
                                        getData.put("submit",update);
                                        getData.put("male",male_count);
                                        getData.put("female",female_count);
                                        getData.put("transgender",transgender_count);
                                        PostResponseAsyncTask task2 = new PostResponseAsyncTask(Turnout.this, getData, new AsyncResponse() {
                                            @Override
                                            public void processFinish(String s) {
                                                if (!(s.isEmpty())) {
                                                    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();

                                                } else {
                                                    Toast.makeText(getApplicationContext(), "Something went wrong! Try later!", Toast.LENGTH_SHORT).show();
                                                }

                                            }
                                        });
                                        task2.execute("http://192.168.1.101/election/turnout.php");

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
                                    else{
                                        Toast.makeText(getApplicationContext(),"Poll Not Started", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "Something went wrong! Try later!", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                        task2.execute("http://192.168.1.101/election/pollboothstatus.php");

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
            });

    }
}
