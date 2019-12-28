package com.blogspot.passovich.sudoku;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class GameActivity extends Activity implements View.OnClickListener {
    private static String TAG="myLogs";

    //массив элементов для вывода основных элементов
    private TextView numbers [][] = new TextView[10][10];
    private TextView auxNumbers[][][] = new TextView[10][10][10];
    //массив элементов для вывода вспомогательных таблиц
    private TableLayout tables[][] = new TableLayout[10][10];
    private Button button,button1,button2,button3,button4,button5,button6,button7,button8,button9,button10,button11,button12;

    private final int DIALOG_EXIT = 1;
    private  String message;
    private int difficulty = 0;
    private boolean pencil = false;
    private boolean load = false; // Если true, то загрузить игру из файла, иначе создать новую игру
    private int iMatrix = 1, jMatrix = 1;
    private Matrix m = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);

        //получаем сложность
        try{
            String s = File.readFile(this,"difficulty");
            char ch = s.charAt(0);
            difficulty=Character.getNumericValue(ch);
        }catch (Exception e){difficulty = 3;}
        // инициализация кнопок
        Animation anim = AnimationUtils.loadAnimation(this,R.anim.scale);
        button  = (Button) findViewById(R.id.button) ;button .setOnClickListener(this);button.startAnimation(anim);
        button1 = (Button) findViewById(R.id.button1);button1.setOnClickListener(this);button1.startAnimation(anim);
        button2 = (Button) findViewById(R.id.button2);button2.setOnClickListener(this);button2.startAnimation(anim);
        button3 = (Button) findViewById(R.id.button3);button3.setOnClickListener(this);button3.startAnimation(anim);
        button4 = (Button) findViewById(R.id.button4);button4.setOnClickListener(this);button4.startAnimation(anim);
        button5 = (Button) findViewById(R.id.button5);button5.setOnClickListener(this);button5.startAnimation(anim);
        button6 = (Button) findViewById(R.id.button6);button6.setOnClickListener(this);button6.startAnimation(anim);
        button7 = (Button) findViewById(R.id.button7);button7.setOnClickListener(this);button7.startAnimation(anim);
        button8 = (Button) findViewById(R.id.button8);button8.setOnClickListener(this);button8.startAnimation(anim);
        button9 = (Button) findViewById(R.id.button9);button9.setOnClickListener(this);button9.startAnimation(anim);

        button10 = (Button) findViewById(R.id.button10);button10.setOnClickListener(this);
        button11 = (Button) findViewById(R.id.button11);button11.setOnClickListener(this);
        button12 = (Button) findViewById(R.id.button12);button12.setOnClickListener(this);

        //инициализация дополнительных таблиц tables[][]
        {
            tables[1][1]= (TableLayout) findViewById(R.id.table11);tables[1][1].setOnClickListener(this);
            tables[1][2]= (TableLayout) findViewById(R.id.table12);tables[1][2].setOnClickListener(this);
            tables[1][3]= (TableLayout) findViewById(R.id.table13);tables[1][3].setOnClickListener(this);
            tables[1][4]= (TableLayout) findViewById(R.id.table14);tables[1][4].setOnClickListener(this);
            tables[1][5]= (TableLayout) findViewById(R.id.table15);tables[1][5].setOnClickListener(this);
            tables[1][6]= (TableLayout) findViewById(R.id.table16);tables[1][6].setOnClickListener(this);
            tables[1][7]= (TableLayout) findViewById(R.id.table17);tables[1][7].setOnClickListener(this);
            tables[1][8]= (TableLayout) findViewById(R.id.table18);tables[1][8].setOnClickListener(this);
            tables[1][9]= (TableLayout) findViewById(R.id.table19);tables[1][9].setOnClickListener(this);

            tables[2][1]= (TableLayout) findViewById(R.id.table21);tables[2][1].setOnClickListener(this);
            tables[2][2]= (TableLayout) findViewById(R.id.table22);tables[2][2].setOnClickListener(this);
            tables[2][3]= (TableLayout) findViewById(R.id.table23);tables[2][3].setOnClickListener(this);
            tables[2][4]= (TableLayout) findViewById(R.id.table24);tables[2][4].setOnClickListener(this);
            tables[2][5]= (TableLayout) findViewById(R.id.table25);tables[2][5].setOnClickListener(this);
            tables[2][6]= (TableLayout) findViewById(R.id.table26);tables[2][6].setOnClickListener(this);
            tables[2][7]= (TableLayout) findViewById(R.id.table27);tables[2][7].setOnClickListener(this);
            tables[2][8]= (TableLayout) findViewById(R.id.table28);tables[2][8].setOnClickListener(this);
            tables[2][9]= (TableLayout) findViewById(R.id.table29);tables[2][9].setOnClickListener(this);

            tables[3][1]= (TableLayout) findViewById(R.id.table31);tables[3][1].setOnClickListener(this);
            tables[3][2]= (TableLayout) findViewById(R.id.table32);tables[3][2].setOnClickListener(this);
            tables[3][3]= (TableLayout) findViewById(R.id.table33);tables[3][3].setOnClickListener(this);
            tables[3][4]= (TableLayout) findViewById(R.id.table34);tables[3][4].setOnClickListener(this);
            tables[3][5]= (TableLayout) findViewById(R.id.table35);tables[3][5].setOnClickListener(this);
            tables[3][6]= (TableLayout) findViewById(R.id.table36);tables[3][6].setOnClickListener(this);
            tables[3][7]= (TableLayout) findViewById(R.id.table37);tables[3][7].setOnClickListener(this);
            tables[3][8]= (TableLayout) findViewById(R.id.table38);tables[3][8].setOnClickListener(this);
            tables[3][9]= (TableLayout) findViewById(R.id.table39);tables[3][9].setOnClickListener(this);

            tables[4][1]= (TableLayout) findViewById(R.id.table41);tables[4][1].setOnClickListener(this);
            tables[4][2]= (TableLayout) findViewById(R.id.table42);tables[4][2].setOnClickListener(this);
            tables[4][3]= (TableLayout) findViewById(R.id.table43);tables[4][3].setOnClickListener(this);
            tables[4][4]= (TableLayout) findViewById(R.id.table44);tables[4][4].setOnClickListener(this);
            tables[4][5]= (TableLayout) findViewById(R.id.table45);tables[4][5].setOnClickListener(this);
            tables[4][6]= (TableLayout) findViewById(R.id.table46);tables[4][6].setOnClickListener(this);
            tables[4][7]= (TableLayout) findViewById(R.id.table47);tables[4][7].setOnClickListener(this);
            tables[4][8]= (TableLayout) findViewById(R.id.table48);tables[4][8].setOnClickListener(this);
            tables[4][9]= (TableLayout) findViewById(R.id.table49);tables[4][9].setOnClickListener(this);

            tables[5][1]= (TableLayout) findViewById(R.id.table51);tables[5][1].setOnClickListener(this);
            tables[5][2]= (TableLayout) findViewById(R.id.table52);tables[5][2].setOnClickListener(this);
            tables[5][3]= (TableLayout) findViewById(R.id.table53);tables[5][3].setOnClickListener(this);
            tables[5][4]= (TableLayout) findViewById(R.id.table54);tables[5][4].setOnClickListener(this);
            tables[5][5]= (TableLayout) findViewById(R.id.table55);tables[5][5].setOnClickListener(this);
            tables[5][6]= (TableLayout) findViewById(R.id.table56);tables[5][6].setOnClickListener(this);
            tables[5][7]= (TableLayout) findViewById(R.id.table57);tables[5][7].setOnClickListener(this);
            tables[5][8]= (TableLayout) findViewById(R.id.table58);tables[5][8].setOnClickListener(this);
            tables[5][9]= (TableLayout) findViewById(R.id.table59);tables[5][9].setOnClickListener(this);

            tables[6][1]= (TableLayout) findViewById(R.id.table61);tables[6][1].setOnClickListener(this);
            tables[6][2]= (TableLayout) findViewById(R.id.table62);tables[6][2].setOnClickListener(this);
            tables[6][3]= (TableLayout) findViewById(R.id.table63);tables[6][3].setOnClickListener(this);
            tables[6][4]= (TableLayout) findViewById(R.id.table64);tables[6][4].setOnClickListener(this);
            tables[6][5]= (TableLayout) findViewById(R.id.table65);tables[6][5].setOnClickListener(this);
            tables[6][6]= (TableLayout) findViewById(R.id.table66);tables[6][6].setOnClickListener(this);
            tables[6][7]= (TableLayout) findViewById(R.id.table67);tables[6][7].setOnClickListener(this);
            tables[6][8]= (TableLayout) findViewById(R.id.table68);tables[6][8].setOnClickListener(this);
            tables[6][9]= (TableLayout) findViewById(R.id.table69);tables[6][9].setOnClickListener(this);

            tables[7][1]= (TableLayout) findViewById(R.id.table71);tables[7][1].setOnClickListener(this);
            tables[7][2]= (TableLayout) findViewById(R.id.table72);tables[7][2].setOnClickListener(this);
            tables[7][3]= (TableLayout) findViewById(R.id.table73);tables[7][3].setOnClickListener(this);
            tables[7][4]= (TableLayout) findViewById(R.id.table74);tables[7][4].setOnClickListener(this);
            tables[7][5]= (TableLayout) findViewById(R.id.table75);tables[7][5].setOnClickListener(this);
            tables[7][6]= (TableLayout) findViewById(R.id.table76);tables[7][6].setOnClickListener(this);
            tables[7][7]= (TableLayout) findViewById(R.id.table77);tables[7][7].setOnClickListener(this);
            tables[7][8]= (TableLayout) findViewById(R.id.table78);tables[7][8].setOnClickListener(this);
            tables[7][9]= (TableLayout) findViewById(R.id.table79);tables[7][9].setOnClickListener(this);

            tables[8][1]= (TableLayout) findViewById(R.id.table81);tables[8][1].setOnClickListener(this);
            tables[8][2]= (TableLayout) findViewById(R.id.table82);tables[8][2].setOnClickListener(this);
            tables[8][3]= (TableLayout) findViewById(R.id.table83);tables[8][3].setOnClickListener(this);
            tables[8][4]= (TableLayout) findViewById(R.id.table84);tables[8][4].setOnClickListener(this);
            tables[8][5]= (TableLayout) findViewById(R.id.table85);tables[8][5].setOnClickListener(this);
            tables[8][6]= (TableLayout) findViewById(R.id.table86);tables[8][6].setOnClickListener(this);
            tables[8][7]= (TableLayout) findViewById(R.id.table87);tables[8][7].setOnClickListener(this);
            tables[8][8]= (TableLayout) findViewById(R.id.table88);tables[8][8].setOnClickListener(this);
            tables[8][9]= (TableLayout) findViewById(R.id.table89);tables[8][9].setOnClickListener(this);

            tables[9][1]= (TableLayout) findViewById(R.id.table91);tables[9][1].setOnClickListener(this);
            tables[9][2]= (TableLayout) findViewById(R.id.table92);tables[9][2].setOnClickListener(this);
            tables[9][3]= (TableLayout) findViewById(R.id.table93);tables[9][3].setOnClickListener(this);
            tables[9][4]= (TableLayout) findViewById(R.id.table94);tables[9][4].setOnClickListener(this);
            tables[9][5]= (TableLayout) findViewById(R.id.table95);tables[9][5].setOnClickListener(this);
            tables[9][6]= (TableLayout) findViewById(R.id.table96);tables[9][6].setOnClickListener(this);
            tables[9][7]= (TableLayout) findViewById(R.id.table97);tables[9][7].setOnClickListener(this);
            tables[9][8]= (TableLayout) findViewById(R.id.table98);tables[9][8].setOnClickListener(this);
            tables[9][9]= (TableLayout) findViewById(R.id.table99);tables[9][9].setOnClickListener(this);}
        //инициализация основного экрана numbers[][]
        {    numbers[1][1] = (TextView) findViewById(R.id.textView1);numbers[1][1].setOnClickListener(this);
            numbers[1][2] = (TextView) findViewById(R.id.textView2);numbers[1][2].setOnClickListener(this);
            numbers[1][3] = (TextView) findViewById(R.id.textView3);numbers[1][3].setOnClickListener(this);
            numbers[1][4] = (TextView) findViewById(R.id.textView10);numbers[1][4].setOnClickListener(this);
            numbers[1][5] = (TextView) findViewById(R.id.textView11);numbers[1][5].setOnClickListener(this);
            numbers[1][6] = (TextView) findViewById(R.id.textView12);numbers[1][6].setOnClickListener(this);
            numbers[1][7] = (TextView) findViewById(R.id.textView19);numbers[1][7].setOnClickListener(this);
            numbers[1][8] = (TextView) findViewById(R.id.textView20);numbers[1][8].setOnClickListener(this);
            numbers[1][9] = (TextView) findViewById(R.id.textView21);numbers[1][9].setOnClickListener(this);

            numbers[2][1] = (TextView) findViewById(R.id.textView4);numbers[2][1].setOnClickListener(this);
            numbers[2][2] = (TextView) findViewById(R.id.textView5);numbers[2][2].setOnClickListener(this);
            numbers[2][3] = (TextView) findViewById(R.id.textView6);numbers[2][3].setOnClickListener(this);
            numbers[2][4] = (TextView) findViewById(R.id.textView13);numbers[2][4].setOnClickListener(this);
            numbers[2][5] = (TextView) findViewById(R.id.textView14);numbers[2][5].setOnClickListener(this);
            numbers[2][6] = (TextView) findViewById(R.id.textView15);numbers[2][6].setOnClickListener(this);
            numbers[2][7] = (TextView) findViewById(R.id.textView22);numbers[2][7].setOnClickListener(this);
            numbers[2][8] = (TextView) findViewById(R.id.textView23);numbers[2][8].setOnClickListener(this);
            numbers[2][9] = (TextView) findViewById(R.id.textView24);numbers[2][9].setOnClickListener(this);

            numbers[3][1] = (TextView) findViewById(R.id.textView7);numbers[3][1].setOnClickListener(this);
            numbers[3][2] = (TextView) findViewById(R.id.textView8);numbers[3][2].setOnClickListener(this);
            numbers[3][3] = (TextView) findViewById(R.id.textView9);numbers[3][3].setOnClickListener(this);
            numbers[3][4] = (TextView) findViewById(R.id.textView16);numbers[3][4].setOnClickListener(this);
            numbers[3][5] = (TextView) findViewById(R.id.textView17);numbers[3][5].setOnClickListener(this);
            numbers[3][6] = (TextView) findViewById(R.id.textView18);numbers[3][6].setOnClickListener(this);
            numbers[3][7] = (TextView) findViewById(R.id.textView25);numbers[3][7].setOnClickListener(this);
            numbers[3][8] = (TextView) findViewById(R.id.textView26);numbers[3][8].setOnClickListener(this);
            numbers[3][9] = (TextView) findViewById(R.id.textView27);numbers[3][9].setOnClickListener(this);



            numbers[4][1] = (TextView) findViewById(R.id.textView28);numbers[4][1].setOnClickListener(this);
            numbers[4][2] = (TextView) findViewById(R.id.textView29);numbers[4][2].setOnClickListener(this);
            numbers[4][3] = (TextView) findViewById(R.id.textView30);numbers[4][3].setOnClickListener(this);
            numbers[4][4] = (TextView) findViewById(R.id.textView37);numbers[4][4].setOnClickListener(this);
            numbers[4][5] = (TextView) findViewById(R.id.textView38);numbers[4][5].setOnClickListener(this);
            numbers[4][6] = (TextView) findViewById(R.id.textView39);numbers[4][6].setOnClickListener(this);
            numbers[4][7] = (TextView) findViewById(R.id.textView46);numbers[4][7].setOnClickListener(this);
            numbers[4][8] = (TextView) findViewById(R.id.textView47);numbers[4][8].setOnClickListener(this);
            numbers[4][9] = (TextView) findViewById(R.id.textView48);numbers[4][9].setOnClickListener(this);

            numbers[5][1] = (TextView) findViewById(R.id.textView31);numbers[5][1].setOnClickListener(this);
            numbers[5][2] = (TextView) findViewById(R.id.textView32);numbers[5][2].setOnClickListener(this);
            numbers[5][3] = (TextView) findViewById(R.id.textView33);numbers[5][3].setOnClickListener(this);
            numbers[5][4] = (TextView) findViewById(R.id.textView40);numbers[5][4].setOnClickListener(this);
            numbers[5][5] = (TextView) findViewById(R.id.textView41);numbers[5][5].setOnClickListener(this);
            numbers[5][6] = (TextView) findViewById(R.id.textView42);numbers[5][6].setOnClickListener(this);
            numbers[5][7] = (TextView) findViewById(R.id.textView49);numbers[5][7].setOnClickListener(this);
            numbers[5][8] = (TextView) findViewById(R.id.textView50);numbers[5][8].setOnClickListener(this);
            numbers[5][9] = (TextView) findViewById(R.id.textView51);numbers[5][9].setOnClickListener(this);

            numbers[6][1] = (TextView) findViewById(R.id.textView34);numbers[6][1].setOnClickListener(this);
            numbers[6][2] = (TextView) findViewById(R.id.textView35);numbers[6][2].setOnClickListener(this);
            numbers[6][3] = (TextView) findViewById(R.id.textView36);numbers[6][3].setOnClickListener(this);
            numbers[6][4] = (TextView) findViewById(R.id.textView43);numbers[6][4].setOnClickListener(this);
            numbers[6][5] = (TextView) findViewById(R.id.textView44);numbers[6][5].setOnClickListener(this);
            numbers[6][6] = (TextView) findViewById(R.id.textView45);numbers[6][6].setOnClickListener(this);
            numbers[6][7] = (TextView) findViewById(R.id.textView52);numbers[6][7].setOnClickListener(this);
            numbers[6][8] = (TextView) findViewById(R.id.textView53);numbers[6][8].setOnClickListener(this);
            numbers[6][9] = (TextView) findViewById(R.id.textView54);numbers[6][9].setOnClickListener(this);


            numbers[7][1] = (TextView) findViewById(R.id.textView55);numbers[7][1].setOnClickListener(this);
            numbers[7][2] = (TextView) findViewById(R.id.textView56);numbers[7][2].setOnClickListener(this);
            numbers[7][3] = (TextView) findViewById(R.id.textView57);numbers[7][3].setOnClickListener(this);
            numbers[7][4] = (TextView) findViewById(R.id.textView64);numbers[7][4].setOnClickListener(this);
            numbers[7][5] = (TextView) findViewById(R.id.textView65);numbers[7][5].setOnClickListener(this);
            numbers[7][6] = (TextView) findViewById(R.id.textView66);numbers[7][6].setOnClickListener(this);
            numbers[7][7] = (TextView) findViewById(R.id.textView73);numbers[7][7].setOnClickListener(this);
            numbers[7][8] = (TextView) findViewById(R.id.textView74);numbers[7][8].setOnClickListener(this);
            numbers[7][9] = (TextView) findViewById(R.id.textView75);numbers[7][9].setOnClickListener(this);

            numbers[8][1] = (TextView) findViewById(R.id.textView58);numbers[8][1].setOnClickListener(this);
            numbers[8][2] = (TextView) findViewById(R.id.textView59);numbers[8][2].setOnClickListener(this);
            numbers[8][3] = (TextView) findViewById(R.id.textView60);numbers[8][3].setOnClickListener(this);
            numbers[8][4] = (TextView) findViewById(R.id.textView67);numbers[8][4].setOnClickListener(this);
            numbers[8][5] = (TextView) findViewById(R.id.textView68);numbers[8][5].setOnClickListener(this);
            numbers[8][6] = (TextView) findViewById(R.id.textView69);numbers[8][6].setOnClickListener(this);
            numbers[8][7] = (TextView) findViewById(R.id.textView76);numbers[8][7].setOnClickListener(this);
            numbers[8][8] = (TextView) findViewById(R.id.textView77);numbers[8][8].setOnClickListener(this);
            numbers[8][9] = (TextView) findViewById(R.id.textView78);numbers[8][9].setOnClickListener(this);

            numbers[9][1] = (TextView) findViewById(R.id.textView61);numbers[9][1].setOnClickListener(this);
            numbers[9][2] = (TextView) findViewById(R.id.textView62);numbers[9][2].setOnClickListener(this);
            numbers[9][3] = (TextView) findViewById(R.id.textView63);numbers[9][3].setOnClickListener(this);
            numbers[9][4] = (TextView) findViewById(R.id.textView70);numbers[9][4].setOnClickListener(this);
            numbers[9][5] = (TextView) findViewById(R.id.textView71);numbers[9][5].setOnClickListener(this);
            numbers[9][6] = (TextView) findViewById(R.id.textView72);numbers[9][6].setOnClickListener(this);
            numbers[9][7] = (TextView) findViewById(R.id.textView79);numbers[9][7].setOnClickListener(this);
            numbers[9][8] = (TextView) findViewById(R.id.textView80);numbers[9][8].setOnClickListener(this);
            numbers[9][9] = (TextView) findViewById(R.id.textView81);numbers[9][9].setOnClickListener(this);}
        //инициализация дополнительных таблиц auxNumbers[][][]
        {
            auxNumbers[1][1][1] = (TextView) findViewById(R.id.textView111);auxNumbers[1][1][1].setOnClickListener(this);
            auxNumbers[1][1][2] = (TextView) findViewById(R.id.textView112);auxNumbers[1][1][2].setOnClickListener(this);
            auxNumbers[1][1][3] = (TextView) findViewById(R.id.textView113);auxNumbers[1][1][3].setOnClickListener(this);
            auxNumbers[1][1][4] = (TextView) findViewById(R.id.textView114);auxNumbers[1][1][4].setOnClickListener(this);
            auxNumbers[1][1][5] = (TextView) findViewById(R.id.textView115);auxNumbers[1][1][5].setOnClickListener(this);
            auxNumbers[1][1][6] = (TextView) findViewById(R.id.textView116);auxNumbers[1][1][6].setOnClickListener(this);
            auxNumbers[1][1][7] = (TextView) findViewById(R.id.textView117);auxNumbers[1][1][7].setOnClickListener(this);
            auxNumbers[1][1][8] = (TextView) findViewById(R.id.textView118);auxNumbers[1][1][8].setOnClickListener(this);
            auxNumbers[1][1][9] = (TextView) findViewById(R.id.textView119);auxNumbers[1][1][9].setOnClickListener(this);

            auxNumbers[1][2][1] = (TextView) findViewById(R.id.textView121);auxNumbers[1][2][1].setOnClickListener(this);
            auxNumbers[1][2][2] = (TextView) findViewById(R.id.textView122);auxNumbers[1][2][2].setOnClickListener(this);
            auxNumbers[1][2][3] = (TextView) findViewById(R.id.textView123);auxNumbers[1][2][3].setOnClickListener(this);
            auxNumbers[1][2][4] = (TextView) findViewById(R.id.textView124);auxNumbers[1][2][4].setOnClickListener(this);
            auxNumbers[1][2][5] = (TextView) findViewById(R.id.textView125);auxNumbers[1][2][5].setOnClickListener(this);
            auxNumbers[1][2][6] = (TextView) findViewById(R.id.textView126);auxNumbers[1][2][6].setOnClickListener(this);
            auxNumbers[1][2][7] = (TextView) findViewById(R.id.textView127);auxNumbers[1][2][7].setOnClickListener(this);
            auxNumbers[1][2][8] = (TextView) findViewById(R.id.textView128);auxNumbers[1][2][8].setOnClickListener(this);
            auxNumbers[1][2][9] = (TextView) findViewById(R.id.textView129);auxNumbers[1][2][9].setOnClickListener(this);

            auxNumbers[1][3][1] = (TextView) findViewById(R.id.textView131);auxNumbers[1][3][1].setOnClickListener(this);
            auxNumbers[1][3][2] = (TextView) findViewById(R.id.textView132);auxNumbers[1][3][2].setOnClickListener(this);
            auxNumbers[1][3][3] = (TextView) findViewById(R.id.textView133);auxNumbers[1][3][3].setOnClickListener(this);
            auxNumbers[1][3][4] = (TextView) findViewById(R.id.textView134);auxNumbers[1][3][4].setOnClickListener(this);
            auxNumbers[1][3][5] = (TextView) findViewById(R.id.textView135);auxNumbers[1][3][5].setOnClickListener(this);
            auxNumbers[1][3][6] = (TextView) findViewById(R.id.textView136);auxNumbers[1][3][6].setOnClickListener(this);
            auxNumbers[1][3][7] = (TextView) findViewById(R.id.textView137);auxNumbers[1][3][7].setOnClickListener(this);
            auxNumbers[1][3][8] = (TextView) findViewById(R.id.textView138);auxNumbers[1][3][8].setOnClickListener(this);
            auxNumbers[1][3][9] = (TextView) findViewById(R.id.textView139);auxNumbers[1][3][9].setOnClickListener(this);

            auxNumbers[1][4][1] = (TextView) findViewById(R.id.textView141);auxNumbers[1][4][1].setOnClickListener(this);
            auxNumbers[1][4][2] = (TextView) findViewById(R.id.textView142);auxNumbers[1][4][2].setOnClickListener(this);
            auxNumbers[1][4][3] = (TextView) findViewById(R.id.textView143);auxNumbers[1][4][3].setOnClickListener(this);
            auxNumbers[1][4][4] = (TextView) findViewById(R.id.textView144);auxNumbers[1][4][4].setOnClickListener(this);
            auxNumbers[1][4][5] = (TextView) findViewById(R.id.textView145);auxNumbers[1][4][5].setOnClickListener(this);
            auxNumbers[1][4][6] = (TextView) findViewById(R.id.textView146);auxNumbers[1][4][6].setOnClickListener(this);
            auxNumbers[1][4][7] = (TextView) findViewById(R.id.textView147);auxNumbers[1][4][7].setOnClickListener(this);
            auxNumbers[1][4][8] = (TextView) findViewById(R.id.textView148);auxNumbers[1][4][8].setOnClickListener(this);
            auxNumbers[1][4][9] = (TextView) findViewById(R.id.textView149);auxNumbers[1][4][9].setOnClickListener(this);

            auxNumbers[1][5][1] = (TextView) findViewById(R.id.textView151);auxNumbers[1][5][1].setOnClickListener(this);
            auxNumbers[1][5][2] = (TextView) findViewById(R.id.textView152);auxNumbers[1][5][2].setOnClickListener(this);
            auxNumbers[1][5][3] = (TextView) findViewById(R.id.textView153);auxNumbers[1][5][3].setOnClickListener(this);
            auxNumbers[1][5][4] = (TextView) findViewById(R.id.textView154);auxNumbers[1][5][4].setOnClickListener(this);
            auxNumbers[1][5][5] = (TextView) findViewById(R.id.textView155);auxNumbers[1][5][5].setOnClickListener(this);
            auxNumbers[1][5][6] = (TextView) findViewById(R.id.textView156);auxNumbers[1][5][6].setOnClickListener(this);
            auxNumbers[1][5][7] = (TextView) findViewById(R.id.textView157);auxNumbers[1][5][7].setOnClickListener(this);
            auxNumbers[1][5][8] = (TextView) findViewById(R.id.textView158);auxNumbers[1][5][8].setOnClickListener(this);
            auxNumbers[1][5][9] = (TextView) findViewById(R.id.textView159);auxNumbers[1][5][9].setOnClickListener(this);

            auxNumbers[1][6][1] = (TextView) findViewById(R.id.textView161);auxNumbers[1][6][1].setOnClickListener(this);
            auxNumbers[1][6][2] = (TextView) findViewById(R.id.textView162);auxNumbers[1][6][2].setOnClickListener(this);
            auxNumbers[1][6][3] = (TextView) findViewById(R.id.textView163);auxNumbers[1][6][3].setOnClickListener(this);
            auxNumbers[1][6][4] = (TextView) findViewById(R.id.textView164);auxNumbers[1][6][4].setOnClickListener(this);
            auxNumbers[1][6][5] = (TextView) findViewById(R.id.textView165);auxNumbers[1][6][5].setOnClickListener(this);
            auxNumbers[1][6][6] = (TextView) findViewById(R.id.textView166);auxNumbers[1][6][6].setOnClickListener(this);
            auxNumbers[1][6][7] = (TextView) findViewById(R.id.textView167);auxNumbers[1][6][7].setOnClickListener(this);
            auxNumbers[1][6][8] = (TextView) findViewById(R.id.textView168);auxNumbers[1][6][8].setOnClickListener(this);
            auxNumbers[1][6][9] = (TextView) findViewById(R.id.textView169);auxNumbers[1][6][9].setOnClickListener(this);

            auxNumbers[1][7][1] = (TextView) findViewById(R.id.textView171);auxNumbers[1][7][1].setOnClickListener(this);
            auxNumbers[1][7][2] = (TextView) findViewById(R.id.textView172);auxNumbers[1][7][2].setOnClickListener(this);
            auxNumbers[1][7][3] = (TextView) findViewById(R.id.textView173);auxNumbers[1][7][3].setOnClickListener(this);
            auxNumbers[1][7][4] = (TextView) findViewById(R.id.textView174);auxNumbers[1][7][4].setOnClickListener(this);
            auxNumbers[1][7][5] = (TextView) findViewById(R.id.textView175);auxNumbers[1][7][5].setOnClickListener(this);
            auxNumbers[1][7][6] = (TextView) findViewById(R.id.textView176);auxNumbers[1][7][6].setOnClickListener(this);
            auxNumbers[1][7][7] = (TextView) findViewById(R.id.textView177);auxNumbers[1][7][7].setOnClickListener(this);
            auxNumbers[1][7][8] = (TextView) findViewById(R.id.textView178);auxNumbers[1][7][8].setOnClickListener(this);
            auxNumbers[1][7][9] = (TextView) findViewById(R.id.textView179);auxNumbers[1][7][9].setOnClickListener(this);

            auxNumbers[1][8][1] = (TextView) findViewById(R.id.textView181);auxNumbers[1][8][1].setOnClickListener(this);
            auxNumbers[1][8][2] = (TextView) findViewById(R.id.textView182);auxNumbers[1][8][2].setOnClickListener(this);
            auxNumbers[1][8][3] = (TextView) findViewById(R.id.textView183);auxNumbers[1][8][3].setOnClickListener(this);
            auxNumbers[1][8][4] = (TextView) findViewById(R.id.textView184);auxNumbers[1][8][4].setOnClickListener(this);
            auxNumbers[1][8][5] = (TextView) findViewById(R.id.textView185);auxNumbers[1][8][5].setOnClickListener(this);
            auxNumbers[1][8][6] = (TextView) findViewById(R.id.textView186);auxNumbers[1][8][6].setOnClickListener(this);
            auxNumbers[1][8][7] = (TextView) findViewById(R.id.textView187);auxNumbers[1][8][7].setOnClickListener(this);
            auxNumbers[1][8][8] = (TextView) findViewById(R.id.textView188);auxNumbers[1][8][8].setOnClickListener(this);
            auxNumbers[1][8][9] = (TextView) findViewById(R.id.textView189);auxNumbers[1][8][9].setOnClickListener(this);

            auxNumbers[1][9][1] = (TextView) findViewById(R.id.textView191);auxNumbers[1][9][1].setOnClickListener(this);
            auxNumbers[1][9][2] = (TextView) findViewById(R.id.textView192);auxNumbers[1][9][2].setOnClickListener(this);
            auxNumbers[1][9][3] = (TextView) findViewById(R.id.textView193);auxNumbers[1][9][3].setOnClickListener(this);
            auxNumbers[1][9][4] = (TextView) findViewById(R.id.textView194);auxNumbers[1][9][4].setOnClickListener(this);
            auxNumbers[1][9][5] = (TextView) findViewById(R.id.textView195);auxNumbers[1][9][5].setOnClickListener(this);
            auxNumbers[1][9][6] = (TextView) findViewById(R.id.textView196);auxNumbers[1][9][6].setOnClickListener(this);
            auxNumbers[1][9][7] = (TextView) findViewById(R.id.textView197);auxNumbers[1][9][7].setOnClickListener(this);
            auxNumbers[1][9][8] = (TextView) findViewById(R.id.textView198);auxNumbers[1][9][8].setOnClickListener(this);
            auxNumbers[1][9][9] = (TextView) findViewById(R.id.textView199);auxNumbers[1][9][9].setOnClickListener(this);

            auxNumbers[2][1][1] = (TextView) findViewById(R.id.textView211);auxNumbers[2][1][1].setOnClickListener(this);
            auxNumbers[2][1][2] = (TextView) findViewById(R.id.textView212);auxNumbers[2][1][2].setOnClickListener(this);
            auxNumbers[2][1][3] = (TextView) findViewById(R.id.textView213);auxNumbers[2][1][3].setOnClickListener(this);
            auxNumbers[2][1][4] = (TextView) findViewById(R.id.textView214);auxNumbers[2][1][4].setOnClickListener(this);
            auxNumbers[2][1][5] = (TextView) findViewById(R.id.textView215);auxNumbers[2][1][5].setOnClickListener(this);
            auxNumbers[2][1][6] = (TextView) findViewById(R.id.textView216);auxNumbers[2][1][6].setOnClickListener(this);
            auxNumbers[2][1][7] = (TextView) findViewById(R.id.textView217);auxNumbers[2][1][7].setOnClickListener(this);
            auxNumbers[2][1][8] = (TextView) findViewById(R.id.textView218);auxNumbers[2][1][8].setOnClickListener(this);
            auxNumbers[2][1][9] = (TextView) findViewById(R.id.textView219);auxNumbers[2][1][9].setOnClickListener(this);

            auxNumbers[2][2][1] = (TextView) findViewById(R.id.textView221);auxNumbers[2][2][1].setOnClickListener(this);
            auxNumbers[2][2][2] = (TextView) findViewById(R.id.textView222);auxNumbers[2][2][2].setOnClickListener(this);
            auxNumbers[2][2][3] = (TextView) findViewById(R.id.textView223);auxNumbers[2][2][3].setOnClickListener(this);
            auxNumbers[2][2][4] = (TextView) findViewById(R.id.textView224);auxNumbers[2][2][4].setOnClickListener(this);
            auxNumbers[2][2][5] = (TextView) findViewById(R.id.textView225);auxNumbers[2][2][5].setOnClickListener(this);
            auxNumbers[2][2][6] = (TextView) findViewById(R.id.textView226);auxNumbers[2][2][6].setOnClickListener(this);
            auxNumbers[2][2][7] = (TextView) findViewById(R.id.textView227);auxNumbers[2][2][7].setOnClickListener(this);
            auxNumbers[2][2][8] = (TextView) findViewById(R.id.textView228);auxNumbers[2][2][8].setOnClickListener(this);
            auxNumbers[2][2][9] = (TextView) findViewById(R.id.textView229);auxNumbers[2][2][9].setOnClickListener(this);

            auxNumbers[2][3][1] = (TextView) findViewById(R.id.textView231);auxNumbers[2][3][1].setOnClickListener(this);
            auxNumbers[2][3][2] = (TextView) findViewById(R.id.textView232);auxNumbers[2][3][2].setOnClickListener(this);
            auxNumbers[2][3][3] = (TextView) findViewById(R.id.textView233);auxNumbers[2][3][3].setOnClickListener(this);
            auxNumbers[2][3][4] = (TextView) findViewById(R.id.textView234);auxNumbers[2][3][4].setOnClickListener(this);
            auxNumbers[2][3][5] = (TextView) findViewById(R.id.textView235);auxNumbers[2][3][5].setOnClickListener(this);
            auxNumbers[2][3][6] = (TextView) findViewById(R.id.textView236);auxNumbers[2][3][6].setOnClickListener(this);
            auxNumbers[2][3][7] = (TextView) findViewById(R.id.textView237);auxNumbers[2][3][7].setOnClickListener(this);
            auxNumbers[2][3][8] = (TextView) findViewById(R.id.textView238);auxNumbers[2][3][8].setOnClickListener(this);
            auxNumbers[2][3][9] = (TextView) findViewById(R.id.textView239);auxNumbers[2][3][9].setOnClickListener(this);

            auxNumbers[2][4][1] = (TextView) findViewById(R.id.textView241);auxNumbers[2][4][1].setOnClickListener(this);
            auxNumbers[2][4][2] = (TextView) findViewById(R.id.textView242);auxNumbers[2][4][2].setOnClickListener(this);
            auxNumbers[2][4][3] = (TextView) findViewById(R.id.textView243);auxNumbers[2][4][3].setOnClickListener(this);
            auxNumbers[2][4][4] = (TextView) findViewById(R.id.textView244);auxNumbers[2][4][4].setOnClickListener(this);
            auxNumbers[2][4][5] = (TextView) findViewById(R.id.textView245);auxNumbers[2][4][5].setOnClickListener(this);
            auxNumbers[2][4][6] = (TextView) findViewById(R.id.textView246);auxNumbers[2][4][6].setOnClickListener(this);
            auxNumbers[2][4][7] = (TextView) findViewById(R.id.textView247);auxNumbers[2][4][7].setOnClickListener(this);
            auxNumbers[2][4][8] = (TextView) findViewById(R.id.textView248);auxNumbers[2][4][8].setOnClickListener(this);
            auxNumbers[2][4][9] = (TextView) findViewById(R.id.textView249);auxNumbers[2][4][9].setOnClickListener(this);

            auxNumbers[2][5][1] = (TextView) findViewById(R.id.textView251);auxNumbers[2][5][1].setOnClickListener(this);
            auxNumbers[2][5][2] = (TextView) findViewById(R.id.textView252);auxNumbers[2][5][2].setOnClickListener(this);
            auxNumbers[2][5][3] = (TextView) findViewById(R.id.textView253);auxNumbers[2][5][3].setOnClickListener(this);
            auxNumbers[2][5][4] = (TextView) findViewById(R.id.textView254);auxNumbers[2][5][4].setOnClickListener(this);
            auxNumbers[2][5][5] = (TextView) findViewById(R.id.textView255);auxNumbers[2][5][5].setOnClickListener(this);
            auxNumbers[2][5][6] = (TextView) findViewById(R.id.textView256);auxNumbers[2][5][6].setOnClickListener(this);
            auxNumbers[2][5][7] = (TextView) findViewById(R.id.textView257);auxNumbers[2][5][7].setOnClickListener(this);
            auxNumbers[2][5][8] = (TextView) findViewById(R.id.textView258);auxNumbers[2][5][8].setOnClickListener(this);
            auxNumbers[2][5][9] = (TextView) findViewById(R.id.textView259);auxNumbers[2][5][9].setOnClickListener(this);

            auxNumbers[2][6][1] = (TextView) findViewById(R.id.textView261);auxNumbers[2][6][1].setOnClickListener(this);
            auxNumbers[2][6][2] = (TextView) findViewById(R.id.textView262);auxNumbers[2][6][2].setOnClickListener(this);
            auxNumbers[2][6][3] = (TextView) findViewById(R.id.textView263);auxNumbers[2][6][3].setOnClickListener(this);
            auxNumbers[2][6][4] = (TextView) findViewById(R.id.textView264);auxNumbers[2][6][4].setOnClickListener(this);
            auxNumbers[2][6][5] = (TextView) findViewById(R.id.textView265);auxNumbers[2][6][5].setOnClickListener(this);
            auxNumbers[2][6][6] = (TextView) findViewById(R.id.textView266);auxNumbers[2][6][6].setOnClickListener(this);
            auxNumbers[2][6][7] = (TextView) findViewById(R.id.textView267);auxNumbers[2][6][7].setOnClickListener(this);
            auxNumbers[2][6][8] = (TextView) findViewById(R.id.textView268);auxNumbers[2][6][8].setOnClickListener(this);
            auxNumbers[2][6][9] = (TextView) findViewById(R.id.textView269);auxNumbers[2][6][9].setOnClickListener(this);

            auxNumbers[2][7][1] = (TextView) findViewById(R.id.textView271);auxNumbers[2][7][1].setOnClickListener(this);
            auxNumbers[2][7][2] = (TextView) findViewById(R.id.textView272);auxNumbers[2][7][2].setOnClickListener(this);
            auxNumbers[2][7][3] = (TextView) findViewById(R.id.textView273);auxNumbers[2][7][3].setOnClickListener(this);
            auxNumbers[2][7][4] = (TextView) findViewById(R.id.textView274);auxNumbers[2][7][4].setOnClickListener(this);
            auxNumbers[2][7][5] = (TextView) findViewById(R.id.textView275);auxNumbers[2][7][5].setOnClickListener(this);
            auxNumbers[2][7][6] = (TextView) findViewById(R.id.textView276);auxNumbers[2][7][6].setOnClickListener(this);
            auxNumbers[2][7][7] = (TextView) findViewById(R.id.textView277);auxNumbers[2][7][7].setOnClickListener(this);
            auxNumbers[2][7][8] = (TextView) findViewById(R.id.textView278);auxNumbers[2][7][8].setOnClickListener(this);
            auxNumbers[2][7][9] = (TextView) findViewById(R.id.textView279);auxNumbers[2][7][9].setOnClickListener(this);

            auxNumbers[2][8][1] = (TextView) findViewById(R.id.textView281);auxNumbers[2][8][1].setOnClickListener(this);
            auxNumbers[2][8][2] = (TextView) findViewById(R.id.textView282);auxNumbers[2][8][2].setOnClickListener(this);
            auxNumbers[2][8][3] = (TextView) findViewById(R.id.textView283);auxNumbers[2][8][3].setOnClickListener(this);
            auxNumbers[2][8][4] = (TextView) findViewById(R.id.textView284);auxNumbers[2][8][4].setOnClickListener(this);
            auxNumbers[2][8][5] = (TextView) findViewById(R.id.textView285);auxNumbers[2][8][5].setOnClickListener(this);
            auxNumbers[2][8][6] = (TextView) findViewById(R.id.textView286);auxNumbers[2][8][6].setOnClickListener(this);
            auxNumbers[2][8][7] = (TextView) findViewById(R.id.textView287);auxNumbers[2][8][7].setOnClickListener(this);
            auxNumbers[2][8][8] = (TextView) findViewById(R.id.textView288);auxNumbers[2][8][8].setOnClickListener(this);
            auxNumbers[2][8][9] = (TextView) findViewById(R.id.textView289);auxNumbers[2][8][9].setOnClickListener(this);

            auxNumbers[2][9][1] = (TextView) findViewById(R.id.textView291);auxNumbers[2][9][1].setOnClickListener(this);
            auxNumbers[2][9][2] = (TextView) findViewById(R.id.textView292);auxNumbers[2][9][2].setOnClickListener(this);
            auxNumbers[2][9][3] = (TextView) findViewById(R.id.textView293);auxNumbers[2][9][3].setOnClickListener(this);
            auxNumbers[2][9][4] = (TextView) findViewById(R.id.textView294);auxNumbers[2][9][4].setOnClickListener(this);
            auxNumbers[2][9][5] = (TextView) findViewById(R.id.textView295);auxNumbers[2][9][5].setOnClickListener(this);
            auxNumbers[2][9][6] = (TextView) findViewById(R.id.textView296);auxNumbers[2][9][6].setOnClickListener(this);
            auxNumbers[2][9][7] = (TextView) findViewById(R.id.textView297);auxNumbers[2][9][7].setOnClickListener(this);
            auxNumbers[2][9][8] = (TextView) findViewById(R.id.textView298);auxNumbers[2][9][8].setOnClickListener(this);
            auxNumbers[2][9][9] = (TextView) findViewById(R.id.textView299);auxNumbers[2][9][9].setOnClickListener(this);

            auxNumbers[3][1][1] = (TextView) findViewById(R.id.textView311);auxNumbers[3][1][1].setOnClickListener(this);
            auxNumbers[3][1][2] = (TextView) findViewById(R.id.textView312);auxNumbers[3][1][2].setOnClickListener(this);
            auxNumbers[3][1][3] = (TextView) findViewById(R.id.textView313);auxNumbers[3][1][3].setOnClickListener(this);
            auxNumbers[3][1][4] = (TextView) findViewById(R.id.textView314);auxNumbers[3][1][4].setOnClickListener(this);
            auxNumbers[3][1][5] = (TextView) findViewById(R.id.textView315);auxNumbers[3][1][5].setOnClickListener(this);
            auxNumbers[3][1][6] = (TextView) findViewById(R.id.textView316);auxNumbers[3][1][6].setOnClickListener(this);
            auxNumbers[3][1][7] = (TextView) findViewById(R.id.textView317);auxNumbers[3][1][7].setOnClickListener(this);
            auxNumbers[3][1][8] = (TextView) findViewById(R.id.textView318);auxNumbers[3][1][8].setOnClickListener(this);
            auxNumbers[3][1][9] = (TextView) findViewById(R.id.textView319);auxNumbers[3][1][9].setOnClickListener(this);

            auxNumbers[3][2][1] = (TextView) findViewById(R.id.textView321);auxNumbers[3][2][1].setOnClickListener(this);
            auxNumbers[3][2][2] = (TextView) findViewById(R.id.textView322);auxNumbers[3][2][2].setOnClickListener(this);
            auxNumbers[3][2][3] = (TextView) findViewById(R.id.textView323);auxNumbers[3][2][3].setOnClickListener(this);
            auxNumbers[3][2][4] = (TextView) findViewById(R.id.textView324);auxNumbers[3][2][4].setOnClickListener(this);
            auxNumbers[3][2][5] = (TextView) findViewById(R.id.textView325);auxNumbers[3][2][5].setOnClickListener(this);
            auxNumbers[3][2][6] = (TextView) findViewById(R.id.textView326);auxNumbers[3][2][6].setOnClickListener(this);
            auxNumbers[3][2][7] = (TextView) findViewById(R.id.textView327);auxNumbers[3][2][7].setOnClickListener(this);
            auxNumbers[3][2][8] = (TextView) findViewById(R.id.textView328);auxNumbers[3][2][8].setOnClickListener(this);
            auxNumbers[3][2][9] = (TextView) findViewById(R.id.textView329);auxNumbers[3][2][9].setOnClickListener(this);

            auxNumbers[3][3][1] = (TextView) findViewById(R.id.textView331);auxNumbers[3][3][1].setOnClickListener(this);
            auxNumbers[3][3][2] = (TextView) findViewById(R.id.textView332);auxNumbers[3][3][2].setOnClickListener(this);
            auxNumbers[3][3][3] = (TextView) findViewById(R.id.textView333);auxNumbers[3][3][3].setOnClickListener(this);
            auxNumbers[3][3][4] = (TextView) findViewById(R.id.textView334);auxNumbers[3][3][4].setOnClickListener(this);
            auxNumbers[3][3][5] = (TextView) findViewById(R.id.textView335);auxNumbers[3][3][5].setOnClickListener(this);
            auxNumbers[3][3][6] = (TextView) findViewById(R.id.textView336);auxNumbers[3][3][6].setOnClickListener(this);
            auxNumbers[3][3][7] = (TextView) findViewById(R.id.textView337);auxNumbers[3][3][7].setOnClickListener(this);
            auxNumbers[3][3][8] = (TextView) findViewById(R.id.textView338);auxNumbers[3][3][8].setOnClickListener(this);
            auxNumbers[3][3][9] = (TextView) findViewById(R.id.textView339);auxNumbers[3][3][9].setOnClickListener(this);

            auxNumbers[3][4][1] = (TextView) findViewById(R.id.textView341);auxNumbers[3][4][1].setOnClickListener(this);
            auxNumbers[3][4][2] = (TextView) findViewById(R.id.textView342);auxNumbers[3][4][2].setOnClickListener(this);
            auxNumbers[3][4][3] = (TextView) findViewById(R.id.textView343);auxNumbers[3][4][3].setOnClickListener(this);
            auxNumbers[3][4][4] = (TextView) findViewById(R.id.textView344);auxNumbers[3][4][4].setOnClickListener(this);
            auxNumbers[3][4][5] = (TextView) findViewById(R.id.textView345);auxNumbers[3][4][5].setOnClickListener(this);
            auxNumbers[3][4][6] = (TextView) findViewById(R.id.textView346);auxNumbers[3][4][6].setOnClickListener(this);
            auxNumbers[3][4][7] = (TextView) findViewById(R.id.textView347);auxNumbers[3][4][7].setOnClickListener(this);
            auxNumbers[3][4][8] = (TextView) findViewById(R.id.textView348);auxNumbers[3][4][8].setOnClickListener(this);
            auxNumbers[3][4][9] = (TextView) findViewById(R.id.textView349);auxNumbers[3][4][9].setOnClickListener(this);

            auxNumbers[3][5][1] = (TextView) findViewById(R.id.textView351);auxNumbers[3][5][1].setOnClickListener(this);
            auxNumbers[3][5][2] = (TextView) findViewById(R.id.textView352);auxNumbers[3][5][2].setOnClickListener(this);
            auxNumbers[3][5][3] = (TextView) findViewById(R.id.textView353);auxNumbers[3][5][3].setOnClickListener(this);
            auxNumbers[3][5][4] = (TextView) findViewById(R.id.textView354);auxNumbers[3][5][4].setOnClickListener(this);
            auxNumbers[3][5][5] = (TextView) findViewById(R.id.textView355);auxNumbers[3][5][5].setOnClickListener(this);
            auxNumbers[3][5][6] = (TextView) findViewById(R.id.textView356);auxNumbers[3][5][6].setOnClickListener(this);
            auxNumbers[3][5][7] = (TextView) findViewById(R.id.textView357);auxNumbers[3][5][7].setOnClickListener(this);
            auxNumbers[3][5][8] = (TextView) findViewById(R.id.textView358);auxNumbers[3][5][8].setOnClickListener(this);
            auxNumbers[3][5][9] = (TextView) findViewById(R.id.textView359);auxNumbers[3][5][9].setOnClickListener(this);

            auxNumbers[3][6][1] = (TextView) findViewById(R.id.textView361);auxNumbers[3][6][1].setOnClickListener(this);
            auxNumbers[3][6][2] = (TextView) findViewById(R.id.textView362);auxNumbers[3][6][2].setOnClickListener(this);
            auxNumbers[3][6][3] = (TextView) findViewById(R.id.textView363);auxNumbers[3][6][3].setOnClickListener(this);
            auxNumbers[3][6][4] = (TextView) findViewById(R.id.textView364);auxNumbers[3][6][4].setOnClickListener(this);
            auxNumbers[3][6][5] = (TextView) findViewById(R.id.textView365);auxNumbers[3][6][5].setOnClickListener(this);
            auxNumbers[3][6][6] = (TextView) findViewById(R.id.textView366);auxNumbers[3][6][6].setOnClickListener(this);
            auxNumbers[3][6][7] = (TextView) findViewById(R.id.textView367);auxNumbers[3][6][7].setOnClickListener(this);
            auxNumbers[3][6][8] = (TextView) findViewById(R.id.textView368);auxNumbers[3][6][8].setOnClickListener(this);
            auxNumbers[3][6][9] = (TextView) findViewById(R.id.textView369);auxNumbers[3][6][9].setOnClickListener(this);

            auxNumbers[3][7][1] = (TextView) findViewById(R.id.textView371);auxNumbers[3][7][1].setOnClickListener(this);
            auxNumbers[3][7][2] = (TextView) findViewById(R.id.textView372);auxNumbers[3][7][2].setOnClickListener(this);
            auxNumbers[3][7][3] = (TextView) findViewById(R.id.textView373);auxNumbers[3][7][3].setOnClickListener(this);
            auxNumbers[3][7][4] = (TextView) findViewById(R.id.textView374);auxNumbers[3][7][4].setOnClickListener(this);
            auxNumbers[3][7][5] = (TextView) findViewById(R.id.textView375);auxNumbers[3][7][5].setOnClickListener(this);
            auxNumbers[3][7][6] = (TextView) findViewById(R.id.textView376);auxNumbers[3][7][6].setOnClickListener(this);
            auxNumbers[3][7][7] = (TextView) findViewById(R.id.textView377);auxNumbers[3][7][7].setOnClickListener(this);
            auxNumbers[3][7][8] = (TextView) findViewById(R.id.textView378);auxNumbers[3][7][8].setOnClickListener(this);
            auxNumbers[3][7][9] = (TextView) findViewById(R.id.textView379);auxNumbers[3][7][9].setOnClickListener(this);

            auxNumbers[3][8][1] = (TextView) findViewById(R.id.textView381);auxNumbers[3][8][1].setOnClickListener(this);
            auxNumbers[3][8][2] = (TextView) findViewById(R.id.textView382);auxNumbers[3][8][2].setOnClickListener(this);
            auxNumbers[3][8][3] = (TextView) findViewById(R.id.textView383);auxNumbers[3][8][3].setOnClickListener(this);
            auxNumbers[3][8][4] = (TextView) findViewById(R.id.textView384);auxNumbers[3][8][4].setOnClickListener(this);
            auxNumbers[3][8][5] = (TextView) findViewById(R.id.textView385);auxNumbers[3][8][5].setOnClickListener(this);
            auxNumbers[3][8][6] = (TextView) findViewById(R.id.textView386);auxNumbers[3][8][6].setOnClickListener(this);
            auxNumbers[3][8][7] = (TextView) findViewById(R.id.textView387);auxNumbers[3][8][7].setOnClickListener(this);
            auxNumbers[3][8][8] = (TextView) findViewById(R.id.textView388);auxNumbers[3][8][8].setOnClickListener(this);
            auxNumbers[3][8][9] = (TextView) findViewById(R.id.textView389);auxNumbers[3][8][9].setOnClickListener(this);

            auxNumbers[3][9][1] = (TextView) findViewById(R.id.textView391);auxNumbers[3][9][1].setOnClickListener(this);
            auxNumbers[3][9][2] = (TextView) findViewById(R.id.textView392);auxNumbers[3][9][2].setOnClickListener(this);
            auxNumbers[3][9][3] = (TextView) findViewById(R.id.textView393);auxNumbers[3][9][3].setOnClickListener(this);
            auxNumbers[3][9][4] = (TextView) findViewById(R.id.textView394);auxNumbers[3][9][4].setOnClickListener(this);
            auxNumbers[3][9][5] = (TextView) findViewById(R.id.textView395);auxNumbers[3][9][5].setOnClickListener(this);
            auxNumbers[3][9][6] = (TextView) findViewById(R.id.textView396);auxNumbers[3][9][6].setOnClickListener(this);
            auxNumbers[3][9][7] = (TextView) findViewById(R.id.textView397);auxNumbers[3][9][7].setOnClickListener(this);
            auxNumbers[3][9][8] = (TextView) findViewById(R.id.textView398);auxNumbers[3][9][8].setOnClickListener(this);
            auxNumbers[3][9][9] = (TextView) findViewById(R.id.textView399);auxNumbers[3][9][9].setOnClickListener(this);

            auxNumbers[4][1][1] = (TextView) findViewById(R.id.textView411);auxNumbers[4][1][1].setOnClickListener(this);
            auxNumbers[4][1][2] = (TextView) findViewById(R.id.textView412);auxNumbers[4][1][2].setOnClickListener(this);
            auxNumbers[4][1][3] = (TextView) findViewById(R.id.textView413);auxNumbers[4][1][3].setOnClickListener(this);
            auxNumbers[4][1][4] = (TextView) findViewById(R.id.textView414);auxNumbers[4][1][4].setOnClickListener(this);
            auxNumbers[4][1][5] = (TextView) findViewById(R.id.textView415);auxNumbers[4][1][5].setOnClickListener(this);
            auxNumbers[4][1][6] = (TextView) findViewById(R.id.textView416);auxNumbers[4][1][6].setOnClickListener(this);
            auxNumbers[4][1][7] = (TextView) findViewById(R.id.textView417);auxNumbers[4][1][7].setOnClickListener(this);
            auxNumbers[4][1][8] = (TextView) findViewById(R.id.textView418);auxNumbers[4][1][8].setOnClickListener(this);
            auxNumbers[4][1][9] = (TextView) findViewById(R.id.textView419);auxNumbers[4][1][9].setOnClickListener(this);

            auxNumbers[4][2][1] = (TextView) findViewById(R.id.textView421);auxNumbers[4][2][1].setOnClickListener(this);
            auxNumbers[4][2][2] = (TextView) findViewById(R.id.textView422);auxNumbers[4][2][2].setOnClickListener(this);
            auxNumbers[4][2][3] = (TextView) findViewById(R.id.textView423);auxNumbers[4][2][3].setOnClickListener(this);
            auxNumbers[4][2][4] = (TextView) findViewById(R.id.textView424);auxNumbers[4][2][4].setOnClickListener(this);
            auxNumbers[4][2][5] = (TextView) findViewById(R.id.textView425);auxNumbers[4][2][5].setOnClickListener(this);
            auxNumbers[4][2][6] = (TextView) findViewById(R.id.textView426);auxNumbers[4][2][6].setOnClickListener(this);
            auxNumbers[4][2][7] = (TextView) findViewById(R.id.textView427);auxNumbers[4][2][7].setOnClickListener(this);
            auxNumbers[4][2][8] = (TextView) findViewById(R.id.textView428);auxNumbers[4][2][8].setOnClickListener(this);
            auxNumbers[4][2][9] = (TextView) findViewById(R.id.textView429);auxNumbers[4][2][9].setOnClickListener(this);

            auxNumbers[4][3][1] = (TextView) findViewById(R.id.textView431);auxNumbers[4][3][1].setOnClickListener(this);
            auxNumbers[4][3][2] = (TextView) findViewById(R.id.textView432);auxNumbers[4][3][2].setOnClickListener(this);
            auxNumbers[4][3][3] = (TextView) findViewById(R.id.textView433);auxNumbers[4][3][3].setOnClickListener(this);
            auxNumbers[4][3][4] = (TextView) findViewById(R.id.textView434);auxNumbers[4][3][4].setOnClickListener(this);
            auxNumbers[4][3][5] = (TextView) findViewById(R.id.textView435);auxNumbers[4][3][5].setOnClickListener(this);
            auxNumbers[4][3][6] = (TextView) findViewById(R.id.textView436);auxNumbers[4][3][6].setOnClickListener(this);
            auxNumbers[4][3][7] = (TextView) findViewById(R.id.textView437);auxNumbers[4][3][7].setOnClickListener(this);
            auxNumbers[4][3][8] = (TextView) findViewById(R.id.textView438);auxNumbers[4][3][8].setOnClickListener(this);
            auxNumbers[4][3][9] = (TextView) findViewById(R.id.textView439);auxNumbers[4][3][9].setOnClickListener(this);

            auxNumbers[4][4][1] = (TextView) findViewById(R.id.textView441);auxNumbers[4][4][1].setOnClickListener(this);
            auxNumbers[4][4][2] = (TextView) findViewById(R.id.textView442);auxNumbers[4][4][2].setOnClickListener(this);
            auxNumbers[4][4][3] = (TextView) findViewById(R.id.textView443);auxNumbers[4][4][3].setOnClickListener(this);
            auxNumbers[4][4][4] = (TextView) findViewById(R.id.textView444);auxNumbers[4][4][4].setOnClickListener(this);
            auxNumbers[4][4][5] = (TextView) findViewById(R.id.textView445);auxNumbers[4][4][5].setOnClickListener(this);
            auxNumbers[4][4][6] = (TextView) findViewById(R.id.textView446);auxNumbers[4][4][6].setOnClickListener(this);
            auxNumbers[4][4][7] = (TextView) findViewById(R.id.textView447);auxNumbers[4][4][7].setOnClickListener(this);
            auxNumbers[4][4][8] = (TextView) findViewById(R.id.textView448);auxNumbers[4][4][8].setOnClickListener(this);
            auxNumbers[4][4][9] = (TextView) findViewById(R.id.textView449);auxNumbers[4][4][9].setOnClickListener(this);

            auxNumbers[4][5][1] = (TextView) findViewById(R.id.textView451);auxNumbers[4][5][1].setOnClickListener(this);
            auxNumbers[4][5][2] = (TextView) findViewById(R.id.textView452);auxNumbers[4][5][2].setOnClickListener(this);
            auxNumbers[4][5][3] = (TextView) findViewById(R.id.textView453);auxNumbers[4][5][3].setOnClickListener(this);
            auxNumbers[4][5][4] = (TextView) findViewById(R.id.textView454);auxNumbers[4][5][4].setOnClickListener(this);
            auxNumbers[4][5][5] = (TextView) findViewById(R.id.textView455);auxNumbers[4][5][5].setOnClickListener(this);
            auxNumbers[4][5][6] = (TextView) findViewById(R.id.textView456);auxNumbers[4][5][6].setOnClickListener(this);
            auxNumbers[4][5][7] = (TextView) findViewById(R.id.textView457);auxNumbers[4][5][7].setOnClickListener(this);
            auxNumbers[4][5][8] = (TextView) findViewById(R.id.textView458);auxNumbers[4][5][8].setOnClickListener(this);
            auxNumbers[4][5][9] = (TextView) findViewById(R.id.textView459);auxNumbers[4][5][9].setOnClickListener(this);

            auxNumbers[4][6][1] = (TextView) findViewById(R.id.textView461);auxNumbers[4][6][1].setOnClickListener(this);
            auxNumbers[4][6][2] = (TextView) findViewById(R.id.textView462);auxNumbers[4][6][2].setOnClickListener(this);
            auxNumbers[4][6][3] = (TextView) findViewById(R.id.textView463);auxNumbers[4][6][3].setOnClickListener(this);
            auxNumbers[4][6][4] = (TextView) findViewById(R.id.textView464);auxNumbers[4][6][4].setOnClickListener(this);
            auxNumbers[4][6][5] = (TextView) findViewById(R.id.textView465);auxNumbers[4][6][5].setOnClickListener(this);
            auxNumbers[4][6][6] = (TextView) findViewById(R.id.textView466);auxNumbers[4][6][6].setOnClickListener(this);
            auxNumbers[4][6][7] = (TextView) findViewById(R.id.textView467);auxNumbers[4][6][7].setOnClickListener(this);
            auxNumbers[4][6][8] = (TextView) findViewById(R.id.textView468);auxNumbers[4][6][8].setOnClickListener(this);
            auxNumbers[4][6][9] = (TextView) findViewById(R.id.textView469);auxNumbers[4][6][9].setOnClickListener(this);

            auxNumbers[4][7][1] = (TextView) findViewById(R.id.textView471);auxNumbers[4][7][1].setOnClickListener(this);
            auxNumbers[4][7][2] = (TextView) findViewById(R.id.textView472);auxNumbers[4][7][2].setOnClickListener(this);
            auxNumbers[4][7][3] = (TextView) findViewById(R.id.textView473);auxNumbers[4][7][3].setOnClickListener(this);
            auxNumbers[4][7][4] = (TextView) findViewById(R.id.textView474);auxNumbers[4][7][4].setOnClickListener(this);
            auxNumbers[4][7][5] = (TextView) findViewById(R.id.textView475);auxNumbers[4][7][5].setOnClickListener(this);
            auxNumbers[4][7][6] = (TextView) findViewById(R.id.textView476);auxNumbers[4][7][6].setOnClickListener(this);
            auxNumbers[4][7][7] = (TextView) findViewById(R.id.textView477);auxNumbers[4][7][7].setOnClickListener(this);
            auxNumbers[4][7][8] = (TextView) findViewById(R.id.textView478);auxNumbers[4][7][8].setOnClickListener(this);
            auxNumbers[4][7][9] = (TextView) findViewById(R.id.textView479);auxNumbers[4][7][9].setOnClickListener(this);

            auxNumbers[4][8][1] = (TextView) findViewById(R.id.textView481);auxNumbers[4][8][1].setOnClickListener(this);
            auxNumbers[4][8][2] = (TextView) findViewById(R.id.textView482);auxNumbers[4][8][2].setOnClickListener(this);
            auxNumbers[4][8][3] = (TextView) findViewById(R.id.textView483);auxNumbers[4][8][3].setOnClickListener(this);
            auxNumbers[4][8][4] = (TextView) findViewById(R.id.textView484);auxNumbers[4][8][4].setOnClickListener(this);
            auxNumbers[4][8][5] = (TextView) findViewById(R.id.textView485);auxNumbers[4][8][5].setOnClickListener(this);
            auxNumbers[4][8][6] = (TextView) findViewById(R.id.textView486);auxNumbers[4][8][6].setOnClickListener(this);
            auxNumbers[4][8][7] = (TextView) findViewById(R.id.textView487);auxNumbers[4][8][7].setOnClickListener(this);
            auxNumbers[4][8][8] = (TextView) findViewById(R.id.textView488);auxNumbers[4][8][8].setOnClickListener(this);
            auxNumbers[4][8][9] = (TextView) findViewById(R.id.textView489);auxNumbers[4][8][9].setOnClickListener(this);

            auxNumbers[4][9][1] = (TextView) findViewById(R.id.textView491);auxNumbers[4][9][1].setOnClickListener(this);
            auxNumbers[4][9][2] = (TextView) findViewById(R.id.textView492);auxNumbers[4][9][2].setOnClickListener(this);
            auxNumbers[4][9][3] = (TextView) findViewById(R.id.textView493);auxNumbers[4][9][3].setOnClickListener(this);
            auxNumbers[4][9][4] = (TextView) findViewById(R.id.textView494);auxNumbers[4][9][4].setOnClickListener(this);
            auxNumbers[4][9][5] = (TextView) findViewById(R.id.textView495);auxNumbers[4][9][5].setOnClickListener(this);
            auxNumbers[4][9][6] = (TextView) findViewById(R.id.textView496);auxNumbers[4][9][6].setOnClickListener(this);
            auxNumbers[4][9][7] = (TextView) findViewById(R.id.textView497);auxNumbers[4][9][7].setOnClickListener(this);
            auxNumbers[4][9][8] = (TextView) findViewById(R.id.textView498);auxNumbers[4][9][8].setOnClickListener(this);
            auxNumbers[4][9][9] = (TextView) findViewById(R.id.textView499);auxNumbers[4][9][9].setOnClickListener(this);

            auxNumbers[5][1][1] = (TextView) findViewById(R.id.textView511);auxNumbers[5][1][1].setOnClickListener(this);
            auxNumbers[5][1][2] = (TextView) findViewById(R.id.textView512);auxNumbers[5][1][2].setOnClickListener(this);
            auxNumbers[5][1][3] = (TextView) findViewById(R.id.textView513);auxNumbers[5][1][3].setOnClickListener(this);
            auxNumbers[5][1][4] = (TextView) findViewById(R.id.textView514);auxNumbers[5][1][4].setOnClickListener(this);
            auxNumbers[5][1][5] = (TextView) findViewById(R.id.textView515);auxNumbers[5][1][5].setOnClickListener(this);
            auxNumbers[5][1][6] = (TextView) findViewById(R.id.textView516);auxNumbers[5][1][6].setOnClickListener(this);
            auxNumbers[5][1][7] = (TextView) findViewById(R.id.textView517);auxNumbers[5][1][7].setOnClickListener(this);
            auxNumbers[5][1][8] = (TextView) findViewById(R.id.textView518);auxNumbers[5][1][8].setOnClickListener(this);
            auxNumbers[5][1][9] = (TextView) findViewById(R.id.textView519);auxNumbers[5][1][9].setOnClickListener(this);

            auxNumbers[5][2][1] = (TextView) findViewById(R.id.textView521);auxNumbers[5][2][1].setOnClickListener(this);
            auxNumbers[5][2][2] = (TextView) findViewById(R.id.textView522);auxNumbers[5][2][2].setOnClickListener(this);
            auxNumbers[5][2][3] = (TextView) findViewById(R.id.textView523);auxNumbers[5][2][3].setOnClickListener(this);
            auxNumbers[5][2][4] = (TextView) findViewById(R.id.textView524);auxNumbers[5][2][4].setOnClickListener(this);
            auxNumbers[5][2][5] = (TextView) findViewById(R.id.textView525);auxNumbers[5][2][5].setOnClickListener(this);
            auxNumbers[5][2][6] = (TextView) findViewById(R.id.textView526);auxNumbers[5][2][6].setOnClickListener(this);
            auxNumbers[5][2][7] = (TextView) findViewById(R.id.textView527);auxNumbers[5][2][7].setOnClickListener(this);
            auxNumbers[5][2][8] = (TextView) findViewById(R.id.textView528);auxNumbers[5][2][8].setOnClickListener(this);
            auxNumbers[5][2][9] = (TextView) findViewById(R.id.textView529);auxNumbers[5][2][9].setOnClickListener(this);

            auxNumbers[5][3][1] = (TextView) findViewById(R.id.textView531);auxNumbers[5][3][1].setOnClickListener(this);
            auxNumbers[5][3][2] = (TextView) findViewById(R.id.textView532);auxNumbers[5][3][2].setOnClickListener(this);
            auxNumbers[5][3][3] = (TextView) findViewById(R.id.textView533);auxNumbers[5][3][3].setOnClickListener(this);
            auxNumbers[5][3][4] = (TextView) findViewById(R.id.textView534);auxNumbers[5][3][4].setOnClickListener(this);
            auxNumbers[5][3][5] = (TextView) findViewById(R.id.textView535);auxNumbers[5][3][5].setOnClickListener(this);
            auxNumbers[5][3][6] = (TextView) findViewById(R.id.textView536);auxNumbers[5][3][6].setOnClickListener(this);
            auxNumbers[5][3][7] = (TextView) findViewById(R.id.textView537);auxNumbers[5][3][7].setOnClickListener(this);
            auxNumbers[5][3][8] = (TextView) findViewById(R.id.textView538);auxNumbers[5][3][8].setOnClickListener(this);
            auxNumbers[5][3][9] = (TextView) findViewById(R.id.textView539);auxNumbers[5][3][9].setOnClickListener(this);

            auxNumbers[5][4][1] = (TextView) findViewById(R.id.textView541);auxNumbers[5][4][1].setOnClickListener(this);
            auxNumbers[5][4][2] = (TextView) findViewById(R.id.textView542);auxNumbers[5][4][2].setOnClickListener(this);
            auxNumbers[5][4][3] = (TextView) findViewById(R.id.textView543);auxNumbers[5][4][3].setOnClickListener(this);
            auxNumbers[5][4][4] = (TextView) findViewById(R.id.textView544);auxNumbers[5][4][4].setOnClickListener(this);
            auxNumbers[5][4][5] = (TextView) findViewById(R.id.textView545);auxNumbers[5][4][5].setOnClickListener(this);
            auxNumbers[5][4][6] = (TextView) findViewById(R.id.textView546);auxNumbers[5][4][6].setOnClickListener(this);
            auxNumbers[5][4][7] = (TextView) findViewById(R.id.textView547);auxNumbers[5][4][7].setOnClickListener(this);
            auxNumbers[5][4][8] = (TextView) findViewById(R.id.textView548);auxNumbers[5][4][8].setOnClickListener(this);
            auxNumbers[5][4][9] = (TextView) findViewById(R.id.textView549);auxNumbers[5][4][9].setOnClickListener(this);

            auxNumbers[5][5][1] = (TextView) findViewById(R.id.textView551);auxNumbers[5][5][1].setOnClickListener(this);
            auxNumbers[5][5][2] = (TextView) findViewById(R.id.textView552);auxNumbers[5][5][2].setOnClickListener(this);
            auxNumbers[5][5][3] = (TextView) findViewById(R.id.textView553);auxNumbers[5][5][3].setOnClickListener(this);
            auxNumbers[5][5][4] = (TextView) findViewById(R.id.textView554);auxNumbers[5][5][4].setOnClickListener(this);
            auxNumbers[5][5][5] = (TextView) findViewById(R.id.textView555);auxNumbers[5][5][5].setOnClickListener(this);
            auxNumbers[5][5][6] = (TextView) findViewById(R.id.textView556);auxNumbers[5][5][6].setOnClickListener(this);
            auxNumbers[5][5][7] = (TextView) findViewById(R.id.textView557);auxNumbers[5][5][7].setOnClickListener(this);
            auxNumbers[5][5][8] = (TextView) findViewById(R.id.textView558);auxNumbers[5][5][8].setOnClickListener(this);
            auxNumbers[5][5][9] = (TextView) findViewById(R.id.textView559);auxNumbers[5][5][9].setOnClickListener(this);

            auxNumbers[5][6][1] = (TextView) findViewById(R.id.textView561);auxNumbers[5][6][1].setOnClickListener(this);
            auxNumbers[5][6][2] = (TextView) findViewById(R.id.textView562);auxNumbers[5][6][2].setOnClickListener(this);
            auxNumbers[5][6][3] = (TextView) findViewById(R.id.textView563);auxNumbers[5][6][3].setOnClickListener(this);
            auxNumbers[5][6][4] = (TextView) findViewById(R.id.textView564);auxNumbers[5][6][4].setOnClickListener(this);
            auxNumbers[5][6][5] = (TextView) findViewById(R.id.textView565);auxNumbers[5][6][5].setOnClickListener(this);
            auxNumbers[5][6][6] = (TextView) findViewById(R.id.textView566);auxNumbers[5][6][6].setOnClickListener(this);
            auxNumbers[5][6][7] = (TextView) findViewById(R.id.textView567);auxNumbers[5][6][7].setOnClickListener(this);
            auxNumbers[5][6][8] = (TextView) findViewById(R.id.textView568);auxNumbers[5][6][8].setOnClickListener(this);
            auxNumbers[5][6][9] = (TextView) findViewById(R.id.textView569);auxNumbers[5][6][9].setOnClickListener(this);

            auxNumbers[5][7][1] = (TextView) findViewById(R.id.textView571);auxNumbers[5][7][1].setOnClickListener(this);
            auxNumbers[5][7][2] = (TextView) findViewById(R.id.textView572);auxNumbers[5][7][2].setOnClickListener(this);
            auxNumbers[5][7][3] = (TextView) findViewById(R.id.textView573);auxNumbers[5][7][3].setOnClickListener(this);
            auxNumbers[5][7][4] = (TextView) findViewById(R.id.textView574);auxNumbers[5][7][4].setOnClickListener(this);
            auxNumbers[5][7][5] = (TextView) findViewById(R.id.textView575);auxNumbers[5][7][5].setOnClickListener(this);
            auxNumbers[5][7][6] = (TextView) findViewById(R.id.textView576);auxNumbers[5][7][6].setOnClickListener(this);
            auxNumbers[5][7][7] = (TextView) findViewById(R.id.textView577);auxNumbers[5][7][7].setOnClickListener(this);
            auxNumbers[5][7][8] = (TextView) findViewById(R.id.textView578);auxNumbers[5][7][8].setOnClickListener(this);
            auxNumbers[5][7][9] = (TextView) findViewById(R.id.textView579);auxNumbers[5][7][9].setOnClickListener(this);

            auxNumbers[5][8][1] = (TextView) findViewById(R.id.textView581);auxNumbers[5][8][1].setOnClickListener(this);
            auxNumbers[5][8][2] = (TextView) findViewById(R.id.textView582);auxNumbers[5][8][2].setOnClickListener(this);
            auxNumbers[5][8][3] = (TextView) findViewById(R.id.textView583);auxNumbers[5][8][3].setOnClickListener(this);
            auxNumbers[5][8][4] = (TextView) findViewById(R.id.textView584);auxNumbers[5][8][4].setOnClickListener(this);
            auxNumbers[5][8][5] = (TextView) findViewById(R.id.textView585);auxNumbers[5][8][5].setOnClickListener(this);
            auxNumbers[5][8][6] = (TextView) findViewById(R.id.textView586);auxNumbers[5][8][6].setOnClickListener(this);
            auxNumbers[5][8][7] = (TextView) findViewById(R.id.textView587);auxNumbers[5][8][7].setOnClickListener(this);
            auxNumbers[5][8][8] = (TextView) findViewById(R.id.textView588);auxNumbers[5][8][8].setOnClickListener(this);
            auxNumbers[5][8][9] = (TextView) findViewById(R.id.textView589);auxNumbers[5][8][9].setOnClickListener(this);

            auxNumbers[5][9][1] = (TextView) findViewById(R.id.textView591);auxNumbers[5][9][1].setOnClickListener(this);
            auxNumbers[5][9][2] = (TextView) findViewById(R.id.textView592);auxNumbers[5][9][2].setOnClickListener(this);
            auxNumbers[5][9][3] = (TextView) findViewById(R.id.textView593);auxNumbers[5][9][3].setOnClickListener(this);
            auxNumbers[5][9][4] = (TextView) findViewById(R.id.textView594);auxNumbers[5][9][4].setOnClickListener(this);
            auxNumbers[5][9][5] = (TextView) findViewById(R.id.textView595);auxNumbers[5][9][5].setOnClickListener(this);
            auxNumbers[5][9][6] = (TextView) findViewById(R.id.textView596);auxNumbers[5][9][6].setOnClickListener(this);
            auxNumbers[5][9][7] = (TextView) findViewById(R.id.textView597);auxNumbers[5][9][7].setOnClickListener(this);
            auxNumbers[5][9][8] = (TextView) findViewById(R.id.textView598);auxNumbers[5][9][8].setOnClickListener(this);
            auxNumbers[5][9][9] = (TextView) findViewById(R.id.textView599);auxNumbers[5][9][9].setOnClickListener(this);

            auxNumbers[6][1][1] = (TextView) findViewById(R.id.textView611);auxNumbers[6][1][1].setOnClickListener(this);
            auxNumbers[6][1][2] = (TextView) findViewById(R.id.textView612);auxNumbers[6][1][2].setOnClickListener(this);
            auxNumbers[6][1][3] = (TextView) findViewById(R.id.textView613);auxNumbers[6][1][3].setOnClickListener(this);
            auxNumbers[6][1][4] = (TextView) findViewById(R.id.textView614);auxNumbers[6][1][4].setOnClickListener(this);
            auxNumbers[6][1][5] = (TextView) findViewById(R.id.textView615);auxNumbers[6][1][5].setOnClickListener(this);
            auxNumbers[6][1][6] = (TextView) findViewById(R.id.textView616);auxNumbers[6][1][6].setOnClickListener(this);
            auxNumbers[6][1][7] = (TextView) findViewById(R.id.textView617);auxNumbers[6][1][7].setOnClickListener(this);
            auxNumbers[6][1][8] = (TextView) findViewById(R.id.textView618);auxNumbers[6][1][8].setOnClickListener(this);
            auxNumbers[6][1][9] = (TextView) findViewById(R.id.textView619);auxNumbers[6][1][9].setOnClickListener(this);

            auxNumbers[6][2][1] = (TextView) findViewById(R.id.textView621);auxNumbers[6][2][1].setOnClickListener(this);
            auxNumbers[6][2][2] = (TextView) findViewById(R.id.textView622);auxNumbers[6][2][2].setOnClickListener(this);
            auxNumbers[6][2][3] = (TextView) findViewById(R.id.textView623);auxNumbers[6][2][3].setOnClickListener(this);
            auxNumbers[6][2][4] = (TextView) findViewById(R.id.textView624);auxNumbers[6][2][4].setOnClickListener(this);
            auxNumbers[6][2][5] = (TextView) findViewById(R.id.textView625);auxNumbers[6][2][5].setOnClickListener(this);
            auxNumbers[6][2][6] = (TextView) findViewById(R.id.textView626);auxNumbers[6][2][6].setOnClickListener(this);
            auxNumbers[6][2][7] = (TextView) findViewById(R.id.textView627);auxNumbers[6][2][7].setOnClickListener(this);
            auxNumbers[6][2][8] = (TextView) findViewById(R.id.textView628);auxNumbers[6][2][8].setOnClickListener(this);
            auxNumbers[6][2][9] = (TextView) findViewById(R.id.textView629);auxNumbers[6][2][9].setOnClickListener(this);

            auxNumbers[6][3][1] = (TextView) findViewById(R.id.textView631);auxNumbers[6][3][1].setOnClickListener(this);
            auxNumbers[6][3][2] = (TextView) findViewById(R.id.textView632);auxNumbers[6][3][2].setOnClickListener(this);
            auxNumbers[6][3][3] = (TextView) findViewById(R.id.textView633);auxNumbers[6][3][3].setOnClickListener(this);
            auxNumbers[6][3][4] = (TextView) findViewById(R.id.textView634);auxNumbers[6][3][4].setOnClickListener(this);
            auxNumbers[6][3][5] = (TextView) findViewById(R.id.textView635);auxNumbers[6][3][5].setOnClickListener(this);
            auxNumbers[6][3][6] = (TextView) findViewById(R.id.textView636);auxNumbers[6][3][6].setOnClickListener(this);
            auxNumbers[6][3][7] = (TextView) findViewById(R.id.textView637);auxNumbers[6][3][7].setOnClickListener(this);
            auxNumbers[6][3][8] = (TextView) findViewById(R.id.textView638);auxNumbers[6][3][8].setOnClickListener(this);
            auxNumbers[6][3][9] = (TextView) findViewById(R.id.textView639);auxNumbers[6][3][9].setOnClickListener(this);

            auxNumbers[6][4][1] = (TextView) findViewById(R.id.textView641);auxNumbers[6][4][1].setOnClickListener(this);
            auxNumbers[6][4][2] = (TextView) findViewById(R.id.textView642);auxNumbers[6][4][2].setOnClickListener(this);
            auxNumbers[6][4][3] = (TextView) findViewById(R.id.textView643);auxNumbers[6][4][3].setOnClickListener(this);
            auxNumbers[6][4][4] = (TextView) findViewById(R.id.textView644);auxNumbers[6][4][4].setOnClickListener(this);
            auxNumbers[6][4][5] = (TextView) findViewById(R.id.textView645);auxNumbers[6][4][5].setOnClickListener(this);
            auxNumbers[6][4][6] = (TextView) findViewById(R.id.textView646);auxNumbers[6][4][6].setOnClickListener(this);
            auxNumbers[6][4][7] = (TextView) findViewById(R.id.textView647);auxNumbers[6][4][7].setOnClickListener(this);
            auxNumbers[6][4][8] = (TextView) findViewById(R.id.textView648);auxNumbers[6][4][8].setOnClickListener(this);
            auxNumbers[6][4][9] = (TextView) findViewById(R.id.textView649);auxNumbers[6][4][9].setOnClickListener(this);

            auxNumbers[6][5][1] = (TextView) findViewById(R.id.textView651);auxNumbers[6][5][1].setOnClickListener(this);
            auxNumbers[6][5][2] = (TextView) findViewById(R.id.textView652);auxNumbers[6][5][2].setOnClickListener(this);
            auxNumbers[6][5][3] = (TextView) findViewById(R.id.textView653);auxNumbers[6][5][3].setOnClickListener(this);
            auxNumbers[6][5][4] = (TextView) findViewById(R.id.textView654);auxNumbers[6][5][4].setOnClickListener(this);
            auxNumbers[6][5][5] = (TextView) findViewById(R.id.textView655);auxNumbers[6][5][5].setOnClickListener(this);
            auxNumbers[6][5][6] = (TextView) findViewById(R.id.textView656);auxNumbers[6][5][6].setOnClickListener(this);
            auxNumbers[6][5][7] = (TextView) findViewById(R.id.textView657);auxNumbers[6][5][7].setOnClickListener(this);
            auxNumbers[6][5][8] = (TextView) findViewById(R.id.textView658);auxNumbers[6][5][8].setOnClickListener(this);
            auxNumbers[6][5][9] = (TextView) findViewById(R.id.textView659);auxNumbers[6][5][9].setOnClickListener(this);

            auxNumbers[6][6][1] = (TextView) findViewById(R.id.textView661);auxNumbers[6][6][1].setOnClickListener(this);
            auxNumbers[6][6][2] = (TextView) findViewById(R.id.textView662);auxNumbers[6][6][2].setOnClickListener(this);
            auxNumbers[6][6][3] = (TextView) findViewById(R.id.textView663);auxNumbers[6][6][3].setOnClickListener(this);
            auxNumbers[6][6][4] = (TextView) findViewById(R.id.textView664);auxNumbers[6][6][4].setOnClickListener(this);
            auxNumbers[6][6][5] = (TextView) findViewById(R.id.textView665);auxNumbers[6][6][5].setOnClickListener(this);
            auxNumbers[6][6][6] = (TextView) findViewById(R.id.textView666);auxNumbers[6][6][6].setOnClickListener(this);
            auxNumbers[6][6][7] = (TextView) findViewById(R.id.textView667);auxNumbers[6][6][7].setOnClickListener(this);
            auxNumbers[6][6][8] = (TextView) findViewById(R.id.textView668);auxNumbers[6][6][8].setOnClickListener(this);
            auxNumbers[6][6][9] = (TextView) findViewById(R.id.textView669);auxNumbers[6][6][9].setOnClickListener(this);

            auxNumbers[6][7][1] = (TextView) findViewById(R.id.textView671);auxNumbers[6][7][1].setOnClickListener(this);
            auxNumbers[6][7][2] = (TextView) findViewById(R.id.textView672);auxNumbers[6][7][2].setOnClickListener(this);
            auxNumbers[6][7][3] = (TextView) findViewById(R.id.textView673);auxNumbers[6][7][3].setOnClickListener(this);
            auxNumbers[6][7][4] = (TextView) findViewById(R.id.textView674);auxNumbers[6][7][4].setOnClickListener(this);
            auxNumbers[6][7][5] = (TextView) findViewById(R.id.textView675);auxNumbers[6][7][5].setOnClickListener(this);
            auxNumbers[6][7][6] = (TextView) findViewById(R.id.textView676);auxNumbers[6][7][6].setOnClickListener(this);
            auxNumbers[6][7][7] = (TextView) findViewById(R.id.textView677);auxNumbers[6][7][7].setOnClickListener(this);
            auxNumbers[6][7][8] = (TextView) findViewById(R.id.textView678);auxNumbers[6][7][8].setOnClickListener(this);
            auxNumbers[6][7][9] = (TextView) findViewById(R.id.textView679);auxNumbers[6][7][9].setOnClickListener(this);

            auxNumbers[6][8][1] = (TextView) findViewById(R.id.textView681);auxNumbers[6][8][1].setOnClickListener(this);
            auxNumbers[6][8][2] = (TextView) findViewById(R.id.textView682);auxNumbers[6][8][2].setOnClickListener(this);
            auxNumbers[6][8][3] = (TextView) findViewById(R.id.textView683);auxNumbers[6][8][3].setOnClickListener(this);
            auxNumbers[6][8][4] = (TextView) findViewById(R.id.textView684);auxNumbers[6][8][4].setOnClickListener(this);
            auxNumbers[6][8][5] = (TextView) findViewById(R.id.textView685);auxNumbers[6][8][5].setOnClickListener(this);
            auxNumbers[6][8][6] = (TextView) findViewById(R.id.textView686);auxNumbers[6][8][6].setOnClickListener(this);
            auxNumbers[6][8][7] = (TextView) findViewById(R.id.textView687);auxNumbers[6][8][7].setOnClickListener(this);
            auxNumbers[6][8][8] = (TextView) findViewById(R.id.textView688);auxNumbers[6][8][8].setOnClickListener(this);
            auxNumbers[6][8][9] = (TextView) findViewById(R.id.textView689);auxNumbers[6][8][9].setOnClickListener(this);

            auxNumbers[6][9][1] = (TextView) findViewById(R.id.textView691);auxNumbers[6][9][1].setOnClickListener(this);
            auxNumbers[6][9][2] = (TextView) findViewById(R.id.textView692);auxNumbers[6][9][2].setOnClickListener(this);
            auxNumbers[6][9][3] = (TextView) findViewById(R.id.textView693);auxNumbers[6][9][3].setOnClickListener(this);
            auxNumbers[6][9][4] = (TextView) findViewById(R.id.textView694);auxNumbers[6][9][4].setOnClickListener(this);
            auxNumbers[6][9][5] = (TextView) findViewById(R.id.textView695);auxNumbers[6][9][5].setOnClickListener(this);
            auxNumbers[6][9][6] = (TextView) findViewById(R.id.textView696);auxNumbers[6][9][6].setOnClickListener(this);
            auxNumbers[6][9][7] = (TextView) findViewById(R.id.textView697);auxNumbers[6][9][7].setOnClickListener(this);
            auxNumbers[6][9][8] = (TextView) findViewById(R.id.textView698);auxNumbers[6][9][8].setOnClickListener(this);
            auxNumbers[6][9][9] = (TextView) findViewById(R.id.textView699);auxNumbers[6][9][9].setOnClickListener(this);

            auxNumbers[7][1][1] = (TextView) findViewById(R.id.textView711);auxNumbers[7][1][1].setOnClickListener(this);
            auxNumbers[7][1][2] = (TextView) findViewById(R.id.textView712);auxNumbers[7][1][2].setOnClickListener(this);
            auxNumbers[7][1][3] = (TextView) findViewById(R.id.textView713);auxNumbers[7][1][3].setOnClickListener(this);
            auxNumbers[7][1][4] = (TextView) findViewById(R.id.textView714);auxNumbers[7][1][4].setOnClickListener(this);
            auxNumbers[7][1][5] = (TextView) findViewById(R.id.textView715);auxNumbers[7][1][5].setOnClickListener(this);
            auxNumbers[7][1][6] = (TextView) findViewById(R.id.textView716);auxNumbers[7][1][6].setOnClickListener(this);
            auxNumbers[7][1][7] = (TextView) findViewById(R.id.textView717);auxNumbers[7][1][7].setOnClickListener(this);
            auxNumbers[7][1][8] = (TextView) findViewById(R.id.textView718);auxNumbers[7][1][8].setOnClickListener(this);
            auxNumbers[7][1][9] = (TextView) findViewById(R.id.textView719);auxNumbers[7][1][9].setOnClickListener(this);

            auxNumbers[7][2][1] = (TextView) findViewById(R.id.textView721);auxNumbers[7][2][1].setOnClickListener(this);
            auxNumbers[7][2][2] = (TextView) findViewById(R.id.textView722);auxNumbers[7][2][2].setOnClickListener(this);
            auxNumbers[7][2][3] = (TextView) findViewById(R.id.textView723);auxNumbers[7][2][3].setOnClickListener(this);
            auxNumbers[7][2][4] = (TextView) findViewById(R.id.textView724);auxNumbers[7][2][4].setOnClickListener(this);
            auxNumbers[7][2][5] = (TextView) findViewById(R.id.textView725);auxNumbers[7][2][5].setOnClickListener(this);
            auxNumbers[7][2][6] = (TextView) findViewById(R.id.textView726);auxNumbers[7][2][6].setOnClickListener(this);
            auxNumbers[7][2][7] = (TextView) findViewById(R.id.textView727);auxNumbers[7][2][7].setOnClickListener(this);
            auxNumbers[7][2][8] = (TextView) findViewById(R.id.textView728);auxNumbers[7][2][8].setOnClickListener(this);
            auxNumbers[7][2][9] = (TextView) findViewById(R.id.textView729);auxNumbers[7][2][9].setOnClickListener(this);

            auxNumbers[7][3][1] = (TextView) findViewById(R.id.textView731);auxNumbers[7][3][1].setOnClickListener(this);
            auxNumbers[7][3][2] = (TextView) findViewById(R.id.textView732);auxNumbers[7][3][2].setOnClickListener(this);
            auxNumbers[7][3][3] = (TextView) findViewById(R.id.textView733);auxNumbers[7][3][3].setOnClickListener(this);
            auxNumbers[7][3][4] = (TextView) findViewById(R.id.textView734);auxNumbers[7][3][4].setOnClickListener(this);
            auxNumbers[7][3][5] = (TextView) findViewById(R.id.textView735);auxNumbers[7][3][5].setOnClickListener(this);
            auxNumbers[7][3][6] = (TextView) findViewById(R.id.textView736);auxNumbers[7][3][6].setOnClickListener(this);
            auxNumbers[7][3][7] = (TextView) findViewById(R.id.textView737);auxNumbers[7][3][7].setOnClickListener(this);
            auxNumbers[7][3][8] = (TextView) findViewById(R.id.textView738);auxNumbers[7][3][8].setOnClickListener(this);
            auxNumbers[7][3][9] = (TextView) findViewById(R.id.textView739);auxNumbers[7][3][9].setOnClickListener(this);

            auxNumbers[7][4][1] = (TextView) findViewById(R.id.textView741);auxNumbers[7][4][1].setOnClickListener(this);
            auxNumbers[7][4][2] = (TextView) findViewById(R.id.textView742);auxNumbers[7][4][2].setOnClickListener(this);
            auxNumbers[7][4][3] = (TextView) findViewById(R.id.textView743);auxNumbers[7][4][3].setOnClickListener(this);
            auxNumbers[7][4][4] = (TextView) findViewById(R.id.textView744);auxNumbers[7][4][4].setOnClickListener(this);
            auxNumbers[7][4][5] = (TextView) findViewById(R.id.textView745);auxNumbers[7][4][5].setOnClickListener(this);
            auxNumbers[7][4][6] = (TextView) findViewById(R.id.textView746);auxNumbers[7][4][6].setOnClickListener(this);
            auxNumbers[7][4][7] = (TextView) findViewById(R.id.textView747);auxNumbers[7][4][7].setOnClickListener(this);
            auxNumbers[7][4][8] = (TextView) findViewById(R.id.textView748);auxNumbers[7][4][8].setOnClickListener(this);
            auxNumbers[7][4][9] = (TextView) findViewById(R.id.textView749);auxNumbers[7][4][9].setOnClickListener(this);

            auxNumbers[7][5][1] = (TextView) findViewById(R.id.textView751);auxNumbers[7][5][1].setOnClickListener(this);
            auxNumbers[7][5][2] = (TextView) findViewById(R.id.textView752);auxNumbers[7][5][2].setOnClickListener(this);
            auxNumbers[7][5][3] = (TextView) findViewById(R.id.textView753);auxNumbers[7][5][3].setOnClickListener(this);
            auxNumbers[7][5][4] = (TextView) findViewById(R.id.textView754);auxNumbers[7][5][4].setOnClickListener(this);
            auxNumbers[7][5][5] = (TextView) findViewById(R.id.textView755);auxNumbers[7][5][5].setOnClickListener(this);
            auxNumbers[7][5][6] = (TextView) findViewById(R.id.textView756);auxNumbers[7][5][6].setOnClickListener(this);
            auxNumbers[7][5][7] = (TextView) findViewById(R.id.textView757);auxNumbers[7][5][7].setOnClickListener(this);
            auxNumbers[7][5][8] = (TextView) findViewById(R.id.textView758);auxNumbers[7][5][8].setOnClickListener(this);
            auxNumbers[7][5][9] = (TextView) findViewById(R.id.textView759);auxNumbers[7][5][9].setOnClickListener(this);

            auxNumbers[7][6][1] = (TextView) findViewById(R.id.textView761);auxNumbers[7][6][1].setOnClickListener(this);
            auxNumbers[7][6][2] = (TextView) findViewById(R.id.textView762);auxNumbers[7][6][2].setOnClickListener(this);
            auxNumbers[7][6][3] = (TextView) findViewById(R.id.textView763);auxNumbers[7][6][3].setOnClickListener(this);
            auxNumbers[7][6][4] = (TextView) findViewById(R.id.textView764);auxNumbers[7][6][4].setOnClickListener(this);
            auxNumbers[7][6][5] = (TextView) findViewById(R.id.textView765);auxNumbers[7][6][5].setOnClickListener(this);
            auxNumbers[7][6][6] = (TextView) findViewById(R.id.textView766);auxNumbers[7][6][6].setOnClickListener(this);
            auxNumbers[7][6][7] = (TextView) findViewById(R.id.textView767);auxNumbers[7][6][7].setOnClickListener(this);
            auxNumbers[7][6][8] = (TextView) findViewById(R.id.textView768);auxNumbers[7][6][8].setOnClickListener(this);
            auxNumbers[7][6][9] = (TextView) findViewById(R.id.textView769);auxNumbers[7][6][9].setOnClickListener(this);

            auxNumbers[7][7][1] = (TextView) findViewById(R.id.textView771);auxNumbers[7][7][1].setOnClickListener(this);
            auxNumbers[7][7][2] = (TextView) findViewById(R.id.textView772);auxNumbers[7][7][2].setOnClickListener(this);
            auxNumbers[7][7][3] = (TextView) findViewById(R.id.textView773);auxNumbers[7][7][3].setOnClickListener(this);
            auxNumbers[7][7][4] = (TextView) findViewById(R.id.textView774);auxNumbers[7][7][4].setOnClickListener(this);
            auxNumbers[7][7][5] = (TextView) findViewById(R.id.textView775);auxNumbers[7][7][5].setOnClickListener(this);
            auxNumbers[7][7][6] = (TextView) findViewById(R.id.textView776);auxNumbers[7][7][6].setOnClickListener(this);
            auxNumbers[7][7][7] = (TextView) findViewById(R.id.textView777);auxNumbers[7][7][7].setOnClickListener(this);
            auxNumbers[7][7][8] = (TextView) findViewById(R.id.textView778);auxNumbers[7][7][8].setOnClickListener(this);
            auxNumbers[7][7][9] = (TextView) findViewById(R.id.textView779);auxNumbers[7][7][9].setOnClickListener(this);

            auxNumbers[7][8][1] = (TextView) findViewById(R.id.textView781);auxNumbers[7][8][1].setOnClickListener(this);
            auxNumbers[7][8][2] = (TextView) findViewById(R.id.textView782);auxNumbers[7][8][2].setOnClickListener(this);
            auxNumbers[7][8][3] = (TextView) findViewById(R.id.textView783);auxNumbers[7][8][3].setOnClickListener(this);
            auxNumbers[7][8][4] = (TextView) findViewById(R.id.textView784);auxNumbers[7][8][4].setOnClickListener(this);
            auxNumbers[7][8][5] = (TextView) findViewById(R.id.textView785);auxNumbers[7][8][5].setOnClickListener(this);
            auxNumbers[7][8][6] = (TextView) findViewById(R.id.textView786);auxNumbers[7][8][6].setOnClickListener(this);
            auxNumbers[7][8][7] = (TextView) findViewById(R.id.textView787);auxNumbers[7][8][7].setOnClickListener(this);
            auxNumbers[7][8][8] = (TextView) findViewById(R.id.textView788);auxNumbers[7][8][8].setOnClickListener(this);
            auxNumbers[7][8][9] = (TextView) findViewById(R.id.textView789);auxNumbers[7][8][9].setOnClickListener(this);

            auxNumbers[7][9][1] = (TextView) findViewById(R.id.textView791);auxNumbers[7][9][1].setOnClickListener(this);
            auxNumbers[7][9][2] = (TextView) findViewById(R.id.textView792);auxNumbers[7][9][2].setOnClickListener(this);
            auxNumbers[7][9][3] = (TextView) findViewById(R.id.textView793);auxNumbers[7][9][3].setOnClickListener(this);
            auxNumbers[7][9][4] = (TextView) findViewById(R.id.textView794);auxNumbers[7][9][4].setOnClickListener(this);
            auxNumbers[7][9][5] = (TextView) findViewById(R.id.textView795);auxNumbers[7][9][5].setOnClickListener(this);
            auxNumbers[7][9][6] = (TextView) findViewById(R.id.textView796);auxNumbers[7][9][6].setOnClickListener(this);
            auxNumbers[7][9][7] = (TextView) findViewById(R.id.textView797);auxNumbers[7][9][7].setOnClickListener(this);
            auxNumbers[7][9][8] = (TextView) findViewById(R.id.textView798);auxNumbers[7][9][8].setOnClickListener(this);
            auxNumbers[7][9][9] = (TextView) findViewById(R.id.textView799);auxNumbers[7][9][9].setOnClickListener(this);

            auxNumbers[8][1][1] = (TextView) findViewById(R.id.textView811);auxNumbers[8][1][1].setOnClickListener(this);
            auxNumbers[8][1][2] = (TextView) findViewById(R.id.textView812);auxNumbers[8][1][2].setOnClickListener(this);
            auxNumbers[8][1][3] = (TextView) findViewById(R.id.textView813);auxNumbers[8][1][3].setOnClickListener(this);
            auxNumbers[8][1][4] = (TextView) findViewById(R.id.textView814);auxNumbers[8][1][4].setOnClickListener(this);
            auxNumbers[8][1][5] = (TextView) findViewById(R.id.textView815);auxNumbers[8][1][5].setOnClickListener(this);
            auxNumbers[8][1][6] = (TextView) findViewById(R.id.textView816);auxNumbers[8][1][6].setOnClickListener(this);
            auxNumbers[8][1][7] = (TextView) findViewById(R.id.textView817);auxNumbers[8][1][7].setOnClickListener(this);
            auxNumbers[8][1][8] = (TextView) findViewById(R.id.textView818);auxNumbers[8][1][8].setOnClickListener(this);
            auxNumbers[8][1][9] = (TextView) findViewById(R.id.textView819);auxNumbers[8][1][9].setOnClickListener(this);

            auxNumbers[8][2][1] = (TextView) findViewById(R.id.textView821);auxNumbers[8][2][1].setOnClickListener(this);
            auxNumbers[8][2][2] = (TextView) findViewById(R.id.textView822);auxNumbers[8][2][2].setOnClickListener(this);
            auxNumbers[8][2][3] = (TextView) findViewById(R.id.textView823);auxNumbers[8][2][3].setOnClickListener(this);
            auxNumbers[8][2][4] = (TextView) findViewById(R.id.textView824);auxNumbers[8][2][4].setOnClickListener(this);
            auxNumbers[8][2][5] = (TextView) findViewById(R.id.textView825);auxNumbers[8][2][5].setOnClickListener(this);
            auxNumbers[8][2][6] = (TextView) findViewById(R.id.textView826);auxNumbers[8][2][6].setOnClickListener(this);
            auxNumbers[8][2][7] = (TextView) findViewById(R.id.textView827);auxNumbers[8][2][7].setOnClickListener(this);
            auxNumbers[8][2][8] = (TextView) findViewById(R.id.textView828);auxNumbers[8][2][8].setOnClickListener(this);
            auxNumbers[8][2][9] = (TextView) findViewById(R.id.textView829);auxNumbers[8][2][9].setOnClickListener(this);

            auxNumbers[8][3][1] = (TextView) findViewById(R.id.textView831);auxNumbers[8][3][1].setOnClickListener(this);
            auxNumbers[8][3][2] = (TextView) findViewById(R.id.textView832);auxNumbers[8][3][2].setOnClickListener(this);
            auxNumbers[8][3][3] = (TextView) findViewById(R.id.textView833);auxNumbers[8][3][3].setOnClickListener(this);
            auxNumbers[8][3][4] = (TextView) findViewById(R.id.textView834);auxNumbers[8][3][4].setOnClickListener(this);
            auxNumbers[8][3][5] = (TextView) findViewById(R.id.textView835);auxNumbers[8][3][5].setOnClickListener(this);
            auxNumbers[8][3][6] = (TextView) findViewById(R.id.textView836);auxNumbers[8][3][6].setOnClickListener(this);
            auxNumbers[8][3][7] = (TextView) findViewById(R.id.textView837);auxNumbers[8][3][7].setOnClickListener(this);
            auxNumbers[8][3][8] = (TextView) findViewById(R.id.textView838);auxNumbers[8][3][8].setOnClickListener(this);
            auxNumbers[8][3][9] = (TextView) findViewById(R.id.textView839);auxNumbers[8][3][9].setOnClickListener(this);

            auxNumbers[8][4][1] = (TextView) findViewById(R.id.textView841);auxNumbers[8][4][1].setOnClickListener(this);
            auxNumbers[8][4][2] = (TextView) findViewById(R.id.textView842);auxNumbers[8][4][2].setOnClickListener(this);
            auxNumbers[8][4][3] = (TextView) findViewById(R.id.textView843);auxNumbers[8][4][3].setOnClickListener(this);
            auxNumbers[8][4][4] = (TextView) findViewById(R.id.textView844);auxNumbers[8][4][4].setOnClickListener(this);
            auxNumbers[8][4][5] = (TextView) findViewById(R.id.textView845);auxNumbers[8][4][5].setOnClickListener(this);
            auxNumbers[8][4][6] = (TextView) findViewById(R.id.textView846);auxNumbers[8][4][6].setOnClickListener(this);
            auxNumbers[8][4][7] = (TextView) findViewById(R.id.textView847);auxNumbers[8][4][7].setOnClickListener(this);
            auxNumbers[8][4][8] = (TextView) findViewById(R.id.textView848);auxNumbers[8][4][8].setOnClickListener(this);
            auxNumbers[8][4][9] = (TextView) findViewById(R.id.textView849);auxNumbers[8][4][9].setOnClickListener(this);

            auxNumbers[8][5][1] = (TextView) findViewById(R.id.textView851);auxNumbers[8][5][1].setOnClickListener(this);
            auxNumbers[8][5][2] = (TextView) findViewById(R.id.textView852);auxNumbers[8][5][2].setOnClickListener(this);
            auxNumbers[8][5][3] = (TextView) findViewById(R.id.textView853);auxNumbers[8][5][3].setOnClickListener(this);
            auxNumbers[8][5][4] = (TextView) findViewById(R.id.textView854);auxNumbers[8][5][4].setOnClickListener(this);
            auxNumbers[8][5][5] = (TextView) findViewById(R.id.textView855);auxNumbers[8][5][5].setOnClickListener(this);
            auxNumbers[8][5][6] = (TextView) findViewById(R.id.textView856);auxNumbers[8][5][6].setOnClickListener(this);
            auxNumbers[8][5][7] = (TextView) findViewById(R.id.textView857);auxNumbers[8][5][7].setOnClickListener(this);
            auxNumbers[8][5][8] = (TextView) findViewById(R.id.textView858);auxNumbers[8][5][8].setOnClickListener(this);
            auxNumbers[8][5][9] = (TextView) findViewById(R.id.textView859);auxNumbers[8][5][9].setOnClickListener(this);

            auxNumbers[8][6][1] = (TextView) findViewById(R.id.textView861);auxNumbers[8][6][1].setOnClickListener(this);
            auxNumbers[8][6][2] = (TextView) findViewById(R.id.textView862);auxNumbers[8][6][2].setOnClickListener(this);
            auxNumbers[8][6][3] = (TextView) findViewById(R.id.textView863);auxNumbers[8][6][3].setOnClickListener(this);
            auxNumbers[8][6][4] = (TextView) findViewById(R.id.textView864);auxNumbers[8][6][4].setOnClickListener(this);
            auxNumbers[8][6][5] = (TextView) findViewById(R.id.textView865);auxNumbers[8][6][5].setOnClickListener(this);
            auxNumbers[8][6][6] = (TextView) findViewById(R.id.textView866);auxNumbers[8][6][6].setOnClickListener(this);
            auxNumbers[8][6][7] = (TextView) findViewById(R.id.textView867);auxNumbers[8][6][7].setOnClickListener(this);
            auxNumbers[8][6][8] = (TextView) findViewById(R.id.textView868);auxNumbers[8][6][8].setOnClickListener(this);
            auxNumbers[8][6][9] = (TextView) findViewById(R.id.textView869);auxNumbers[8][6][9].setOnClickListener(this);

            auxNumbers[8][7][1] = (TextView) findViewById(R.id.textView871);auxNumbers[8][7][1].setOnClickListener(this);
            auxNumbers[8][7][2] = (TextView) findViewById(R.id.textView872);auxNumbers[8][7][2].setOnClickListener(this);
            auxNumbers[8][7][3] = (TextView) findViewById(R.id.textView873);auxNumbers[8][7][3].setOnClickListener(this);
            auxNumbers[8][7][4] = (TextView) findViewById(R.id.textView874);auxNumbers[8][7][4].setOnClickListener(this);
            auxNumbers[8][7][5] = (TextView) findViewById(R.id.textView875);auxNumbers[8][7][5].setOnClickListener(this);
            auxNumbers[8][7][6] = (TextView) findViewById(R.id.textView876);auxNumbers[8][7][6].setOnClickListener(this);
            auxNumbers[8][7][7] = (TextView) findViewById(R.id.textView877);auxNumbers[8][7][7].setOnClickListener(this);
            auxNumbers[8][7][8] = (TextView) findViewById(R.id.textView878);auxNumbers[8][7][8].setOnClickListener(this);
            auxNumbers[8][7][9] = (TextView) findViewById(R.id.textView879);auxNumbers[8][7][9].setOnClickListener(this);

            auxNumbers[8][8][1] = (TextView) findViewById(R.id.textView881);auxNumbers[8][8][1].setOnClickListener(this);
            auxNumbers[8][8][2] = (TextView) findViewById(R.id.textView882);auxNumbers[8][8][2].setOnClickListener(this);
            auxNumbers[8][8][3] = (TextView) findViewById(R.id.textView883);auxNumbers[8][8][3].setOnClickListener(this);
            auxNumbers[8][8][4] = (TextView) findViewById(R.id.textView884);auxNumbers[8][8][4].setOnClickListener(this);
            auxNumbers[8][8][5] = (TextView) findViewById(R.id.textView885);auxNumbers[8][8][5].setOnClickListener(this);
            auxNumbers[8][8][6] = (TextView) findViewById(R.id.textView886);auxNumbers[8][8][6].setOnClickListener(this);
            auxNumbers[8][8][7] = (TextView) findViewById(R.id.textView887);auxNumbers[8][8][7].setOnClickListener(this);
            auxNumbers[8][8][8] = (TextView) findViewById(R.id.textView888);auxNumbers[8][8][8].setOnClickListener(this);
            auxNumbers[8][8][9] = (TextView) findViewById(R.id.textView889);auxNumbers[8][8][9].setOnClickListener(this);

            auxNumbers[8][9][1] = (TextView) findViewById(R.id.textView891);auxNumbers[8][9][1].setOnClickListener(this);
            auxNumbers[8][9][2] = (TextView) findViewById(R.id.textView892);auxNumbers[8][9][2].setOnClickListener(this);
            auxNumbers[8][9][3] = (TextView) findViewById(R.id.textView893);auxNumbers[8][9][3].setOnClickListener(this);
            auxNumbers[8][9][4] = (TextView) findViewById(R.id.textView894);auxNumbers[8][9][4].setOnClickListener(this);
            auxNumbers[8][9][5] = (TextView) findViewById(R.id.textView895);auxNumbers[8][9][5].setOnClickListener(this);
            auxNumbers[8][9][6] = (TextView) findViewById(R.id.textView896);auxNumbers[8][9][6].setOnClickListener(this);
            auxNumbers[8][9][7] = (TextView) findViewById(R.id.textView897);auxNumbers[8][9][7].setOnClickListener(this);
            auxNumbers[8][9][8] = (TextView) findViewById(R.id.textView898);auxNumbers[8][9][8].setOnClickListener(this);
            auxNumbers[8][9][9] = (TextView) findViewById(R.id.textView899);auxNumbers[8][9][9].setOnClickListener(this);

            auxNumbers[9][1][1] = (TextView) findViewById(R.id.textView911);auxNumbers[9][1][1].setOnClickListener(this);
            auxNumbers[9][1][2] = (TextView) findViewById(R.id.textView912);auxNumbers[9][1][2].setOnClickListener(this);
            auxNumbers[9][1][3] = (TextView) findViewById(R.id.textView913);auxNumbers[9][1][3].setOnClickListener(this);
            auxNumbers[9][1][4] = (TextView) findViewById(R.id.textView914);auxNumbers[9][1][4].setOnClickListener(this);
            auxNumbers[9][1][5] = (TextView) findViewById(R.id.textView915);auxNumbers[9][1][5].setOnClickListener(this);
            auxNumbers[9][1][6] = (TextView) findViewById(R.id.textView916);auxNumbers[9][1][6].setOnClickListener(this);
            auxNumbers[9][1][7] = (TextView) findViewById(R.id.textView917);auxNumbers[9][1][7].setOnClickListener(this);
            auxNumbers[9][1][8] = (TextView) findViewById(R.id.textView918);auxNumbers[9][1][8].setOnClickListener(this);
            auxNumbers[9][1][9] = (TextView) findViewById(R.id.textView919);auxNumbers[9][1][9].setOnClickListener(this);

            auxNumbers[9][2][1] = (TextView) findViewById(R.id.textView921);auxNumbers[9][2][1].setOnClickListener(this);
            auxNumbers[9][2][2] = (TextView) findViewById(R.id.textView922);auxNumbers[9][2][2].setOnClickListener(this);
            auxNumbers[9][2][3] = (TextView) findViewById(R.id.textView923);auxNumbers[9][2][3].setOnClickListener(this);
            auxNumbers[9][2][4] = (TextView) findViewById(R.id.textView924);auxNumbers[9][2][4].setOnClickListener(this);
            auxNumbers[9][2][5] = (TextView) findViewById(R.id.textView925);auxNumbers[9][2][5].setOnClickListener(this);
            auxNumbers[9][2][6] = (TextView) findViewById(R.id.textView926);auxNumbers[9][2][6].setOnClickListener(this);
            auxNumbers[9][2][7] = (TextView) findViewById(R.id.textView927);auxNumbers[9][2][7].setOnClickListener(this);
            auxNumbers[9][2][8] = (TextView) findViewById(R.id.textView928);auxNumbers[9][2][8].setOnClickListener(this);
            auxNumbers[9][2][9] = (TextView) findViewById(R.id.textView929);auxNumbers[9][2][9].setOnClickListener(this);

            auxNumbers[9][3][1] = (TextView) findViewById(R.id.textView931);auxNumbers[9][3][1].setOnClickListener(this);
            auxNumbers[9][3][2] = (TextView) findViewById(R.id.textView932);auxNumbers[9][3][2].setOnClickListener(this);
            auxNumbers[9][3][3] = (TextView) findViewById(R.id.textView933);auxNumbers[9][3][3].setOnClickListener(this);
            auxNumbers[9][3][4] = (TextView) findViewById(R.id.textView934);auxNumbers[9][3][4].setOnClickListener(this);
            auxNumbers[9][3][5] = (TextView) findViewById(R.id.textView935);auxNumbers[9][3][5].setOnClickListener(this);
            auxNumbers[9][3][6] = (TextView) findViewById(R.id.textView936);auxNumbers[9][3][6].setOnClickListener(this);
            auxNumbers[9][3][7] = (TextView) findViewById(R.id.textView937);auxNumbers[9][3][7].setOnClickListener(this);
            auxNumbers[9][3][8] = (TextView) findViewById(R.id.textView938);auxNumbers[9][3][8].setOnClickListener(this);
            auxNumbers[9][3][9] = (TextView) findViewById(R.id.textView939);auxNumbers[9][3][9].setOnClickListener(this);

            auxNumbers[9][4][1] = (TextView) findViewById(R.id.textView941);auxNumbers[9][4][1].setOnClickListener(this);
            auxNumbers[9][4][2] = (TextView) findViewById(R.id.textView942);auxNumbers[9][4][2].setOnClickListener(this);
            auxNumbers[9][4][3] = (TextView) findViewById(R.id.textView943);auxNumbers[9][4][3].setOnClickListener(this);
            auxNumbers[9][4][4] = (TextView) findViewById(R.id.textView944);auxNumbers[9][4][4].setOnClickListener(this);
            auxNumbers[9][4][5] = (TextView) findViewById(R.id.textView945);auxNumbers[9][4][5].setOnClickListener(this);
            auxNumbers[9][4][6] = (TextView) findViewById(R.id.textView946);auxNumbers[9][4][6].setOnClickListener(this);
            auxNumbers[9][4][7] = (TextView) findViewById(R.id.textView947);auxNumbers[9][4][7].setOnClickListener(this);
            auxNumbers[9][4][8] = (TextView) findViewById(R.id.textView948);auxNumbers[9][4][8].setOnClickListener(this);
            auxNumbers[9][4][9] = (TextView) findViewById(R.id.textView949);auxNumbers[9][4][9].setOnClickListener(this);

            auxNumbers[9][5][1] = (TextView) findViewById(R.id.textView951);auxNumbers[9][5][1].setOnClickListener(this);
            auxNumbers[9][5][2] = (TextView) findViewById(R.id.textView952);auxNumbers[9][5][2].setOnClickListener(this);
            auxNumbers[9][5][3] = (TextView) findViewById(R.id.textView953);auxNumbers[9][5][3].setOnClickListener(this);
            auxNumbers[9][5][4] = (TextView) findViewById(R.id.textView954);auxNumbers[9][5][4].setOnClickListener(this);
            auxNumbers[9][5][5] = (TextView) findViewById(R.id.textView955);auxNumbers[9][5][5].setOnClickListener(this);
            auxNumbers[9][5][6] = (TextView) findViewById(R.id.textView956);auxNumbers[9][5][6].setOnClickListener(this);
            auxNumbers[9][5][7] = (TextView) findViewById(R.id.textView957);auxNumbers[9][5][7].setOnClickListener(this);
            auxNumbers[9][5][8] = (TextView) findViewById(R.id.textView958);auxNumbers[9][5][8].setOnClickListener(this);
            auxNumbers[9][5][9] = (TextView) findViewById(R.id.textView959);auxNumbers[9][5][9].setOnClickListener(this);

            auxNumbers[9][6][1] = (TextView) findViewById(R.id.textView961);auxNumbers[9][6][1].setOnClickListener(this);
            auxNumbers[9][6][2] = (TextView) findViewById(R.id.textView962);auxNumbers[9][6][2].setOnClickListener(this);
            auxNumbers[9][6][3] = (TextView) findViewById(R.id.textView963);auxNumbers[9][6][3].setOnClickListener(this);
            auxNumbers[9][6][4] = (TextView) findViewById(R.id.textView964);auxNumbers[9][6][4].setOnClickListener(this);
            auxNumbers[9][6][5] = (TextView) findViewById(R.id.textView965);auxNumbers[9][6][5].setOnClickListener(this);
            auxNumbers[9][6][6] = (TextView) findViewById(R.id.textView966);auxNumbers[9][6][6].setOnClickListener(this);
            auxNumbers[9][6][7] = (TextView) findViewById(R.id.textView967);auxNumbers[9][6][7].setOnClickListener(this);
            auxNumbers[9][6][8] = (TextView) findViewById(R.id.textView968);auxNumbers[9][6][8].setOnClickListener(this);
            auxNumbers[9][6][9] = (TextView) findViewById(R.id.textView969);auxNumbers[9][6][9].setOnClickListener(this);

            auxNumbers[9][7][1] = (TextView) findViewById(R.id.textView971);auxNumbers[9][7][1].setOnClickListener(this);
            auxNumbers[9][7][2] = (TextView) findViewById(R.id.textView972);auxNumbers[9][7][2].setOnClickListener(this);
            auxNumbers[9][7][3] = (TextView) findViewById(R.id.textView973);auxNumbers[9][7][3].setOnClickListener(this);
            auxNumbers[9][7][4] = (TextView) findViewById(R.id.textView974);auxNumbers[9][7][4].setOnClickListener(this);
            auxNumbers[9][7][5] = (TextView) findViewById(R.id.textView975);auxNumbers[9][7][5].setOnClickListener(this);
            auxNumbers[9][7][6] = (TextView) findViewById(R.id.textView976);auxNumbers[9][7][6].setOnClickListener(this);
            auxNumbers[9][7][7] = (TextView) findViewById(R.id.textView977);auxNumbers[9][7][7].setOnClickListener(this);
            auxNumbers[9][7][8] = (TextView) findViewById(R.id.textView978);auxNumbers[9][7][8].setOnClickListener(this);
            auxNumbers[9][7][9] = (TextView) findViewById(R.id.textView979);auxNumbers[9][7][9].setOnClickListener(this);

            auxNumbers[9][8][1] = (TextView) findViewById(R.id.textView981);auxNumbers[9][8][1].setOnClickListener(this);
            auxNumbers[9][8][2] = (TextView) findViewById(R.id.textView982);auxNumbers[9][8][2].setOnClickListener(this);
            auxNumbers[9][8][3] = (TextView) findViewById(R.id.textView983);auxNumbers[9][8][3].setOnClickListener(this);
            auxNumbers[9][8][4] = (TextView) findViewById(R.id.textView984);auxNumbers[9][8][4].setOnClickListener(this);
            auxNumbers[9][8][5] = (TextView) findViewById(R.id.textView985);auxNumbers[9][8][5].setOnClickListener(this);
            auxNumbers[9][8][6] = (TextView) findViewById(R.id.textView986);auxNumbers[9][8][6].setOnClickListener(this);
            auxNumbers[9][8][7] = (TextView) findViewById(R.id.textView987);auxNumbers[9][8][7].setOnClickListener(this);
            auxNumbers[9][8][8] = (TextView) findViewById(R.id.textView988);auxNumbers[9][8][8].setOnClickListener(this);
            auxNumbers[9][8][9] = (TextView) findViewById(R.id.textView989);auxNumbers[9][8][9].setOnClickListener(this);

            auxNumbers[9][9][1] = (TextView) findViewById(R.id.textView991);auxNumbers[9][9][1].setOnClickListener(this);
            auxNumbers[9][9][2] = (TextView) findViewById(R.id.textView992);auxNumbers[9][9][2].setOnClickListener(this);
            auxNumbers[9][9][3] = (TextView) findViewById(R.id.textView993);auxNumbers[9][9][3].setOnClickListener(this);
            auxNumbers[9][9][4] = (TextView) findViewById(R.id.textView994);auxNumbers[9][9][4].setOnClickListener(this);
            auxNumbers[9][9][5] = (TextView) findViewById(R.id.textView995);auxNumbers[9][9][5].setOnClickListener(this);
            auxNumbers[9][9][6] = (TextView) findViewById(R.id.textView996);auxNumbers[9][9][6].setOnClickListener(this);
            auxNumbers[9][9][7] = (TextView) findViewById(R.id.textView997);auxNumbers[9][9][7].setOnClickListener(this);
            auxNumbers[9][9][8] = (TextView) findViewById(R.id.textView998);auxNumbers[9][9][8].setOnClickListener(this);
            auxNumbers[9][9][9] = (TextView) findViewById(R.id.textView999);auxNumbers[9][9][9].setOnClickListener(this);

        }
        Log.d(TAG,"onCreate Game_Activity");
        Intent intent = getIntent();
        load=intent.getBooleanExtra("load",load);
        m= new Matrix(difficulty);
        if (load==true){
            try {
                String solvedMatrixString = File.readFile(this,"QS_solvedMatrix");
                m.fromString(solvedMatrixString, m.solvedMatrix);
                String matrixString = File.readFile(this,"QS_matrix");
                m.fromString(matrixString, m.matrix);
                String taskMatrixString = File.readFile(this,"QS_taskMatrix");
                m.fromString(taskMatrixString, m.taskMatrix);
                String auxMatrixString = File.readFile(this,"QS_auxMatrix");
                m.fromString(auxMatrixString, m.auxMatrix);
            }catch (Exception e){}
        }
        screenUpdate(m.auxMatrix,numbers);
        setCheckedBold(m.taskMatrix,numbers);
    }
    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }
    @Override
    public  boolean onOptionsItemSelected(MenuItem item){

        int id = item.getItemId();
        switch (id){
            case R.id.m1:   m.generateZeroAuxMatrix(m.matrix,m.auxMatrix);screenUpdate(m.auxMatrix,numbers);break;
            case R.id.m2:   m.getSolvedMatrix(m.basicMatrix,m.solvedMatrix);
                m.getNonSolvedMatrix(m.basicMatrix,m.solvedMatrix,m.matrix);
                m.generateAuxMatrix(m.matrix,m.auxMatrix);
                m.copyMatrix(m.matrix,m.taskMatrix);
                screenUpdate(m.auxMatrix,numbers);
                setCheckedBold(m.taskMatrix,numbers);
                break;
            case R.id.m3:  // saveFile("quickSave",Matrix.toString(auxMatrix));break;
            case R.id.m4:  // Matrix.fromString(readFile("quickSave"),auxMatrix);break;
            case R.id.m5:   m.copyMatrix(m.taskMatrix,m.matrix);
                Matrix.generateAuxMatrix(m.matrix,m.auxMatrix);
                screenUpdate(m.auxMatrix,numbers);
                setCheckedBold(m.taskMatrix,numbers);
                break;
            case R.id.m6:   //Intent intent =getIntent();finish();startActivity(intent);break;
                Intent intent = new Intent(this,MenuActivity.class);
                startActivity(intent);

        }
        checkReset(numbers,iMatrix,jMatrix);
        screenUpdate(m.auxMatrix,numbers);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        Intent intent=null;
        Resources res = getResources();
        String tempStringMatrix="erer";
        String solvedMatrixString="";
        String matrixString="";
        String taskMatrixString="";
        String auxMatrixString="";
        Animation anim = AnimationUtils.loadAnimation(this,R.anim.scale_fast);
        Animation animMove = AnimationUtils.loadAnimation(this,R.anim.translate);
        switch(view.getId())
        {
            case R.id.button :if(pencil==true){pencil=false;button.setText(res.getString(R.string.Pen));}else {pencil=true;button.setText(res.getString(R.string.Pencil));}button.startAnimation(animMove);break;
            case R.id.button1:m.undoSet(m.auxMatrix,m.undoMatrix);buttonNumberClick(1,iMatrix,jMatrix,m.taskMatrix,m.auxMatrix);button1.startAnimation(animMove);break;
            case R.id.button2:m.undoSet(m.auxMatrix,m.undoMatrix);buttonNumberClick(2,iMatrix,jMatrix,m.taskMatrix,m.auxMatrix);button2.startAnimation(animMove);break;
            case R.id.button3:m.undoSet(m.auxMatrix,m.undoMatrix);buttonNumberClick(3,iMatrix,jMatrix,m.taskMatrix,m.auxMatrix);button3.startAnimation(animMove);break;
            case R.id.button4:m.undoSet(m.auxMatrix,m.undoMatrix);buttonNumberClick(4,iMatrix,jMatrix,m.taskMatrix,m.auxMatrix);button4.startAnimation(animMove);break;
            case R.id.button5:m.undoSet(m.auxMatrix,m.undoMatrix);buttonNumberClick(5,iMatrix,jMatrix,m.taskMatrix,m.auxMatrix);button5.startAnimation(animMove);break;
            case R.id.button6:m.undoSet(m.auxMatrix,m.undoMatrix);buttonNumberClick(6,iMatrix,jMatrix,m.taskMatrix,m.auxMatrix);button6.startAnimation(animMove);break;
            case R.id.button7:m.undoSet(m.auxMatrix,m.undoMatrix);buttonNumberClick(7,iMatrix,jMatrix,m.taskMatrix,m.auxMatrix);button7.startAnimation(animMove);break;
            case R.id.button8:m.undoSet(m.auxMatrix,m.undoMatrix);buttonNumberClick(8,iMatrix,jMatrix,m.taskMatrix,m.auxMatrix);button8.startAnimation(animMove);break;
            case R.id.button9:m.undoSet(m.auxMatrix,m.undoMatrix);buttonNumberClick(9,iMatrix,jMatrix,m.taskMatrix,m.auxMatrix);button9.startAnimation(animMove);break;
            case R.id.button10: button10.startAnimation(animMove);
                File.saveFile(this,"QS_solvedMatrix",m.toString(m.solvedMatrix));
                File.saveFile(this,"QS_matrix",m.toString(m.matrix));
                File.saveFile(this,"QS_taskMatrix",m.toString(m.taskMatrix));
                File.saveFile(this,"QS_auxMatrix",m.toString(m.auxMatrix));
                intent = new Intent(this,MenuActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.button11: button11.startAnimation(animMove);
                m.undoSet(m.auxMatrix,m.undoMatrix);
                m.generateZeroAuxMatrix(m.matrix,m.auxMatrix);
                screenUpdate(m.auxMatrix,numbers);
                break;
            case R.id.button12: button12.startAnimation(animMove);
                m.undoGet(m.auxMatrix,m.undoMatrix);
                screenUpdate(m.auxMatrix,numbers);
                break;
            case R.id.textView1 :iMatrix=1;jMatrix=1;break;     //1 строка
            case R.id.textView2 :iMatrix=1;jMatrix=2;break;
            case R.id.textView3 :iMatrix=1;jMatrix=3;break;
            case R.id.textView10:iMatrix=1;jMatrix=4;break;
            case R.id.textView11:iMatrix=1;jMatrix=5;break;
            case R.id.textView12:iMatrix=1;jMatrix=6;break;
            case R.id.textView19:iMatrix=1;jMatrix=7;break;
            case R.id.textView20:iMatrix=1;jMatrix=8;break;
            case R.id.textView21:iMatrix=1;jMatrix=9;break;

            case R.id.textView111:iMatrix=1;jMatrix=1;break;
            case R.id.textView112:iMatrix=1;jMatrix=1;break;
            case R.id.textView113:iMatrix=1;jMatrix=1;break;
            case R.id.textView114:iMatrix=1;jMatrix=1;break;
            case R.id.textView115:iMatrix=1;jMatrix=1;break;
            case R.id.textView116:iMatrix=1;jMatrix=1;break;
            case R.id.textView117:iMatrix=1;jMatrix=1;break;
            case R.id.textView118:iMatrix=1;jMatrix=1;break;
            case R.id.textView119:iMatrix=1;jMatrix=1;break;

            case R.id.textView121:iMatrix=1;jMatrix=2;break;
            case R.id.textView122:iMatrix=1;jMatrix=2;break;
            case R.id.textView123:iMatrix=1;jMatrix=2;break;
            case R.id.textView124:iMatrix=1;jMatrix=2;break;
            case R.id.textView125:iMatrix=1;jMatrix=2;break;
            case R.id.textView126:iMatrix=1;jMatrix=2;break;
            case R.id.textView127:iMatrix=1;jMatrix=2;break;
            case R.id.textView128:iMatrix=1;jMatrix=2;break;
            case R.id.textView129:iMatrix=1;jMatrix=2;break;

            case R.id.textView131:iMatrix=1;jMatrix=3;break;
            case R.id.textView132:iMatrix=1;jMatrix=3;break;
            case R.id.textView133:iMatrix=1;jMatrix=3;break;
            case R.id.textView134:iMatrix=1;jMatrix=3;break;
            case R.id.textView135:iMatrix=1;jMatrix=3;break;
            case R.id.textView136:iMatrix=1;jMatrix=3;break;
            case R.id.textView137:iMatrix=1;jMatrix=3;break;
            case R.id.textView138:iMatrix=1;jMatrix=3;break;
            case R.id.textView139:iMatrix=1;jMatrix=3;break;

            case R.id.textView141:iMatrix=1;jMatrix=4;break;
            case R.id.textView142:iMatrix=1;jMatrix=4;break;
            case R.id.textView143:iMatrix=1;jMatrix=4;break;
            case R.id.textView144:iMatrix=1;jMatrix=4;break;
            case R.id.textView145:iMatrix=1;jMatrix=4;break;
            case R.id.textView146:iMatrix=1;jMatrix=4;break;
            case R.id.textView147:iMatrix=1;jMatrix=4;break;
            case R.id.textView148:iMatrix=1;jMatrix=4;break;
            case R.id.textView149:iMatrix=1;jMatrix=4;break;

            case R.id.textView151:iMatrix=1;jMatrix=5;break;
            case R.id.textView152:iMatrix=1;jMatrix=5;break;
            case R.id.textView153:iMatrix=1;jMatrix=5;break;
            case R.id.textView154:iMatrix=1;jMatrix=5;break;
            case R.id.textView155:iMatrix=1;jMatrix=5;break;
            case R.id.textView156:iMatrix=1;jMatrix=5;break;
            case R.id.textView157:iMatrix=1;jMatrix=5;break;
            case R.id.textView158:iMatrix=1;jMatrix=5;break;
            case R.id.textView159:iMatrix=1;jMatrix=5;break;

            case R.id.textView161:iMatrix=1;jMatrix=6;break;
            case R.id.textView162:iMatrix=1;jMatrix=6;break;
            case R.id.textView163:iMatrix=1;jMatrix=6;break;
            case R.id.textView164:iMatrix=1;jMatrix=6;break;
            case R.id.textView165:iMatrix=1;jMatrix=6;break;
            case R.id.textView166:iMatrix=1;jMatrix=6;break;
            case R.id.textView167:iMatrix=1;jMatrix=6;break;
            case R.id.textView168:iMatrix=1;jMatrix=6;break;
            case R.id.textView169:iMatrix=1;jMatrix=6;break;

            case R.id.textView171:iMatrix=1;jMatrix=7;break;
            case R.id.textView172:iMatrix=1;jMatrix=7;break;
            case R.id.textView173:iMatrix=1;jMatrix=7;break;
            case R.id.textView174:iMatrix=1;jMatrix=7;break;
            case R.id.textView175:iMatrix=1;jMatrix=7;break;
            case R.id.textView176:iMatrix=1;jMatrix=7;break;
            case R.id.textView177:iMatrix=1;jMatrix=7;break;
            case R.id.textView178:iMatrix=1;jMatrix=7;break;
            case R.id.textView179:iMatrix=1;jMatrix=7;break;

            case R.id.textView181:iMatrix=1;jMatrix=8;break;
            case R.id.textView182:iMatrix=1;jMatrix=8;break;
            case R.id.textView183:iMatrix=1;jMatrix=8;break;
            case R.id.textView184:iMatrix=1;jMatrix=8;break;
            case R.id.textView185:iMatrix=1;jMatrix=8;break;
            case R.id.textView186:iMatrix=1;jMatrix=8;break;
            case R.id.textView187:iMatrix=1;jMatrix=8;break;
            case R.id.textView188:iMatrix=1;jMatrix=8;break;
            case R.id.textView189:iMatrix=1;jMatrix=8;break;

            case R.id.textView191:iMatrix=1;jMatrix=9;break;
            case R.id.textView192:iMatrix=1;jMatrix=9;break;
            case R.id.textView193:iMatrix=1;jMatrix=9;break;
            case R.id.textView194:iMatrix=1;jMatrix=9;break;
            case R.id.textView195:iMatrix=1;jMatrix=9;break;
            case R.id.textView196:iMatrix=1;jMatrix=9;break;
            case R.id.textView197:iMatrix=1;jMatrix=9;break;
            case R.id.textView198:iMatrix=1;jMatrix=9;break;
            case R.id.textView199:iMatrix=1;jMatrix=9;break;


            case R.id.textView4 :iMatrix=2;jMatrix=1;break;     //2 строка
            case R.id.textView5 :iMatrix=2;jMatrix=2;break;
            case R.id.textView6 :iMatrix=2;jMatrix=3;break;
            case R.id.textView13:iMatrix=2;jMatrix=4;break;
            case R.id.textView14:iMatrix=2;jMatrix=5;break;
            case R.id.textView15:iMatrix=2;jMatrix=6;break;
            case R.id.textView22:iMatrix=2;jMatrix=7;break;
            case R.id.textView23:iMatrix=2;jMatrix=8;break;
            case R.id.textView24:iMatrix=2;jMatrix=9;break;

            case R.id.textView211:iMatrix=2;jMatrix=1;break;
            case R.id.textView212:iMatrix=2;jMatrix=1;break;
            case R.id.textView213:iMatrix=2;jMatrix=1;break;
            case R.id.textView214:iMatrix=2;jMatrix=1;break;
            case R.id.textView215:iMatrix=2;jMatrix=1;break;
            case R.id.textView216:iMatrix=2;jMatrix=1;break;
            case R.id.textView217:iMatrix=2;jMatrix=1;break;
            case R.id.textView218:iMatrix=2;jMatrix=1;break;
            case R.id.textView219:iMatrix=2;jMatrix=1;break;

            case R.id.textView221:iMatrix=2;jMatrix=2;break;
            case R.id.textView222:iMatrix=2;jMatrix=2;break;
            case R.id.textView223:iMatrix=2;jMatrix=2;break;
            case R.id.textView224:iMatrix=2;jMatrix=2;break;
            case R.id.textView225:iMatrix=2;jMatrix=2;break;
            case R.id.textView226:iMatrix=2;jMatrix=2;break;
            case R.id.textView227:iMatrix=2;jMatrix=2;break;
            case R.id.textView228:iMatrix=2;jMatrix=2;break;
            case R.id.textView229:iMatrix=2;jMatrix=2;break;

            case R.id.textView231:iMatrix=2;jMatrix=3;break;
            case R.id.textView232:iMatrix=2;jMatrix=3;break;
            case R.id.textView233:iMatrix=2;jMatrix=3;break;
            case R.id.textView234:iMatrix=2;jMatrix=3;break;
            case R.id.textView235:iMatrix=2;jMatrix=3;break;
            case R.id.textView236:iMatrix=2;jMatrix=3;break;
            case R.id.textView237:iMatrix=2;jMatrix=3;break;
            case R.id.textView238:iMatrix=2;jMatrix=3;break;
            case R.id.textView239:iMatrix=2;jMatrix=3;break;

            case R.id.textView241:iMatrix=2;jMatrix=4;break;
            case R.id.textView242:iMatrix=2;jMatrix=4;break;
            case R.id.textView243:iMatrix=2;jMatrix=4;break;
            case R.id.textView244:iMatrix=2;jMatrix=4;break;
            case R.id.textView245:iMatrix=2;jMatrix=4;break;
            case R.id.textView246:iMatrix=2;jMatrix=4;break;
            case R.id.textView247:iMatrix=2;jMatrix=4;break;
            case R.id.textView248:iMatrix=2;jMatrix=4;break;
            case R.id.textView249:iMatrix=2;jMatrix=4;break;

            case R.id.textView251:iMatrix=2;jMatrix=5;break;
            case R.id.textView252:iMatrix=2;jMatrix=5;break;
            case R.id.textView253:iMatrix=2;jMatrix=5;break;
            case R.id.textView254:iMatrix=2;jMatrix=5;break;
            case R.id.textView255:iMatrix=2;jMatrix=5;break;
            case R.id.textView256:iMatrix=2;jMatrix=5;break;
            case R.id.textView257:iMatrix=2;jMatrix=5;break;
            case R.id.textView258:iMatrix=2;jMatrix=5;break;
            case R.id.textView259:iMatrix=2;jMatrix=5;break;

            case R.id.textView261:iMatrix=2;jMatrix=6;break;
            case R.id.textView262:iMatrix=2;jMatrix=6;break;
            case R.id.textView263:iMatrix=2;jMatrix=6;break;
            case R.id.textView264:iMatrix=2;jMatrix=6;break;
            case R.id.textView265:iMatrix=2;jMatrix=6;break;
            case R.id.textView266:iMatrix=2;jMatrix=6;break;
            case R.id.textView267:iMatrix=2;jMatrix=6;break;
            case R.id.textView268:iMatrix=2;jMatrix=6;break;
            case R.id.textView269:iMatrix=2;jMatrix=6;break;

            case R.id.textView271:iMatrix=2;jMatrix=7;break;
            case R.id.textView272:iMatrix=2;jMatrix=7;break;
            case R.id.textView273:iMatrix=2;jMatrix=7;break;
            case R.id.textView274:iMatrix=2;jMatrix=7;break;
            case R.id.textView275:iMatrix=2;jMatrix=7;break;
            case R.id.textView276:iMatrix=2;jMatrix=7;break;
            case R.id.textView277:iMatrix=2;jMatrix=7;break;
            case R.id.textView278:iMatrix=2;jMatrix=7;break;
            case R.id.textView279:iMatrix=2;jMatrix=7;break;

            case R.id.textView281:iMatrix=2;jMatrix=8;break;
            case R.id.textView282:iMatrix=2;jMatrix=8;break;
            case R.id.textView283:iMatrix=2;jMatrix=8;break;
            case R.id.textView284:iMatrix=2;jMatrix=8;break;
            case R.id.textView285:iMatrix=2;jMatrix=8;break;
            case R.id.textView286:iMatrix=2;jMatrix=8;break;
            case R.id.textView287:iMatrix=2;jMatrix=8;break;
            case R.id.textView288:iMatrix=2;jMatrix=8;break;
            case R.id.textView289:iMatrix=2;jMatrix=8;break;

            case R.id.textView291:iMatrix=2;jMatrix=9;break;
            case R.id.textView292:iMatrix=2;jMatrix=9;break;
            case R.id.textView293:iMatrix=2;jMatrix=9;break;
            case R.id.textView294:iMatrix=2;jMatrix=9;break;
            case R.id.textView295:iMatrix=2;jMatrix=9;break;
            case R.id.textView296:iMatrix=2;jMatrix=9;break;
            case R.id.textView297:iMatrix=2;jMatrix=9;break;
            case R.id.textView298:iMatrix=2;jMatrix=9;break;
            case R.id.textView299:iMatrix=2;jMatrix=9;break;

            case R.id.textView7 :iMatrix=3;jMatrix=1;break;     //3 строка
            case R.id.textView8 :iMatrix=3;jMatrix=2;break;
            case R.id.textView9 :iMatrix=3;jMatrix=3;break;
            case R.id.textView16:iMatrix=3;jMatrix=4;break;
            case R.id.textView17:iMatrix=3;jMatrix=5;break;
            case R.id.textView18:iMatrix=3;jMatrix=6;break;
            case R.id.textView25:iMatrix=3;jMatrix=7;break;
            case R.id.textView26:iMatrix=3;jMatrix=8;break;
            case R.id.textView27:iMatrix=3;jMatrix=9;break;

            case R.id.textView311:iMatrix=3;jMatrix=1;break;
            case R.id.textView312:iMatrix=3;jMatrix=1;break;
            case R.id.textView313:iMatrix=3;jMatrix=1;break;
            case R.id.textView314:iMatrix=3;jMatrix=1;break;
            case R.id.textView315:iMatrix=3;jMatrix=1;break;
            case R.id.textView316:iMatrix=3;jMatrix=1;break;
            case R.id.textView317:iMatrix=3;jMatrix=1;break;
            case R.id.textView318:iMatrix=3;jMatrix=1;break;
            case R.id.textView319:iMatrix=3;jMatrix=1;break;


            case R.id.textView321:iMatrix=3;jMatrix=2;break;
            case R.id.textView322:iMatrix=3;jMatrix=2;break;
            case R.id.textView323:iMatrix=3;jMatrix=2;break;
            case R.id.textView324:iMatrix=3;jMatrix=2;break;
            case R.id.textView325:iMatrix=3;jMatrix=2;break;
            case R.id.textView326:iMatrix=3;jMatrix=2;break;
            case R.id.textView327:iMatrix=3;jMatrix=2;break;
            case R.id.textView328:iMatrix=3;jMatrix=2;break;
            case R.id.textView329:iMatrix=3;jMatrix=2;break;

            case R.id.textView331:iMatrix=3;jMatrix=3;break;
            case R.id.textView332:iMatrix=3;jMatrix=3;break;
            case R.id.textView333:iMatrix=3;jMatrix=3;break;
            case R.id.textView334:iMatrix=3;jMatrix=3;break;
            case R.id.textView335:iMatrix=3;jMatrix=3;break;
            case R.id.textView336:iMatrix=3;jMatrix=3;break;
            case R.id.textView337:iMatrix=3;jMatrix=3;break;
            case R.id.textView338:iMatrix=3;jMatrix=3;break;
            case R.id.textView339:iMatrix=3;jMatrix=3;break;

            case R.id.textView341:iMatrix=3;jMatrix=4;break;
            case R.id.textView342:iMatrix=3;jMatrix=4;break;
            case R.id.textView343:iMatrix=3;jMatrix=4;break;
            case R.id.textView344:iMatrix=3;jMatrix=4;break;
            case R.id.textView345:iMatrix=3;jMatrix=4;break;
            case R.id.textView346:iMatrix=3;jMatrix=4;break;
            case R.id.textView347:iMatrix=3;jMatrix=4;break;
            case R.id.textView348:iMatrix=3;jMatrix=4;break;
            case R.id.textView349:iMatrix=3;jMatrix=4;break;

            case R.id.textView351:iMatrix=3;jMatrix=5;break;
            case R.id.textView352:iMatrix=3;jMatrix=5;break;
            case R.id.textView353:iMatrix=3;jMatrix=5;break;
            case R.id.textView354:iMatrix=3;jMatrix=5;break;
            case R.id.textView355:iMatrix=3;jMatrix=5;break;
            case R.id.textView356:iMatrix=3;jMatrix=5;break;
            case R.id.textView357:iMatrix=3;jMatrix=5;break;
            case R.id.textView358:iMatrix=3;jMatrix=5;break;
            case R.id.textView359:iMatrix=3;jMatrix=5;break;

            case R.id.textView361:iMatrix=3;jMatrix=6;break;
            case R.id.textView362:iMatrix=3;jMatrix=6;break;
            case R.id.textView363:iMatrix=3;jMatrix=6;break;
            case R.id.textView364:iMatrix=3;jMatrix=6;break;
            case R.id.textView365:iMatrix=3;jMatrix=6;break;
            case R.id.textView366:iMatrix=3;jMatrix=6;break;
            case R.id.textView367:iMatrix=3;jMatrix=6;break;
            case R.id.textView368:iMatrix=3;jMatrix=6;break;
            case R.id.textView369:iMatrix=3;jMatrix=6;break;

            case R.id.textView371:iMatrix=3;jMatrix=7;break;
            case R.id.textView372:iMatrix=3;jMatrix=7;break;
            case R.id.textView373:iMatrix=3;jMatrix=7;break;
            case R.id.textView374:iMatrix=3;jMatrix=7;break;
            case R.id.textView375:iMatrix=3;jMatrix=7;break;
            case R.id.textView376:iMatrix=3;jMatrix=7;break;
            case R.id.textView377:iMatrix=3;jMatrix=7;break;
            case R.id.textView378:iMatrix=3;jMatrix=7;break;
            case R.id.textView379:iMatrix=3;jMatrix=7;break;

            case R.id.textView381:iMatrix=3;jMatrix=8;break;
            case R.id.textView382:iMatrix=3;jMatrix=8;break;
            case R.id.textView383:iMatrix=3;jMatrix=8;break;
            case R.id.textView384:iMatrix=3;jMatrix=8;break;
            case R.id.textView385:iMatrix=3;jMatrix=8;break;
            case R.id.textView386:iMatrix=3;jMatrix=8;break;
            case R.id.textView387:iMatrix=3;jMatrix=8;break;
            case R.id.textView388:iMatrix=3;jMatrix=8;break;
            case R.id.textView389:iMatrix=3;jMatrix=8;break;

            case R.id.textView391:iMatrix=3;jMatrix=9;break;
            case R.id.textView392:iMatrix=3;jMatrix=9;break;
            case R.id.textView393:iMatrix=3;jMatrix=9;break;
            case R.id.textView394:iMatrix=3;jMatrix=9;break;
            case R.id.textView395:iMatrix=3;jMatrix=9;break;
            case R.id.textView396:iMatrix=3;jMatrix=9;break;
            case R.id.textView397:iMatrix=3;jMatrix=9;break;
            case R.id.textView398:iMatrix=3;jMatrix=9;break;
            case R.id.textView399:iMatrix=3;jMatrix=9;break;

            case R.id.textView28:iMatrix=4;jMatrix=1;break;     //4 строка
            case R.id.textView29:iMatrix=4;jMatrix=2;break;
            case R.id.textView30:iMatrix=4;jMatrix=3;break;
            case R.id.textView37:iMatrix=4;jMatrix=4;break;
            case R.id.textView38:iMatrix=4;jMatrix=5;break;
            case R.id.textView39:iMatrix=4;jMatrix=6;break;
            case R.id.textView46:iMatrix=4;jMatrix=7;break;
            case R.id.textView47:iMatrix=4;jMatrix=8;break;
            case R.id.textView48:iMatrix=4;jMatrix=9;break;

            case R.id.textView411:iMatrix=4;jMatrix=1;break;
            case R.id.textView412:iMatrix=4;jMatrix=1;break;
            case R.id.textView413:iMatrix=4;jMatrix=1;break;
            case R.id.textView414:iMatrix=4;jMatrix=1;break;
            case R.id.textView415:iMatrix=4;jMatrix=1;break;
            case R.id.textView416:iMatrix=4;jMatrix=1;break;
            case R.id.textView417:iMatrix=4;jMatrix=1;break;
            case R.id.textView418:iMatrix=4;jMatrix=1;break;
            case R.id.textView419:iMatrix=4;jMatrix=1;break;

            case R.id.textView421:iMatrix=4;jMatrix=2;break;
            case R.id.textView422:iMatrix=4;jMatrix=2;break;
            case R.id.textView423:iMatrix=4;jMatrix=2;break;
            case R.id.textView424:iMatrix=4;jMatrix=2;break;
            case R.id.textView425:iMatrix=4;jMatrix=2;break;
            case R.id.textView426:iMatrix=4;jMatrix=2;break;
            case R.id.textView427:iMatrix=4;jMatrix=2;break;
            case R.id.textView428:iMatrix=4;jMatrix=2;break;
            case R.id.textView429:iMatrix=4;jMatrix=2;break;

            case R.id.textView431:iMatrix=4;jMatrix=3;break;
            case R.id.textView432:iMatrix=4;jMatrix=3;break;
            case R.id.textView433:iMatrix=4;jMatrix=3;break;
            case R.id.textView434:iMatrix=4;jMatrix=3;break;
            case R.id.textView435:iMatrix=4;jMatrix=3;break;
            case R.id.textView436:iMatrix=4;jMatrix=3;break;
            case R.id.textView437:iMatrix=4;jMatrix=3;break;
            case R.id.textView438:iMatrix=4;jMatrix=3;break;
            case R.id.textView439:iMatrix=4;jMatrix=3;break;

            case R.id.textView441:iMatrix=4;jMatrix=4;break;
            case R.id.textView442:iMatrix=4;jMatrix=4;break;
            case R.id.textView443:iMatrix=4;jMatrix=4;break;
            case R.id.textView444:iMatrix=4;jMatrix=4;break;
            case R.id.textView445:iMatrix=4;jMatrix=4;break;
            case R.id.textView446:iMatrix=4;jMatrix=4;break;
            case R.id.textView447:iMatrix=4;jMatrix=4;break;
            case R.id.textView448:iMatrix=4;jMatrix=4;break;
            case R.id.textView449:iMatrix=4;jMatrix=4;break;

            case R.id.textView451:iMatrix=4;jMatrix=5;break;
            case R.id.textView452:iMatrix=4;jMatrix=5;break;
            case R.id.textView453:iMatrix=4;jMatrix=5;break;
            case R.id.textView454:iMatrix=4;jMatrix=5;break;
            case R.id.textView455:iMatrix=4;jMatrix=5;break;
            case R.id.textView456:iMatrix=4;jMatrix=5;break;
            case R.id.textView457:iMatrix=4;jMatrix=5;break;
            case R.id.textView458:iMatrix=4;jMatrix=5;break;
            case R.id.textView459:iMatrix=4;jMatrix=5;break;

            case R.id.textView461:iMatrix=4;jMatrix=6;break;
            case R.id.textView462:iMatrix=4;jMatrix=6;break;
            case R.id.textView463:iMatrix=4;jMatrix=6;break;
            case R.id.textView464:iMatrix=4;jMatrix=6;break;
            case R.id.textView465:iMatrix=4;jMatrix=6;break;
            case R.id.textView466:iMatrix=4;jMatrix=6;break;
            case R.id.textView467:iMatrix=4;jMatrix=6;break;
            case R.id.textView468:iMatrix=4;jMatrix=6;break;
            case R.id.textView469:iMatrix=4;jMatrix=6;break;

            case R.id.textView471:iMatrix=4;jMatrix=7;break;
            case R.id.textView472:iMatrix=4;jMatrix=7;break;
            case R.id.textView473:iMatrix=4;jMatrix=7;break;
            case R.id.textView474:iMatrix=4;jMatrix=7;break;
            case R.id.textView475:iMatrix=4;jMatrix=7;break;
            case R.id.textView476:iMatrix=4;jMatrix=7;break;
            case R.id.textView477:iMatrix=4;jMatrix=7;break;
            case R.id.textView478:iMatrix=4;jMatrix=7;break;
            case R.id.textView479:iMatrix=4;jMatrix=7;break;

            case R.id.textView481:iMatrix=4;jMatrix=8;break;
            case R.id.textView482:iMatrix=4;jMatrix=8;break;
            case R.id.textView483:iMatrix=4;jMatrix=8;break;
            case R.id.textView484:iMatrix=4;jMatrix=8;break;
            case R.id.textView485:iMatrix=4;jMatrix=8;break;
            case R.id.textView486:iMatrix=4;jMatrix=8;break;
            case R.id.textView487:iMatrix=4;jMatrix=8;break;
            case R.id.textView488:iMatrix=4;jMatrix=8;break;
            case R.id.textView489:iMatrix=4;jMatrix=8;break;

            case R.id.textView491:iMatrix=4;jMatrix=9;break;
            case R.id.textView492:iMatrix=4;jMatrix=9;break;
            case R.id.textView493:iMatrix=4;jMatrix=9;break;
            case R.id.textView494:iMatrix=4;jMatrix=9;break;
            case R.id.textView495:iMatrix=4;jMatrix=9;break;
            case R.id.textView496:iMatrix=4;jMatrix=9;break;
            case R.id.textView497:iMatrix=4;jMatrix=9;break;
            case R.id.textView498:iMatrix=4;jMatrix=9;break;
            case R.id.textView499:iMatrix=4;jMatrix=9;break;

            case R.id.textView31:iMatrix=5;jMatrix=1;break;     //5 строка
            case R.id.textView32:iMatrix=5;jMatrix=2;break;
            case R.id.textView33:iMatrix=5;jMatrix=3;break;
            case R.id.textView40:iMatrix=5;jMatrix=4;break;
            case R.id.textView41:iMatrix=5;jMatrix=5;break;
            case R.id.textView42:iMatrix=5;jMatrix=6;break;
            case R.id.textView49:iMatrix=5;jMatrix=7;break;
            case R.id.textView50:iMatrix=5;jMatrix=8;break;
            case R.id.textView51:iMatrix=5;jMatrix=9;break;

            case R.id.textView511:iMatrix=5;jMatrix=1;break;
            case R.id.textView512:iMatrix=5;jMatrix=1;break;
            case R.id.textView513:iMatrix=5;jMatrix=1;break;
            case R.id.textView514:iMatrix=5;jMatrix=1;break;
            case R.id.textView515:iMatrix=5;jMatrix=1;break;
            case R.id.textView516:iMatrix=5;jMatrix=1;break;
            case R.id.textView517:iMatrix=5;jMatrix=1;break;
            case R.id.textView518:iMatrix=5;jMatrix=1;break;
            case R.id.textView519:iMatrix=5;jMatrix=1;break;

            case R.id.textView521:iMatrix=5;jMatrix=2;break;
            case R.id.textView522:iMatrix=5;jMatrix=2;break;
            case R.id.textView523:iMatrix=5;jMatrix=2;break;
            case R.id.textView524:iMatrix=5;jMatrix=2;break;
            case R.id.textView525:iMatrix=5;jMatrix=2;break;
            case R.id.textView526:iMatrix=5;jMatrix=2;break;
            case R.id.textView527:iMatrix=5;jMatrix=2;break;
            case R.id.textView528:iMatrix=5;jMatrix=2;break;
            case R.id.textView529:iMatrix=5;jMatrix=2;break;

            case R.id.textView531:iMatrix=5;jMatrix=3;break;
            case R.id.textView532:iMatrix=5;jMatrix=3;break;
            case R.id.textView533:iMatrix=5;jMatrix=3;break;
            case R.id.textView534:iMatrix=5;jMatrix=3;break;
            case R.id.textView535:iMatrix=5;jMatrix=3;break;
            case R.id.textView536:iMatrix=5;jMatrix=3;break;
            case R.id.textView537:iMatrix=5;jMatrix=3;break;
            case R.id.textView538:iMatrix=5;jMatrix=3;break;
            case R.id.textView539:iMatrix=5;jMatrix=3;break;

            case R.id.textView541:iMatrix=5;jMatrix=4;break;
            case R.id.textView542:iMatrix=5;jMatrix=4;break;
            case R.id.textView543:iMatrix=5;jMatrix=4;break;
            case R.id.textView544:iMatrix=5;jMatrix=4;break;
            case R.id.textView545:iMatrix=5;jMatrix=4;break;
            case R.id.textView546:iMatrix=5;jMatrix=4;break;
            case R.id.textView547:iMatrix=5;jMatrix=4;break;
            case R.id.textView548:iMatrix=5;jMatrix=4;break;
            case R.id.textView549:iMatrix=5;jMatrix=4;break;

            case R.id.textView551:iMatrix=5;jMatrix=5;break;
            case R.id.textView552:iMatrix=5;jMatrix=5;break;
            case R.id.textView553:iMatrix=5;jMatrix=5;break;
            case R.id.textView554:iMatrix=5;jMatrix=5;break;
            case R.id.textView555:iMatrix=5;jMatrix=5;break;
            case R.id.textView556:iMatrix=5;jMatrix=5;break;
            case R.id.textView557:iMatrix=5;jMatrix=5;break;
            case R.id.textView558:iMatrix=5;jMatrix=5;break;
            case R.id.textView559:iMatrix=5;jMatrix=5;break;

            case R.id.textView561:iMatrix=5;jMatrix=6;break;
            case R.id.textView562:iMatrix=5;jMatrix=6;break;
            case R.id.textView563:iMatrix=5;jMatrix=6;break;
            case R.id.textView564:iMatrix=5;jMatrix=6;break;
            case R.id.textView565:iMatrix=5;jMatrix=6;break;
            case R.id.textView566:iMatrix=5;jMatrix=6;break;
            case R.id.textView567:iMatrix=5;jMatrix=6;break;
            case R.id.textView568:iMatrix=5;jMatrix=6;break;
            case R.id.textView569:iMatrix=5;jMatrix=6;break;

            case R.id.textView571:iMatrix=5;jMatrix=7;break;
            case R.id.textView572:iMatrix=5;jMatrix=7;break;
            case R.id.textView573:iMatrix=5;jMatrix=7;break;
            case R.id.textView574:iMatrix=5;jMatrix=7;break;
            case R.id.textView575:iMatrix=5;jMatrix=7;break;
            case R.id.textView576:iMatrix=5;jMatrix=7;break;
            case R.id.textView577:iMatrix=5;jMatrix=7;break;
            case R.id.textView578:iMatrix=5;jMatrix=7;break;
            case R.id.textView579:iMatrix=5;jMatrix=7;break;

            case R.id.textView581:iMatrix=5;jMatrix=8;break;
            case R.id.textView582:iMatrix=5;jMatrix=8;break;
            case R.id.textView583:iMatrix=5;jMatrix=8;break;
            case R.id.textView584:iMatrix=5;jMatrix=8;break;
            case R.id.textView585:iMatrix=5;jMatrix=8;break;
            case R.id.textView586:iMatrix=5;jMatrix=8;break;
            case R.id.textView587:iMatrix=5;jMatrix=8;break;
            case R.id.textView588:iMatrix=5;jMatrix=8;break;
            case R.id.textView589:iMatrix=5;jMatrix=8;break;

            case R.id.textView591:iMatrix=5;jMatrix=9;break;
            case R.id.textView592:iMatrix=5;jMatrix=9;break;
            case R.id.textView593:iMatrix=5;jMatrix=9;break;
            case R.id.textView594:iMatrix=5;jMatrix=9;break;
            case R.id.textView595:iMatrix=5;jMatrix=9;break;
            case R.id.textView596:iMatrix=5;jMatrix=9;break;
            case R.id.textView597:iMatrix=5;jMatrix=9;break;
            case R.id.textView598:iMatrix=5;jMatrix=9;break;
            case R.id.textView599:iMatrix=5;jMatrix=9;break;

            case R.id.textView34:iMatrix=6;jMatrix=1;break;     //6 строка
            case R.id.textView35:iMatrix=6;jMatrix=2;break;
            case R.id.textView36:iMatrix=6;jMatrix=3;break;
            case R.id.textView43:iMatrix=6;jMatrix=4;break;
            case R.id.textView44:iMatrix=6;jMatrix=5;break;
            case R.id.textView45:iMatrix=6;jMatrix=6;break;
            case R.id.textView52:iMatrix=6;jMatrix=7;break;
            case R.id.textView53:iMatrix=6;jMatrix=8;break;
            case R.id.textView54:iMatrix=6;jMatrix=9;break;

            case R.id.textView611:iMatrix=6;jMatrix=1;break;
            case R.id.textView612:iMatrix=6;jMatrix=1;break;
            case R.id.textView613:iMatrix=6;jMatrix=1;break;
            case R.id.textView614:iMatrix=6;jMatrix=1;break;
            case R.id.textView615:iMatrix=6;jMatrix=1;break;
            case R.id.textView616:iMatrix=6;jMatrix=1;break;
            case R.id.textView617:iMatrix=6;jMatrix=1;break;
            case R.id.textView618:iMatrix=6;jMatrix=1;break;
            case R.id.textView619:iMatrix=6;jMatrix=1;break;

            case R.id.textView621:iMatrix=6;jMatrix=2;break;
            case R.id.textView622:iMatrix=6;jMatrix=2;break;
            case R.id.textView623:iMatrix=6;jMatrix=2;break;
            case R.id.textView624:iMatrix=6;jMatrix=2;break;
            case R.id.textView625:iMatrix=6;jMatrix=2;break;
            case R.id.textView626:iMatrix=6;jMatrix=2;break;
            case R.id.textView627:iMatrix=6;jMatrix=2;break;
            case R.id.textView628:iMatrix=6;jMatrix=2;break;
            case R.id.textView629:iMatrix=6;jMatrix=2;break;

            case R.id.textView631:iMatrix=6;jMatrix=3;break;
            case R.id.textView632:iMatrix=6;jMatrix=3;break;
            case R.id.textView633:iMatrix=6;jMatrix=3;break;
            case R.id.textView634:iMatrix=6;jMatrix=3;break;
            case R.id.textView635:iMatrix=6;jMatrix=3;break;
            case R.id.textView636:iMatrix=6;jMatrix=3;break;
            case R.id.textView637:iMatrix=6;jMatrix=3;break;
            case R.id.textView638:iMatrix=6;jMatrix=3;break;
            case R.id.textView639:iMatrix=6;jMatrix=3;break;

            case R.id.textView641:iMatrix=6;jMatrix=4;break;
            case R.id.textView642:iMatrix=6;jMatrix=4;break;
            case R.id.textView643:iMatrix=6;jMatrix=4;break;
            case R.id.textView644:iMatrix=6;jMatrix=4;break;
            case R.id.textView645:iMatrix=6;jMatrix=4;break;
            case R.id.textView646:iMatrix=6;jMatrix=4;break;
            case R.id.textView647:iMatrix=6;jMatrix=4;break;
            case R.id.textView648:iMatrix=6;jMatrix=4;break;
            case R.id.textView649:iMatrix=6;jMatrix=4;break;

            case R.id.textView651:iMatrix=6;jMatrix=5;break;
            case R.id.textView652:iMatrix=6;jMatrix=5;break;
            case R.id.textView653:iMatrix=6;jMatrix=5;break;
            case R.id.textView654:iMatrix=6;jMatrix=5;break;
            case R.id.textView655:iMatrix=6;jMatrix=5;break;
            case R.id.textView656:iMatrix=6;jMatrix=5;break;
            case R.id.textView657:iMatrix=6;jMatrix=5;break;
            case R.id.textView658:iMatrix=6;jMatrix=5;break;
            case R.id.textView659:iMatrix=6;jMatrix=5;break;

            case R.id.textView661:iMatrix=6;jMatrix=6;break;
            case R.id.textView662:iMatrix=6;jMatrix=6;break;
            case R.id.textView663:iMatrix=6;jMatrix=6;break;
            case R.id.textView664:iMatrix=6;jMatrix=6;break;
            case R.id.textView665:iMatrix=6;jMatrix=6;break;
            case R.id.textView666:iMatrix=6;jMatrix=6;break;
            case R.id.textView667:iMatrix=6;jMatrix=6;break;
            case R.id.textView668:iMatrix=6;jMatrix=6;break;
            case R.id.textView669:iMatrix=6;jMatrix=6;break;

            case R.id.textView671:iMatrix=6;jMatrix=7;break;
            case R.id.textView672:iMatrix=6;jMatrix=7;break;
            case R.id.textView673:iMatrix=6;jMatrix=7;break;
            case R.id.textView674:iMatrix=6;jMatrix=7;break;
            case R.id.textView675:iMatrix=6;jMatrix=7;break;
            case R.id.textView676:iMatrix=6;jMatrix=7;break;
            case R.id.textView677:iMatrix=6;jMatrix=7;break;
            case R.id.textView678:iMatrix=6;jMatrix=7;break;
            case R.id.textView679:iMatrix=6;jMatrix=7;break;

            case R.id.textView681:iMatrix=6;jMatrix=8;break;
            case R.id.textView682:iMatrix=6;jMatrix=8;break;
            case R.id.textView683:iMatrix=6;jMatrix=8;break;
            case R.id.textView684:iMatrix=6;jMatrix=8;break;
            case R.id.textView685:iMatrix=6;jMatrix=8;break;
            case R.id.textView686:iMatrix=6;jMatrix=8;break;
            case R.id.textView687:iMatrix=6;jMatrix=8;break;
            case R.id.textView688:iMatrix=6;jMatrix=8;break;
            case R.id.textView689:iMatrix=6;jMatrix=8;break;

            case R.id.textView691:iMatrix=6;jMatrix=9;break;
            case R.id.textView692:iMatrix=6;jMatrix=9;break;
            case R.id.textView693:iMatrix=6;jMatrix=9;break;
            case R.id.textView694:iMatrix=6;jMatrix=9;break;
            case R.id.textView695:iMatrix=6;jMatrix=9;break;
            case R.id.textView696:iMatrix=6;jMatrix=9;break;
            case R.id.textView697:iMatrix=6;jMatrix=9;break;
            case R.id.textView698:iMatrix=6;jMatrix=9;break;
            case R.id.textView699:iMatrix=6;jMatrix=9;break;

            case R.id.textView55:iMatrix=7;jMatrix=1;break;     //7 строка
            case R.id.textView56:iMatrix=7;jMatrix=2;break;
            case R.id.textView57:iMatrix=7;jMatrix=3;break;
            case R.id.textView64:iMatrix=7;jMatrix=4;break;
            case R.id.textView65:iMatrix=7;jMatrix=5;break;
            case R.id.textView66:iMatrix=7;jMatrix=6;break;
            case R.id.textView73:iMatrix=7;jMatrix=7;break;
            case R.id.textView74:iMatrix=7;jMatrix=8;break;
            case R.id.textView75:iMatrix=7;jMatrix=9;break;

            case R.id.textView711:iMatrix=7;jMatrix=1;break;
            case R.id.textView712:iMatrix=7;jMatrix=1;break;
            case R.id.textView713:iMatrix=7;jMatrix=1;break;
            case R.id.textView714:iMatrix=7;jMatrix=1;break;
            case R.id.textView715:iMatrix=7;jMatrix=1;break;
            case R.id.textView716:iMatrix=7;jMatrix=1;break;
            case R.id.textView717:iMatrix=7;jMatrix=1;break;
            case R.id.textView718:iMatrix=7;jMatrix=1;break;
            case R.id.textView719:iMatrix=7;jMatrix=1;break;

            case R.id.textView721:iMatrix=7;jMatrix=2;break;
            case R.id.textView722:iMatrix=7;jMatrix=2;break;
            case R.id.textView723:iMatrix=7;jMatrix=2;break;
            case R.id.textView724:iMatrix=7;jMatrix=2;break;
            case R.id.textView725:iMatrix=7;jMatrix=2;break;
            case R.id.textView726:iMatrix=7;jMatrix=2;break;
            case R.id.textView727:iMatrix=7;jMatrix=2;break;
            case R.id.textView728:iMatrix=7;jMatrix=2;break;
            case R.id.textView729:iMatrix=7;jMatrix=2;break;

            case R.id.textView731:iMatrix=7;jMatrix=3;break;
            case R.id.textView732:iMatrix=7;jMatrix=3;break;
            case R.id.textView733:iMatrix=7;jMatrix=3;break;
            case R.id.textView734:iMatrix=7;jMatrix=3;break;
            case R.id.textView735:iMatrix=7;jMatrix=3;break;
            case R.id.textView736:iMatrix=7;jMatrix=3;break;
            case R.id.textView737:iMatrix=7;jMatrix=3;break;
            case R.id.textView738:iMatrix=7;jMatrix=3;break;
            case R.id.textView739:iMatrix=7;jMatrix=3;break;

            case R.id.textView741:iMatrix=7;jMatrix=4;break;
            case R.id.textView742:iMatrix=7;jMatrix=4;break;
            case R.id.textView743:iMatrix=7;jMatrix=4;break;
            case R.id.textView744:iMatrix=7;jMatrix=4;break;
            case R.id.textView745:iMatrix=7;jMatrix=4;break;
            case R.id.textView746:iMatrix=7;jMatrix=4;break;
            case R.id.textView747:iMatrix=7;jMatrix=4;break;
            case R.id.textView748:iMatrix=7;jMatrix=4;break;
            case R.id.textView749:iMatrix=7;jMatrix=4;break;

            case R.id.textView751:iMatrix=7;jMatrix=5;break;
            case R.id.textView752:iMatrix=7;jMatrix=5;break;
            case R.id.textView753:iMatrix=7;jMatrix=5;break;
            case R.id.textView754:iMatrix=7;jMatrix=5;break;
            case R.id.textView755:iMatrix=7;jMatrix=5;break;
            case R.id.textView756:iMatrix=7;jMatrix=5;break;
            case R.id.textView757:iMatrix=7;jMatrix=5;break;
            case R.id.textView758:iMatrix=7;jMatrix=5;break;
            case R.id.textView759:iMatrix=7;jMatrix=5;break;

            case R.id.textView761:iMatrix=7;jMatrix=6;break;
            case R.id.textView762:iMatrix=7;jMatrix=6;break;
            case R.id.textView763:iMatrix=7;jMatrix=6;break;
            case R.id.textView764:iMatrix=7;jMatrix=6;break;
            case R.id.textView765:iMatrix=7;jMatrix=6;break;
            case R.id.textView766:iMatrix=7;jMatrix=6;break;
            case R.id.textView767:iMatrix=7;jMatrix=6;break;
            case R.id.textView768:iMatrix=7;jMatrix=6;break;
            case R.id.textView769:iMatrix=7;jMatrix=6;break;

            case R.id.textView771:iMatrix=7;jMatrix=7;break;
            case R.id.textView772:iMatrix=7;jMatrix=7;break;
            case R.id.textView773:iMatrix=7;jMatrix=7;break;
            case R.id.textView774:iMatrix=7;jMatrix=7;break;
            case R.id.textView775:iMatrix=7;jMatrix=7;break;
            case R.id.textView776:iMatrix=7;jMatrix=7;break;
            case R.id.textView777:iMatrix=7;jMatrix=7;break;
            case R.id.textView778:iMatrix=7;jMatrix=7;break;
            case R.id.textView779:iMatrix=7;jMatrix=7;break;

            case R.id.textView781:iMatrix=7;jMatrix=8;break;
            case R.id.textView782:iMatrix=7;jMatrix=8;break;
            case R.id.textView783:iMatrix=7;jMatrix=8;break;
            case R.id.textView784:iMatrix=7;jMatrix=8;break;
            case R.id.textView785:iMatrix=7;jMatrix=8;break;
            case R.id.textView786:iMatrix=7;jMatrix=8;break;
            case R.id.textView787:iMatrix=7;jMatrix=8;break;
            case R.id.textView788:iMatrix=7;jMatrix=8;break;
            case R.id.textView789:iMatrix=7;jMatrix=8;break;

            case R.id.textView791:iMatrix=7;jMatrix=9;break;
            case R.id.textView792:iMatrix=7;jMatrix=9;break;
            case R.id.textView793:iMatrix=7;jMatrix=9;break;
            case R.id.textView794:iMatrix=7;jMatrix=9;break;
            case R.id.textView795:iMatrix=7;jMatrix=9;break;
            case R.id.textView796:iMatrix=7;jMatrix=9;break;
            case R.id.textView797:iMatrix=7;jMatrix=9;break;
            case R.id.textView798:iMatrix=7;jMatrix=9;break;
            case R.id.textView799:iMatrix=7;jMatrix=9;break;

            case R.id.textView58:iMatrix=8;jMatrix=1;break;     //8 строка
            case R.id.textView59:iMatrix=8;jMatrix=2;break;
            case R.id.textView60:iMatrix=8;jMatrix=3;break;
            case R.id.textView67:iMatrix=8;jMatrix=4;break;
            case R.id.textView68:iMatrix=8;jMatrix=5;break;
            case R.id.textView69:iMatrix=8;jMatrix=6;break;
            case R.id.textView76:iMatrix=8;jMatrix=7;break;
            case R.id.textView77:iMatrix=8;jMatrix=8;break;
            case R.id.textView78:iMatrix=8;jMatrix=9;break;

            case R.id.textView811:iMatrix=8;jMatrix=1;break;
            case R.id.textView812:iMatrix=8;jMatrix=1;break;
            case R.id.textView813:iMatrix=8;jMatrix=1;break;
            case R.id.textView814:iMatrix=8;jMatrix=1;break;
            case R.id.textView815:iMatrix=8;jMatrix=1;break;
            case R.id.textView816:iMatrix=8;jMatrix=1;break;
            case R.id.textView817:iMatrix=8;jMatrix=1;break;
            case R.id.textView818:iMatrix=8;jMatrix=1;break;
            case R.id.textView819:iMatrix=8;jMatrix=1;break;

            case R.id.textView821:iMatrix=8;jMatrix=2;break;
            case R.id.textView822:iMatrix=8;jMatrix=2;break;
            case R.id.textView823:iMatrix=8;jMatrix=2;break;
            case R.id.textView824:iMatrix=8;jMatrix=2;break;
            case R.id.textView825:iMatrix=8;jMatrix=2;break;
            case R.id.textView826:iMatrix=8;jMatrix=2;break;
            case R.id.textView827:iMatrix=8;jMatrix=2;break;
            case R.id.textView828:iMatrix=8;jMatrix=2;break;
            case R.id.textView829:iMatrix=8;jMatrix=2;break;

            case R.id.textView831:iMatrix=8;jMatrix=3;break;
            case R.id.textView832:iMatrix=8;jMatrix=3;break;
            case R.id.textView833:iMatrix=8;jMatrix=3;break;
            case R.id.textView834:iMatrix=8;jMatrix=3;break;
            case R.id.textView835:iMatrix=8;jMatrix=3;break;
            case R.id.textView836:iMatrix=8;jMatrix=3;break;
            case R.id.textView837:iMatrix=8;jMatrix=3;break;
            case R.id.textView838:iMatrix=8;jMatrix=3;break;
            case R.id.textView839:iMatrix=8;jMatrix=3;break;

            case R.id.textView841:iMatrix=8;jMatrix=4;break;
            case R.id.textView842:iMatrix=8;jMatrix=4;break;
            case R.id.textView843:iMatrix=8;jMatrix=4;break;
            case R.id.textView844:iMatrix=8;jMatrix=4;break;
            case R.id.textView845:iMatrix=8;jMatrix=4;break;
            case R.id.textView846:iMatrix=8;jMatrix=4;break;
            case R.id.textView847:iMatrix=8;jMatrix=4;break;
            case R.id.textView848:iMatrix=8;jMatrix=4;break;
            case R.id.textView849:iMatrix=8;jMatrix=4;break;

            case R.id.textView851:iMatrix=8;jMatrix=5;break;
            case R.id.textView852:iMatrix=8;jMatrix=5;break;
            case R.id.textView853:iMatrix=8;jMatrix=5;break;
            case R.id.textView854:iMatrix=8;jMatrix=5;break;
            case R.id.textView855:iMatrix=8;jMatrix=5;break;
            case R.id.textView856:iMatrix=8;jMatrix=5;break;
            case R.id.textView857:iMatrix=8;jMatrix=5;break;
            case R.id.textView858:iMatrix=8;jMatrix=5;break;
            case R.id.textView859:iMatrix=8;jMatrix=5;break;

            case R.id.textView861:iMatrix=8;jMatrix=6;break;
            case R.id.textView862:iMatrix=8;jMatrix=6;break;
            case R.id.textView863:iMatrix=8;jMatrix=6;break;
            case R.id.textView864:iMatrix=8;jMatrix=6;break;
            case R.id.textView865:iMatrix=8;jMatrix=6;break;
            case R.id.textView866:iMatrix=8;jMatrix=6;break;
            case R.id.textView867:iMatrix=8;jMatrix=6;break;
            case R.id.textView868:iMatrix=8;jMatrix=6;break;
            case R.id.textView869:iMatrix=8;jMatrix=6;break;

            case R.id.textView871:iMatrix=8;jMatrix=7;break;
            case R.id.textView872:iMatrix=8;jMatrix=7;break;
            case R.id.textView873:iMatrix=8;jMatrix=7;break;
            case R.id.textView874:iMatrix=8;jMatrix=7;break;
            case R.id.textView875:iMatrix=8;jMatrix=7;break;
            case R.id.textView876:iMatrix=8;jMatrix=7;break;
            case R.id.textView877:iMatrix=8;jMatrix=7;break;
            case R.id.textView878:iMatrix=8;jMatrix=7;break;
            case R.id.textView879:iMatrix=8;jMatrix=7;break;

            case R.id.textView881:iMatrix=8;jMatrix=8;break;
            case R.id.textView882:iMatrix=8;jMatrix=8;break;
            case R.id.textView883:iMatrix=8;jMatrix=8;break;
            case R.id.textView884:iMatrix=8;jMatrix=8;break;
            case R.id.textView885:iMatrix=8;jMatrix=8;break;
            case R.id.textView886:iMatrix=8;jMatrix=8;break;
            case R.id.textView887:iMatrix=8;jMatrix=8;break;
            case R.id.textView888:iMatrix=8;jMatrix=8;break;
            case R.id.textView889:iMatrix=8;jMatrix=8;break;

            case R.id.textView891:iMatrix=8;jMatrix=9;break;
            case R.id.textView892:iMatrix=8;jMatrix=9;break;
            case R.id.textView893:iMatrix=8;jMatrix=9;break;
            case R.id.textView894:iMatrix=8;jMatrix=9;break;
            case R.id.textView895:iMatrix=8;jMatrix=9;break;
            case R.id.textView896:iMatrix=8;jMatrix=9;break;
            case R.id.textView897:iMatrix=8;jMatrix=9;break;
            case R.id.textView898:iMatrix=8;jMatrix=9;break;
            case R.id.textView899:iMatrix=8;jMatrix=9;break;

            case R.id.textView61:iMatrix=9;jMatrix=1;break;     //9 строка
            case R.id.textView62:iMatrix=9;jMatrix=2;break;
            case R.id.textView63:iMatrix=9;jMatrix=3;break;
            case R.id.textView70:iMatrix=9;jMatrix=4;break;
            case R.id.textView71:iMatrix=9;jMatrix=5;break;
            case R.id.textView72:iMatrix=9;jMatrix=6;break;
            case R.id.textView79:iMatrix=9;jMatrix=7;break;
            case R.id.textView80:iMatrix=9;jMatrix=8;break;
            case R.id.textView81:iMatrix=9;jMatrix=9;break;

            case R.id.textView911:iMatrix=9;jMatrix=1;break;
            case R.id.textView912:iMatrix=9;jMatrix=1;break;
            case R.id.textView913:iMatrix=9;jMatrix=1;break;
            case R.id.textView914:iMatrix=9;jMatrix=1;break;
            case R.id.textView915:iMatrix=9;jMatrix=1;break;
            case R.id.textView916:iMatrix=9;jMatrix=1;break;
            case R.id.textView917:iMatrix=9;jMatrix=1;break;
            case R.id.textView918:iMatrix=9;jMatrix=1;break;
            case R.id.textView919:iMatrix=9;jMatrix=1;break;

            case R.id.textView921:iMatrix=9;jMatrix=2;break;
            case R.id.textView922:iMatrix=9;jMatrix=2;break;
            case R.id.textView923:iMatrix=9;jMatrix=2;break;
            case R.id.textView924:iMatrix=9;jMatrix=2;break;
            case R.id.textView925:iMatrix=9;jMatrix=2;break;
            case R.id.textView926:iMatrix=9;jMatrix=2;break;
            case R.id.textView927:iMatrix=9;jMatrix=2;break;
            case R.id.textView928:iMatrix=9;jMatrix=2;break;
            case R.id.textView929:iMatrix=9;jMatrix=2;break;

            case R.id.textView931:iMatrix=9;jMatrix=3;break;
            case R.id.textView932:iMatrix=9;jMatrix=3;break;
            case R.id.textView933:iMatrix=9;jMatrix=3;break;
            case R.id.textView934:iMatrix=9;jMatrix=3;break;
            case R.id.textView935:iMatrix=9;jMatrix=3;break;
            case R.id.textView936:iMatrix=9;jMatrix=3;break;
            case R.id.textView937:iMatrix=9;jMatrix=3;break;
            case R.id.textView938:iMatrix=9;jMatrix=3;break;
            case R.id.textView939:iMatrix=9;jMatrix=3;break;

            case R.id.textView941:iMatrix=9;jMatrix=4;break;
            case R.id.textView942:iMatrix=9;jMatrix=4;break;
            case R.id.textView943:iMatrix=9;jMatrix=4;break;
            case R.id.textView944:iMatrix=9;jMatrix=4;break;
            case R.id.textView945:iMatrix=9;jMatrix=4;break;
            case R.id.textView946:iMatrix=9;jMatrix=4;break;
            case R.id.textView947:iMatrix=9;jMatrix=4;break;
            case R.id.textView948:iMatrix=9;jMatrix=4;break;
            case R.id.textView949:iMatrix=9;jMatrix=4;break;

            case R.id.textView951:iMatrix=9;jMatrix=5;break;
            case R.id.textView952:iMatrix=9;jMatrix=5;break;
            case R.id.textView953:iMatrix=9;jMatrix=5;break;
            case R.id.textView954:iMatrix=9;jMatrix=5;break;
            case R.id.textView955:iMatrix=9;jMatrix=5;break;
            case R.id.textView956:iMatrix=9;jMatrix=5;break;
            case R.id.textView957:iMatrix=9;jMatrix=5;break;
            case R.id.textView958:iMatrix=9;jMatrix=5;break;
            case R.id.textView959:iMatrix=9;jMatrix=5;break;

            case R.id.textView961:iMatrix=9;jMatrix=6;break;
            case R.id.textView962:iMatrix=9;jMatrix=6;break;
            case R.id.textView963:iMatrix=9;jMatrix=6;break;
            case R.id.textView964:iMatrix=9;jMatrix=6;break;
            case R.id.textView965:iMatrix=9;jMatrix=6;break;
            case R.id.textView966:iMatrix=9;jMatrix=6;break;
            case R.id.textView967:iMatrix=9;jMatrix=6;break;
            case R.id.textView968:iMatrix=9;jMatrix=6;break;
            case R.id.textView969:iMatrix=9;jMatrix=6;break;

            case R.id.textView971:iMatrix=9;jMatrix=7;break;
            case R.id.textView972:iMatrix=9;jMatrix=7;break;
            case R.id.textView973:iMatrix=9;jMatrix=7;break;
            case R.id.textView974:iMatrix=9;jMatrix=7;break;
            case R.id.textView975:iMatrix=9;jMatrix=7;break;
            case R.id.textView976:iMatrix=9;jMatrix=7;break;
            case R.id.textView977:iMatrix=9;jMatrix=7;break;
            case R.id.textView978:iMatrix=9;jMatrix=7;break;
            case R.id.textView979:iMatrix=9;jMatrix=7;break;

            case R.id.textView981:iMatrix=9;jMatrix=8;break;
            case R.id.textView982:iMatrix=9;jMatrix=8;break;
            case R.id.textView983:iMatrix=9;jMatrix=8;break;
            case R.id.textView984:iMatrix=9;jMatrix=8;break;
            case R.id.textView985:iMatrix=9;jMatrix=8;break;
            case R.id.textView986:iMatrix=9;jMatrix=8;break;
            case R.id.textView987:iMatrix=9;jMatrix=8;break;
            case R.id.textView988:iMatrix=9;jMatrix=8;break;
            case R.id.textView989:iMatrix=9;jMatrix=8;break;

            case R.id.textView991:iMatrix=9;jMatrix=9;break;
            case R.id.textView992:iMatrix=9;jMatrix=9;break;
            case R.id.textView993:iMatrix=9;jMatrix=9;break;
            case R.id.textView994:iMatrix=9;jMatrix=9;break;
            case R.id.textView995:iMatrix=9;jMatrix=9;break;
            case R.id.textView996:iMatrix=9;jMatrix=9;break;
            case R.id.textView997:iMatrix=9;jMatrix=9;break;
            case R.id.textView998:iMatrix=9;jMatrix=9;break;
            case R.id.textView999:iMatrix=9;jMatrix=9;break;
        }
        checkReset(numbers,iMatrix,jMatrix);
        screenUpdate(m.auxMatrix,numbers);

        // здесь проверка выигрыша и ошибок игрока
        if (m.winnerCheck(m.auxMatrix,m.solvedMatrix) == true){
            message = "Это победа";showDialog(DIALOG_EXIT);
        }else{
            if (m.checkForEndGame(m.auxMatrix)){
                for(int i = 1; i <= 9; i++) {
                    for (int j = 1; j <= 9; j++) {
                        if (m.auxMatrix[i][j][0] != m.solvedMatrix[i][j]){
                            numbers[i][j].setBackgroundColor(Color.RED);
                        }
                    }
                }
            }
        }
    }
    public void buttonNumberClick(int buttonNumber
            ,int iMatrix
            ,int jMatrix
            ,int taskMatrix[][]
            ,int auxMatrix[][][]
    ){                                                          //реакция на нажатие кнопки с цифрой
        if (taskMatrix[iMatrix][jMatrix] == 0) {                //проверка на цифру известную из задания
            if(pencil == false){                                //работаем с основной матрицей
                if(auxMatrix[iMatrix][jMatrix][0] == -1){
                    auxMatrix[iMatrix][jMatrix][0] = buttonNumber;
                    m.elementAuxMatrixReset(iMatrix, jMatrix, auxMatrix);
                    m.removeButtonNumberFromAuxMatrix(iMatrix, jMatrix, buttonNumber, auxMatrix);
                    return;
                }
                if(auxMatrix[iMatrix][jMatrix][0] == 0){
                    auxMatrix[iMatrix][jMatrix][0] = buttonNumber;
                    m.removeButtonNumberFromAuxMatrix(iMatrix, jMatrix, buttonNumber, auxMatrix);
                    return;
                }
                if(auxMatrix[iMatrix][jMatrix][0] > 0){
                    if (auxMatrix[iMatrix][jMatrix][0] == buttonNumber){
                        auxMatrix[iMatrix][jMatrix][0] = 0;
                    }else {
                        auxMatrix[iMatrix][jMatrix][0] = buttonNumber;
                        m.removeButtonNumberFromAuxMatrix(iMatrix, jMatrix, buttonNumber, auxMatrix);
                    }
                }
            }else{                                                            //работаем с доп матрицей
                if (auxMatrix[iMatrix][jMatrix][0] == 0){auxMatrix[iMatrix][jMatrix][0]= -1;}
                if (auxMatrix[iMatrix][jMatrix][0] == -1){
                    if(auxMatrix[iMatrix][jMatrix][buttonNumber] == buttonNumber){
                        auxMatrix[iMatrix][jMatrix][buttonNumber] = 0;
                    }else auxMatrix[iMatrix][jMatrix][buttonNumber] = buttonNumber;
                }
            }
            int element=m.checkAuxMatrixForLastElement(iMatrix, jMatrix, auxMatrix);
            // проверка на последний элемент в доп матрице
            if (element > 0 && element != buttonNumber) {
                auxMatrix[iMatrix][jMatrix][0] = element;
                auxMatrix[iMatrix][jMatrix][element] = 0;
                m.removeButtonNumberFromAuxMatrix(iMatrix, jMatrix, element, auxMatrix);
            }
        }
    }
    public void checkReset(
            //Обработка выделения ячейки игрового поля
            TextView numbers[][],
            int iMatrix,
            int jMatrix
    ){
        for(int i=1;i<=9;i++){
            for (int j = 1; j <= 9; j++){
                if ((i == 1 || i == 2 || i == 3) && (j == 4 || j == 5 || j == 6)){
                    numbers[i][j].setBackgroundColor(getResources().getColor(R.color.backgroundGrayColor));
                    tables[i][j].setBackgroundColor(getResources().getColor(R.color.backgroundGrayColor));
                }else
                if ((i == 4 || i == 5 || i == 6) && (j == 1 || j == 2 || j == 3)){
                    numbers[i][j].setBackgroundColor(getResources().getColor(R.color.backgroundGrayColor));
                    tables[i][j].setBackgroundColor(getResources().getColor(R.color.backgroundGrayColor));
                } else
                if ((i == 4 || i == 5 || i == 6) && (j == 7 || j == 8 || j == 9)) {
                    numbers[i][j].setBackgroundColor(getResources().getColor(R.color.backgroundGrayColor));
                    tables[i][j].setBackgroundColor(getResources().getColor(R.color.backgroundGrayColor));
                }else
                if ((i == 7 || i == 8 || i == 9) && (j == 4 || j == 5 || j == 6)){
                    numbers[i][j].setBackgroundColor(getResources().getColor(R.color.backgroundGrayColor));
                    tables[i][j].setBackgroundColor(getResources().getColor(R.color.backgroundGrayColor));
                }else{
                    numbers[i][j].setBackgroundColor(getResources().getColor(R.color.backgroundLightColor));
                    tables[i][j].setBackgroundColor(getResources().getColor(R.color.backgroundLightColor));
                }
            }
        }
        numbers[iMatrix][jMatrix].setBackgroundColor(getResources().getColor(R.color.backgroundCheckedColor));
        tables[iMatrix][jMatrix].setBackgroundColor(getResources().getColor(R.color.backgroundCheckedColor));
    }

    public void screenUpdate(
            //обновление основных элементов игрового поля
            int auxMatrix[][][],
            TextView numbers[][]
    ){
        for(int i = 1; i <= 9; i++) {
            for (int j = 1; j <= 9; j++) {
                if (auxMatrix[i][j][0] == 0){                     //Ячейка пустая
                    numbers[i][j].setText("");
                    numbers[i][j].setVisibility(View.VISIBLE); tables[i][j].setVisibility(View.GONE);
                }                                               //есть дополнительная таблица
                if (auxMatrix[i][j][0] == -1 ){
                    numbers[i][j].setVisibility(View.GONE); tables[i][j].setVisibility(View.VISIBLE);
                }
                if (auxMatrix[i][j][0] > 0){                      //Ячейка заполнена
                    numbers[i][j].setText(Integer.toString(auxMatrix[i][j][0]));
                    numbers[i][j].setVisibility(View.VISIBLE); tables[i][j].setVisibility(View.GONE);
                }
            }
        }                                     // Заполнение EditText дополнительной таблицы
        for (int i = 1; i <= 9; i++) {
            for (int j = 1; j <= 9; j++) {
                for (int k = 1; k <= 9; k++) {
                    if (auxMatrix[i][j][0] == -1) {
                        if (auxMatrix[i][j][k] != 0)
                            auxNumbers[i][j][k].setText(Integer.toString(auxMatrix[i][j][k]));
                        else auxNumbers[i][j][k].setText("");
                    }
                }
            }
        }
    }

    public void setCheckedBold  (
            //Установка  шрифта задания в жирный
            int taskMatrix[][],
            TextView numbers[][]
    ){
        for(int i = 1; i <= 9; i++) {
            for (int j = 1; j <= 9; j++) {
                numbers[i][j].setTypeface(null, Typeface.NORMAL);
            }
        }
        for(int i = 1; i <= 9; i++) {
            for (int j = 1; j <= 9; j++) {
                if (taskMatrix[i][j] != 0) numbers[i][j].setTypeface(null, Typeface.BOLD);
            }
        }
    }


    @Override
    protected void onPrepareDialog(int id, Dialog dialog){
        super.onPrepareDialog(id,dialog);
    }
    @Override
    protected Dialog onCreateDialog (int id){
        if (id==DIALOG_EXIT){
            AlertDialog.Builder adb = new AlertDialog.Builder(this);
            adb.setTitle("Итоги игры");                                     //заголовок
            adb.setMessage(message);                                        //сообщение
            adb.setIcon(R.drawable.ic_launcher_background);                 //иконка
            adb.setPositiveButton("OK",myClickListener);               //кнопка да
            return adb.create();                                            //создаём диалог
        }
        return super.onCreateDialog(id);
    }
    DialogInterface.OnClickListener myClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case Dialog.BUTTON_POSITIVE: Intent intent =getIntent();finish();startActivity(intent);break;
                case Dialog.BUTTON_NEGATIVE:            finish();   break;
                case Dialog.BUTTON_NEUTRAL:                         break;
            }
        }
    };
    @Override
    public boolean onKeyDown(int keyKode, KeyEvent event){
        if (keyKode==KeyEvent.KEYCODE_BACK){
        }
        return true;
    }
    @Override
    protected void onStart(){
        Log.d(TAG,"onStart Game_Activity");super.onStart();
    }
    @Override
    protected void onRestart(){
        super.onRestart();
        String solvedMatrixString=File.readFile(this,"QS_solvedMatrix");
        m.fromString(solvedMatrixString, m.solvedMatrix);
        String matrixString=File.readFile(this,"QS_matrix");
        m.fromString(matrixString, m.matrix);
        String taskMatrixString=File.readFile(this,"QS_taskMatrix");
        m.fromString(taskMatrixString, m.taskMatrix);
        String auxMatrixString=File.readFile(this,"QS_auxMatrix");
        m.fromString(auxMatrixString, m.auxMatrix);
        Log.d(TAG,"onRestart Game_Activity");
    }
    @Override
    protected void onResume(){
        super.onResume();
        Log.d(TAG,"onResume Game_Activity");
    }
    @Override
    protected void onPause(){
        super.onPause();
        File.saveFile(this,"QS_solvedMatrix",m.toString(m.solvedMatrix));
        File.saveFile(this,"QS_matrix",m.toString(m.matrix));
        File.saveFile(this,"QS_taskMatrix",m.toString(m.taskMatrix));
        File.saveFile(this,"QS_auxMatrix",m.toString(m.auxMatrix));
        Log.d(TAG,"onPause Game_Activity");
    }
    @Override
    protected void onStop(){
        super.onStop();
        Log.d(TAG,"onStop Game_Activity");
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        File.saveFile(this,"QS_solvedMatrix",m.toString(m.solvedMatrix));
        File.saveFile(this,"QS_matrix",m.toString(m.matrix));
        File.saveFile(this,"QS_taskMatrix",m.toString(m.taskMatrix));
        File.saveFile(this,"QS_auxMatrix",m.toString(m.auxMatrix));
        Log.d(TAG,"onDestroy Game_Activity");
    }
}
