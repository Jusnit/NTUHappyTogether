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
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import org.json.JSONObject;

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
        ParseCloud.callFunctionInBackground("hello", new HashMap<String, Object>(), new FunctionCallback<String>() {
            public void done(String result, ParseException e) {
                if (e == null) {
                    Log.i(tag, result);
                }
            }
        });
        HashMap<String,String> create = new HashMap<String,String>();
        create.put("title","titleAndroid");
        create.put("context", "contextAndroid");
        create.put("limit", "10");

        JSONObject createObj = new JSONObject();
        try{
            createObj.put("title","titleAndroid");
            createObj.put("context", "contextAndroid");
            createObj.put("limit", "10");
        }catch(Exception e){}
        ParseCloud.callFunctionInBackground("create", create, new FunctionCallback<String>() {
            public void done(String result, ParseException e) {
                if (e == null) {
                    Log.i(tag,result);
                }
                else
                    Log.i(tag, e.getMessage());
            }
        });
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
                startActivity(new Intent().setClass(LoginActivity.this,Lobby.class));
                finish();
            }
        });

        register.setOnClickListener(new RegisterOnClickListener() );

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
