package com.example.asus.election;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.graphics.Color.GRAY;

//public class Poll extends AppCompatActivity implements ExampleDialog.ExampleDialogListener
public class Poll extends AppCompatActivity{
    Button mockpoll,mockabsent,pollstart,pollclose,finalclose,pollparty;
    TextView datetime2;
    RelativeLayout layoutpoll;
    Calendar calendar;
    SimpleDateFormat simpledateformat;
    String Date;
    EditText edittime,agents,votersqueue;
    PopupWindow popupWindow;
    Button submit,cancel;
    String mp,ma,ps,pc,fc,pp;
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
                    Date = simpledateformat.format(calendar.getTime());
                    edittime.setText(Date);
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
                            mockabsent.setEnabled(false);
                            mockpoll.setEnabled(false);
                            mockpoll.setBackgroundColor(GRAY);
                            mockabsent.setBackgroundColor(GRAY);
                            String date = edittime.getText().toString();
                            String agentcount = agents.getText().toString();
                            mockpoll.setText("Mock Poll Completed at " + date + "\nNo. of Agents " + agentcount);
                            popupWindow.dismiss();
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
                    Date = simpledateformat.format(calendar.getTime());
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
                            pollstart.setText("Poll Started at "+date);
                            pollstart.setEnabled(false);
                            pollstart.setBackgroundColor(GRAY);
                            popupWindow.dismiss();
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
                    View customView = layoutInflater.inflate(R.layout.popup2, null);
                    submit = customView.findViewById(R.id.confirm2);
                    cancel = customView.findViewById(R.id.cancel2);
                    edittime = customView.findViewById(R.id.edittime2);
                    votersqueue = customView.findViewById(R.id.votersqueue);
                    calendar = Calendar.getInstance();
                    simpledateformat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    Date = simpledateformat.format(calendar.getTime());
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
                            String voterqueue = votersqueue.getText().toString();
                            pollclose.setText("Poll Closed at "+date+"\nVoters in Queue at time of Closing "+voterqueue);
                            pollclose.setEnabled(false);
                            pollclose.setBackgroundColor(GRAY);
                            popupWindow.dismiss();
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
                    Date = simpledateformat.format(calendar.getTime());
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
                            finalclose.setText("Final Poll Closure at "+date);
                            finalclose.setEnabled(false);
                            finalclose.setBackgroundColor(GRAY);
                            popupWindow.dismiss();
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
                Date = simpledateformat.format(calendar.getTime());
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
                        pollparty.setText("Polling Party Reached at "+date);
                        pollparty.setEnabled(false);
                        pollparty.setBackgroundColor(GRAY);
                        popupWindow.dismiss();
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
           // @Override
            //public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
              //  if(isChecked) {


    }

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
}
