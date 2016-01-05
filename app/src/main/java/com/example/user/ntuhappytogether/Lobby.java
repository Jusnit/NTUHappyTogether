package com.example.user.ntuhappytogether;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseUser;

import ParseUtil.ParseFunction;
import loginregister.TextValidation;

public class Lobby extends Activity {

    private ImageView createEventView;
    private ImageView joinEventView;
    private static final String tag ="Lobby";

    private int myYear,myMonth,myDay;
    private String[] years ,months,days,hours,mins;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_lobby);
        ParseFunction.exitEvent(ParseUser.getCurrentUser().getObjectId(), "lk4LeiWUWP");
        initialize();
        setWidget();
    }

    private void initialize(){
        years = new  String[2];years[0] = "2016";years[1] = "2017";
        months = new String[12];
        for(int i = 0;i < 12;i++){
            months[i] = String.valueOf(i+1);
        }
        days = new String[30];
        for(int i = 0;i < 30;i++){
            days[i] = String.valueOf(i+1);
        }
        hours = new String[24];
        for(int i = 0;i < 24;i++){
            hours[i] = String.valueOf(i);
        }
        mins = new String[60];
        for(int i = 0;i < 60;i++){
            mins[i] = String.valueOf(i);
        }
    }
    private void setWidget(){
        createEventView = (ImageView)findViewById(R.id.create_event);
        createEventView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View item = LayoutInflater.from(Lobby.this).inflate(R.layout.create_event_dialog, null);
                Spinner yspin = (Spinner)item.findViewById(R.id.year_spinner);
                ArrayAdapter<String> adp1 = new ArrayAdapter<String>(Lobby.this,android.R.layout.simple_spinner_item,years);
                adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                yspin.setAdapter(adp1);
                Spinner mspin = (Spinner)item.findViewById(R.id.month_spinner);
                ArrayAdapter<String> adp2 = new ArrayAdapter<String>(Lobby.this,android.R.layout.simple_spinner_item,months);
                adp2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mspin.setAdapter(adp2);
                Spinner dspin = (Spinner)item.findViewById(R.id.day_spinner);
                ArrayAdapter<String> adp3 = new ArrayAdapter<String>(Lobby.this,android.R.layout.simple_spinner_item,days);
                adp3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                dspin.setAdapter(adp3);
                Spinner hspin = (Spinner)item.findViewById(R.id.hour_spinner);
                ArrayAdapter<String> adp4 = new ArrayAdapter<String>(Lobby.this,android.R.layout.simple_spinner_item,hours);
                adp4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                hspin.setAdapter(adp4);
                Spinner minspin = (Spinner)item.findViewById(R.id.min_spinner);
                ArrayAdapter<String> adp5 = new ArrayAdapter<String>(Lobby.this,android.R.layout.simple_spinner_item,mins);
                adp5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                minspin.setAdapter(adp5);

                AlertDialog dialog = new AlertDialog.Builder(Lobby.this, R.style.FullHeightDialog)
                        .setView(item)
                        .setPositiveButton(R.string.create, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                EditText title = (EditText) item.findViewById(R.id.create_title);
                                EditText limit = (EditText) item.findViewById(R.id.create_limit);
                                EditText context = (EditText) item.findViewById(R.id.context_edit);
                                if (title.getText().toString() == null || title.getText().toString().length() < 1) {
                                    Toast t1 = Toast.makeText(Lobby.this, "請輸入活動名稱", Toast.LENGTH_SHORT);
                                    t1.show();
                                    return;
                                }
                                try {
                                    if (limit.getText().toString() == null) {
                                        Toast t1 = Toast.makeText(Lobby.this, "請輸入需求人數", Toast.LENGTH_SHORT);
                                        t1.show();
                                        return;
                                    }
                                } catch (Exception e) {
                                    Toast t1 = Toast.makeText(Lobby.this, "需求人數須為數字", Toast.LENGTH_SHORT);
                                    t1.show();
                                    return;
                                }
                                if (context.getText().toString() == null) {
                                    Toast t1 = Toast.makeText(Lobby.this, "請輸入活動敘述", Toast.LENGTH_SHORT);
                                    t1.show();
                                    return;
                                }
                                ParseFunction.createEvent(title.getText().toString(), context.getText().toString()
                                        , Integer.valueOf(limit.getText().toString()), ParseUser.getCurrentUser().getObjectId());
                            }
                        })
                        .show();

//                dialog.dismiss();
//                WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
//                params.width = WindowManager.LayoutParams.MATCH_PARENT;
//                params.height = WindowManager.LayoutParams.MATCH_PARENT ;
//                dialog.getWindow().setAttributes(params);
//                dialog.show();
            }
        });
        joinEventView = (ImageView)findViewById(R.id.join_event);
        joinEventView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent().setClass(Lobby.this,EventController.class));

            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lobby, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
