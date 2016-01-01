package com.example.user.ntuhappytogether;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class LoginActivity extends Activity {

    private TextView directEnter;
    private EditText account;
    private EditText password;
    private EditText email;
    private Button register;

    private static final String tag = "LoginActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        HashMap<String,String> query = new HashMap();
        query.put("title", "食飯");
//        ParseCloud.callFunctionInBackground("query", query, new FunctionCallback<ArrayList<ParseObject>>() {
//            public void done(ArrayList<ParseObject> result, ParseException e) {
//                if (e == null) {
//                    try {
//                        //JSONObject jsonObj = new JSONObject(result);
//                        for (ParseObject temp : result) {
//                            Log.i(tag, temp.getString("title"));
//                            //Log.i(tag,result.get("comment").toString());
//                        }
//
//
//                    } catch (Exception e1) {
//                        e1.printStackTrace();
//                    }
//                    Log.i(tag, result + ":hellofunction");
//
//                }
//            }
//        });
//===========
        HashMap<String,String> querytitle = new HashMap();
        querytitle.put("title", "食飯");
        ParseCloud.callFunctionInBackground("query_title", querytitle, new FunctionCallback<ArrayList<ParseObject>>() {
            public void done(ArrayList<ParseObject> result, ParseException e) {
                if (e == null) {
                    try {
                        Log.i(tag, "result.size()=" + result.size());
                        //JSONObject jsonObj = new JSONObject(result);
                        for (ParseObject temp : result) {

                            ParseUser user = ParseUser.getCurrentUser();
                            ParseRelation<ParseObject> relation = temp.getRelation("participant");
                            relation.add(user);
                            temp.saveInBackground();
                            Log.i(tag, "title:" + temp.getString("title"));
                            //Log.i(tag,result.get("comment").toString());
                        }


                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    Log.i(tag, result + ":hellofunction");

                }
            }
        });
//================
        HashMap<String,String> joinMap = new HashMap();
        joinMap.put("objectId","q7dcxdMi2D");
        ParseUser user = ParseUser.getCurrentUser();
//        joinMap.put("userId",user.getObjectId());
//        ParseCloud.callFunctionInBackground("join", joinMap, new FunctionCallback<String>() {
//            public void done(String result, ParseException e) {
//                if (e == null) {
//                    if(result == null){Log.i(tag,"result is null");}
//                    else Log.i(tag,"Join:"+result);
//
//
//
//                    //Log.i(tag, result + ":hellofunction");
//
//                }else{
//                    Log.i(tag, "join Exception:"+e.getMessage());
//                }
//            }
//        });
        HashMap<String,String> exitMap = new HashMap();
        exitMap.put("objectId","q7dcxdMi2D");
        exitMap.put("userId",user.getObjectId());
        ParseCloud.callFunctionInBackground("exit", exitMap, new FunctionCallback<String>() {
            public void done(String result, ParseException e) {
                if (e == null) {
                    if(result == null){Log.i(tag,"result is null");}
                    else Log.i(tag,"Exit:"+result);



                    //Log.i(tag, result + ":hellofunction");

                }
            }
        });
//=========================

        //=======

        ParseObject testObject = new ParseObject("Event");
        //testObject.put("name", "Jusnit");
        String s  = testObject.getObjectId();
        // Log.i("Event Object Id:", s);

        //testObject.setObjectId("yYq1c85QtH");
//        testObject.put("limit", 9);
//        testObject.put("title","召喚峽谷");
//        testObject.put("context","context");
//        testObject.saveInBackground();


//        JSONObject createObj = new JSONObject();
//        try{
//            createObj.put("title","titleAndroid");
//            createObj.put("context", "contextAndroid");
//            createObj.put("limit", "10");
//        }catch(Exception e){}
        //===================
//        HashMap<String,Object> create = new HashMap();
//        create.put("title","titleAndroid");
//        create.put("context", "contextAndroid");
//        create.put("limit", 10);
//        ParseCloud.callFunctionInBackground("create", create, new FunctionCallback<String>() {
//            public void done(String result, ParseException e) {
//                Log.i(tag,"create:"+result);
//                if (e == null) {
//                    Log.i(tag,"create:"+result);
//                }
//                else
//                    Log.i(tag, "create Exception:"+e.getMessage());
//            }
//        });
        //==========================
        setWidget();
    }

    private void setWidget(){
        this.directEnter = (TextView)findViewById(R.id.direct_enter);
        account = (EditText)findViewById(R.id.account);
        password = (EditText)findViewById(R.id.password);
        email = (EditText)findViewById(R.id.email);
        register = (Button)findViewById(R.id.register);
        directEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent().setClass(LoginActivity.this, Lobby.class));
                finish();
            }
        });

        register.setOnClickListener(new RegisterOnClickListener());

    }
    class RegisterOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v){
            ParseUser user = new ParseUser();
            user.setUsername(account.getText().toString());
            user.setPassword(password.getText().toString());
            user.setEmail(email.getText().toString());
            user.signUpInBackground(new SignUpCallback() {
                public void done(ParseException e) {
                    if (e == null) {
                        Log.i("Login", "register complete");
                    } else {
                        // Sign up didn't succeed. Look at the ParseException
                        // to figure out what went wrong
                    }
                }
            });
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
