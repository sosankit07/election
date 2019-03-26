package com.example.asus.election;

import android.content.Context;
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

public class Prepoll extends AppCompatActivity {
    TextView datetime3;
    Button partydispatch,partyarrived;
    Button submit,cancel;
    EditText edittime;
    Calendar calendar;
    SimpleDateFormat simpledateformat;
    String Date;
    PopupWindow popupWindow;
    RelativeLayout layoutprepoll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prepoll);
        layoutprepoll = findViewById(R.id.layoutprepoll);
        datetime3 = findViewById(R.id.datetime3);
        partydispatch = findViewById(R.id.partydispatch);
        partyarrived = findViewById(R.id.partyarrived);
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
        partydispatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    LayoutInflater layoutInflater = (LayoutInflater) Prepoll.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
                    popupWindow.showAtLocation(layoutprepoll, Gravity.CENTER, 0, 0);
                    popupWindow.setFocusable(true);
                    popupWindow.update();
                    //close the popup window on button click
                    submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String date = edittime.getText().toString();
                            partydispatch.setText("Party dispatched at "+date);
                            partydispatch.setEnabled(false);
                            partydispatch.setBackgroundColor(GRAY);
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
        partyarrived.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LayoutInflater layoutInflater = (LayoutInflater) Prepoll.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
                    popupWindow.showAtLocation(layoutprepoll, Gravity.CENTER, 0, 0);
                    popupWindow.setFocusable(true);
                    popupWindow.update();
                    //close the popup window on button click
                    submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String date = edittime.getText().toString();
                            partyarrived.setText("Party arrived at "+date);
                            partyarrived.setEnabled(false);
                            partyarrived.setBackgroundColor(GRAY);
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
    }
}
