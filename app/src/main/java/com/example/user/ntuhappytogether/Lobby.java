package com.example.user.ntuhappytogether;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import ParseUtil.ParseFunction;
import loginregister.TextValidation;

public class Lobby extends Activity {

    private ImageView createEventView;
    private ImageView joinEventView;
    private static final String tag ="Lobby";

    private int myYear,myMonth,myDay;
    private String[] years ,months,days,hours,mins;

    //spinner
    private Spinner yspin;
    private Spinner mspin;
    private Spinner dspin;
    private Spinner hspin;
    private Spinner minspin;
    private Handler handler;
    private View item ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_lobby);
//        handler = new Handler(){
//            @Override
//            public void handleMessage(Message e){
//
//                switch(e.what){
//                    case 1:
//                        Log.i(tag,"handler");
//                        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(Lobby.this,android.R.layout.simple_spinner_item,years);
//                        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                        yspin.setAdapter(adp1);
//                        break;
//                    case 2:
//                        break;
//                    case 3:
//                        break;
//                    case 4:
//                        break;
//                    case 5:
//                        break;
//
//                }
//            }
//        };
        item = LayoutInflater.from(Lobby.this).inflate(R.layout.create_event_dialog, null);
        yspin = (Spinner)item.findViewById(R.id.year_spinner);
        mspin = (Spinner)item.findViewById(R.id.month_spinner);
        dspin = (Spinner)item.findViewById(R.id.day_spinner);
        hspin = (Spinner)item.findViewById(R.id.hour_spinner);
        minspin = (Spinner)item.findViewById(R.id.min_spinner);
