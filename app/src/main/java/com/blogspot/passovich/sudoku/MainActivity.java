package com.blogspot.passovich.sudoku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private String TAG = "myLogs";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    @Override
    public void onClick(View view){
        if (view.getId()==R.id.linearLayout){
            Intent intent = new Intent(this,MenuActivity.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    public boolean onKeyDown(int keyKode, KeyEvent event){
        if (keyKode==KeyEvent.KEYCODE_BACK){
            Intent intent = new Intent(this,MenuActivity.class);
            startActivity(intent);
            finish();
        }
        return true;
    }
    @Override
    protected void onStart(){
        Log.d(TAG,"onStart");super.onStart();
    }
    @Override
    protected void onRestart(){
        Log.d(TAG,"onRestart");super.onRestart();
    }
    @Override
    protected void onResume(){
        Log.d(TAG,"onResume");super.onResume();
    }
    @Override
    protected void onPause(){
        Log.d(TAG,"onPause");super.onPause();
    }
    @Override
    protected void onStop(){
        Log.d(TAG,"onStop");super.onStop();
    }
    @Override
    protected void onDestroy(){
        Log.d(TAG,"onDestriy");super.onDestroy();
    }
}