package com.blogspot.passovich.sudoku;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

public class AboutActivity extends Activity implements View.OnClickListener {
    private Button button1;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_activity);
        button1 = (Button) findViewById(R.id.button1);button1.setOnClickListener(this);
    }
    @Override
    public void onClick(View view){
        Intent intent = null;
        switch(view.getId()) {
            case R.id.button1:
                intent = new Intent(this, MenuActivity.class);
                startActivity(intent);
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
