package ParseUtil;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.user.ntuhappytogether.Lobby;
import com.parse.FunctionCallback;
import com.parse.LogInCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.ArrayList;
import java.util.HashMap;

import loginregister.LoginActivity;

/**
 * Created by user on 2016/1/1.
 */
public class ParseFunction {
    private static final String tag = "ParseFunction";

    public static void createEvent(String title,String context,int limit,String userId){
        HashMap<String,Object> create = new HashMap();
        create.put("title",title);
        create.put("context", context);
        create.put("limit", limit);
        create.put("userId", userId);
        ParseCloud.callFunctionInBackground("create", create, new FunctionCallback<String>() {
            public void done(String result, ParseException e) {
                if (e == null) {
                    Log.i(tag,"create:"+result);
                }
                else
                    Log.i(tag, "create Exception:"+e.getMessage());
            }
        });
    }

    public static void cancelEvent(String userId,String eventId){
        HashMap<String,String> cancelMap = new HashMap();
        cancelMap.put("userId",userId);
        cancelMap.put("eventId",eventId);
        ParseCloud.callFunctionInBackground("cancel", cancelMap, new FunctionCallback<String>() {
            public void done(String result, ParseException e) {
                if (e == null) {
                    Log.i(tag, "cancel:" + result);
                } else
                    Log.i(tag, "cancel Exception:" + e.getMessage());
            }
        });
    }

    public  ArrayList<ParseObject> queryEvent(String title){
        HashMap<String,String> querytitle = new HashMap();
        querytitle.put("title", title);
        final ArrayList<ParseObject> parseObjList = new ArrayList();
        Flag flag = new Flag();
        queryFunctionCallback qCallBack = new queryFunctionCallback(parseObjList,flag);

        ParseCloud.callFunctionInBackground("query_title", querytitle, qCallBack);
        while(!flag.queryFinished){
            //do nothing.
        }
        return parseObjList;
    }
    class Flag{
        boolean queryFinished = false;
    }
    class queryFunctionCallback<T> implements FunctionCallback<T>{
        private ArrayList<ParseObject> objList;
        private Flag innerflag;
        public queryFunctionCallback(ArrayList<ParseObject> objList,Flag flag){
            this.objList = objList;
            this.innerflag = flag;
        }
        public void done(T result, ParseException e) {
            if (e == null) {
                try {
                    this.innerflag.queryFinished = true;
                    objList.addAll((ArrayList<ParseObject>)result);
                    Log.i(tag, "result.size()=" + ((ArrayList<ParseObject>)result).size());
                    //JSONObject jsonObj = new JSONObject(result);
                    for (ParseObject temp : (ArrayList<ParseObject>)result) {
//                        ParseUser user = ParseUser.getCurrentUser();
//                        ParseRelation<ParseObject> relation = temp.getRelation("participant");
//                        relation.add(user);
//                        temp.saveInBackground();
                        Log.i(tag, "title:" + temp.getString("title"));
                        //Log.i(tag,result.get("comment").toString());
                    }


                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
            else{
                Log.i(tag,"query Exception"+e.toString());
            }
        }
    }

    public static void joinEvent (ParseUser user,String eventId) {
        HashMap<String, String> joinMap = new HashMap();
        joinMap.put("objectId", eventId);
        joinMap.put("userId",user.getObjectId());
        ParseCloud.callFunctionInBackground("join", joinMap, new FunctionCallback<String>() {
            public void done(String result, ParseException e) {
                if (e == null) {
                    if(result == null){Log.i(tag,"result is null");}
                    else {
                        Log.i(tag, "Join:" + result);
                    }

                }else{
                    Log.i(tag, "join Exception:"+e.getMessage());
                }
            }
        });
    }

    public static void exitEvent(ParseUser user,String eventId) {
        HashMap<String, String> exitMap = new HashMap();
        exitMap.put("objectId", eventId);
        exitMap.put("userId", user.getObjectId());
        ParseCloud.callFunctionInBackground("exit", exitMap, new FunctionCallback<String>() {
            public void done(String result, ParseException e) {
                if (e == null) {
                    if (result == null) {
                        Log.i(tag, "result is null");
                    } else
                        Log.i(tag, "Exit:" + result);


                }
            }
        });
    }

    public static void signUp(String name,String password,String email){
        ParseUser user = new ParseUser();
        user.setUsername(name);
        user.setPassword(password);
        user.setEmail(email);
        user.put("InstallationId", ParseInstallation.getCurrentInstallation().getObjectId());
        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    Log.i("signUp:", "register complete");
                } else {
                    Log.i("signUp fail:", e.toString());
                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong
                }
            }
        });
    }

    public static void login(String name, String password,final Activity activity) {
        Log.i(tag, "登入名:"+name+",密碼:"+password);
        ParseUser.logInInBackground(name, password, new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    user.put("InstallationId", ParseInstallation.getCurrentInstallation().getObjectId());
                    user.saveInBackground();
                    // Hooray! The user is logged in.
                    Log.i(tag, "login:" + "login success");
                    activity.startActivity(new Intent().setClass(activity, Lobby.class));
                    Log.i(tag, "user login後切換畫面");

                } else {
                    Log.i(tag, "login:" + e.toString());
                    Toast t1 = Toast.makeText(activity, "帳號或密碼錯誤!", Toast.LENGTH_SHORT);
                    t1.show();
                }
            }
        });
    }

    public static void logout(){
        ParseUser user = ParseUser.getCurrentUser();
        user.put("InstallationId", "None");
        user.saveInBackground();
        ParseUser.logOut();
    }
}