//        new Thread(){
//            @Override
//            public void run(){
//                initialize();
//            }
//        }.start();
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
//        Message m1 = Message.obtain(handler);
//        yspin = (Spinner)item.findViewById(R.id.year_spinner);
//        mspin = (Spinner)item.findViewById(R.id.month_spinner);
//        dspin = (Spinner)item.findViewById(R.id.day_spinner);
//        hspin = (Spinner)item.findViewById(R.id.hour_spinner);
//        minspin = (Spinner)item.findViewById(R.id.min_spinner);
        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(Lobby.this,android.R.layout.simple_spinner_item,years);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //m1.obj = adp1;m1.what = 1;handler.sendMessage(m1);
        yspin.setAdapter(adp1);

        ArrayAdapter<String> adp2 = new ArrayAdapter<String>(Lobby.this,android.R.layout.simple_spinner_item,months);
        adp2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mspin.setAdapter(adp2);

        ArrayAdapter<String> adp3 = new ArrayAdapter<String>(Lobby.this,android.R.layout.simple_spinner_item,days);
        adp3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dspin.setAdapter(adp3);
        ArrayAdapter<String> adp4 = new ArrayAdapter<String>(Lobby.this,android.R.layout.simple_spinner_item,hours);
        adp4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hspin.setAdapter(adp4);
        ArrayAdapter<String> adp5 = new ArrayAdapter<String>(Lobby.this,android.R.layout.simple_spinner_item,mins);
        adp5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        minspin.setAdapter(adp5);
    }
    private void setWidget(){
        createEventView = (ImageView)findViewById(R.id.create_event);
        createEventView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //final View item2 = LayoutInflater.from(Lobby.this).inflate(R.layout.create_event_dialog, null);
//                Spinner yspin = (Spinner)item.findViewById(R.id.year_spinner);
//                ArrayAdapter<String> adp1 = new ArrayAdapter<String>(Lobby.this,android.R.layout.simple_spinner_item,years);
//                adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                yspin.setAdapter(adp1);
//                Spinner mspin = (Spinner)item.findViewById(R.id.month_spinner);
//                ArrayAdapter<String> adp2 = new ArrayAdapter<String>(Lobby.this,android.R.layout.simple_spinner_item,months);
//                adp2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                mspin.setAdapter(adp2);
//                Spinner dspin = (Spinner)item.findViewById(R.id.day_spinner);
//                ArrayAdapter<String> adp3 = new ArrayAdapter<String>(Lobby.this,android.R.layout.simple_spinner_item,days);
//                adp3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                dspin.setAdapter(adp3);
//                Spinner hspin = (Spinner)item.findViewById(R.id.hour_spinner);
//                ArrayAdapter<String> adp4 = new ArrayAdapter<String>(Lobby.this,android.R.layout.simple_spinner_item,hours);
//                adp4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                hspin.setAdapter(adp4);
//                Spinner minspin = (Spinner)item.findViewById(R.id.min_spinner);
//                ArrayAdapter<String> adp5 = new ArrayAdapter<String>(Lobby.this,android.R.layout.simple_spinner_item,mins);
//                adp5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                minspin.setAdapter(adp5);
                if(item.getParent()!=null){((ViewGroup)item.getParent()).removeView(item);}
                AlertDialog dialog = new AlertDialog.Builder(Lobby.this, R.style.FullHeightDialog)
                        .setView(item)
                        .setPositiveButton(R.string.create, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                EditText title = (EditText) item.findViewById(R.id.create_title);
                                EditText limit = (EditText) item.findViewById(R.id.create_limit);
                                EditText context = (EditText) item.findViewById(R.id.context_edit);
                                if (title.getText().toString() == null || title.getText().toString().length() < 1) {
                                    Toast t1 = Toast.makeText(Lobby.this, "請輸入活動名稱", Toast.LENGTH_LONG);
                                    t1.show();
                                    //dialog.dismiss();
                                    return;
                                }
                                try {
                                    if (limit.getText().toString() == null) {
                                        Toast t1 = Toast.makeText(Lobby.this, "請輸入需求人數", Toast.LENGTH_LONG);
                                        t1.show();
                                        //dialog.dismiss();
                                        return;
                                    }
                                } catch (Exception e) {
                                    Toast t1 = Toast.makeText(Lobby.this, "需求人數須為數字", Toast.LENGTH_LONG);
                                    t1.show();
                                   // dialog.dismiss();
                                    return;
                                }
                                if (context.getText().toString() == null) {
                                    Toast t1 = Toast.makeText(Lobby.this, "請輸入活動敘述", Toast.LENGTH_LONG);
                                    t1.show();
                                   // dialog.dismiss();
                                    return;
                                }
                                GregorianCalendar date = new GregorianCalendar(Integer.valueOf(yspin.getSelectedItem().toString()),Integer.valueOf(mspin.getSelectedItem().toString()),
                                        Integer.valueOf(dspin.getSelectedItem().toString()), Integer.valueOf(hspin.getSelectedItem().toString())
                                        ,Integer.valueOf(minspin.getSelectedItem().toString()));
                                GregorianCalendar date2 = new GregorianCalendar(Integer.valueOf(yspin.getSelectedItem().toString()),Integer.valueOf(mspin.getSelectedItem().toString()),
                                        Integer.valueOf(dspin.getSelectedItem().toString()), Integer.valueOf(hspin.getSelectedItem().toString())
                                        ,Integer.valueOf(minspin.getSelectedItem().toString()));
                                if(date.before(new GregorianCalendar())){
                                    Toast t1 = Toast.makeText(Lobby.this, "時間已過，請重選時間!", Toast.LENGTH_LONG);
                                    t1.show();
                                    Log.i(tag, "my create year" + yspin.getSelectedItem().toString());
                                    Log.i(tag,"create time:"+date.toString());
                                    Log.i(tag, "now time:" + new GregorianCalendar().toString());
                                    return;
                                }
                                Log.i(tag,"year:"+yspin.getSelectedItem().toString());
                                Log.i(tag,"month:"+mspin.getSelectedItem().toString());
                                Log.i(tag,"day:"+dspin.getSelectedItem().toString());
                                Log.i(tag,"hour:"+hspin.getSelectedItem().toString());
                                Log.i(tag,"min:"+minspin.getSelectedItem().toString());
                                Log.i(tag,"create time:"+date.toString());


                                Log.i(tag,"create time:"+date.toString());
                                Log.i(tag,"重打create time:"+date2.toString());
                                Log.i(tag,"now time:"+new GregorianCalendar().toString());
                                Log.i(tag,"now time Calendar:"+Calendar.getInstance().toString());


                                //  yyyy/mm/dd hh:mm
                                //String month = mspin.getSelectedItem().toString()
                                String time = yspin.getSelectedItem().toString()+"/"+mspin.getSelectedItem().toString()
                                        +"/"+dspin.getSelectedItem().toString()+" "+hspin.getSelectedItem().toString()
                                        +":"+minspin.getSelectedItem().toString();

                                ParseFunction.createEvent(title.getText().toString(), context.getText().toString()
                                        , Integer.valueOf(limit.getText().toString()), ParseUser.getCurrentUser().getObjectId(),time);

                            }
                        })
                        .show();

//
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
