package loginregister;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.ntuhappytogether.Lobby;
import com.example.user.ntuhappytogether.R;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.HashMap;

import ParseUtil.ParseFunction;

public class LoginActivity extends Activity {

    private TextValidation textValidation;
    private Register register;
    private TextView directEnter,registerText,loginText;


    private static final String tag = "LoginActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        HashMap<String,String> query = new HashMap();

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
//

        setWidget();
    }

    private void setWidget(){
        this.directEnter = (TextView)findViewById(R.id.direct_enter);
        registerText = (TextView)findViewById(R.id.registerText);
        loginText = (TextView)findViewById(R.id.login_text);
        directEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent().setClass(LoginActivity.this, Lobby.class));
                finish();
            }
        });
        registerText.setOnClickListener(new RegisterTextOnClickListener());

       // registerButton.setOnClickListener(new RegisterOnClickListener());
        register = new Register();
        textValidation = new TextValidation();

    }
//

    class RegisterTextOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v){

            final View item = LayoutInflater.from(LoginActivity.this).inflate(R.layout.register_dialog, null);
            new AlertDialog.Builder(LoginActivity.this)
                    .setTitle(R.string.register)
                    .setView(item)
                    .setPositiveButton(R.string.sendregister, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            EditText account = (EditText)item.findViewById(R.id.account);
                            EditText password = (EditText)item.findViewById(R.id.password);
                            EditText email = (EditText)item.findViewById(R.id.email);
                            TextValidation textValidation = new TextValidation();
                            Log.i(tag,account.getText().toString());
                            Log.i(tag,password.getText().toString());
                            Log.i(tag,email.getText().toString());
                            if(!textValidation.checkValidity(account.getText().toString())) {
                                Toast t1 = Toast.makeText(LoginActivity.this, "帳號長度錯誤!", Toast.LENGTH_SHORT);
                                t1.show();
                                return;
                            }
                            if(!textValidation.checkValidity(password.getText().toString())) {
                                Toast t1 = Toast.makeText(LoginActivity.this, "密碼長度錯誤!", Toast.LENGTH_SHORT);
                                t1.show();
                                return;
                            }
                            if(!textValidation.isEmailValid(email.getText().toString())){
                                Toast t1 = Toast.makeText(LoginActivity.this, "Email格式錯誤!\n請使用ntu信箱", Toast.LENGTH_SHORT);
                                t1.show();
                                Log.i(tag, "Email格式錯誤!");
                                return;
                            }
                            ParseFunction.signUp(account.getText().toString(),password.getText().toString()
                                    ,email.getText().toString());
                        }
                    })
                    .show();
        }
    }

    class LoginTextOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v){

            final View item = LayoutInflater.from(LoginActivity.this).inflate(R.layout.login_dialog, null);
            new AlertDialog.Builder(LoginActivity.this)
                    .setTitle(R.string.login)
                    .setView(item)
                    .setPositiveButton(R.string.login, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            EditText account = (EditText)item.findViewById(R.id.login_account);
                            EditText password = (EditText)item.findViewById(R.id.login_password);
                            TextValidation textValidation = new TextValidation();
                            Log.i(tag,account.getText().toString());
                            Log.i(tag,password.getText().toString());
                            if(!textValidation.checkValidity(account.getText().toString())) {
                                Toast t1 = Toast.makeText(LoginActivity.this, "帳號長度錯誤!", Toast.LENGTH_SHORT);
                                t1.show();
                                return;
                            }
                            if(!textValidation.checkValidity(password.getText().toString())) {
                                Toast t1 = Toast.makeText(LoginActivity.this, "密碼長度錯誤!", Toast.LENGTH_SHORT);
                                t1.show();
                                return;
                            }

                            ParseFunction.login(account.getText().toString(),password.getText().toString());
                        }
                    })
                    .show();
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
