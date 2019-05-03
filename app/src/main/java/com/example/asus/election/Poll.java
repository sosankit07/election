package com.example.asus.election;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.BLUE;
import static android.graphics.Color.GRAY;
import static android.graphics.Color.WHITE;
/**Import For Dialog Box**/
//public class Poll extends AppCompatActivity implements ExampleDialog.ExampleDialogListener

public class Poll extends AppCompatActivity{
    Button mockpoll,mockabsent,pollstart,pollclose,finalclose,pollparty,poll_votersqueue;
    TextView datetime2,error,mock_report,mock_absence_report,pollstart_report,poll_votersqueue_report,finalpoll_report,pollparty_report;
    RelativeLayout layoutpoll;
    Calendar calendar;
    SimpleDateFormat simpledateformat,simpledateformat1;
    String Date;
    Date d;
    EditText edittime,agents,votersqueue;
    PopupWindow popupWindow;
    Button submit,cancel;
    String temp;
    Spinner sp;
    String[] booths;
    String[] status,reports;
    int length;
    ArrayList<String> listbooth = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poll);
        layoutpoll = findViewById(R.id.layoutpoll);
        mockpoll = findViewById(R.id.mockpoll);
        mockabsent = findViewById(R.id.mockpoll_absence);
        pollstart = findViewById(R.id.pollstart);
        //pollclose = findViewById(R.id.pollclose);
        finalclose = findViewById(R.id.finalpoll);
        pollparty = findViewById(R.id.pollparty_reached);
        datetime2 = findViewById(R.id.datetime2);
        poll_votersqueue = findViewById(R.id.poll_votersqueue);
        error = findViewById(R.id.error);
        mock_report = findViewById(R.id.mockpoll_report);
        mock_absence_report = findViewById(R.id.mockpoll_absence_report);
        pollstart_report = findViewById(R.id.pollstart_report);
        poll_votersqueue_report = findViewById(R.id.poll_votersqueue_report);
        finalpoll_report = findViewById(R.id.finalpoll_report);
        pollparty_report = findViewById(R.id.pollparty_reached_report);
        sp = findViewById(R.id.poll_booth);
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
                                datetime2.setText(dateString);
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        t.start();
        HashMap<String, String> getData = new HashMap<String, String>();

        PostResponseAsyncTask task2 = new PostResponseAsyncTask(Poll.this, getData, new AsyncResponse() {
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
        HashMap<String, String> getData1 = new HashMap<String, String>();

        PostResponseAsyncTask task3 = new PostResponseAsyncTask(Poll.this, getData1, new AsyncResponse() {
            @Override
            public void processFinish(String s) {
                if (!(s.isEmpty())) {
                    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                    reports = s.split(";");
                    mock_report.setText(reports[1]);
                    mock_absence_report.setText(reports[2]);
                    pollstart_report.setText(reports[3]);
                    poll_votersqueue_report.setText(reports[4]);
                    finalpoll_report.setText(reports[5]);
                    pollparty_report.setText(reports[6]);
                    mock_report.append("\n*Not Reported");
                    mock_absence_report.append("\n*Not Reported");
                    pollstart_report.append("\n*Not Reported");
                    poll_votersqueue_report.append("\n*Not Reported");
                    finalpoll_report.append("\n*Not Reported");
                    pollparty_report.append("\n*Not Reported");
                } else {
                    Toast.makeText(getApplicationContext(), "Something went wrong! Try later!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        task3.execute("http://192.168.1.101/election/poll_reports.php");

        task3.setEachExceptionsHandler(new EachExceptionsHandler() {
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
                    error.setText("*Select Any Booth");
                    error.setError("");
                    mockpoll.setBackgroundColor(BLUE);
                    mockabsent.setBackgroundColor(BLUE);
                    mockpoll.setText("MOCK POLL COMPLETED");
                    mockabsent.setText("MOCK POLL IN ABSENCE OF POLLING AGENT");
                    pollstart.setText("POLL STARTED");
                    poll_votersqueue.setText("VOTERS IN QUEUE AT THE TIME OF CLOSING");
                    finalclose.setText("POLL CONCLUDED");
                    pollparty.setText("POLLING PARTY REACHED AT RECEIVING CELL");
                } else {
                    error.setText("");
                    error.setError(null);
                    mockpoll.setText("MOCK POLL COMPLETED");
                    mockabsent.setText("MOCK POLL IN ABSENCE OF POLLING AGENT");
                    pollstart.setText("POLL STARTED");
                    poll_votersqueue.setText("VOTERS IN QUEUE AT THE TIME OF CLOSING");
                    finalclose.setText("POLL CONCLUDED");
                    pollparty.setText("POLLING PARTY REACHED AT RECEIVING CELL");
                    mockpoll.setBackgroundColor(BLUE);
                    mockabsent.setBackgroundColor(BLUE);
                    pollstart.setBackgroundColor(GRAY);
                    poll_votersqueue.setBackgroundColor(GRAY);
                    finalclose.setBackgroundColor(GRAY);
                    pollparty.setBackgroundColor(GRAY);
                    mockabsent.setEnabled(true);
                    mockpoll.setEnabled(true);
                    pollstart.setEnabled(false);
                    finalclose.setEnabled(false);
                    poll_votersqueue.setEnabled(false);
                    pollparty.setEnabled(false);
                    layout(position);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
           // @Override
            //public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
              //  if(isChecked) {


    }
    /*
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("ms", mockpoll.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String myString = savedInstanceState.getString("ms");
        mockpoll.setText(myString);

    }
    public void openDialog(int position) {
        ExampleDialog exampleDialog = new ExampleDialog();
        exampleDialog.show(getSupportFragmentManager(), "example dialog");
    }
    @Override
    public void applyTexts(String date,String agents){
        mockpoll.setText("Mock Poll Completed at "+date+"\nNo. of Agents "+agents);
        mockpoll.setEnabled(false);
        mockabsent.setEnabled(false);
    }
    public void cancel(){
        mockpoll.setChecked(false);
    }*/
    public void layout(int position) {
        final String booth = sp.getItemAtPosition(position).toString();
        HashMap<String, String> getData = new HashMap<String, String>();
        getData.put("booth",booth);
        PostResponseAsyncTask task2 = new PostResponseAsyncTask(Poll.this, getData, new AsyncResponse() {
            @Override
            public void processFinish(String s) {
                if (!(s.isEmpty())) {
                    //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                    status = s.split(",");
                    if(status[0].equals("y1") || status[0].equals("y2")){
                        if(status[0].equals("y1")){
                            mockpoll.setText("Mock Poll Completed at "+status[1]);
                            mockpoll.setEnabled(false);
                            mockpoll.setBackgroundColor(GRAY);
                            mockabsent.setEnabled(false);
                            mockabsent.setBackgroundColor(GRAY);
                            pollstart.setEnabled(true);
                            pollstart.setBackgroundColor(BLUE);
                        }
                        if(status[0].equals("y2")){
                            mockpoll.setText("Mock Poll Completed");
                            mockpoll.setEnabled(false);
                            mockpoll.setBackgroundColor(GRAY);
                            mockabsent.setEnabled(false);
                            mockabsent.setBackgroundColor(GRAY);;
                            pollstart.setEnabled(true);
                            pollstart.setBackgroundColor(BLUE);
                        }
                        if(status[2].equals("Y")){
                            pollstart.setText("Poll Started at "+status[3]);
                            pollstart.setEnabled(false);
                            pollstart.setBackgroundColor(GRAY);;
                            poll_votersqueue.setEnabled(true);
                            poll_votersqueue.setBackgroundColor(BLUE);;
                            finalclose.setEnabled(true);
                            finalclose.setBackgroundColor(BLUE);
                            if(!status[4].equals("0")){
                                poll_votersqueue.setText("Voters in Queue at time of Closing "+status[4]);
                                poll_votersqueue.setEnabled(false);
                                poll_votersqueue.setBackgroundColor(GRAY);
                            }
                            if(status[5].equals("Y")){
                                poll_votersqueue.setText("Voters in Queue at time of Closing "+status[4]);
                                finalclose.setText("Poll Concluded at "+status[6]);
                                finalclose.setEnabled(false);
                                finalclose.setBackgroundColor(GRAY);
                                poll_votersqueue.setEnabled(false);
                                poll_votersqueue.setBackgroundColor(GRAY);;
                                pollparty.setEnabled(true);
                                pollparty.setBackgroundColor(BLUE);
                                if(status[7].equals("Y")){
                                    pollparty.setText("Polling Party Reached at " +status[8]);
                                    pollparty.setEnabled(false);
                                    pollparty.setBackgroundColor(GRAY);
                                }
                            }

                        }
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
        mockpoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*LayoutInflater layoutInflater = (LayoutInflater) Poll.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View customView = layoutInflater.inflate(R.layout.popup, null);
                submit = customView.findViewById(R.id.confirm);
                cancel = customView.findViewById(R.id.cancel);
                edittime = customView.findViewById(R.id.edittime);
                agents = customView.findViewById(R.id.agents);*/
                HashMap<String, String> getData = new HashMap<String, String>();
                getData.put("booth",booth);
                PostResponseAsyncTask task2 = new PostResponseAsyncTask(Poll.this, getData, new AsyncResponse() {
                    @Override
                    public void processFinish(String s) {
                        if (!(s.isEmpty())) {
                            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                            String[] temp = s.split(",");
                            if(temp[0] == "Y"){
                                if(temp[3] == "Y"){
                                    reports[1] = reports[1].replace(booth,"");
                                    reports[2] = reports[2].replace(booth,"");
                                    reports[1] = reports[1].replace(",,",",");
                                    reports[1] = reports[1].replaceAll(",$","");
                                    reports[1] = reports[1].replaceFirst("^,","");
                                    reports[2] = reports[2].replace(",,",",");
                                    reports[2] = reports[2].replaceAll(",$","");
                                    reports[2] = reports[2].replaceFirst("^,","");
                                    mock_report.setText(reports[1]);
                                    mock_absence_report.setText(reports[2]);
                                    mock_report.append("\n*Not Reported");
                                    mock_absence_report.append("\n*Not Reported");
                                    calendar = Calendar.getInstance();
                                    simpledateformat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                                    simpledateformat1 = new SimpleDateFormat("hh:mma");
                                    Date = simpledateformat1.format(calendar.getTime());
                                    mockabsent.setEnabled(false);
                                    mockpoll.setEnabled(false);
                                    pollstart.setEnabled(true);
                                    pollstart.setBackgroundColor(BLUE);
                                    mockpoll.setBackgroundColor(GRAY);
                                    mockabsent.setBackgroundColor(GRAY);
                                    mockpoll.setText("Mock Poll Completed at " + Date);
                                    HashMap<String, String> getData = new HashMap<String, String>();
                                    getData.put("date",Date);
                                    getData.put("mockpoll","Y");
                                    getData.put("pollstart","-1");
                                    getData.put("voterqueue","-1");
                                    getData.put("finalclose","-1");
                                    getData.put("pollparty","-1");
                                    PostResponseAsyncTask task2 = new PostResponseAsyncTask(Poll.this, getData, new AsyncResponse() {
                                        @Override
                                        public void processFinish(String s) {
                                            if (!(s.isEmpty())) {
                                                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();

                                            } else {
                                                Toast.makeText(getApplicationContext(), "Something went wrong! Try later!", Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    });
                                    task2.execute("http://192.168.1.101/election/pollstatus.php");

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
                                    mockpoll.setError("Polling Party Not Arrived");
                                }
                            }
                            else{
                                mockpoll.setError("Polling Party Not Dispatched");
                            }

                        } else {
                            Toast.makeText(getApplicationContext(), "Something went wrong! Try later!", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                task2.execute("http://192.168.1.101/election/preboothstatus.php");

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
                //edittime.setText(Date);
                //instantiate popup window
                /*popupWindow = new PopupWindow(customView, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                popupWindow.setHeight(900);
                //display the popup window
                popupWindow.showAtLocation(layoutpoll, Gravity.CENTER, 0, 0);
                popupWindow.setFocusable(true);
                popupWindow.update();
                /*final String regex,date;
                regex = "^(?:[01]\\d|2[0123]):(?:[012345]\\d) (AM|PM)$";
                date = simpledateformat1.format(edittime.getText());
                edittime.addTextChangedListener(new TextWatcher() {
                public void afterTextChanged(Editable s) {

                    if (date.matches(regex) && s.length() > 0)
                    {
                        Toast.makeText(getApplicationContext(),"valid",Toast.LENGTH_SHORT).show();
                        // or
                        //textView.setText("valid email");
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Invalid",Toast.LENGTH_SHORT).show();
                        //or
                        //textView.setText("invalid email");
                    }
                }
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    // other stuffs
                }
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    // other stuffs
                }
            });
                //close the popup window on button click
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String date = edittime.getText().toString();
                        String agentcount = agents.getText().toString();
                        //Pattern p=Pattern.compile("^[0-9]{2}:[0-9]{2}([a|p]m)?",Pattern.CASE_INSENSITIVE);
                        Pattern p = Pattern.compile("^(?:[01]\\d|2[0123]):(?:[012345]\\d)(AM|PM)$", Pattern.CASE_INSENSITIVE);
                        Matcher m = p.matcher(date);
                        boolean check = m.matches();
                        Pattern q = Pattern.compile("^[1-9][0-9]*$");
                        Matcher n = q.matcher(agentcount);
                        boolean agent_check = n.matches();
                        //Toast.makeText(getApplicationContext(), "this is "+date+"check"+check, Toast.LENGTH_SHORT).show();

                   /* try {
                        // To get the date object from the string just called the
                        // parse method and pass the time string to it. This method
                        // throws ParseException if the time string is invalid.
                        // But remember as we don't pass the date information this
                        // date object will represent the 1st of january 1970.
                        Date d = simpledateformat1.parse(date);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Pattern p = Pattern.compile("^(?:[01]\\d|2[0123]):(?:[012345]\\d) (AM|PM)$");
                    Matcher m = p.matcher(date);
                    boolean check = m.matches();*/
                        //boolean check = Pattern.matches("^(?:[01]\\d|2[0123]):(?:[012345]\\d) (AM|PM)$",date);
                        //if(!check) {
                        //mockpoll.setText("Mock Poll Completed at " + date + "\nNo. of Agents " + agentcount);
                        //popupWindow.dismiss();
                        //  edittime.setError("Wrong Time Format");
                        //}
                    /*
                    else{
                        error.setText("Date is Incorrect");
                    }
                        if (check == false || agent_check == false) {
                            if (check == false)
                                edittime.setError("Invalid Format");
                            if (agent_check == false)
                                agents.setError("Invalid Entry");
                        } else {
                            mockabsent.setEnabled(false);
                            mockpoll.setEnabled(false);
                            pollstart.setEnabled(true);
                            pollstart.setBackgroundColor(BLUE);
                            mockpoll.setBackgroundColor(GRAY);
                            mockabsent.setBackgroundColor(GRAY);
                            mockpoll.setText("Mock Poll Completed at " + date + "\nNo. of Agents " + agentcount);
                            popupWindow.dismiss();
                        }
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });*/
            }
        });
        mockabsent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*LayoutInflater layoutInflater = (LayoutInflater) Poll.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View customView = layoutInflater.inflate(R.layout.popup, null);
                submit = customView.findViewById(R.id.confirm);
                cancel = customView.findViewById(R.id.cancel);
                edittime = customView.findViewById(R.id.edittime);
                agents = customView.findViewById(R.id.agents);*/
                HashMap<String, String> getData = new HashMap<String, String>();
                getData.put("booth",booth);
                PostResponseAsyncTask task2 = new PostResponseAsyncTask(Poll.this, getData, new AsyncResponse() {
                    @Override
                    public void processFinish(String s) {
                        if (!(s.isEmpty())) {
                            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                            String[] temp = s.split(",");
                            if(temp[0] == "Y"){
                                if(temp[3] == "Y"){
                                    reports[1] = reports[1].replace(booth,"");
                                    reports[2] = reports[2].replace(booth,"");
                                    reports[1] = reports[1].replace(",,",",");
                                    reports[1] = reports[1].replaceAll(",$","");
                                    reports[1] = reports[1].replaceFirst("^,","");
                                    reports[2] = reports[2].replace(",,",",");
                                    reports[2] = reports[2].replaceAll(",$","");
                                    reports[2] = reports[2].replaceFirst("^,","");
                                    mock_report.setText(reports[1]);
                                    mock_absence_report.setText(reports[2]);
                                    mock_report.append("\n*Not Reported");
                                    mock_absence_report.append("\n*Not Reported");
                                    calendar = Calendar.getInstance();
                                    simpledateformat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                                    simpledateformat1 = new SimpleDateFormat("hh:mma");
                                    Date = simpledateformat1.format(calendar.getTime());
                                    mockabsent.setEnabled(false);
                                    mockpoll.setEnabled(false);
                                    pollstart.setEnabled(true);
                                    pollstart.setBackgroundColor(BLUE);
                                    mockpoll.setBackgroundColor(GRAY);
                                    mockabsent.setBackgroundColor(GRAY);
                                    HashMap<String, String> getData = new HashMap<String, String>();
                                    getData.put("date",Date);
                                    getData.put("mockpoll","N");
                                    getData.put("pollstart","-1");
                                    getData.put("voterqueue","-1");
                                    getData.put("finalclose","-1");
                                    getData.put("pollparty","-1");
                                    PostResponseAsyncTask task2 = new PostResponseAsyncTask(Poll.this, getData, new AsyncResponse() {
                                        @Override
                                        public void processFinish(String s) {
                                            if (!(s.isEmpty())) {
                                                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();

                                            } else {
                                                Toast.makeText(getApplicationContext(), "Something went wrong! Try later!", Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    });
                                    task2.execute("http://192.168.1.101/election/pollstatus.php");

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
                                    mockabsent.setError("Polling Party Not Arrived");
                                }
                            }
                            else{
                                mockabsent.setError("Polling Party Not Dispatched");
                            }

                        } else {
                            Toast.makeText(getApplicationContext(), "Something went wrong! Try later!", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                task2.execute("http://192.168.1.101/election/preboothstatus.php");

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

                //mockpoll.setText("Mock Poll Completed at " + Date);
                //edittime.setText(Date);
                //instantiate popup window
                /*popupWindow = new PopupWindow(customView, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                popupWindow.setHeight(900);
                //display the popup window
                popupWindow.showAtLocation(layoutpoll, Gravity.CENTER, 0, 0);
                popupWindow.setFocusable(true);
                popupWindow.update();
                /*final String regex,date;
                regex = "^(?:[01]\\d|2[0123]):(?:[012345]\\d) (AM|PM)$";
                date = simpledateformat1.format(edittime.getText());
                edittime.addTextChangedListener(new TextWatcher() {
                    public void afterTextChanged(Editable s) {

                    if (date.matches(regex) && s.length() > 0)
                    {
                        Toast.makeText(getApplicationContext(),"valid",Toast.LENGTH_SHORT).show();
                        // or
                        //textView.setText("valid email");
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Invalid",Toast.LENGTH_SHORT).show();
                        //or
                        //textView.setText("invalid email");
                    }
                }
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    // other stuffs
                }
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    // other stuffs
                }
            });
                //close the popup window on button click
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String date = edittime.getText().toString();
                        String agentcount = agents.getText().toString();
                        //Pattern p=Pattern.compile("^[0-9]{2}:[0-9]{2}([a|p]m)?",Pattern.CASE_INSENSITIVE);
                        Pattern p = Pattern.compile("^(?:[01]\\d|2[0123]):(?:[012345]\\d)(AM|PM)$", Pattern.CASE_INSENSITIVE);
                        Matcher m = p.matcher(date);
                        boolean check = m.matches();
                        Pattern q = Pattern.compile("^[1-9][0-9]*$");
                        Matcher n = q.matcher(agentcount);
                        boolean agent_check = n.matches();
                        //Toast.makeText(getApplicationContext(), "this is "+date+"check"+check, Toast.LENGTH_SHORT).show();

                   /* try {
                        // To get the date object from the string just called the
                        // parse method and pass the time string to it. This method
                        // throws ParseException if the time string is invalid.
                        // But remember as we don't pass the date information this
                        // date object will represent the 1st of january 1970.
                        Date d = simpledateformat1.parse(date);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Pattern p = Pattern.compile("^(?:[01]\\d|2[0123]):(?:[012345]\\d) (AM|PM)$");
                    Matcher m = p.matcher(date);
                    boolean check = m.matches();*/
                //boolean check = Pattern.matches("^(?:[01]\\d|2[0123]):(?:[012345]\\d) (AM|PM)$",date);
                //if(!check) {
                //mockpoll.setText("Mock Poll Completed at " + date + "\nNo. of Agents " + agentcount);
                //popupWindow.dismiss();
                //  edittime.setError("Wrong Time Format");
                //}
                    /*
                    else{
                        error.setText("Date is Incorrect");
                    }
                        if (check == false || agent_check == false) {
                            if (check == false)
                                edittime.setError("Invalid Format");
                            if (agent_check == false)
                                agents.setError("Invalid Entry");
                        } else {
                            mockabsent.setEnabled(false);
                            mockpoll.setEnabled(false);
                            pollstart.setEnabled(true);
                            pollstart.setBackgroundColor(BLUE);
                            mockpoll.setBackgroundColor(GRAY);
                            mockabsent.setBackgroundColor(GRAY);
                            mockpoll.setText("Mock Poll Completed at " + date + "\nNo. of Agents " + agentcount);
                            popupWindow.dismiss();
                        }
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });*/
            }
        });
            /*calendar = Calendar.getInstance();
            simpledateformat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            Date = simpledateformat.format(calendar.getTime());
           if(isChecked) {
               openDialog(1);
           }*/

        pollstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*LayoutInflater layoutInflater = (LayoutInflater) Poll.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View customView = layoutInflater.inflate(R.layout.popup1, null);
                submit = customView.findViewById(R.id.confirm1);
                cancel = customView.findViewById(R.id.cancel1);
                edittime = customView.findViewById(R.id.edittime1);*/
                //agents = customView.findViewById(R.id.agents);
                reports[3] = reports[3].replace(booth,"");
                reports[3] = reports[3].replace(",,",",");
                reports[3] = reports[3].replaceAll(",$","");
                reports[3] = reports[3].replaceFirst("^,","");
                pollstart_report.setText(reports[3]);
                pollstart_report.append("\n*Not Reported");
                calendar = Calendar.getInstance();
                simpledateformat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                simpledateformat1 = new SimpleDateFormat("hh:mma");
                Date = simpledateformat1.format(calendar.getTime());
                pollstart.setText("Poll Started at " + Date);
                pollstart.setEnabled(false);
                //pollclose.setEnabled(true);
                //pollclose.setBackgroundColor(BLUE);
                pollstart.setBackgroundColor(GRAY);
                finalclose.setEnabled(true);
                poll_votersqueue.setEnabled(true);
                finalclose.setBackgroundColor(BLUE);
                poll_votersqueue.setBackgroundColor(BLUE);
                HashMap<String, String> getData = new HashMap<String, String>();
                getData.put("date",Date);
                getData.put("mockpoll","-1");
                getData.put("pollstart","Y");
                getData.put("voterqueue","-1");
                getData.put("finalclose","-1");
                getData.put("pollparty","-1");
                PostResponseAsyncTask task2 = new PostResponseAsyncTask(Poll.this, getData, new AsyncResponse() {
                    @Override
                    public void processFinish(String s) {
                        if (!(s.isEmpty())) {
                            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(getApplicationContext(), "Something went wrong! Try later!", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                task2.execute("http://192.168.1.101/election/pollstatus.php");

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
                //edittime.setText(Date);
                //agents.setVisibility(View.GONE);
                //instantiate popup window
                /*popupWindow = new PopupWindow(customView, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                popupWindow.setHeight(900);
                //display the popup window
                popupWindow.showAtLocation(layoutpoll, Gravity.CENTER, 0, 0);
                popupWindow.setFocusable(true);
                popupWindow.update();
                //close the popup window on button click
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String date = edittime.getText().toString();
                        Pattern p = Pattern.compile("^(?:[01]\\d|2[0123]):(?:[012345]\\d)(AM|PM)$", Pattern.CASE_INSENSITIVE);
                        Matcher m = p.matcher(date);
                        boolean check = m.matches();
                        if (check == false) {
                            if (check == false)
                                edittime.setError("Invalid Format");
                        } else {
                            pollstart.setText("Poll Started at " + date);
                            pollstart.setEnabled(false);
                            pollclose.setEnabled(true);
                            pollclose.setBackgroundColor(BLUE);
                            pollstart.setBackgroundColor(GRAY);
                            popupWindow.dismiss();
                        }
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });*/
            }
        });
        /*pollclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = (LayoutInflater) Poll.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View customView = layoutInflater.inflate(R.layout.popup1, null);
                submit = customView.findViewById(R.id.confirm1);
                cancel = customView.findViewById(R.id.cancel1);
                edittime = customView.findViewById(R.id.edittime1);
                //votersqueue = customView.findViewById(R.id.votersqueue);
                calendar = Calendar.getInstance();
                simpledateformat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                simpledateformat1 = new SimpleDateFormat("hh:mma");
                Date = simpledateformat1.format(calendar.getTime());
                edittime.setText(Date);
                //agents.setVisibility(View.GONE);
                //instantiate popup window
                popupWindow = new PopupWindow(customView, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                popupWindow.setHeight(900);
                //display the popup window
                popupWindow.showAtLocation(layoutpoll, Gravity.CENTER, 0, 0);
                popupWindow.setFocusable(true);
                popupWindow.update();
                //close the popup window on button click
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String date = edittime.getText().toString();
                        //String voterqueue = votersqueue.getText().toString();
                        Pattern p = Pattern.compile("^(?:[01]\\d|2[0123]):(?:[012345]\\d)(AM|PM)$", Pattern.CASE_INSENSITIVE);
                        Matcher m = p.matcher(date);
                        boolean check = m.matches();
                        if (check == false) {
                            if (check == false)
                                edittime.setError("Invalid Format");
                        } else {
                            pollclose.setText("Poll Closed at " + date);
                            pollclose.setEnabled(false);
                            finalclose.setEnabled(true);
                            poll_votersqueue.setEnabled(true);
                            pollclose.setBackgroundColor(GRAY);
                            finalclose.setBackgroundColor(BLUE);
                            poll_votersqueue.setBackgroundColor(BLUE);
                            popupWindow.dismiss();
                        }
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
            }
        });*/
        finalclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*LayoutInflater layoutInflater = (LayoutInflater) Poll.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View customView = layoutInflater.inflate(R.layout.popup1, null);
                submit = customView.findViewById(R.id.confirm1);
                cancel = customView.findViewById(R.id.cancel1);
                edittime = customView.findViewById(R.id.edittime1);*/
                HashMap<String, String> getData = new HashMap<String, String>();
                getData.put("booth",booth);
                getData.put("timeselected","5PM");
                PostResponseAsyncTask task2 = new PostResponseAsyncTask(Poll.this, getData, new AsyncResponse() {
                    @Override
                    public void processFinish(String s) {
                        if (!(s.isEmpty())) {
                            //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                            status = s.split(",");
                            if(status[0].equals("-1")){
                                finalclose.setError("5PM Turnout Count Not Given");
                            }
                            else {
                                reports[4] = reports[4].replace(booth,"");
                                reports[5] = reports[5].replace(booth,"");
                                reports[4] = reports[4].replace(",,",",");
                                reports[4] = reports[4].replaceAll(",$","");
                                reports[4] = reports[4].replaceFirst("^,","");
                                reports[5] = reports[5].replace(",,",",");
                                reports[5] = reports[5].replaceAll(",$","");
                                reports[5] = reports[5].replaceFirst("^,","");
                                poll_votersqueue_report.setText(reports[4]);
                                finalpoll_report.setText(reports[5]);
                                poll_votersqueue_report.append("\n*Not Reported");
                                finalpoll_report.append("\n*Not Reported");
                                calendar = Calendar.getInstance();
                                simpledateformat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                                simpledateformat1 = new SimpleDateFormat("hh:mma");
                                Date = simpledateformat1.format(calendar.getTime());
                                //edittime.setText(Date);
                                //agents.setVisibility(View.GONE);
                                //instantiate popup window
                                pollparty.setEnabled(true);
                                poll_votersqueue.setEnabled(false);
                                poll_votersqueue.setBackgroundColor(GRAY);
                                pollparty.setBackgroundColor(BLUE);
                                finalclose.setText("Poll Concluded at " + Date);
                                finalclose.setEnabled(false);
                                finalclose.setBackgroundColor(GRAY);
                                HashMap<String, String> getData = new HashMap<String, String>();
                                getData.put("date",Date);
                                getData.put("mockpoll","-1");
                                getData.put("pollstart","-1");
                                getData.put("voterqueue","-1");
                                getData.put("finalclose","Y");
                                getData.put("pollparty","-1");
                                PostResponseAsyncTask task2 = new PostResponseAsyncTask(Poll.this, getData, new AsyncResponse() {
                                    @Override
                                    public void processFinish(String s) {
                                        if (!(s.isEmpty())) {
                                            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();

                                        } else {
                                            Toast.makeText(getApplicationContext(), "Something went wrong! Try later!", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });
                                task2.execute("http://192.168.1.101/election/pollstatus.php");

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

                /*popupWindow = new PopupWindow(customView, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                popupWindow.setHeight(900);
                //display the popup window
                popupWindow.showAtLocation(layoutpoll, Gravity.CENTER, 0, 0);
                popupWindow.setFocusable(true);
                popupWindow.update();
                //close the popup window on button click
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String date = edittime.getText().toString();
                        Pattern p = Pattern.compile("^(?:[01]\\d|2[0123]):(?:[012345]\\d)(AM|PM)$", Pattern.CASE_INSENSITIVE);
                        Matcher m = p.matcher(date);
                        boolean check = m.matches();
                        if (check == false) {
                            if (check == false)
                                edittime.setError("Invalid Format");
                        } else {
                            finalclose.setText("Final Poll Closure at " + date);
                            finalclose.setEnabled(false);
                            finalclose.setBackgroundColor(GRAY);
                            popupWindow.dismiss();
                        }
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });*/
            }
        });
        poll_votersqueue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = (LayoutInflater) Poll.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View customView = layoutInflater.inflate(R.layout.popup2, null);
                submit = customView.findViewById(R.id.confirm2);
                cancel = customView.findViewById(R.id.cancel2);
                //edittime = customView.findViewById(R.id.edittime2);
                votersqueue = customView.findViewById(R.id.votersqueue);
                //agents = customView.findViewById(R.id.agents);
                /*calendar = Calendar.getInstance();
                simpledateformat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                simpledateformat1 = new SimpleDateFormat("hh:mma");
                Date = simpledateformat1.format(calendar.getTime());
                edittime.setText(Date);*/
                //agents.setVisibility(View.GONE);
                //instantiate popup window
                popupWindow = new PopupWindow(customView, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                popupWindow.setHeight(900);
                //display the popup window
                popupWindow.showAtLocation(layoutpoll, Gravity.CENTER, 0, 0);
                popupWindow.setFocusable(true);
                popupWindow.update();
                //close the popup window on button click
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       // String date = edittime.getText().toString();
                        String voterqueue = votersqueue.getText().toString();
                       // Pattern p = Pattern.compile("^(?:[01]\\d|2[0123]):(?:[012345]\\d)(AM|PM)$", Pattern.CASE_INSENSITIVE);
                        //Matcher m = p.matcher(date);
                        //boolean check = m.matches();
                        Pattern q = Pattern.compile("^[1-9][0-9]*$");
                        Matcher n = q.matcher(voterqueue);
                        boolean queue_check = n.matches();
                        if (queue_check == false) {
                            if (queue_check == false)
                                votersqueue.setError("Kindly Click Poll Concluded if no voters in queue");
                        } else {
                            reports[4] = reports[4].replace(booth,"");
                            reports[4] = reports[4].replace(",,",",");
                            reports[4] = reports[4].replaceAll(",$","");
                            reports[4] = reports[4].replaceFirst("^,","");
                            poll_votersqueue_report.setText(reports[4]);
                            poll_votersqueue_report.append("\n*Not Reported");
                            poll_votersqueue.setText("Voters in queue at time of closing " + voterqueue);
                            poll_votersqueue.setEnabled(false);
                            poll_votersqueue.setBackgroundColor(GRAY);
                            popupWindow.dismiss();
                            HashMap<String, String> getData = new HashMap<String, String>();
                            getData.put("date",Date);
                            getData.put("mockpoll","-1");
                            getData.put("pollstart","-1");
                            getData.put("voterqueue",voterqueue);
                            getData.put("finalclose","-1");
                            getData.put("pollparty","-1");
                            PostResponseAsyncTask task2 = new PostResponseAsyncTask(Poll.this, getData, new AsyncResponse() {
                                @Override
                                public void processFinish(String s) {
                                    if (!(s.isEmpty())) {
                                        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();

                                    } else {
                                        Toast.makeText(getApplicationContext(), "Something went wrong! Try later!", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                            task2.execute("http://192.168.1.101/election/pollstatus.php");

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
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
            }
        });
        pollparty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*LayoutInflater layoutInflater = (LayoutInflater) Poll.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View customView = layoutInflater.inflate(R.layout.popup1, null);
                submit = customView.findViewById(R.id.confirm1);
                cancel = customView.findViewById(R.id.cancel1);
                edittime = customView.findViewById(R.id.edittime1);*/
                //agents = customView.findViewById(R.id.agents);
                reports[6] = reports[6].replace(booth,"");
                reports[6] = reports[6].replace(",,",",");
                reports[6] = reports[6].replaceAll(",$","");
                reports[6] = reports[6].replaceFirst("^,","");
                pollparty_report.setText(reports[6]);
                pollparty_report.append("\n*Not Reported");
                calendar = Calendar.getInstance();
                simpledateformat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                simpledateformat1 = new SimpleDateFormat("hh:mma");
                Date = simpledateformat1.format(calendar.getTime());
                pollparty.setText("Polling Party Reached at " + Date);
                pollparty.setEnabled(false);
                pollparty.setBackgroundColor(GRAY);
                HashMap<String, String> getData = new HashMap<String, String>();
                getData.put("date",Date);
                getData.put("mockpoll","-1");
                getData.put("pollstart","-1");
                getData.put("voterqueue","-1");
                getData.put("finalclose","-1");
                getData.put("pollparty","Y");
                PostResponseAsyncTask task2 = new PostResponseAsyncTask(Poll.this, getData, new AsyncResponse() {
                    @Override
                    public void processFinish(String s) {
                        if (!(s.isEmpty())) {
                            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(getApplicationContext(), "Something went wrong! Try later!", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                task2.execute("http://192.168.1.101/election/pollstatus.php");

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
                //edittime.setText(Date);
                //agents.setVisibility(View.GONE);
                //instantiate popup window
                /*popupWindow = new PopupWindow(customView, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                popupWindow.setHeight(900);
                //display the popup window
                popupWindow.showAtLocation(layoutpoll, Gravity.CENTER, 0, 0);
                popupWindow.setFocusable(true);
                popupWindow.update();
                //close the popup window on button click
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String date = edittime.getText().toString();
                        Pattern p = Pattern.compile("^(?:[01]\\d|2[0123]):(?:[012345]\\d)(AM|PM)$", Pattern.CASE_INSENSITIVE);
                        Matcher m = p.matcher(date);
                        boolean check = m.matches();
                        if (check == false) {
                            if (check == false)
                                edittime.setError("Invalid Format");
                        } else {
                            pollparty.setText("Polling Party Reached at " + date);
                            pollparty.setEnabled(false);
                            pollparty.setBackgroundColor(GRAY);
                            popupWindow.dismiss();
                        }
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });*/
            }
        });
    }
}
