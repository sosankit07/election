package com.example.asus.election;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static android.graphics.Color.BLUE;
import static android.graphics.Color.GRAY;

public class Prepoll extends AppCompatActivity {
    TextView datetime3, prepoll_error,dispatch_status,arrived_status;
    Button partydispatch, partyarrived;
    Button submit, cancel;
    EditText edittime;
    Calendar calendar;
    SimpleDateFormat simpledateformat, simpledateformat1;
    String Date;
    PopupWindow popupWindow;
    RelativeLayout layoutprepoll;
    Spinner sp;
    String[] booths;
    String[] status;
    String[] reports;
    String temp;
    int length;
    final List<String> listbooth = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prepoll);
        layoutprepoll = findViewById(R.id.layoutprepoll);
        datetime3 = findViewById(R.id.datetime3);
        partydispatch = findViewById(R.id.partydispatch);
        partyarrived = findViewById(R.id.partyarrived);
        prepoll_error = findViewById(R.id.prepoll_error);
        sp = findViewById(R.id.pre_booth);
        dispatch_status = findViewById(R.id.dispatch_status);
        arrived_status = findViewById(R.id.arrived_status);
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
                                datetime3.setText(dateString);
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        t.start();
        HashMap<String, String> getData = new HashMap<String, String>();

        PostResponseAsyncTask task2 = new PostResponseAsyncTask(Prepoll.this, getData, new AsyncResponse() {
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
            temp = task2.execute("http://192.168.1.101/election/booths.php").get();
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

        PostResponseAsyncTask task3 = new PostResponseAsyncTask(Prepoll.this, getData1, new AsyncResponse() {
            @Override
            public void processFinish(String s) {
                if (!(s.isEmpty())) {
                    //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                    reports = s.split(";");
                    dispatch_status.setText(reports[1]);
                    dispatch_status.append("\n*Not Reported");
                    arrived_status.setText(reports[2]);
                    arrived_status.append("\n*Not Reported");
                } else {
                    Toast.makeText(getApplicationContext(), "Something went wrong! Try later!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        task3.execute("http://192.168.1.101/election/prepoll_reports.php");

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
                    prepoll_error.setText("*Select Any Booth");
                    prepoll_error.setError("");
                    partydispatch.setText("POLLING PARTY DISPATCHED");
                    partyarrived.setText("PARTY ARRIVED");
                    partyarrived.setBackgroundColor(GRAY);
                    partydispatch.setBackgroundColor(BLUE);
                } else {
                    prepoll_error.setText("");
                    prepoll_error.setError(null);
                    partydispatch.setText("POLLING PARTY DISPATCHED");
                    partyarrived.setText("PARTY ARRIVED");
                    prepoll_layout(position);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void prepoll_layout(int position) {
        final String booth = sp.getItemAtPosition(position).toString();
        HashMap<String, String> getData = new HashMap<String, String>();
        getData.put("booth",booth);
        PostResponseAsyncTask task2 = new PostResponseAsyncTask(Prepoll.this, getData, new AsyncResponse() {
            @Override
            public void processFinish(String s) {
                if (!(s.isEmpty())) {
                    //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                    status = s.split(",");
                    if(status[0].equals("Y")){
                        partydispatch.setText("Polling Party Dispatched at "+status[1]);
                        partydispatch.setEnabled(false);
                        partydispatch.setBackgroundColor(GRAY);
                        if(status[2].equals("Y")){
                            partyarrived.setText("Polling Party Arrived at "+status[3]);
                            partyarrived.setEnabled(false);
                            partyarrived.setBackgroundColor(GRAY);
                        }
                        else {
                            partyarrived.setEnabled(true);
                            partyarrived.setBackgroundColor(BLUE);
                        }
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
        partydispatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* LayoutInflater layoutInflater = (LayoutInflater) Prepoll.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View customView = layoutInflater.inflate(R.layout.popup1, null);
                submit = customView.findViewById(R.id.confirm1);
                cancel = customView.findViewById(R.id.cancel1);
                edittime = customView.findViewById(R.id.edittime1);
                agents = customView.findViewById(R.id.agents);*/
                reports[1] = reports[1].replace(booth,"");
                reports[1] = reports[1].replace(",,",",");
                reports[1] = reports[1].replaceAll(",$","");
                reports[1] = reports[1].replaceFirst("^,","");
                dispatch_status.setText(reports[1]);
                dispatch_status.append("\n*Not Reported");
                calendar = Calendar.getInstance();
                simpledateformat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                simpledateformat1 = new SimpleDateFormat("hh:mma");
                Date = simpledateformat1.format(calendar.getTime());
                partydispatch.setText("Party dispatched at " + Date);
                partydispatch.setEnabled(false);
                partydispatch.setBackgroundColor(GRAY);
                partyarrived.setEnabled(true);
                partyarrived.setBackgroundColor(BLUE);
                HashMap<String, String> getData = new HashMap<String, String>();
                getData.put("date",Date);
                getData.put("prepoll","Dispatch");
                PostResponseAsyncTask task2 = new PostResponseAsyncTask(Prepoll.this, getData, new AsyncResponse() {
                    @Override
                    public void processFinish(String s) {
                        if (!(s.isEmpty())) {
                            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(getApplicationContext(), "Something went wrong! Try later!", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                task2.execute("http://192.168.1.101/election/prepollstatus.php");

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
                /*edittime.setText(Date);
                agents.setVisibility(View.GONE);
                instantiate popup window
                popupWindow = new PopupWindow(customView, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                popupWindow.setHeight(900);
                //display the popup window
                popupWindow.showAtLocation(layoutprepoll, Gravity.CENTER, 0, 0);
                popupWindow.setFocusable(true);
                popupWindow.update();
                //close the popup window on button click
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String date = edittime.getText().toString();
                        Pattern p = Pattern.compile("^(?:[01]\\d|2[0123]):(?:[012345]\\d)(AM|PM)$",Pattern.CASE_INSENSITIVE);
                        Matcher m = p.matcher(date);
                        boolean check = m.matches();
                        if(check == false) {
                            if (check == false)
                                edittime.setError("Invalid Format");
                        }
                        else {
                            partydispatch.setText("Party dispatched at " + date);
                            partydispatch.setEnabled(false);
                            partydispatch.setBackgroundColor(GRAY);
                            partyarrived.setEnabled(true);
                            partyarrived.setBackgroundColor(BLUE);
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
        partyarrived.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*LayoutInflater layoutInflater = (LayoutInflater) Prepoll.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View customView = layoutInflater.inflate(R.layout.popup1, null);
                submit = customView.findViewById(R.id.confirm1);
                cancel = customView.findViewById(R.id.cancel1);
                edittime = customView.findViewById(R.id.edittime1);
                agents = customView.findViewById(R.id.agents);*/
                reports[2] = reports[2].replace(booth,"");
                reports[2] = reports[2].replace(",,",",");
                reports[2] = reports[2].replaceAll(",$","");
                reports[2] = reports[2].replaceFirst("^,","");
                arrived_status.setText(reports[2]);
                arrived_status.append("\n*Not Reported");
                calendar = Calendar.getInstance();
                simpledateformat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                simpledateformat1 = new SimpleDateFormat("hh:mma");
                Date = simpledateformat1.format(calendar.getTime());
                partyarrived.setText("Party arrived at " + Date);
                partyarrived.setEnabled(false);
                partyarrived.setBackgroundColor(GRAY);
                HashMap<String, String> getData = new HashMap<String, String>();
                getData.put("date",Date);
                getData.put("prepoll","Arrived");
                PostResponseAsyncTask task2 = new PostResponseAsyncTask(Prepoll.this, getData, new AsyncResponse() {
                    @Override
                    public void processFinish(String s) {
                        if (!(s.isEmpty())) {
                            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(getApplicationContext(), "Something went wrong! Try later!", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                task2.execute("http://192.168.1.101/election/prepollstatus.php");

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
                /*edittime.setText(Date);
                agents.setVisibility(View.GONE);
                instantiate popup window
                popupWindow = new PopupWindow(customView, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                popupWindow.setHeight(900);
                //display the popup window
                popupWindow.showAtLocation(layoutprepoll, Gravity.CENTER, 0, 0);
                popupWindow.setFocusable(true);
                popupWindow.update();
                //close the popup window on button click
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String date = edittime.getText().toString();
                        Pattern p = Pattern.compile("^(?:[01]\\d|2[0123]):(?:[012345]\\d)(AM|PM)$",Pattern.CASE_INSENSITIVE);
                        Matcher m = p.matcher(date);
                        boolean check = m.matches();
                        if(check == false) {
                            if (check == false)
                                edittime.setError("Invalid Format");
                        }
                        else {
                            partyarrived.setText("Party arrived at " + date);
                            partyarrived.setEnabled(false);
                            partyarrived.setBackgroundColor(GRAY);
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

