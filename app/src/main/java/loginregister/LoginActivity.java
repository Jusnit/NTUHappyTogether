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
import account.AccountBuilder;
import account.NormalAccountBuilder;

public class LoginActivity extends Activity {

    private Register register;
    private TextView registerText,loginText,logoutText;


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

        registerText = (TextView)findViewById(R.id.registerText);
        loginText = (TextView)findViewById(R.id.login_text);
        logoutText = (TextView)findViewById(R.id.logoutText);
        AccountBuilder builder = new NormalAccountBuilder();
        registerText.setOnClickListener(new RegisterTextOnClickListener(builder));
        loginText.setOnClickListener(new LoginTextOnClickListener());
        logoutText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ParseUser.getCurrentUser() == null){
                    Toast t = Toast.makeText(LoginActivity.this,"尚未登入!",Toast.LENGTH_SHORT);
                    t.show();
                }
                else {
                    ParseUser.logOut();
                    Toast t = Toast.makeText(LoginActivity.this,"已登出!",Toast.LENGTH_SHORT);
                    t.show();

                }
            }
        });
        register = new Register();


    }
//
    private boolean checkInfo(String text,TextValidation textValidation){
        if(!textValidation.checkValidity(text))
            return false;
        return true;
    }

    class RegisterTextOnClickListener implements View.OnClickListener{
        private AccountBuilder builder;
        RegisterTextOnClickListener(AccountBuilder builder){
            this.builder = builder;
        }
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
                            EditText nickname = (EditText)item.findViewById(R.id.nickname);

                           // TextValidation textValidation = new TextValidation();
                            Log.i(tag,account.getText().toString());
                            Log.i(tag,password.getText().toString());
                            Log.i(tag,email.getText().toString());
                            if(!checkInfo(account.getText().toString(), new AccountValidation(LoginActivity.this)))
                                return;
                            if(!checkInfo(nickname.getText().toString(),new NicknameValidation(LoginActivity.this)))
                                return;
                            if(!checkInfo(password.getText().toString(),new PasswordValidation(LoginActivity.this)))
                                return;
                            if(!checkInfo(email.getText().toString(),new EmailValidation(LoginActivity.this)))
                                return;
//                            if(!textValidation.checkValidity(account.getText().toString())) {
//                                Toast t1 = Toast.makeText(LoginActivity.this, "帳號長度錯誤!", Toast.LENGTH_SHORT);
//                                t1.show();
//                                return;
//                            }
//                            if(!textValidation.checkValidity(nickname.getText().toString())) {
//                                Toast t1 = Toast.makeText(LoginActivity.this, "暱稱輸入有誤!", Toast.LENGTH_SHORT);
//                                t1.show();
//                                return;
//                            }
//                            if(!textValidation.checkValidity(password.getText().toString())) {
//                                Toast t1 = Toast.makeText(LoginActivity.this, "密碼長度錯誤!", Toast.LENGTH_SHORT);
//                                t1.show();
//                                return;
//                            }
//                            if(!textValidation.isEmailValid(email.getText().toString())){
//                                Toast t1 = Toast.makeText(LoginActivity.this, "Email格式錯誤!\n請使用ntu信箱", Toast.LENGTH_SHORT);
//                                t1.show();
//                                Log.i(tag, "Email格式錯誤!");
//                                return;
//                            }
//                            ParseFunction.signUp(account.getText().toString(),password.getText().toString()
//                                    ,email.getText().toString());
                            builder.buildAccount(account.getText().toString(),password.getText().toString()
                                    ,email.getText().toString(),nickname.getText().toString());
                        }
                    })
                    .show();
        }
    }

    class LoginTextOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v){
            if(ParseUser.getCurrentUser() != null){
                startActivity(new Intent().setClass(LoginActivity.this, Lobby.class));
                Log.i(tag, "現有user login");
                return;
            }

            final View item = LayoutInflater.from(LoginActivity.this).inflate(R.layout.login_dialog, null);
            new AlertDialog.Builder(LoginActivity.this)
                    .setTitle(R.string.login)
                    .setView(item)
                    .setPositiveButton(R.string.login, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            EditText account = (EditText)item.findViewById(R.id.login_account);
                            EditText password = (EditText)item.findViewById(R.id.login_password);
                            //TextValidation textValidation = new TextValidation();
                            Log.i(tag,account.getText().toString());
                            Log.i(tag,password.getText().toString());
                            if(!checkInfo(account.getText().toString(), new AccountValidation(LoginActivity.this)))
                                return;
                            if(!checkInfo(password.getText().toString(),new PasswordValidation(LoginActivity.this)))
                                return;

//                            if(!textValidation.checkValidity(account.getText().toString())) {
//                                Toast t1 = Toast.makeText(LoginActivity.this, "帳號長度錯誤!", Toast.LENGTH_SHORT);
//                                t1.show();
//                                return;
//                            }
//                            if(!textValidation.checkValidity(password.getText().toString())) {
//                                Toast t1 = Toast.makeText(LoginActivity.this, "密碼長度錯誤!", Toast.LENGTH_SHORT);
//                                t1.show();
//                                return;
//                            }

                            ParseFunction.login(account.getText().toString(), password.getText().toString(),LoginActivity.this);
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
