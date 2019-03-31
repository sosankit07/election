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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.graphics.Color.BLUE;
import static android.graphics.Color.GRAY;

//public class Poll extends AppCompatActivity implements ExampleDialog.ExampleDialogListener

public class Poll extends AppCompatActivity{
    Button mockpoll,mockabsent,pollstart,pollclose,finalclose,pollparty,poll_votersqueue;
    TextView datetime2,error;
    RelativeLayout layoutpoll;
    Calendar calendar;
    SimpleDateFormat simpledateformat,simpledateformat1;
    String Date;
    Date d;
    EditText edittime,agents,votersqueue;
    PopupWindow popupWindow;
    Button submit,cancel;
    String mp,ma,ps,pc,fc,pp;
    Spinner sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poll);
        layoutpoll = findViewById(R.id.layoutpoll);
        mockpoll = findViewById(R.id.mockpoll);
        mockabsent = findViewById(R.id.mockpoll_absence);
        pollstart = findViewById(R.id.pollstart);
        pollclose = findViewById(R.id.pollclose);
        finalclose = findViewById(R.id.finalpoll);
        pollparty = findViewById(R.id.pollparty_reached);
        datetime2 = findViewById(R.id.datetime2);
        poll_votersqueue = findViewById(R.id.poll_votersqueue);
        error = findViewById(R.id.error);
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
        sp = findViewById(R.id.poll_booth);
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
                layout(position);
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
    /*
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
        //mockpoll.setBackgroundColor(BLUE);
        //mockabsent.setBackgroundColor(BLUE);
        //mockpoll.setEnabled(true);
        //mockabsent.setEnabled(true);
        if (position == 0) {
            error.setText("*Select Any Booth");
            error.setError("");
        } else {
            error.setText("");
            error.setError(null);
            mockpoll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LayoutInflater layoutInflater = (LayoutInflater) Poll.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View customView = layoutInflater.inflate(R.layout.popup, null);
                    submit = customView.findViewById(R.id.confirm);
                    cancel = customView.findViewById(R.id.cancel);
                    edittime = customView.findViewById(R.id.edittime);
                    agents = customView.findViewById(R.id.agents);
                    calendar = Calendar.getInstance();
                    simpledateformat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    simpledateformat1 = new SimpleDateFormat("hh:mma");
                    Date = simpledateformat1.format(calendar.getTime());
                    edittime.setText(Date);
                    //instantiate popup window
                    popupWindow = new PopupWindow(customView, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
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
                });*/
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
                        }*/
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
                    });
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
                    LayoutInflater layoutInflater = (LayoutInflater) Poll.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View customView = layoutInflater.inflate(R.layout.popup1, null);
                    submit = customView.findViewById(R.id.confirm1);
                    cancel = customView.findViewById(R.id.cancel1);
                    edittime = customView.findViewById(R.id.edittime1);
                    //agents = customView.findViewById(R.id.agents);
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
                    });
                }
            });
            pollclose.setOnClickListener(new View.OnClickListener() {
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
            });
            finalclose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LayoutInflater layoutInflater = (LayoutInflater) Poll.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View customView = layoutInflater.inflate(R.layout.popup1, null);
                    submit = customView.findViewById(R.id.confirm1);
                    cancel = customView.findViewById(R.id.cancel1);
                    edittime = customView.findViewById(R.id.edittime1);
                    //agents = customView.findViewById(R.id.agents);
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
                    });
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
                                    votersqueue.setError("Invalid Entry");
                            } else {
                                poll_votersqueue.setText("Voters in queue at time of closing " + voterqueue);
                                poll_votersqueue.setEnabled(false);
                                pollparty.setEnabled(true);
                                pollparty.setBackgroundColor(BLUE);
                                poll_votersqueue.setBackgroundColor(GRAY);
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
            });
            pollparty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LayoutInflater layoutInflater = (LayoutInflater) Poll.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View customView = layoutInflater.inflate(R.layout.popup1, null);
                    submit = customView.findViewById(R.id.confirm1);
                    cancel = customView.findViewById(R.id.cancel1);
                    edittime = customView.findViewById(R.id.edittime1);
                    //agents = customView.findViewById(R.id.agents);
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
                    });
                }
            });
        }
    }
}
