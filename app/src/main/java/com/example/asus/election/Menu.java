package com.example.asus.election;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Menu extends AppCompatActivity {
    Button prepoll,poll,turnout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        prepoll = findViewById(R.id.prepoll);
        poll = findViewById(R.id.poll);
        turnout = findViewById(R.id.turnout);
        prepoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this,Prepoll.class);
                startActivity(intent);
            }
        });
        poll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this,Poll.class);
                startActivity(intent);
            }
        });
        turnout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this,Turnout.class);
                startActivity(intent);
            }
        });
    }
}
