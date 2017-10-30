package com.example.nitantsood.othello;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    int n;
    Button button1,button2,button3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        callintent();
    }
    @Override
    public void onClick(View v){
        if(v.getId()==R.id.easy){
            n=8;
        }
        else if(v.getId()==R.id.medium) {
            n = 10;
        }
        else if(v.getId()==R.id.hard) {
            n = 12;
        }
        Intent intent=new Intent(MainActivity.this,MainGame.class);
        intent.putExtra("level",n);
        startActivity(intent);
    }
    void callintent(){
        button1=(Button)findViewById(R.id.easy);
        button1.setOnClickListener(MainActivity.this);
        button2=(Button)findViewById(R.id.medium);
        button2.setOnClickListener(MainActivity.this);
        button3=(Button)findViewById(R.id.hard);
        button3.setOnClickListener(MainActivity.this);
    }
}
