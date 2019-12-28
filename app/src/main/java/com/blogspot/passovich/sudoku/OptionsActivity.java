package com.blogspot.passovich.sudoku;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class OptionsActivity extends Activity implements View.OnClickListener {
    private static String TAG = "myLogs";
    private int difficulty = 3;
    private Button button,button1,button2,button3;
    private TextView textView;
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options_activity);
        Resources res = getResources();
        button = (Button) findViewById(R.id.button);button.setOnClickListener(this);
        button1 = (Button) findViewById(R.id.button1);button1.setOnClickListener(this);
        button2 = (Button) findViewById(R.id.button2);button2.setOnClickListener(this);
        button3 = (Button) findViewById(R.id.button3);button3.setOnClickListener(this);
        textView = (TextView) findViewById(R.id.textView83);
        textView.setText(res.getString(R.string.Difficulty) + " - " + res.getString(R.string.Diff3));
    }
    @Override
    public void onClick(View view){
        Intent intent = null;
        Animation anim = AnimationUtils.loadAnimation(this,R.anim.translate);
        Resources res = getResources();
        switch(view.getId()) {
            case R.id.button:
                button.startAnimation(anim);
                intent = new Intent(this, MenuActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.button1:
                button1.startAnimation(anim);
                textView.setText(res.getString(R.string.Difficulty) + " - " + res.getString(R.string.Diff1));
                difficulty=1;
                File.saveFile(this,"difficulty",Integer.toString(difficulty));
                break;
            case R.id.button2:
                button2.startAnimation(anim);
                textView.setText(res.getString(R.string.Difficulty) + " - " + res.getString(R.string.Diff2));
                difficulty=2;
                File.saveFile(this,"difficulty",Integer.toString(difficulty));
                break;
            case R.id.button3:
                button3.startAnimation(anim);
                textView.setText(res.getString(R.string.Difficulty) + " - " + res.getString(R.string.Diff3));
                difficulty=3;
                File.saveFile(this,"difficulty",Integer.toString(difficulty));
                break;
        }
    }
    @Override
    public boolean onKeyDown(int keyKode, KeyEvent event){
        if (keyKode==KeyEvent.KEYCODE_BACK){
        }
        return true;
    }
}
