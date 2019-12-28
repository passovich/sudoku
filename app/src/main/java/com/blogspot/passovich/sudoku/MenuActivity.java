package com.blogspot.passovich.sudoku;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;



import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class MenuActivity extends Activity implements View.OnClickListener {
    private static final String TAG="myLogs";
    private Button button1,button2,button3,button4,button5,button6;
    private boolean load = false;
    @Override
    protected void onCreate (Bundle savedInstanceState){
        int solvedMatrix[][]=new int [10][10];
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);

        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alphaprozrachn);
        button1=(Button) findViewById(R.id.button1); button1.setOnClickListener(this);
        button1.startAnimation(anim);
        button2=(Button) findViewById(R.id.button2); button2.setOnClickListener(this);
        button2.startAnimation(anim);
        button3=(Button) findViewById(R.id.button3); button3.setOnClickListener(this);
        button3.startAnimation(anim);
        button4=(Button) findViewById(R.id.button4); button4.setOnClickListener(this);
        button4.startAnimation(anim);
        button5=(Button) findViewById(R.id.button5); button5.setOnClickListener(this);
        button5.startAnimation(anim);
        button6=(Button) findViewById(R.id.button6); button6.setOnClickListener(this);
        button6.startAnimation(anim);
    }
    @Override
    public void onClick(View view){
        Intent intent = null;
        Animation anim =AnimationUtils.loadAnimation(this, R.anim.translate);
        switch(view.getId())
        {
            case R.id.button1:  button1.startAnimation(anim);
                                intent = new Intent(this, GameActivity.class);
                                startActivity(intent);
                                finish();
                                break;
            case R.id.button2:  button2.startAnimation(anim);
                                load=true;
                                intent = new Intent(this, GameActivity.class);
                                intent.putExtra("load", load);
                                startActivity(intent);
                                finish();
                                break;
            case R.id.button3:  button3.startAnimation(anim);
                                intent = new Intent(this, OptionsActivity.class);
                                startActivity(intent);
                                finish();
                                break;
            case R.id.button4:  button4.startAnimation(anim);
                                intent = new Intent(this, AboutActivity.class);
                                startActivity(intent);
                                finish();
                                break;
            case R.id.button5:  button5.startAnimation(anim);
                                break;
            case R.id.button6:  button6.startAnimation(anim);
                                finish();break;
        }
    }

    @Override
    protected void onStart(){
        Log.d(TAG,"onStart Menu_Activity");super.onStart();
    }
    @Override
    protected void onRestart(){
        Log.d(TAG,"onRestart Menu_Activity");super.onRestart();
    }
    @Override
    protected void onResume(){
        Log.d(TAG,"onResume Menu_Activity");super.onResume();
    }
    @Override
    protected void onPause(){
        Log.d(TAG,"onPause Menu_Activity");super.onPause();
    }
    @Override
    protected void onStop(){
        Log.d(TAG,"onStop  Menu_Activity");super.onStop();
    }
    @Override
    protected void onDestroy(){
        Log.d(TAG,"onDestroy  Menu_Activity");super.onDestroy();
    }
}
