package com.example.nitantsood.othello;

import android.content.Context;
import android.widget.Button;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyButton extends ImageButton {
    int rowNumber;
    int colNumber;
    int sumConversion;
    int maybe,confirmed;
    int color;
    int loc[]=new int[8];
    int moc[]=new int[8];
    public MyButton(Context context) {
        super(context);
        confirmed=0;
        maybe=0;
        sumConversion=0;
        color=MainGame.FREE;
        for(int i=0;i<8;i++){
            loc[i]=0;
            moc[i]=0;
        }
    }
}
