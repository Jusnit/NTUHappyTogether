package ParseUtil;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.user.ntuhappytogether.Lobby;
import com.parse.FunctionCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
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

    public static void createEvent(String title,String context,int limit,String userId,String time,String type){
        HashMap<String,Object> create = new HashMap();
        create.put("title",title);
        create.put("context", context);
        create.put("limit", limit);
        create.put("userId", userId);
        create.put("time",time);
        create.put("type",type);
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

    public static void modifyEvent(String userId,String eventId,String newcontext){
        HashMap<String,String> modifyMap = new HashMap();
        modifyMap .put("userId", userId);
        modifyMap .put("eventId", eventId);
        modifyMap .put("newcontext", newcontext);
        ParseCloud.callFunctionInBackground("modify", modifyMap , new FunctionCallback<String>() {
            public void done(String result, ParseException e) {
                if (e == null) {
                    Log.i(tag, "Modify event:" + result);
                } else
                    Log.i(tag, "Modify Exception:" + e.getMessage());
            }
        });
    }

    public  ArrayList<ParseObject> queryType(String type){
        HashMap<String,String> querytitle = new HashMap();
        querytitle.put("type", type);
        final ArrayList<ParseObject> parseObjList = new ArrayList();
        Flag flag = new Flag();
        queryFunctionCallback qCallBack = new queryFunctionCallback(parseObjList,flag);

        ParseCloud.callFunctionInBackground("query_type", querytitle, qCallBack);
        while(!flag.queryFinished){
            //do nothing.
        }
        return parseObjList;
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

    public static void joinEvent (String userId,String eventId) {
        HashMap<String, String> joinMap = new HashMap();
        joinMap.put("eventId", eventId);
        joinMap.put("userId",userId);
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

    public static void exitEvent(String userId,String eventId) {
        HashMap<String, String> exitMap = new HashMap();
        exitMap.put("eventId", eventId);
        exitMap.put("userId", userId);
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

    public static void rateEvent(String userId,String eventId,int rate){
        HashMap<String, Object> rateMap = new HashMap();
        rateMap.put("eventId", eventId);
        rateMap.put("userId", userId);
        rateMap.put("rate",rate);
        ParseCloud.callFunctionInBackground("rate", rateMap, new FunctionCallback<String>() {
            public void done(String result, ParseException e) {
                if (e == null) {
                    if (result == null) {
                        Log.i(tag, "rate null");
                    } else
                        Log.i(tag, "Ratecomplete");


                }
            }
        });
    }

    public static void signUp(String name,String password,String email,String nickname){
        ParseUser user = new ParseUser();
        user.setUsername(name);
        user.setPassword(password);
        user.setEmail(email);
        user.put("nickname",nickname);
        user.put("rating",0);
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
        Log.i(tag, "登入名:" + name + ",密碼:" + password);
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

    public  ArrayList<ParseObject> getAlreadyJoinEvent(){
        final ArrayList<ParseObject> parseObjList = new ArrayList();
        Flag flag = new Flag();

        AlreadyJoinFunctionCallback<ParseUser> aCallBack = new AlreadyJoinFunctionCallback(parseObjList,flag);
//        ParseQuery<ParseUser> query = ParseUser.getQuery();

        //query.getInBackground(ParseUser.getCurrentUser().getObjectId(), aCallBack);
        HashMap<String,String> joinMap = new HashMap();
        joinMap.put("userId",ParseUser.getCurrentUser().getObjectId());
        ParseCloud.callFunctionInBackground("query_join", joinMap, aCallBack);
        while(!flag.queryFinished){
            //do nothing.
        }
        return parseObjList;
    }
    class AlreadyJoinFunctionCallback<T> implements FunctionCallback<T>{
        private ArrayList<ParseObject> objList;
        private Flag innerflag;
        public AlreadyJoinFunctionCallback(ArrayList<ParseObject> objList,Flag flag){
            this.objList = objList;
            this.innerflag = flag;
        }
        public void done(T result, ParseException e) {
            if (e == null) {
                try {
                    this.innerflag.queryFinished = true;
                    //ParseRelation<ParseObject> relation = (result).getRelation("joinEvent");
                    objList.addAll((ArrayList<ParseObject>)result);
                    Log.i(tag, "My joinEvent size=" + ((ArrayList<ParseObject>) result).size());

                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
            else{
                Log.i(tag,"query Exception"+e.toString());
            }
        }
    }

    public  ArrayList<ParseObject> getMyEvent(){
        final ArrayList<ParseObject> parseObjList = new ArrayList();
        Flag flag = new Flag();
        MyEventFunctionCallback<ParseUser> mCallBack = new MyEventFunctionCallback(parseObjList,flag);
//        ParseQuery<ParseUser> query = ParseUser.getQuery();
//        query.getInBackground(ParseUser.getCurrentUser().getObjectId(), mCallBack);
        HashMap<String,String> hostMap = new HashMap();
        hostMap.put("userId",ParseUser.getCurrentUser().getObjectId());
        ParseCloud.callFunctionInBackground("query_host", hostMap, mCallBack);
        while(!flag.queryFinished){
            //do nothing.
        }
        return parseObjList;
    }
    class MyEventFunctionCallback<T> implements FunctionCallback<T>{
        private ArrayList<ParseObject> objList;
        private Flag innerflag;
        public MyEventFunctionCallback(ArrayList<ParseObject> objList,Flag flag){
            this.objList = objList;
            this.innerflag = flag;
        }
        public void done(T result, ParseException e) {
            if (e == null) {
                try {
                    this.innerflag.queryFinished = true;
//                    ParseRelation<ParseObject> relation = (result).getRelation("hostEvent");
                    objList.addAll((ArrayList<ParseObject>)result);
                    Log.i(tag, "My hostEvent size=" + ((ArrayList<ParseObject>) result).size());

                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
            else{
                Log.i(tag,"hostEventQuery Exception"+e.toString());
            }
        }
    }


}
