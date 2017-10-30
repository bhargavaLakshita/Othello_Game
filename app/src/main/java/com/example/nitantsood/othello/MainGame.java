package com.example.nitantsood.othello;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.zip.Inflater;

/**
 * Created by NITANT SOOD on 19-06-2017.
 * */

public class MainGame extends AppCompatActivity implements View.OnClickListener {
    public int k,reasonToOver=0;
    MyButton buttons[][];
    MyButton W;
    public final static int WHITE=1;
    public final static int BLACK =2;
    public final static int FREE=0;
    LinearLayout mainLayout;
    LinearLayout upperRow;
    LinearLayout rowLayout[];
    Button on_off;
    TextView T[]=new TextView[2];
    int[] col_8={0,1,1,1,0,-1,-1,-1};
    int[] row_8={-1,-1,0,1,1,1,0,-1};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_game);
        mainLayout=(LinearLayout) findViewById(R.id.mainLayout) ;
        Intent intent = getIntent();
        k = intent.getIntExtra("level", 8);
        setupUpBoard();
    }
    public boolean onCreateOptionsMenu(Menu menu1){
        getMenuInflater().inflate(R.menu.main_menu,menu1);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        int id=item.getItemId();
        if(id==R.id.newGame){
            AlertDialog.Builder  builder=new AlertDialog.Builder(this);
            builder.setTitle("New Game");
            builder.setMessage("Please confirm !!");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    resetAll();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog dialog=builder.create();
            dialog.show();
        }
        else if(id==R.id.hint){
            for(int i=0;i<k;i++){
                for(int j=0;j<k;j++){
                    if(buttons[i][j].color==FREE && buttons[i][j].confirmed==1){
                        buttons[i][j].setBackground(ContextCompat.getDrawable(this,R.drawable.rectangle1));
                    }
                }
            }
        }
        return true;
    }
    public void resetAll(){
        reasonToOver=0;
        buttons=null;
        W=null;
        mainLayout=null;
        upperRow=null;
        rowLayout=null;
        on_off=null;
        T=null;
        col_8=null;
        row_8=null;
        Intent intent=new Intent(MainGame.this,MainActivity.class);
        startActivity(intent);
    }
    void setupUpBoard() {
        rowLayout = new LinearLayout[k];
        buttons = new MyButton[k][k];
        mainLayout.removeAllViews();
        int color = Color.parseColor("#00000000");
        mainLayout.setBackgroundColor(color);
        upperRow = new LinearLayout(this);
        LinearLayout.LayoutParams param1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 50, 1);
        upperRow.setLayoutParams(param1);
        upperRow.setOrientation(LinearLayout.HORIZONTAL);
        for (int i = 0; i < 2; i++) {
            T[i] = new TextView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
            params.setMargins(5, 5, 5, 5);
            T[i].setLayoutParams(params);
            T[i].setTextSize(50);
        }
        upperRow.addView(T[0]);
        on_off = new Button(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, 1);
        params.setMargins(5, 5, 5, 5);
        on_off.setLayoutParams(params);
        on_off.setText("PLAY");
        on_off.setId(R.id.bun);
        on_off.setOnClickListener(this);
        on_off.setBackground(ContextCompat.getDrawable(this, R.drawable.reset_button));
        upperRow.addView(on_off);
        upperRow.addView(T[1]);
        upperRow.setBackgroundColor(color);
        mainLayout.addView(upperRow);
        int textColor = Color.parseColor("#ffcc0000");
        T[0].setTextColor(textColor);
        T[1].setTextColor(textColor);
        T[1].setGravity(Gravity.RIGHT);
        T[0].setText("2");
        T[1].setText("2");
        for (int i = 0; i < k; i++) {
            rowLayout[i] = new LinearLayout(this);
            LinearLayout.LayoutParams param2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1);
            //params.setMargins(5,5,5,5);
            rowLayout[i].setLayoutParams(param2);
            rowLayout[i].setOrientation(LinearLayout.HORIZONTAL);
            mainLayout.addView(rowLayout[i]);
        }
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < k; j++) {
                buttons[i][j] = new MyButton(this);
                LinearLayout.LayoutParams param2 = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
                //params.setMargins(5,5,5,5);
                buttons[i][j].setLayoutParams(param2);
                buttons[i][j].setOnClickListener(this);
                //buttons[i][j].setOnLongClickListener(this);
                buttons[i][j].setBackground(ContextCompat.getDrawable(this, R.drawable.rectangle));
                rowLayout[i].addView(buttons[i][j]);
            }
        }
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < k; j++) {
                buttons[i][j].rowNumber = i;
                buttons[i][j].colNumber = j;
            }
        }
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Rules");
        builder.setMessage("1. Yours is Black and computer plays with the White \n 2.You have the First Turn \n 3. Press Play Button above to let the computer have the turn \n Good Luck !!");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog=builder.create();
        dialog.show();
        initialSetup();
        userSetup();
    }
    void initialSetup(){
        buttons[k/2][k/2].setImageResource(R.drawable.black);
        buttons[k/2][k/2].color=BLACK;
        buttons[k/2][k/2].setScaleType(ImageView.ScaleType.FIT_XY);
        buttons[(k/2)-1][(k/2)-1].setImageResource(R.drawable.black);
        buttons[(k/2)-1][(k/2)-1].setScaleType(ImageView.ScaleType.FIT_XY);
        buttons[(k/2)-1][(k/2)-1].color=BLACK;
        buttons[(k/2)-1][k/2].setImageResource(R.drawable.white);
        buttons[(k/2)-1][k/2].setScaleType(ImageView.ScaleType.FIT_XY);
        buttons[(k/2)-1][k/2].color=WHITE;
        buttons[k/2][(k/2)-1].setImageResource(R.drawable.white);
        buttons[k/2][(k/2)-1].setScaleType(ImageView.ScaleType.FIT_XY);
        buttons[k/2][(k/2)-1].color=WHITE;
        return;
    }//sets the first four keys
    boolean blackChecker(MyButton b,int row,int col){
        int check=0;
        for(int i=0;i<8;i++){
            if(row==0){
                if(col==0 && (i==0 || i==1 || i==5 || i==6 || i==7)){
                    continue;
                }
                else if( col==k-1 && (i==0 || i==1 || i==2 || i==3 || i==7)){
                    continue;
                }
                else if (i==0 || i==1 || i==7){
                    continue;
                }
            }
            else if(row==k-1){
                if(col==0 && (i==3 || i==4||i==5||i==6||i==7)){
                    continue;
                }
                else if(col==k-1 && (i==1||i==2||i==3||i==4||i==5)){
                    continue;
                }
                else if(i==3 || i==4 || i==5){
                    continue;
                }
            }
            else if(col==0 && (i==5||i==6||i==7)){
                continue;
            }
            else if(col==k-1 && (i==1||i==2||i==3)){
                continue;
            }
            if(buttons[row+row_8[i]][col+col_8[i]].color==WHITE){
                b.loc[i]=1;
                check=1;
            }
        }
        if(check==1)
            return true;
        else
            return false;
    }//it evaluates and make loc[i]=1 for white around button b and tells if there is even single white around or not
    boolean whiteChecker(MyButton b,int row,int col){
        int check=0;
        for(int i=0;i<8;i++){
            if(row==0){
                if(col==0 && (i==0 || i==1 || i==5 || i==6 || i==7)){
                    continue;
                }
                else if( col==k-1 && (i==0 || i==1 || i==2 || i==3 || i==7)){
                    continue;
                }
                else if (i==0 || i==1 || i==7){
                    continue;
                }
            }
            else if(row==k-1){
                if(col==0 && (i==3 || i==4||i==5||i==6||i==7)){
                    continue;
                }
                else if(col==k-1 && (i==1||i==2||i==3||i==4||i==5)){
                    continue;
                }
                else if(i==3 || i==4 || i==5){
                    continue;
                }
            }
            else if(col==0 && (i==5||i==6||i==7)){
                continue;
            }
            else if(col==k-1 && (i==1||i==2||i==3)){
                continue;
            }
            if(buttons[row+row_8[i]][col+col_8[i]].color==BLACK){
                b.loc[i]=1;
                check=1;
            }
        }
        if(check==1)
            return true;
        else
            return false;
    }
    boolean black_checker(MyButton b,int l,int atleast_one_check){
        MyButton b1;
        int r=b.rowNumber+row_8[l];
        int c=b.colNumber+col_8[l];
        if(r<k && r>=0 && c>=0 && c<k){
            b1=buttons[b.rowNumber+row_8[l]][b.colNumber+col_8[l]];
        }
        else
        {
            return false;
        }
        if(b1.color==BLACK && atleast_one_check==1){
            return true;
        }
        else if(b1.color==FREE)
        {
            return false;
        }
        else {
            atleast_one_check=1;
            return (black_checker(b1,l,atleast_one_check));
        }
    }
    boolean white_checker(MyButton b,int l,int atleast_one_check){
        MyButton b1;
        int r=b.rowNumber+row_8[l];
        int c=b.colNumber+col_8[l];
        if(r<k && r>0 && c>0 && c<k){
            b1=buttons[b.rowNumber+row_8[l]][b.colNumber+col_8[l]];
        }
        else
        {
            return false;
        }
        if(b1.color==WHITE && atleast_one_check==1){
            return true;
        }
        else if(b1.color==FREE)
        {
            return false;
        }
        else {
            atleast_one_check=1;
            W.sumConversion++;
            return (white_checker(b1,l,atleast_one_check));
        }
    }
    boolean finalBlackChecker(MyButton b,int row,int col){
        int atleat_one_check=0;
        int checker=0;
        for(int i=0;i<8;i++){
            if(b.loc[i]==1){
                b.loc[i]=0;
                if(black_checker(b,i,atleat_one_check)){
                    b.moc[i]=1;
                    atleat_one_check=0;
                    checker=1;
                }
            }
        }
        if(checker==1){
            return true;
        }
        else{
            return false;
        }
    }
    boolean finalWhiteChecker(MyButton b,int row,int col){
        int atleat_one_check=0;
        int checker=0;
        for(int i=0;i<8;i++){
            if(b.loc[i]==1){
                W=b;
                int f=W.sumConversion;
                b.loc[i]=0;
                if(white_checker(b,i,atleat_one_check)){
                    b.moc[i]=1;
                    atleat_one_check=0;
                    checker=1;
                }
                else{
                    W.sumConversion=f;
                }
            }
        }
        if(checker==1){
            return true;
        }
        else{
            return false;
        }
    }
    void userSetup(){
        int checker=0;
        for(int i=0;i<k;i++){
            for(int j=0;j<k;j++){
                if(buttons[i][j].color==FREE){
                    if(blackChecker(buttons[i][j],buttons[i][j].rowNumber,buttons[i][j].colNumber)){
                        buttons[i][j].maybe=1;
                    }
                }
            }
        }
        for(int i=0;i<k;i++){
            for(int j=0;j<k;j++){
                if(buttons[i][j].maybe==1){
                    if(finalBlackChecker(buttons[i][j],buttons[i][j].rowNumber,buttons[i][j].colNumber)){
                        buttons[i][j].maybe=0;
                        buttons[i][j].confirmed=1;
                    }
                    else {
                        buttons[i][j].maybe = 0;
                    }
                }
            }
        }
        for(int i=0;i<k;i++) {
            for (int j = 0; j < k; j++) {
                if (buttons[i][j].confirmed == 1) {
                    checker = 1;
                    reasonToOver=0;
                }
            }
        }
        checkScores();
        if(checker!=1){
            if(reasonToOver==0) {
                Toast.makeText(this, "No valid Move for you !!", Toast.LENGTH_SHORT).show();
                reasonToOver = 1;
                computerSetup();
            }
            else
                stuckDialog();
        }
    }
    void computerSetup(){
        int checker=0;
        for(int i=0;i<k;i++){
            for(int j=0;j<k;j++){
                if(buttons[i][j].color==FREE){
                    if(whiteChecker(buttons[i][j],buttons[i][j].rowNumber,buttons[i][j].colNumber)){
                        buttons[i][j].maybe=1;
                    }
                }
            }
        }
        for(int i=0;i<k;i++){
            for(int j=0;j<k;j++){
                if(buttons[i][j].maybe==1){
                    if(finalWhiteChecker(buttons[i][j],buttons[i][j].rowNumber,buttons[i][j].colNumber)){
                        buttons[i][j].maybe=0;
                        buttons[i][j].confirmed=1;
                    }
                    else {
                        buttons[i][j].maybe = 0;
                    }
                }
            }
        }
        for(int i=0;i<k;i++) {
            for (int j = 0; j < k; j++) {
                if (buttons[i][j].confirmed == 1) {
                    checker = 1;
                    reasonToOver=0;
                }
            }
        }
        checkScores();
        if(checker!=1){
            if(reasonToOver==0) {
                Toast.makeText(this, "Computer can't make a valid move !!", Toast.LENGTH_SHORT).show();
                reasonToOver = 1;
                userSetup();
            }
            else
                stuckDialog();
        }
        else{
            computerPlay();
        }
    }
    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.bun){
            computerSetup();
        }
        else {
            MyButton button=(MyButton) v;
            if (button.confirmed == 1) {
                for(int i=0;i<k;i++){
                    for(int j=0;j<k;j++){
                        buttons[i][j].setBackground(ContextCompat.getDrawable(this,R.drawable.rectangle));
                    }
                }
                button.setImageResource(R.drawable.black);
                button.color=BLACK;
                button.setScaleType(ImageView.ScaleType.FIT_XY);
                for (int i = 0; i < 8; i++) {
                    if (button.moc[i] == 1) {
                        changeToBlack(buttons[button.rowNumber+row_8[i]][button.colNumber+col_8[i]], i);
                    }
                }
                reset_afterTurn();
                updateCounter();
                on_off.setEnabled(true);
                //computerSetup();
            }
        }
    }
    public void reset_afterTurn(){
        for(int i=0;i<k;i++){
            for(int j=0;j<k;j++){
                buttons[i][j].sumConversion=0;
                buttons[i][j].confirmed=0;
                for(int t=0;t<8;t++){
                    buttons[i][j].moc[t]=0;
                }
            }
        }
    }
    public void computerPlay(){
        MyButton button1=buttons[0][0];
        int max=0;
        for(int i=0;i<k;i++){
            for(int j=0;j<k;j++){
                if(max<=buttons[i][j].sumConversion){
                    button1=buttons[i][j];
                    max=buttons[i][j].sumConversion;
                }
            }
        }
        button1.setImageResource(R.drawable.white);
        button1.color=WHITE;
        button1.setScaleType(ImageView.ScaleType.FIT_XY);
        for(int i=0;i<8;i++){
            if(button1.moc[i]==1){
                changeToWhite(buttons[button1.rowNumber+row_8[i]][button1.colNumber+col_8[i]],i);
            }
        }
        reset_afterTurn();
        updateCounter();
        on_off.setEnabled(false);
        userSetup();
    }
    public void changeToWhite(MyButton b,int l){
        if(b.color==WHITE){
            return;
        }
        else{
            b.setImageResource(R.drawable.white);
            b.color=WHITE;
            b.setScaleType(ImageView.ScaleType.FIT_XY);
            changeToWhite(buttons[b.rowNumber+row_8[l]][b.colNumber+col_8[l]],l);
        }
    }
    public void changeToBlack(MyButton b,int l){
        if(b.color==BLACK){
            return;
        }
        else{
            b.setImageResource(R.drawable.black);
            b.color=BLACK;
            b.setScaleType(ImageView.ScaleType.FIT_XY);
            changeToBlack(buttons[b.rowNumber+row_8[l]][b.colNumber+col_8[l]],l);
        }
    }
    public void updateCounter(){
        int black=0;
        int white=0;
        for(int i=0;i<k;i++){
            for(int j=0;j<k;j++){
                if(buttons[i][j].color==WHITE){
                    white++;
                }
                else if(buttons[i][j].color==BLACK){
                    black++;
                }
            }
        }
        T[1].setText(""+white);
        T[0].setText(""+black);
    }
    public void stuckDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Stuck");
        builder.setMessage("No Further Valid Moves available for Both the Players");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showResultDialog();
            }
        });
        AlertDialog dialog=builder.create();
        dialog.show();
    }
    public void checkScores(){
        int white,black;
        white=Integer.parseInt(T[1].getText().toString());
        black=Integer.parseInt(T[0].getText().toString());
        if(white+black==(k*k)){
            showResultDialog();
        }
    }
    public void showResultDialog(){
        AlertDialog.Builder  builder=new AlertDialog.Builder(this);
        builder.setTitle("Result");
        int white,black;
        white=Integer.parseInt(T[1].getText().toString());
        black=Integer.parseInt(T[0].getText().toString());
        if(black==white){
            builder.setMessage("ohh!! It's a Draw!!");
        }
        else if (black<white){
            builder.setMessage("You loose "+white+"-"+black+" !!");
        }
        else
            builder.setMessage("You won "+black+"-"+white+" !!");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                resetAll();
            }
        });
        AlertDialog dialog=builder.create();
        dialog.show();
    }
}
